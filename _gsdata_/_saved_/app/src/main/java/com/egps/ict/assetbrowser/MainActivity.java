package com.egps.ict.assetbrowser;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import static com.egps.ict.assetbrowser.DBAdapter.ID;
import static com.egps.ict.assetbrowser.DBAdapter.LONG_DESC;
import static com.egps.ict.assetbrowser.DBAdapter.PARENT_ID;
import static com.egps.ict.assetbrowser.DBAdapter.SHORT_DESC;

public class MainActivity extends AppCompatActivity {

    private Cursor assets;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.searchFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        establishDBConnection();
        populateTreeView();

    }

    private void populateTreeView() {
        Cursor temp;
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("EGPS Powerplant Assets");
        //First level
        temp = db.getAssets("EGPS PLANT", false, ID, SHORT_DESC);
        if (temp.moveToFirst()){
            String nodeName, assetId;
            TreeNode nodeP, nodeC;
            Cursor c;
            do {
                assetId = temp.getString(temp.getColumnIndex(ID));
                nodeName = temp.getString(temp.getColumnIndex(SHORT_DESC));
                nodeP = new TreeNode(nodeName);
                c = db.getAssets(assetId, false, ID, SHORT_DESC);
                if(c.moveToFirst()){
                    do {
                        nodeName = c.getString(c.getColumnIndex(SHORT_DESC));
                        nodeC = new TreeNode(nodeName);
                        nodeP.addChild(nodeC);
                    } while (c.moveToNext());
                }
                c.close();
                parent.addChild(nodeP);

            } while(temp.moveToNext());
        }
        root.addChild(parent);

        temp.close();

        RelativeLayout container = (RelativeLayout) findViewById(R.id.content_main);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(TreeNodeHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        //tView.setDefaultNodeLongClickListener(nodeLongClickListener);
        container.addView(tView.getView());
    }
    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            if(node.getLevel() >= 2) {

            }
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            TreeNodeHolder.IconTreeItem item = (TreeNodeHolder.IconTreeItem) value;
            Toast.makeText(MainActivity.this, "Long click: " + item.text, Toast.LENGTH_SHORT).show();
            return true;
        }
    };
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(assets != null)
            assets.close();
        if(db != null)
            db.close();
    }
}
