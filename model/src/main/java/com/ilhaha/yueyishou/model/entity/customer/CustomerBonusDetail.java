package com.ilhaha.yueyishou.model.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("t_customer_bonus_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerBonusDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 关联单号
     */
    private String associatedNo;
    /**
     * 奖励金明细类型：1-回收收入 2-奖励金提现扣减 3-签到得奖励金 4-抽奖得奖励金 5-商家手动补发
     */
    private Integer type;
    /**
     * 金额
     */
    private BigDecimal amount;
}
