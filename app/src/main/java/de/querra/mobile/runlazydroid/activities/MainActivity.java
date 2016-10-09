package de.querra.mobile.runlazydroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.fragments.OverviewFragment;
import de.querra.mobile.runlazydroid.fragments.RunningDataFragment;

public class MainActivity extends BaseNavigationActivity {

    private SupportMapFragment mapFragment;
    private MapHandler mapHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mapHandler != null) {
            this.mapHandler.handleStart();
        }
    }

    @Override
    protected void onStop() {
        if (this.mapHandler != null) {
            this.mapHandler.handleStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (this.mapHandler != null) {
            this.mapHandler.handleDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onSwitchedToMap() {
        View displayContainer = findViewById(R.id.activity_maps__display_container);
        if (this.mapHandler == null) {
            this.mapHandler = new MapHandler(this.mapFragment, displayContainer, this);
            this.mapHandler.initialize();
        }
    }

    @Override
    protected SupportMapFragment getMapFragment() {
        if (this.mapFragment == null) {
            this.mapFragment = new SupportMapFragment();
        }
        this.floatingActionButton.setVisibility(View.VISIBLE);
        int fabDrawableId = R.drawable.ic_start_tracking;
        if (this.mapHandler != null && this.mapHandler.isServiceTracking()){
            fabDrawableId = R.drawable.ic_menu;
        }
        this.floatingActionButton.setImageDrawable(getResources().getDrawable(fabDrawableId));
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mapHandler.handleFabClick((FloatingActionButton) view);
            }
        });
        return this.mapFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Fragment getInitialFragment() {
        return new OverviewFragment();
    }

    @Override
    protected FloatingActionButton getFloatingActionButton() {
        return (FloatingActionButton) findViewById(R.id.activity_main__fab);
    }

    @Override
    protected void onPrepareOverviewFragment() {
        this.floatingActionButton.setVisibility(View.VISIBLE);
        this.floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(new RunningDataFragment(), true);
                MainActivity.this.navigationView.setCheckedItem(R.id.nav_enter_running_data);
                MainActivity.this.floatingActionButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onPrepareInitialFragment() {
        onPrepareOverviewFragment();
    }
}
