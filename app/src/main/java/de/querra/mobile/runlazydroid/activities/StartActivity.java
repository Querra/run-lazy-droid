package de.querra.mobile.runlazydroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.data.helper.AchievementHelper;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(getApplicationContext());
        }
        AppEventsLogger.activateApp(getApplication());
        new Handler().postDelayed(() -> updateWithToken(AccessToken.getCurrentAccessToken()), 1000);

        setContentView(R.layout.activity_start);
        AchievementHelper.createAchievements();
    }


    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            startMainActivity();
        } else {
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
