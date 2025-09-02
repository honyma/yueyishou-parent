package com.ilhaha.yueyishou.model.form.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/28 21:15
 * @Version 1.0
 */
@Data
public class OrderPlaceForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 7100946210915989066L;

    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户地点
     */
    private String customerLocation;
    /**
     * 客户地点经度
     */
    private BigDecimal customerPointLongitude;
    /**
     * 客户地点纬度
     */
    private BigDecimal customerPointLatitude;
    /**
     * 预约时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date appointmentTime;
    /**
     * 实物照片
     */
    private String actualPhotos;
    /**
     * 订单类型
     */
    private Integer type;
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

    private List<OrderDetailForm> orderDetailFormList;

    @Data
    public static class OrderDetailForm implements Serializable {

        @Serial
        private static final long serialVersionUID = -8749609800079460491L;
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
         * 单位-用枚举
         */
        private Integer unitName;
        /**
         * 单件预计回收金额(金额是一个区间)
         */
        private String estimatedAmount;

        private String remark;
    }
}
