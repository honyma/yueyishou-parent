package com.ilhaha.yueyishou.model.form.recycler;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/8 14:11
 * @Version 1.0
 */
@Data
public class RecyclerUpdateStatusForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -3992439944609702569L;

    /**
     * 回收员id集合
     */
    private List<Long> RecyclerIds;

    /**
     * 状态
     */
    private Integer status;
}
