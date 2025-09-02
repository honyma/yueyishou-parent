package com.ilhaha.yueyishou.model.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("t_customer_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 账户环保奖励金余额
     */
    private BigDecimal bonusBalanceAmount;
    /**
     * 回收总收入
     */
    private BigDecimal totalRecycleIncome;
}
