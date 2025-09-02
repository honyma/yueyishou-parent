package com.ilhaha.yueyishou.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author ilhaha
 * @Create 2024/9/2 22:00
 * @Version 1.0
 * <p>
 * 奖励金明细类型
 */
@Getter
public enum BonusDetailType {

    //奖励金明细类型：1-回收收入 2-奖励金提现扣减 3-签到得奖励金 4-抽奖得奖励金 5-商家手动补发
    ITEMS_RECYCLE_INCOME(1, "回收收入"),
    WITHDRAWAL_DEDUCTION(2, "奖励金提现扣减"),
    SIGN_IN_TO_GET(3, "签到得奖励金"),
    LOTTERY_TO_GET(4, "抽奖得奖励金"),
    MERCHANT_MANUAL_DELIVER(5, "商家手动补发"),
    ;


    @EnumValue
    private Integer type;
    private String comment;

    BonusDetailType(Integer type, String comment) {
        this.type = type;
        this.comment = comment;
    }


}
