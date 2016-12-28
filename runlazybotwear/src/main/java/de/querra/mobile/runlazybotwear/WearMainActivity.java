package de.querra.mobile.runlazybotwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.util.Locale;

import de.querra.mobile.rlblib.helper.DateHelper;
import de.querra.mobile.rlblib.helper.Formatter;
import de.querra.mobile.runlazydroid.R;


public class WearMainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                String daysLeft = Formatter.getDaysLeft(WearMainActivity.this, DateHelper.getNextSunday());
                mTextView.setText(String.format(Locale.getDefault(), "%s: %s", getString(R.string.time_left), daysLeft));
            }
        });
    }
}
