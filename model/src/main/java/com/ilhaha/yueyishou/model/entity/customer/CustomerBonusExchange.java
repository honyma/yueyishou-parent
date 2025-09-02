package com.ilhaha.yueyishou.model.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("t_customer_bonus_exchange")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerBonusExchange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 交易编号
     */
    private String tradeNo;
    /**
     * 状态[0-审核中，1-提现中 2-成功 3-失败]
     */
    private Integer status;
}
