package com.ilhaha.yueyishou.model.form.recycler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ilhaha
 * @Create 2024/9/11 17:35
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RecyclerQueryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 7261820915435459165L;

    /**
     * 回收员工号
     */
    private String recyclerNo;
    /**
     * 电话
     */
    private String phone;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idcardNo;
    /**
     * 状态，1正常，0禁用
     */
    private Integer status;
}
