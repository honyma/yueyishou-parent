package com.ilhaha.yueyishou.model.vo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/29 21:30
 * @Version 1.0
 *
 * 顾客我的页面订单初始化信息
 */
@Data
public class OrderMyVo {

    /**
     * 参与回收次数
     */
    private Long recycleCount;
    /**
     * 碳排放量
     */
    private BigDecimal carbonEmissions;
}
