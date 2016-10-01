package de.querra.mobile.runlazydroid.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.entities.User;
import de.querra.mobile.runlazydroid.fragments.OverviewFragment;
import de.querra.mobile.runlazydroid.fragments.PenaltyFragment;
import de.querra.mobile.runlazydroid.fragments.RunningDataFragment;
import de.querra.mobile.runlazydroid.fragments.SettingsFragment;
import de.querra.mobile.runlazydroid.fragments.StatisticsFragment;
import de.querra.mobile.runlazydroid.helper.DateHelper;
import de.querra.mobile.runlazydroid.helper.Formatter;
import de.querra.mobile.runlazydroid.helper.PreferencesHelper;
import de.querra.mobile.runlazydroid.widgets.ProfilePictureView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String USER = "user";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUser();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                updateUser(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                updateUser(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //NOOP
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //NOOP
            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_overview);
        }

        OverviewFragment overviewFragment = new OverviewFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_main, overviewFragment).commit();
    }

    private void initUser() {
        if(AccessToken.getCurrentAccessToken() != null){
            Profile.fetchProfileForCurrentAccessToken();
        }
        if(Profile.getCurrentProfile() != null) {
            this.user = new User(Profile.getCurrentProfile());
        }
    }

    private void updateUser(View view) {
        if (this.user == null){
            initUser();
            if (this.user == null){
                return;
            }
        }

        ProfilePictureView profileImage = (ProfilePictureView) view.findViewById(R.id.nav_header_main__profilePicture);
        profileImage.setProfileId(this.user.getId());

        CircularProgressBar progress = (CircularProgressBar) view.findViewById(R.id.nav_header_main__progress);
        progress.setProgressWithAnimation(Formatter.getProgress(this)*100f);

        TextView userFirstName = (TextView) view.findViewById(R.id.nav_header_main__user_first_name);
        userFirstName.setText(this.user.getFirstName());

        TextView goal = (TextView) view.findViewById(R.id.nav_header_main__goal);
        goal.setText(Formatter.asKilometers(PreferencesHelper.getWeekGoal(this)));

        TextView penalty = (TextView) view.findViewById(R.id.nav_header_main__days_left);
        penalty.setText(Formatter.getDaysLeft(DateHelper.getNextSunday(), this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            switchFragment(new OverviewFragment());
        } else if (id == R.id.nav_enter_running_data) {
            switchFragment(new RunningDataFragment());
        } else if (id == R.id.nav_add_penalty) {
            switchFragment(new PenaltyFragment());
        } else if (id == R.id.nav_settings) {
            switchFragment(new SettingsFragment());
        } else if (id == R.id.nav_statistics) {
            switchFragment(new StatisticsFragment());
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {
            // TODO: share intent
        } else if (id == R.id.nav_send) {
            // TODO: share intent
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack(fragment.getTag()).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.user != null) {
            outState.putParcelable(USER, this.user);
        }
    }

    public void onAddEntryRequested() {
        switchFragment(new RunningDataFragment());
    }
}
