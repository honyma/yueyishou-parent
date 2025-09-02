package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author ilhaha
 * @Create 2024/9/2 22:30
 * @Version 1.0
 *
 * 回收员认证状态
 */
@Getter
public enum RecyclerAuthStatus {

    /**认证状态  1：审核中 2：认证通过 -1：认证未通过*/

    UNDER_REVIEW(1, "审核中"),
    CERTIFICATION_PASSED(2, "认证通过"),
    AUTHENTICATION_FAILED(-1, "顾客确定订单"),
  ;


    @EnumValue
    private Integer status;
    private String comment;

    RecyclerAuthStatus(Integer status, String comment) {
        this.status = status;
        this.comment = comment;
    }

}
