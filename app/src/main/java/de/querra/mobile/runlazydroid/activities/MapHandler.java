package de.querra.mobile.runlazydroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;
import de.querra.mobile.runlazydroid.data.RealmOperator;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.entities.RunType;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.ImageHelper;
import de.querra.mobile.runlazydroid.helper.MathHelper;

public class MapHandler {

    @Inject
    Formatter formatter;
    @Inject
    ImageHelper imageHelper;
    @Inject
    MathHelper mathHelper;

    private SupportMapFragment mapFragment;
    private Activity activity;
    private GoogleMap        mMap;
    private GoogleApiClient  googleApiClient;
    public  boolean          connected;
    public  LocationListener locationListener;
    private LocationRequest  locationRequest;
    private long             startTrackTime;
    private long             stopTrackTime;
    private View             displayContainer;
    private TextView         displayTime;
    private TextView         displayDistance;
    private List<Location> locations     = new ArrayList<>();
    private boolean        isTracking    = false;
    private float          distance      = 0f;
    private Handler        timerHandler  = new Handler();
    private Runnable       timerRunnable;

    public MapHandler(SupportMapFragment mapFragment, View displayContainer, Activity activity) {
        RunLazyDroidApplication.getAppComponent().inject(this);
        this.timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (displayTime != null && displayTime.getText() != null) {
                    displayTime.setText(formatter.millisToTimeString(new Date().getTime() - startTrackTime));
                }
                timerHandler.postDelayed(this, 1000);
            }
        };
        this.displayContainer = displayContainer;
        this.activity = activity;
        this.mapFragment = mapFragment;
    }

    public void onStartTracking() {
        // save tracking status for fab
        this.isTracking = true;
        // set start time
        this.startTrackTime = new Date().getTime();
        // initialize the display
        this.displayTime = (TextView) this.displayContainer.findViewById(R.id.activity_maps__time);
        this.displayDistance = (TextView) this.displayContainer.findViewById(R.id.activity_maps__distance);
        this.displayContainer.setVisibility(View.VISIBLE);
        this.displayDistance.setText(String.format(Locale.getDefault(), "%.2f km", this.distance / 1000));
        // start counter
        this.timerHandler.post(this.timerRunnable);
    }

    public void onStopTracking() {
        // save tracking status for fab
        this.isTracking = false;
        // set stop time
        this.stopTrackTime = new Date().getTime();
        // hide display
        this.displayContainer.setVisibility(View.INVISIBLE);
        // stop timer
        this.timerHandler.removeCallbacks(this.timerRunnable);
        // stop receiving location updates
        LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
        if (locations.size() != 0) {
            mMap.addCircle(getCircle(locations.get(locations.size() - 1)));
        }
    }

    public void initialize() {
        if (this.googleApiClient == null) {
            this.googleApiClient = new GoogleApiClient.Builder(this.activity)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            connected = true;
                            prepareMap();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            connected = false;
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            //Let google handle this
                            GoogleApiAvailability.getInstance().getErrorDialog(activity, connectionResult.getErrorCode(), 0);
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
        if (!this.googleApiClient.isConnected() || !this.googleApiClient.isConnecting()){
            this.googleApiClient.connect();
        }
    }

    public void handleFabClick(final FloatingActionButton view) {
        if (!isTracking) {
            if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        this.googleApiClient);
                if (lastLocation != null) {
                    moveCamera(lastLocation);
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                    mMap.addCircle(getCircle(lastLocation));
                    locations.add(lastLocation);
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, getLocationRequest(), getLocationListener());
            }
            onStartTracking();
            view.setImageDrawable(this.activity.getResources().getDrawable(R.drawable.ic_menu));
        } else {
            Snackbar.make(this.activity.findViewById(R.id.activity_main__main_layout), R.string.stop_tracking_question, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.stop_tracking, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onStopTracking();
                            view.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_start_tracking));
                            new AlertDialog.Builder(activity)
                                    .setTitle(R.string.add_entry)
                                    .setMessage(R.string.add_run_question)
                                    .setCancelable(false)
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            resetData();
                                        }
                                    })
                                    .setPositiveButton(R.string.add_entry, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            addToDataset();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    })
                    .show();


        }
    }

    public void prepareMap() {
        this.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // TODO
                // Add a marker in Sydney and move the camera
                LatLng location;
                if (locations.size() != 0) {
                    location = getLatLng(locations.get(locations.size() - 1));
                } else {
                    location = new LatLng(-34, 151);
                }

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    // Show rationale and request permission.
                }
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            }
        });
    }

    public void handleStop() {
        if (this.locationListener != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
        }
        this.timerHandler.removeCallbacks(this.timerRunnable);
        this.googleApiClient.disconnect();
    }

    public void handleResume() {
        this.googleApiClient.connect();
    }

    private void resetData() {
        this.startTrackTime = 0;
        this.stopTrackTime = 0;
        this.distance = 0f;
        this.locations.clear();
    }

    @NonNull
    private CircleOptions getCircle(Location location) {
        return new CircleOptions().center(getLatLng(location)).radius(10).strokeColor(getColor()).fillColor(getColor());
    }

    private int getColor() {
        return Color.parseColor("#00CC99");
    }

    @NonNull
    private LocationRequest getLocationRequest() {
        if (this.locationRequest != null) {
            return this.locationRequest;
        }
        this.locationRequest = new LocationRequest();
        this.locationRequest
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setFastestInterval(500);
        return this.locationRequest;
    }

    @NonNull
    private LocationListener getLocationListener() {
        if (this.locationListener != null) {
            return this.locationListener;
        }
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locations.add(location);
                moveCamera(location);
                //mMap.addCircle(getCircle(location));
                mMap.addPolyline(getPolyLine());
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                updateDistance();
            }
        };
        return locationListener;
    }

    private void updateDistance() {
        int last = this.locations.size() - 1;
        LatLng recentPoint = getLatLng(this.locations.get(last));
        LatLng lastPoint = getLatLng(this.locations.get(last - 1));
        float distance[] = new float[1];
        Location.distanceBetween(recentPoint.latitude, recentPoint.longitude, lastPoint.latitude, lastPoint.longitude, distance);
        this.distance += distance[0];
        this.displayDistance.setText(String.format(Locale.getDefault(), "%.2f km", this.distance / 1000));
    }

    @NonNull
    private PolylineOptions getPolyLine() {
        return new PolylineOptions().add(getLatLangList()).color(getColor()).visible(true).width(5);
    }

    private LatLng[] getLatLangList() {
        List<LatLng> latLangs = new ArrayList<>();
        for (Location location : this.locations) {
            latLangs.add(getLatLng(location));
        }
        return latLangs.toArray(new LatLng[latLangs.size()]);
    }

    private void moveCamera(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(location)));
    }

    @NonNull
    private LatLng getLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void addToDataset() {
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                createRunEntry(bitmap);
            }
        });
    }

    private void createRunEntry(Bitmap bitmap) {
        RunEntry runEntry = new RunEntry();
        Date now = new Date();
        long id = now.getTime();
        String fileName = this.formatter.getFileName(id);
        runEntry.setCreated(now);
        runEntry.setId(id);
        runEntry.setType(RunType.MAP_RUN.getName());
        runEntry.setTime(this.mathHelper.getDifferenceInMinutes(this.startTrackTime, this.stopTrackTime));
        runEntry.setDistance(this.distance / 1000);
        runEntry.setImageFilepath(fileName);
        RealmOperator.saveOrUpdate(runEntry);
        String saved = this.activity.getString(R.string.entry_not_saved);
        if (this.imageHelper.saveImage(bitmap, fileName)) {
            saved = this.activity.getString(R.string.entry_saved);
        }
        Toast.makeText(this.activity, saved, Toast.LENGTH_SHORT).show();

        resetData();
    }
}
