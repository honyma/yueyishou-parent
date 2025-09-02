package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author ilhaha
 * @Create 2024/9/24 23:11
 * @Version 1.0
 */
@Getter
public enum UnitName {

    AMOUNT(1, "元"),
    QUANTITY(2, "个");

    @EnumValue
    private Integer value;

    private String name;

    UnitName(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static UnitName getEnumByValue(Integer value) {
        return Arrays.stream(UnitName.values()).filter(e -> Objects.equals(e.getValue(), value)).findAny().orElse(null);
    }
}
