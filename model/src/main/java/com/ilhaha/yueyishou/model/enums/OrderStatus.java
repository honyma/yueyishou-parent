package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author ilhaha
 * @Create 2024/9/2 22:00
 * @Version 1.0
 * <p>
 * 订单状态
 */
@Getter
public enum OrderStatus {

    //订单状态：1等待接单，2待上门，3服务中，4已完成，5已取消
    WAITING_ACCEPT(1, "等待接单"),
    WAITING_SERVICE(2, "待上门"),
    SERVICE_PROCESSING(3, "服务中"),
    SERVICE_COMPLETE(4, "已完成"),
    ORDER_CANCELED(5, "订单已取消"),
    ;


    @EnumValue
    private Integer status;
    private String comment;

    OrderStatus(Integer status, String comment) {
        this.status = status;
        this.comment = comment;
    }

    public static OrderStatus getEnumByStatus(Integer status) {
        return Arrays.stream(OrderStatus.values()).filter(e -> Objects.equals(e.getStatus(), status)).findAny().orElse(null);
    }
}
