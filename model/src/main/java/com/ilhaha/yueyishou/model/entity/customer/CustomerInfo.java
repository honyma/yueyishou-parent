package com.ilhaha.yueyishou.model.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("t_customer_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 微信openId
     */
    private String wxOpenId;
    /**
     * 客户昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 电话
     */
    private String phone;
    /**
     * 1有效，0禁用
     */
    private Integer status;
}
