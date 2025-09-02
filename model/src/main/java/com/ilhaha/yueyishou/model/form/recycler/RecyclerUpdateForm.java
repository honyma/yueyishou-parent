package com.ilhaha.yueyishou.model.form.recycler;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/24 14:47
 * @Version 1.0
 * <p>
 * 修改回收员评分表单类
 */
@Data
public class RecyclerUpdateForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -3734068594156678805L;

    /**
     * 回收员Id
     */
    private Long recyclerId;

    /**
     * 评分
     */
    private BigDecimal rate;
}
