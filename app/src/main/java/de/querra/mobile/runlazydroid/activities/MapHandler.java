package de.querra.mobile.runlazydroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;
import java.util.Locale;

import de.querra.mobile.rlblib.entities.RunType;
import de.querra.mobile.rlblib.helper.Formatter;
import de.querra.mobile.rlblib.helper.MathHelper;
import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.RunEntry;
import de.querra.mobile.runlazydroid.services.internal.ImageService;
import de.querra.mobile.runlazydroid.services.internal.RealmService;
import de.querra.mobile.runlazydroid.services.system.MapSystemService;

import static android.content.Context.BIND_AUTO_CREATE;

public class MapHandler {

    private SupportMapFragment mapFragment;
    private Activity activity;
    private GoogleMap mMap;
    private View displayContainer;
    private TextView displayTime;
    private TextView displayDistance;
    private boolean isTracking = false;
    private Handler uiHandler = new Handler();
    private Runnable uiUpdate;
    private MapSystemService mapSystemService;
    private boolean mapServiceIsConnected = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MapSystemService.MapServiceBinder binder = (MapSystemService.MapServiceBinder) service;
            mapSystemService = binder.getService();
            mapServiceIsConnected = true;
            mapSystemService.initialize(activity, MapHandler.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mapSystemService = null;
            mapServiceIsConnected = false;
        }
    };

    public MapHandler(SupportMapFragment mapFragment, View displayContainer, Activity activity) {

        this.uiUpdate = new Runnable() {
            @Override
            public void run() {
                if (displayTime != null && displayTime.getText() != null) {
                    displayTime.setText(Formatter.millisToTimeString(mapSystemService.getElapsedTime()));
                }
                if (displayDistance != null && displayDistance.getText() != null) {
                    displayDistance.setText(String.format(Locale.getDefault(), "%.2f km", mapSystemService.getDistance() / 1000));
                }
                moveCamera(mapSystemService.getCurrentLocation());
                //mMap.addCircle(getCircle(location));
                mMap.addPolyline(getPolyLine());
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                uiHandler.postDelayed(this, 1000);
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
        this.mapSystemService.startLocationUpdates(this.activity);
        this.mapSystemService.setStartTrackTime(new Date().getTime());
        // initialize the display
        this.displayTime = (TextView) this.displayContainer.findViewById(R.id.activity_maps__time);
        this.displayDistance = (TextView) this.displayContainer.findViewById(R.id.activity_maps__distance);
        this.displayContainer.setVisibility(View.VISIBLE);
        this.displayDistance.setText(String.format(Locale.getDefault(), "%.2f km", 0f));
        // start counter
        this.uiHandler.post(this.uiUpdate);
    }

    public void onStopTracking() {
        // save tracking status for fab
        this.isTracking = false;
        // set stop time
        this.mapSystemService.setStopTrackTime(new Date().getTime());
        // hide display
        this.displayContainer.setVisibility(View.INVISIBLE);
        // stop timer
        this.uiHandler.removeCallbacks(this.uiUpdate);

        Location currentLocation = this.mapSystemService.getCurrentLocation();
        if (currentLocation != null) {
            mMap.addCircle(getCircle(currentLocation));
        }
        this.mapSystemService.stopLocationUpdates();
    }

    public void initialize() {
        Intent intent = new Intent(this.activity, MapSystemService.class);
        this.activity.startService(intent);
        this.activity.bindService(intent, this.serviceConnection, BIND_AUTO_CREATE);
    }

    public void handleStart() {
        if (!this.mapServiceIsConnected) {
            Intent intent = new Intent(this.activity, MapSystemService.class);
            this.activity.bindService(intent, this.serviceConnection, BIND_AUTO_CREATE);
        }
        if (this.isTracking) {
            this.uiHandler.post(this.uiUpdate);
        }
    }

    public void handleStop() {
        this.uiHandler.removeCallbacks(this.uiUpdate);
    }

    public void handleDestroy() {
        if (this.mapServiceIsConnected) {
            this.activity.unbindService(this.serviceConnection);
        }
        this.activity.stopService(new Intent(this.activity, MapSystemService.class));
        this.uiHandler.removeCallbacks(this.uiUpdate);
    }

    private void createRunEntry(Bitmap bitmap) {
        RunEntry runEntry = new RunEntry();
        Date now = new Date();
        long id = now.getTime();
        final String fileName = Formatter.getFileName(id);
        runEntry.setCreated(now);
        runEntry.setId(id);
        runEntry.setType(RunType.MAP_RUN.getName());
        final int runTimeInMinutes = MathHelper.getDifferenceInMinutes(this.mapSystemService.getStartTrackTime(), this.mapSystemService.getStopTrackTime());
        runEntry.setTime(runTimeInMinutes);
        final float distance = this.mapSystemService.getDistance() / 1000;
        runEntry.setDistance(distance);
        runEntry.setImageFilepath(fileName);
        RealmService.getInstance().saveOrUpdate(runEntry);
        String saved = this.activity.getString(R.string.entry_not_saved);
        if (ImageService.getInstance().saveImage(this.activity, bitmap, fileName)) {
            saved = this.activity.getString(R.string.entry_saved);
        }
        Toast.makeText(this.activity, saved, Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this.activity)
                .setTitle("Share")
                .setMessage("Do you want to share your run?")
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.getDefault(), "I ran %.1f km in %d minutes with Run Lazybot", distance, runTimeInMinutes));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().toString()+"/"+fileName));
                        shareIntent.setType("image/*");
                        activity.startActivity(Intent.createChooser(shareIntent, "Share image via:"));
                    }
                }).setNegativeButton(activity.getString(R.string.cancel), null)
                .setCancelable(true)
                .create()
                .show();
        this.mapSystemService.resetData();
        this.mMap.clear();
    }

    private void addToDataset() {
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                createRunEntry(bitmap);
            }
        });
    }

    @NonNull
    private PolylineOptions getPolyLine() {
        return new PolylineOptions().add(this.mapSystemService.getLatLangList()).color(getColor()).visible(true).width(5);
    }

    private void moveCamera(Location location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLng(location)));
    }

    @NonNull
    private LatLng getLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @NonNull
    private CircleOptions getCircle(Location location) {
        return new CircleOptions().center(getLatLng(location)).radius(10).strokeColor(getColor()).fillColor(getColor());
    }

    private int getColor() {
        return Color.parseColor("#00CC99");
    }

    public void prepareMap() {
        this.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // TODO
                mMap = googleMap;
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    // Show rationale and request permission.
                }
            }
        });
    }

    public void handleFabClick(final FloatingActionButton view) {
        if (!isTracking) {
            Location lastLocation = this.mapSystemService.initiateLocations(this.activity);
            if (lastLocation != null) {
                moveCamera(lastLocation);
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                mMap.addCircle(getCircle(lastLocation));
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
                                            mapSystemService.resetData();
                                            mMap.clear();
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

    public boolean isServiceTracking() {
        return this.mapSystemService != null && this.mapSystemService.isTrackingLocation();
    }
}
