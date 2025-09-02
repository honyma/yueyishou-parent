package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 订单类型
 */
@Getter
public enum OrderType {

    //订单类型：1公益赠送，2付费订单
    CHARITY_DONATIONS(1, "公益赠送"),
    PAID_ORDERS(2, "付费订单"),
    ;

    @EnumValue
    private Integer type;
    private String comment;

    OrderType(Integer type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    public static OrderType getEnumByStatus(Integer type) {
        return Arrays.stream(OrderType.values()).filter(e -> Objects.equals(e.getType(), type)).findAny().orElse(null);
    }
}
