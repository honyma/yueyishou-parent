package com.ilhaha.yueyishou.model.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/29 19:57
 * @Version 1.0
 */
@Data
public class OrderDetailsVo {
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 客户ID
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
     * 第一张实物照片
     */
    private String actualPhoto;
    /**
     * 实物照片集合
     */
    private List<String> actualPhotoList;
    /**
     * 回收员接单时间
     */
    private Date acceptTime;
    /**
     * 回收员到达时间
     */
    private Date arriveTime;
    /**
     * 订单状态：1等待接单，2待上门，3服务中，4已完成，5订单已取消
     */
    private Integer status;
    private String statusDesc;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 取消订单信息
     */
    private String cancelMessage;
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
    /**
     * 回收员相距顾客多远
     */
    private BigDecimal distance;
    /**
     * 回收员ID
     */
    private Long recyclerId;
    /**
     * 回收员名称
     */
    private Long recyclerName;
    /**
     * 回收员付款结算时间
     */
    private Date settleTime;

    private List<OrderDetail> orderDetailList;

    @Data
    public static class OrderDetail {
        /**
         * 订单回收分类父ID
         */
        private Long parentCategoryId;
        /**
         * 订单回收分类父名称
         */
        private String parentCategoryName;
        /**
         * 订单回收分类子ID
         */
        private Long sonCategoryId;
        /**
         * 订单回收分类子名称
         */
        private String sonCategoryName;
        /**
         * 单价
         */
        private BigDecimal unitPrice;
        /**
         * 单位
         */
        private String unitName;
        /**
         * 回收重量
         */
        private BigDecimal recycleWeigh;
        /**
         * 单件预计回收金额(金额是一个区间)
         */
        private String estimatedAmount;
        /**
         * 单件实际回收金额
         */
        private BigDecimal recycleAmount;

        private String remark;
    }
}
