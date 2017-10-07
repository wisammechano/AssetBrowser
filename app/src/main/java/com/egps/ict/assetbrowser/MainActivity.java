package com.egps.ict.assetbrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import static com.egps.ict.assetbrowser.DBAdapter.ID;
import static com.egps.ict.assetbrowser.DBAdapter.SHORT_DESC;

public class MainActivity extends BaseActivity {

    TreeNode rootNode;
    private List<Asset> assetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.ic_launcher);
        }
        View content = getLayoutInflater().inflate(R.layout.activity_main, childrenContainer, false);
        childrenContainer.addView(content, childrenIndex);
        assetList = new ArrayList<>();
        TreeNodeHolder th = new TreeNodeHolder(getApplicationContext());
        populateTreeView();
    }

    private void populateTreeView() {
        Asset rootAsset = new Asset("EGPS PLANT", null, "EGPS Powerplant Assets", null);
        rootAsset.hasChildren(true);
        TreeNode root = TreeNode.root();
        rootNode = new TreeNode(rootAsset);
        fetchAssetList(rootAsset.getId());
        //First level
        for (Asset childAsset : assetList) {
            TreeNode child = new TreeNode(childAsset);
            rootNode.addChild(child);
        }
        root.addChild(rootNode);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.content_main);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(TreeNodeHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(longClickListener);
        tView.expandLevel(1);
        View tree = tView.getView();
        tree.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(tree);
    }

    private void fetchAssetList(String parent) {
        Cursor mCursor = db.getAssets(parent, true);
        Asset mAsset;
        String assetId, shortDesc;
        assetList.clear();
        if (mCursor.moveToFirst()) {
            do {
                assetId = mCursor.getString(mCursor.getColumnIndex(ID));
                shortDesc = mCursor.getString(mCursor.getColumnIndex(SHORT_DESC));
                mAsset = new Asset(assetId, parent);
                mAsset.setShortDesc(shortDesc);
                mAsset.hasChildren(db.hasChildren(assetId));
                assetList.add(mAsset);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode parent, Object asset) {
            if (!((Asset) asset).hasChildren()) {
                Intent intent = new Intent(MainActivity.this, AssetViewer.class);
                intent.putExtra("assetId", ((Asset) asset).getId());
                startActivity(intent);
            }
            if (parent.getLevel() < 2 || parent.isExpanded() || parent.size() > 0) {
                return;
            }
            //collapseOther(parent);
            fetchAssetList(((Asset) asset).getId());
            for (Asset childAsset : assetList) {
                if (childAsset.hasChildren()) {
                    TreeNode child = new TreeNode(childAsset);
                    parent.addChild(child);
                }
            }
            for (Asset childAsset : assetList) {
                if (!childAsset.hasChildren()) {
                    TreeNode child = new TreeNode(childAsset);
                    parent.addChild(child);
                }
            }
        }
    };

    private TreeNode.TreeNodeLongClickListener longClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object asset) {
            Intent intent = new Intent(MainActivity.this, AssetViewer.class);
            intent.putExtra("assetId", ((Asset) asset).getId());
            startActivity(intent);
            return true;
        }
    };
}
