package com.ilhaha.yueyishou.model.form.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ilhaha
 * @Create 2024/9/26 22:22
 * @Version 1.0
 */
@Data
public class CustomerUpdateBaseInfoForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1691944076020017396L;

    /**
     * 顾客id
     */
    private Long id;
    /**
     * 客户昵称
     */
    @NotBlank
    private String nickname;
    /**
     * 头像
     */
    @NotBlank
    private String avatarUrl;
}
