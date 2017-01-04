package de.querra.mobile.runlazydroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.SupportMapFragment;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.IOException;
import java.util.Date;

import de.querra.mobile.rlblib.entities.User;
import de.querra.mobile.rlblib.helper.DateHelper;
import de.querra.mobile.rlblib.helper.Formatter;
import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.entities.Target;
import de.querra.mobile.runlazydroid.fragments.AchievementFragment;
import de.querra.mobile.runlazydroid.fragments.OverviewFragment;
import de.querra.mobile.runlazydroid.fragments.PenaltyFragment;
import de.querra.mobile.runlazydroid.fragments.PreferencesFragment;
import de.querra.mobile.runlazydroid.fragments.RunningDataFragment;
import de.querra.mobile.runlazydroid.fragments.StatisticsFragment;
import de.querra.mobile.runlazydroid.fragments.TimeLineFragment;
import de.querra.mobile.runlazydroid.services.internal.PreferencesService;
import de.querra.mobile.runlazydroid.services.internal.RealmService;
import de.querra.mobile.runlazydroid.widgets.ProfilePictureView;

public abstract class BaseNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "BaseNavigationActivity";

    public static final String USER = "user";
    public static final String PROFILE_NAME = "profile_name";
    public static final String LOGIN_TYPE = "login_type";
    public static final String LOGIN_FACEBOOK = "login_facebook";
    public static final String LOGIN_TEST = "login_test";
    public static final String PROFILE_PICTURE = "profile_picture";

    RealmService realmService;
    PreferencesService preferencesService;

    protected User user;
    protected SupportMapFragment mMap;
    protected FloatingActionButton floatingActionButton;
    protected NavigationView navigationView;
    protected boolean isLoggedInWithFacebook;
    protected Uri imageUri;
    protected String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realmService = RealmService.getInstance();
        this.preferencesService = PreferencesService.getInstance();

        Bundle extras = getIntent().getExtras();
        String loginType = (String) extras.get(LOGIN_TYPE);
        if (LOGIN_FACEBOOK.equals(loginType)) {
            this.isLoggedInWithFacebook = true;
        } else if (LOGIN_TEST.equals(loginType)) {
            this.imageUri = (Uri) extras.get(PROFILE_PICTURE);
            this.userName = (String) extras.get(PROFILE_NAME);
        } else {
            Log.e(TAG, "No valid login type found");
            finish();
        }

        if (this.isLoggedInWithFacebook && !FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            initUser();
        }
        setContentView(getLayout());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (this.realmService.targetNeedsUpdate()) {
            this.realmService.handleTargetAchieved();
            createTarget();
        }

        this.floatingActionButton = getFloatingActionButton();

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

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (this.navigationView != null) {
            this.navigationView.setNavigationItemSelectedListener(this);
            this.navigationView.setCheckedItem(R.id.nav_overview);
        }

        switchFragment(getInitialFragment(), false);
        onPrepareInitialFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // check if target has to be updated and act accordingly
        if (this.realmService.targetNeedsUpdate()) {
            this.realmService.handleTargetAchieved();
            createTarget();
        }
    }

    private void createTarget() {
        Target target = new Target();
        Date now = new Date();
        target.setId(now.getTime());
        target.setAchieved(false);
        target.setCreated(now);
        target.setStartDate(DateHelper.getLastSunday().toDate());
        target.setEndDate(DateHelper.getNextSunday().toDate());
        if (this.realmService.newTargetNeedsCopy()) {
            target.setBaseDistance(this.realmService.getLastTarget().getBaseDistance());
        } else {
            target.setBaseDistance(calculateBaseDistance());
        }
        this.realmService.saveOrUpdate(target);
    }

    private float calculateBaseDistance() {
        int numberAchieved = this.realmService.getAllTimeTargetsAchieved();
        float startValue = this.preferencesService.getWeekTarget(this);
        float incrementDistance = this.preferencesService.getIncrementDistance(this);
        return startValue + incrementDistance * numberAchieved;
    }

    private void initUser() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (Profile.getCurrentProfile() != null) {
            this.user = new User(Profile.getCurrentProfile());
        }
    }

    private void updateUser(View view) {
        if (this.user == null && this.isLoggedInWithFacebook) {
            initUser();
            if (this.user == null) {
                return;
            }
        }

        ProfilePictureView profileImage = (ProfilePictureView) view.findViewById(R.id.nav_header_main__profilePicture);
        if (profileImage != null) {
            if (this.isLoggedInWithFacebook) {
                profileImage.setProfileId(this.user.getId());
            } else {
                if (this.imageUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.imageUri);
                        profileImage.setDefaultProfilePicture(bitmap);
                        profileImage.setProfileId(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        TextView userFirstName = (TextView) view.findViewById(R.id.nav_header_main__user_first_name);
        if (userFirstName != null) {
            if (this.isLoggedInWithFacebook) {
                userFirstName.setText(this.user.getFirstName());
            } else {
                if (this.userName != null) {
                    userFirstName.setText(this.userName);
                }
            }
        }

        CircularProgressBar progress = (CircularProgressBar) view.findViewById(R.id.nav_header_main__progress);
        if (progress != null)

        {
            progress.setProgressWithAnimation(this.realmService.getProgress());
        }

        TextView target = (TextView) view.findViewById(R.id.nav_header_main__target);
        if (target != null)

        {
            float distanceLeft = this.realmService.getDistanceLeft();
            if (distanceLeft < 0f) {
                distanceLeft = 0f;
                target.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flag, 0, R.drawable.ic_check, 0);
            } else {
                target.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flag, 0, 0, 0);
            }
            target.setText(Formatter.asKilometers(distanceLeft));
        }

        TextView penalty = (TextView) view.findViewById(R.id.nav_header_main__days_left);
        if (penalty != null)

        {
            penalty.setText(Formatter.getDaysLeft(this, DateHelper.getNextSunday()));
        }

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.floatingActionButton.setVisibility(View.INVISIBLE);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            switchFragment(new OverviewFragment(), true);
            onPrepareOverviewFragment();
        } else if (id == R.id.nav_map) {
            this.mMap = getMapFragment();
            switchFragment(this.mMap, true);
            onSwitchedToMap();
        } else if (id == R.id.nav_enter_running_data) {
            switchFragment(new RunningDataFragment(), true);
        } else if (id == R.id.nav_add_penalty) {
            switchFragment(new PenaltyFragment(), true);
        } else if (id == R.id.nav_settings) {
            switchFragment(new PreferencesFragment(), true);
        } else if (id == R.id.nav_time_line) {
            switchFragment(new TimeLineFragment(), true);
        } else if (id == R.id.nav_statistics) {
            switchFragment(new StatisticsFragment(), true);
        } else if (id == R.id.nav_achievements) {
            switchFragment(new AchievementFragment(), true);
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected abstract void onSwitchedToMap();

    protected abstract SupportMapFragment getMapFragment();

    protected void switchFragment(Fragment fragment, boolean backstack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment);
        if (backstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.user != null) {
            outState.putParcelable(USER, this.user);
        }
    }

    protected abstract void onPrepareOverviewFragment();

    protected abstract void onPrepareInitialFragment();

    public abstract int getLayout();

    protected abstract Fragment getInitialFragment();

    protected abstract FloatingActionButton getFloatingActionButton();
}
