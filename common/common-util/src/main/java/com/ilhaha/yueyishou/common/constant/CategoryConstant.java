package com.ilhaha.yueyishou.common.constant;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/9/6 17:26
 * @Version 1.0
 * <p>
 * 废品品类常量
 */
public class CategoryConstant {

    /**
     * 废品一级品类ID
     */
    public static final Integer FIRST_LEVEL_CATEGORY_ID = 0;

    /**
     * 非称重物品都按5kg碳排放量
     */
    public static final BigDecimal NON_WEIGHING_ITEMS_CARBON_EMISSIONS = new BigDecimal("5");
}
