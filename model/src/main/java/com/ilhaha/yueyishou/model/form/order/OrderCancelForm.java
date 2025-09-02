package com.ilhaha.yueyishou.model.form.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ilhaha
 * @Create 2024/10/24 21:21
 * @Version 1.0
 * <p>
 * 接单后取消订单表单类
 */
@Data
public class OrderCancelForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 4809197518903230005L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 取消原因
     */
    private String cancelReason;
}
