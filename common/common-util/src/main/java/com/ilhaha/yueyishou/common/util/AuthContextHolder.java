package com.ilhaha.yueyishou.common.util;

/**
 * 获取当前用户信息帮助类
 */
public class AuthContextHolder {

    private static ThreadLocal<Long> customerId = new ThreadLocal<>();
    private static ThreadLocal<Long> recyclerId = new ThreadLocal<>();
    private static ThreadLocal<Long> operatorId = new ThreadLocal<>();

    // 设置 customerId
    public static void setCustomerId(Long userId) {
        customerId.set(userId);
    }

    // 获取 customerId
    public static Long getCustomerId() {
        return customerId.get();
    }

    // 移除 customerId
    public static void removeCustomerId() {
        customerId.remove();
    }

    // 设置 recyclerId
    public static void setRecyclerId(Long recyclerID) {
        recyclerId.set(recyclerID);
    }

    // 获取 recyclerId
    public static Long getRecyclerId() {
        return recyclerId.get();
    }

    // 移除 recyclerId
    public static void removeRecyclerId() {
        recyclerId.remove();
    }

    // 设置 operatorId
    public static void setOperatorId(Long operatorID) {
        operatorId.set(operatorID);
    }

    // 获取 operatorId
    public static Long getOperatorId() {
        return operatorId.get();
    }

    // 移除 operatorId
    public static void removeOperatorId() {
        operatorId.remove();
    }
}

