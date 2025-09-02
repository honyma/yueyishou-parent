package com.ilhaha.yueyishou.common.constant;

import lombok.Data;

/**
 * @Author ilhaha
 * @Create 2024/10/9 22:14
 * @Version 1.0
 * <p>
 * 订单常量
 */
@Data
public class OrderConstant {
    /**
     * 取消订单操作人：顾客
     */
    public static final String CUSTOMER_CANCELS_ORDER = "customer";

    /**
     * 取消订单操作人：回收员
     */
    public static final String RECYCLER_CANCELS_ORDER = "recycler";
}
