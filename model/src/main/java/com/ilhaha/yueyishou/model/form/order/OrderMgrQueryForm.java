package com.ilhaha.yueyishou.model.form.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/9/11 17:35
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderMgrQueryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -4315883936643694460L;

    /**
     * 顾客ID
     */
    private Long customerId;

    /**
     * 回收员Id
     */
    private Long recyclerId;

    /**
     * 下单联系人电话
     */
    private String phoneNumber;

    /**
     * 接单回收员名称
     */
    private String recyclerName;

    /**
     * 预约时间
     */
    private Date appointmentTime;

    /**
     * 订单状态
     */
    private Integer status;
}
