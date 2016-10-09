package de.querra.mobile.runlazydroid.services.system;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.querra.mobile.runlazydroid.activities.MapHandler;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MapSystemService extends Service {

    private static final String TAG = "MapSystemService";
    private static final float MAX_HUMAN_DISTANCE_PER_SECOND = 10; // in meters

    private IBinder binder = new MapServiceBinder();
    private List<Location> locations = new ArrayList<>();
    private long startTrackTime;
    private long stopTrackTime;
    private float distance = 0f;
    private LocationListener locationListener;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private boolean connected;
    private boolean trackingLocation;
    private long lastSignalTime = -1;

    @Override
    public void onCreate() {
        Log.d(TAG, "Service created");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed");
        if (this.locationListener != null) {
            stopLocationUpdates();
        }
        if (this.googleApiClient != null && this.connected) {
            this.googleApiClient.disconnect();
        }
        super.onDestroy();
    }

    public void stopLocationUpdates() {
        if (this.trackingLocation) {
            FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
            this.trackingLocation = false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service bound");
        return this.binder;
    }

    public long getElapsedTime() {
        return new Date().getTime() - startTrackTime;
    }

    public void setStartTrackTime(long startTime) {
        this.startTrackTime = startTime;
    }

    public void setStopTrackTime(long stopTime) {
        this.stopTrackTime = stopTime;
    }

    public long getStartTrackTime() {
        return this.startTrackTime;
    }

    public long getStopTrackTime() {
        return this.stopTrackTime;
    }

    public void resetData() {
        this.startTrackTime = 0;
        this.stopTrackTime = 0;
        this.distance = 0f;
        this.lastSignalTime = -1;
        this.locations.clear();
    }

    public float getDistance() {
        return this.distance;
    }

    @NonNull
    private LocationRequest getLocationRequest() {
        if (this.locationRequest != null) {
            return this.locationRequest;
        }
        this.locationRequest = new LocationRequest();
        this.locationRequest
                .setInterval(5000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setFastestInterval(3000);
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
                float distance = calculateDistance(getLatLng(location), getLatLng(getCurrentLocation()));
                // only count new location if it is outside 95% interval (2 * 68% interval) of the accuracy and within human limits
                if (distance > (2 * location.getAccuracy()) && distance < (MAX_HUMAN_DISTANCE_PER_SECOND * secondsSinceLastUpdate())) {
                    locations.add(location);
                    updateDistance();
                    lastSignalTime = new Date().getTime();
                }
            }
        };
        return locationListener;
    }

    private long secondsSinceLastUpdate() {
        long lastSignal = this.lastSignalTime;
        if (lastSignal == -1) {
            lastSignal = this.startTrackTime;
        }
        return (new Date().getTime() - lastSignal) / 1000;
    }

    private void updateDistance() {
        int last = this.locations.size() - 1;
        LatLng recentPoint = getLatLng(getCurrentLocation());
        LatLng lastPoint = getLatLng(this.locations.get(last - 1));
        this.distance += calculateDistance(recentPoint, lastPoint);
    }

    private float calculateDistance(LatLng first, LatLng second) {
        float distance[] = new float[1];
        Location.distanceBetween(first.latitude, first.longitude, second.latitude, second.longitude, distance);
        return distance[0];
    }

    public LatLng[] getLatLangList() {
        List<LatLng> latLangs = new ArrayList<>();
        for (Location location : this.locations) {
            latLangs.add(getLatLng(location));
        }
        return latLangs.toArray(new LatLng[latLangs.size()]);
    }

    @NonNull
    private LatLng getLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public Location getCurrentLocation() {
        int last = this.locations.size() - 1;
        return this.locations.get(last);
    }

    public Location initiateLocations(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    this.googleApiClient);
            if (lastLocation != null) {
                this.locations.add(lastLocation);
            }
            startLocationUpdates(context);

            return lastLocation;
        }
        return null;
    }

    public void startLocationUpdates(Context context) {
        if (!trackingLocation) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, getLocationRequest(), getLocationListener());
                this.trackingLocation = true;
            }
        }
    }

    public void initialize(final Activity activity, final MapHandler mapHandler) {
        if (this.googleApiClient == null) {
            this.googleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            connected = true;
                            mapHandler.prepareMap();
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
        if (!this.googleApiClient.isConnected() || !this.googleApiClient.isConnecting()) {
            this.googleApiClient.connect();
        }
    }

    public boolean isTrackingLocation() {
        return this.trackingLocation;
    }

    public class MapServiceBinder extends Binder {
        public MapSystemService getService() {
            return MapSystemService.this;
        }
    }
}
