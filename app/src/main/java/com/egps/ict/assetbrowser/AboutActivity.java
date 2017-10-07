package com.egps.ict.assetbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("About");
        }

        View content = getLayoutInflater().inflate(R.layout.activity_about, childrenContainer, false);
        childrenContainer.addView(content, childrenIndex);
    }
}
