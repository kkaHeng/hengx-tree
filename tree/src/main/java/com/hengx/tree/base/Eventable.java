package com.hengx.tree.base;

import com.hengx.tree.widget.OnNodeClickListener;
import com.hengx.tree.widget.OnNodeExpandListener;
import com.hengx.tree.widget.OnNodeLongClickListener;

public interface Eventable {
    
    OnNodeClickListener getOnNodeClickListener();
    
    OnNodeLongClickListener getOnNodeLongClickListener();
    
    OnNodeExpandListener getOnNodeExpandListener();
    
    Eventable setOnNodeClickListener(OnNodeClickListener click);
    
    Eventable setOnNodeLongClickListener(OnNodeLongClickListener longClick);
    
    Eventable setOnNodeExpandListener(OnNodeExpandListener expand);
}
