package com.egps.ict.assetbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected DBAdapter db;
    protected int childrenIndex;
    protected ViewGroup childrenContainer;
    private Intent searchIntent;
    private Intent aboutIntent;
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchIntent = new Intent(getBaseContext(), SearchActivity.class);
        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        aboutIntent = new Intent(getBaseContext(), AboutActivity.class);
        aboutIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        fab = (FloatingActionButton) findViewById(R.id.searchFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(searchIntent);
            }
        });
        childrenIndex = ((ViewGroup) fab.getParent()).indexOfChild(fab) - 1;
        childrenContainer = (ViewGroup) fab.getParent();
        establishDBConnection();
    }

    private void establishDBConnection() {
        db = new DBAdapter(this);
        //assets = db.getAssets(null, false, "assetId", "assetShortDesc", "parentId", "assetLongDesc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_about:
                startActivity(aboutIntent);
                break;
            case R.id.search:
                startActivity(searchIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null)
            db.close();
    }
}
