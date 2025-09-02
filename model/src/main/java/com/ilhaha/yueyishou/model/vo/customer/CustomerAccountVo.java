package com.ilhaha.yueyishou.model.vo.customer;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/23 23:01
 * @Version 1.0
 * <p>
 * 顾客账户信息Vo
 */
@Data
public class CustomerAccountVo {

    /**
     * 账户环保奖励金总余额
     */
    private BigDecimal bonusBalanceAmount = BigDecimal.ZERO;
    /**
     * 回收总收入
     */
    private BigDecimal totalRecycleIncome = BigDecimal.ZERO;
}
