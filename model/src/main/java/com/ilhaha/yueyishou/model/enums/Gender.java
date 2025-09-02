package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author ilhaha
 * @Create 2024/9/24 23:11
 * @Version 1.0
 */
@Getter
public enum Gender {

    MALE(1,"男"),
    WOMAN(2,"女");

    @EnumValue
    private Integer value;

    private String comment;

    Gender(Integer value, String comment) {
        this.value = value;
        this.comment = comment;
    }
}
