package com.ilhaha.yueyishou.model.form.customer;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/23 22:15
 * @Version 1.0
 * <p>
 * 顾客结算表单类
 */
@Data
public class CustomerSettlementForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 8170739335760244962L;

    /**
     * 顾客Id
     */
    private Long customerId;

    /**
     * 关联单号
     */
    private String associatedNo;

    /**
     * 支出/收入金额
     */
    private BigDecimal amount;

    /**
     * 结算类型
     */
    private Integer type;
}
