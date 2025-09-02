package com.ilhaha.yueyishou.model.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("t_order_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 订单号
     */
    private String orderNo;

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
