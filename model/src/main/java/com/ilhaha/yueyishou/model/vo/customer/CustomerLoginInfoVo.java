package com.ilhaha.yueyishou.model.vo.customer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/9/22 13:46
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerLoginInfoVo {
    /**
     * 头像路径
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickname;

    private Date createTime;
}
