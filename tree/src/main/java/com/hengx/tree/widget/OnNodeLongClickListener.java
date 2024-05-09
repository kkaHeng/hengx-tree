package com.hengx.tree.widget;

import com.hengx.tree.base.TreeNode;

public interface OnNodeLongClickListener <E extends TreeNode> {
    
    boolean onLongClick(E node);
    
}
