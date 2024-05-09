package com.hengx.tree.widget;

import com.hengx.tree.base.TreeNode;

public interface OnNodeExpandListener <E extends TreeNode> {
    
    boolean onExpand(E node);
    
}
