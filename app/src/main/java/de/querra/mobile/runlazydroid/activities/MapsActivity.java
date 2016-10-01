package de.querra.mobile.runlazydroid.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import de.querra.mobile.runlazydroid.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    public boolean connected;
    public LocationListener locationListener;
    private LocationRequest locationRequest;
    private Polyline polyLine;
    private List<Location> locations = new ArrayList<>();
    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_maps__fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTracking) {
                    startTracking();
                    ((FloatingActionButton) view).setImageDrawable(getDrawable(R.drawable.ic_menu));
                }
                else{

                    Snackbar.make(findViewById(R.id.activity_maps__main_layout), "What up?", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Nothing much", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MapsActivity.this, "Alrighty then", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    ((FloatingActionButton) view).setImageDrawable(getDrawable(R.drawable.ic_start_tracking));
                    isTracking = false;
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (this.googleApiClient == null) {
            this.googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {



                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            MapsActivity.this.connected = true;
                            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                        MapsActivity.this.googleApiClient);
                                if (lastLocation != null){
                                    moveCamera(lastLocation);
                                    mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                                    mMap.addCircle(getCircle(lastLocation));
                                    locations.add(lastLocation);
                                }
                                LocationServices.FusedLocationApi.requestLocationUpdates(MapsActivity.this.googleApiClient, getLocationRequest(), getLocationListener());
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            MapsActivity.this.connected = false;
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            new AlertDialog.Builder(MapsActivity.this)
                                    .setTitle("Connection failed")
                                    .setMessage("Not connected, retry connection?")
                                    .setCancelable(true)
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("Reconnect", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MapsActivity.this.googleApiClient.connect();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void startTracking() {
        this.isTracking = true;
    }

    @NonNull
    private CircleOptions getCircle(Location lastLocation) {
        return new CircleOptions().center(getLatLng(lastLocation)).radius(10).strokeColor(getColor()).fillColor(getColor());
    }

    private int getColor() {
        return Color.parseColor("#00CC99");
    }

    @NonNull
    private LocationRequest getLocationRequest() {
        if (this.locationRequest != null){
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
        if (this.locationListener != null){
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
            }
        };
        return locationListener;
    }

    @NonNull
    private PolylineOptions getPolyLine() {
        return new PolylineOptions().add(getLatLangList()).color(getColor()).visible(true).width(5);
    }

    private LatLng[] getLatLangList() {
        List<LatLng> latLangs = new ArrayList<>();
        for (Location location : this.locations){
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

    @Override
    protected void onStart() {
        this.googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
        this.googleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location;
        if (locations.size() != 0){
            location = getLatLng(locations.get(locations.size()-1));
        }
        else{
            location = new LatLng(-34, 151);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
