package de.querra.mobile.runlazydroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;

import de.querra.mobile.runlazydroid.R;
import rx.Observable;


public class ProfileActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;

    private EditText name;
    private ImageView profilePicture;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppCompatButton applyButton = (AppCompatButton) findViewById(R.id.activity_profile_apply);
        applyButton.setOnClickListener(v -> {
            goToMainActivity();
        });

        this.name = (EditText) findViewById(R.id.activity_profile_name);
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name);
        nameObservable.subscribe(profileName -> {
            applyButton.setEnabled(profileName.length() > 0);
        });

        this.profilePicture = (ImageView) findViewById(R.id.activity_profile_picture);
        this.profilePicture.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        });



    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.LOGIN_TYPE, MainActivity.LOGIN_TEST);
        intent.putExtra(MainActivity.PROFILE_NAME, this.name.getText().toString());
        intent.putExtra(MainActivity.PROFILE_PICTURE, this.selectedImageUri);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                this.selectedImageUri = data.getData();
                this.profilePicture.setImageURI(this.selectedImageUri);
            }
        }
    }
}
