package com.ilhaha.yueyishou.model.form.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ilhaha
 * @Create 2024/10/23 23:06
 * @Version 1.0
 * <p>
 * 查询顾客账户信息表单类
 */
@Data
public class CustomerAccountForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 4728812491240340966L;

    /**
     * 顾客Id
     */
    private Long customerId;

    /**
     * 明细日期
     */
    @NotBlank
    private String detailsTime;
}
