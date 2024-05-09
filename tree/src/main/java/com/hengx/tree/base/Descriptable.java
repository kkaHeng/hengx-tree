package com.hengx.tree.base;

import android.graphics.drawable.Drawable;
import com.hengx.tree.widget.TreeViewHolder;

public interface Descriptable {
    
    CharSequence getTitle();
    
    CharSequence getDescription();
    
    TreeViewHolder getViewHolder();
    
    Drawable getIcon();
    
    Descriptable setTitle(CharSequence title);
    
    Descriptable setDescription(CharSequence description);
    
    Descriptable setViewHolder(TreeViewHolder viewHolder);
    
    Descriptable setIcon(Drawable icon);
    
}
