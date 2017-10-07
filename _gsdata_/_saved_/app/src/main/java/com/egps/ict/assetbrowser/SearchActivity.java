package com.egps.ict.assetbrowser;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {
    public static boolean active = false;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText searchBar;
    private Cursor searchResult;
    private TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (searchResult != null)
                searchResult.close();
            mAdapter.clear();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchResult = db.search(s);
            if (searchResult.moveToFirst()) {
                ArrayList<Asset> assets = new ArrayList<>();
                do {
                    if (searchResult.getString(searchResult.getColumnIndex(DBAdapter.ID)).length() < 6)
                        continue;
                    Asset asset = new Asset();
                    asset.setShortDesc(searchResult.getString(searchResult.getColumnIndex(DBAdapter.SHORT_DESC)));
                    asset.setId(searchResult.getString(searchResult.getColumnIndex(DBAdapter.ID)));
                    assets.add(asset);

                } while (searchResult.moveToNext());
                mAdapter.setData(assets);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        active = true;
        searchBar = new EditText(getBaseContext());
        searchBar.setSingleLine(true);
        searchBar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        searchBar.setLayoutParams(lp);
        searchBar.setHint("Search KKS..");
        searchBar.addTextChangedListener(textChangeListener);
        toolbar.addView(searchBar, 1);
        //We don't need it since we already are in the search activity
        fab.setVisibility(View.GONE);

        View content = getLayoutInflater().inflate(R.layout.content_search, childrenContainer, false);
        childrenContainer.addView(content, childrenIndex);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_results_rv);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        searchBar.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }
}
