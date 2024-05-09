package com.hengx.tree.base;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HasParentNodeException extends RuntimeException {
    
    public HasParentNodeException() {
        super(getDefaultErrorMessage());
    }
    
    public HasParentNodeException(String message) {
        super(getDefaultErrorMessage());
    }
    
    private static final Map<Locale, String> errorMessages = new HashMap<>();

    static {
        errorMessages.put(Locale.ENGLISH, "The node already has a parent node!");
        errorMessages.put(Locale.CHINESE, "该节点已经存在父节点！");
        // 可以继续添加其他语言的消息
    }
    
    private static String getDefaultErrorMessage() {
        Locale currentLocale = Locale.getDefault(); // 获取当前系统Locale
        String message = errorMessages.get(currentLocale); // 尝试获取当前Locale的消息
        if (message == null) {
            message = errorMessages.get(Locale.ROOT); // 使用默认Locale的消息
        }
        if (message == null) {
            message = "该节点已经存在父节点！";
        }
        return message;
    }
    
}
