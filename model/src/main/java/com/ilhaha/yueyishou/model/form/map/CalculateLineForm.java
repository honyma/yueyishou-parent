package com.ilhaha.yueyishou.model.form.map;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/13 21:35
 * @Version 1.0
 */
@Data
public class CalculateLineForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1873946758653715057L;

    /**
     * 回收员Id
     */
    private Long recyclerId;

    /**
     * 顾客Id
     */
    private Long customerId;

    /**
     * 回收点地点经度
     */
    private BigDecimal endPointLongitude;

    /**
     * 回收点点纬度
     */
    private BigDecimal endPointLatitude;
}
