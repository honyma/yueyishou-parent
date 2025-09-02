package com.ilhaha.yueyishou.model.vo.category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author ilhaha
 * @Create 2024/9/22 15:22
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CategorySubVo {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * icon
     */
    private String icon;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 单位
     */
    private String unitName;

    /**
     * 是否常用，1:是 0:否
     */
    private Integer common;
}
