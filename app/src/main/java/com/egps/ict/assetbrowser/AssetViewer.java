package com.egps.ict.assetbrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.egps.ict.assetbrowser.DBAdapter.ID;

public class AssetViewer extends BaseActivity {
    Asset asset;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        View content = getLayoutInflater().inflate(R.layout.activity_asset_viewer, childrenContainer, false);
        childrenContainer.addView(content, childrenIndex);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    private void createLayout() {
        if (asset == null) return;
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container = (LinearLayout) findViewById(R.id.activity_asset_viewer);
        if (container.getChildCount() > 0) container.removeAllViews();
        for (int i = 1; i < c.getColumnCount(); i++) {
            View node = inflater.inflate(R.layout.layout_asset_details, null);
            TextView item = (TextView) node.findViewById(R.id.item);
            TextView itemTxt = (TextView) node.findViewById(R.id.itemText);
            item.setText(Asset.getFieldName(c.getColumnName(i)) + ": ");
            itemTxt.setText(c.getString(i).trim().length() == 0? "Not Available": c.getString(i));
            container.addView(node);
        }
        View node = inflater.inflate(R.layout.layout_asset_details, null);
        TextView item = (TextView) node.findViewById(R.id.item);
        TextView itemTxt = (TextView) node.findViewById(R.id.itemText);
        this.asset.hasChildren(db.hasChildren(this.asset.getId()));
        item.setText("Has Subsystems?");
        itemTxt.setText(asset.hasChildren() ? "YES" : "NO");
        container.addView(node);
        c.close();
    }

    private void setAssetFromExtras() {
        String assetId = getIntent().getExtras().getString("assetId");
        toolbar.setTitle(assetId);
        c = db.getAsset(assetId);
        if (c.moveToFirst()) {
            this.asset = new Asset(c.getString(c.getColumnIndex(ID)), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9));
            return;
        }
        c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAssetFromExtras();
        createLayout();
    }
}
