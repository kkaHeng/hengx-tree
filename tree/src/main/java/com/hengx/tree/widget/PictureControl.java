package com.hengx.tree.widget;

import android.graphics.drawable.Drawable;

public interface PictureControl <E extends Object> {
    
    Drawable getDrawable();
    
    PictureControl setDrawable(Drawable drawable);
    
    E getView();
    
}
