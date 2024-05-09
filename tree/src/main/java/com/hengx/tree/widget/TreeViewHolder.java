package com.hengx.tree.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

public class TreeViewHolder extends RecyclerView.ViewHolder {
    
    public TextControl title, description;
    public PictureControl expandButton, icon;
    public ViewGroup layout;
    
    public TreeViewHolder(View view) {
    	super(view);
    }
    
}
