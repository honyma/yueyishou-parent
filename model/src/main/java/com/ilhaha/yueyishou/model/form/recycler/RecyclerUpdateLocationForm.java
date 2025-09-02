package com.ilhaha.yueyishou.model.form.recycler;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/11 15:07
 * @Version 1.0
 * <p>
 * 更新回收员的地理位置表单
 */
@Data
public class RecyclerUpdateLocationForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -1305936561306955245L;

    /**
     * 回收员Id
     */
    private Long recyclerId;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
