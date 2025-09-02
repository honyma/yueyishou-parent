package com.ilhaha.yueyishou.model.form.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class OrderCommentForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -707323607467842984L;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 回收员ID
     */
    private Long recyclerId;
    /**
     * 顾客ID
     */
    private Long customerId;
}
