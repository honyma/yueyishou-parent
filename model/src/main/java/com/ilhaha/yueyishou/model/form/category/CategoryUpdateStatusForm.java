package com.ilhaha.yueyishou.model.form.category;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/7 14:04
 * @Version 1.0
 */
@Data
public class CategoryUpdateStatusForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 6890003960695980204L;

    /**
     * 品类id集合
     */
    private List<Long> categoryIds;

    /**
     * 状态
     */
    private Integer status;
}
