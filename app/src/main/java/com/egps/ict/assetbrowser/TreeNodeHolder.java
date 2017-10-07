package com.egps.ict.assetbrowser;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.model.TreeNode.BaseNodeViewHolder;

/**
 * Created by ICT on 2/22/2017.
 */

public class TreeNodeHolder extends BaseNodeViewHolder<Asset> {
    private PrintView arrowView;
    private TreeNode node;

    public TreeNodeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, Asset asset) {
        int level = node.getLevel();
        this.node = node;

        final View view = LayoutInflater.from(context).inflate(R.layout.layout_icon_node, null, false);

        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        if (level == 1) {
            tvValue.setTypeface(null, Typeface.BOLD);
            arrowView.setIconText(R.string.ic_flash_on);
        } else {
            if (!asset.hasChildren()) {
                arrowView.setIconText(R.string.ic_label_outline);
            } else {
                arrowView.setIconText(R.string.ic_add);
            }
        }
        tvValue.setText(asset.hasChildren() ? asset.getShortDesc() : asset.getId());
        return view;
    }

    @Override
    public void toggle(boolean active) {
        if (node.getLevel() != 1) {
            if (((Asset) node.getValue()).hasChildren()) {
                arrowView.setIconText(context.getResources().getString(active ? R.string.ic_remove : R.string.ic_add));
            }
        }
    }
}
