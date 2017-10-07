package com.egps.ict.assetbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by ICT on 2/22/2017.
 */

public class TreeNodeHolder extends TreeNode.BaseNodeViewHolder<Object> {
    private TextView tvValue;
    private PrintView arrowView;

    public TreeNodeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, Object val) {
        IconTreeItem value = getValue(val);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        return view;
    }

    private IconTreeItem getValue(Object val) {
        if(val instanceof String) {
            IconTreeItem value = new IconTreeItem(R.string.ic_photo_library, (String) val);
            return value;
        } else {
            return (IconTreeItem) val;
        }
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
    }
}
