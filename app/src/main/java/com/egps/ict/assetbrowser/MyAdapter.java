package com.egps.ict.assetbrowser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ICT on 2/27/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Asset> mDataset;
    private Context ctx;

    public MyAdapter(Context ctx) {
        this.mDataset = new ArrayList<>();
        this.ctx = ctx;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tag;
        public TextView desc;
        public RelativeLayout layout;

        public ViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
            tag = (TextView) v.findViewById(R.id.idTag);
            desc = (TextView) v.findViewById(R.id.description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public void setData(ArrayList dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
    }

    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_node, parent, false);
        // set the view's size, margins, paddings and layout parameters...
        return new ViewHolder(layout);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tag.setText(mDataset.get(position).getId());
        holder.desc.setText(mDataset.get(position).getShortDesc());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AssetViewer.class);
                intent.putExtra("assetId", mDataset.get(holder.getAdapterPosition()).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                ctx.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}