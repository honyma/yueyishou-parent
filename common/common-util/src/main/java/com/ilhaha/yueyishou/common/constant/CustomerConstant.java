package com.ilhaha.yueyishou.common.constant;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/9/22 13:33
 * @Version 1.0
 */
@Data
public class CustomerConstant {

    /**
     * 顾客默认头像路径
     */
    public static final String DEFAULT_AVATAR = "https://ilhaha-yueyishou-1314167533.cos.ap-beijing.myqcloud.com/category/bf039a8ad42a40788d32d22c20d416d2.jpg";

    /**
     * 顾客默认名称
     */
    public static final String DEFAULT_NICKNAME = "小蜜蜂达人";

    /**
     * 用户最少提现金额
     */
    public static final BigDecimal MIN_WITHDRAW_AMOUNT = new BigDecimal("2.0");

    /**
     * 用户最大提现金额
     */
    public static final BigDecimal MAX_WITHDRAW_AMOUNT = new BigDecimal("200.0");

    /**
     * 用户奖励金计算倍数
     */
    public static final BigDecimal BONUS_CALCULATE_DIVIDE = new BigDecimal("100.0");
}
