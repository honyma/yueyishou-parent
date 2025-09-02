package com.ilhaha.yueyishou.model.vo.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/9/11 17:26
 * @Version 1.0
 */
@Data
public class OrderMgrQueryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 5246207556688761137L;
    /**
     * 客户名称
     */
    private Long customerId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 客户地点
     */
    private String customerLocation;
    /**
     * 预约时间
     */
    private Date appointmentTime;
    /**
     * 回收员名称
     */
    private Long recyclerName;
    /**
     * 回收员接单时间
     */
    private Date acceptTime;
    /**
     * 回收员到达时间
     */
    private Date arriveTime;
    /**
     * 回收员付款结算时间
     */
    private Date settleTime;
    /**
     * 订单状态：1等待接单，2待上门，3服务中，4已完成，5订单已取消
     */
    private Integer status;

    private String statusDesc;
    /**
     * 取消订单信息
     */
    private String cancelMessage;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 订单备注信息
     */
    private String remark;
    /**
     * 订单联系人
     */
    private String contactPerson;
    /**
     * 订单联系电话
     */
    private String contactPhone;
    /**
     * 订单预计回收总金额(金额是一个区间)
     */
    private String estimatedAmount;
    /**
     * 订单实际回收总金额
     */
    private BigDecimal actualAmount;
    /**
     * 订单获得奖励金金额
     */
    private BigDecimal bonusAmount;
}
