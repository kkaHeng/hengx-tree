package com.hengx.tree.base;

public interface Dataable {
    
    <E extends Object> E get(String key);
    
    Dataable put(String key, Object data);
    
    boolean has(String key);
    
    Dataable remove(String key);
    
    String[] keys();
    
    String getString(String key);
    
    int getInt(String key);
    
    float getFloat(String key);
    
    boolean getBoolean(String key);
    
    double getDouble(String key);
    
    long getLong(String key);
    
}
