package de.querra.mobile.runlazydroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import de.querra.mobile.runlazydroid.R;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(getApplicationContext());
        }
        this.callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        LoginButton facebookLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        facebookLoginButton.setReadPermissions("public_profile");

        // Callback registration
        facebookLoginButton.registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToMainActivity();
            }

            @Override
            public void onCancel() {
                Log.e("Callback", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Callback", "error");
            }
        });

        AppCompatButton testLoginButton = (AppCompatButton) findViewById(R.id.test_login_button);
        testLoginButton.setOnClickListener(v -> startProfileActivity());
    }

    private void startProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.LOGIN_TYPE, MainActivity.LOGIN_FACEBOOK);
        startActivity(intent);
        finish();
    }

}
