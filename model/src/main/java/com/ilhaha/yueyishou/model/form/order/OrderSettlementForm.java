package com.ilhaha.yueyishou.model.form.order;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/10/23 15:24
 * @Version 1.0
 * <p>
 * 结算订单表单类
 */
@Data
public class OrderSettlementForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -4449753494987197116L;

    /**
     * 回收员Id
     */
    private Long recyclerId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 结算金额
     */
    private BigDecimal amount;

    /**
     * 订单详情明细
     */
    private List<SettlementDetailForm> settlementDetailFormList;

    @Data
    public static class SettlementDetailForm implements Serializable {

        @Serial
        private static final long serialVersionUID = -7153684462327177718L;

        private Long orderDetailId;
        /**
         * 单价
         */
        private BigDecimal unitPrice;
        /**
         * 回收重量
         */
        private BigDecimal recycleWeigh;
    }
}
