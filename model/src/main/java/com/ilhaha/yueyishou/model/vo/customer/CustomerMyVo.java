package com.ilhaha.yueyishou.model.vo.customer;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/29 21:25
 * @Version 1.0
 *
 * 顾客我的页面初始信息Vo
 */
@Data
public class CustomerMyVo {

    /**
     * 参与回收次数
     */
    private Long recycleCount;

    /**
     * 回收总收入
     */
    private BigDecimal totalRecycleAmount;

    /**
     * 环保奖励金总余额
     */
    private BigDecimal bonusBalanceAmount;

    /**
     * 碳排放量
     */
    private BigDecimal carbonEmissions;
}
