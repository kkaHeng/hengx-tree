package com.hengx.tree.widget;

import com.hengx.tree.base.TreeNode;

public interface OnNodeClickListener<E extends TreeNode> {
    
    void onClick(E node);
    
}
