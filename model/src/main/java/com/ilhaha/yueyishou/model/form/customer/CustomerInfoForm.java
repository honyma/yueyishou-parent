package com.ilhaha.yueyishou.model.form.customer;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CustomerInfoForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -8593210891998257352L;

    /**
     * 微信openId
     */
    private String wxOpenId;
    /**
     * 客户昵称
     */
    private String nickname;
    /**
     * 电话
     */
    private String phone;
    /**
     * 1有效，2禁用
     */
    private Integer status;
}
