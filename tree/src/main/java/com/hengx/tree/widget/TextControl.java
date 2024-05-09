package com.hengx.tree.widget;

public interface TextControl <E extends Object> {
    
    CharSequence getText();
    
    TextControl setText(CharSequence text);
    
    E getView();
    
}
