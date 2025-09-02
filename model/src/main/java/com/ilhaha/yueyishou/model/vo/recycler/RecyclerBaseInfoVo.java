package com.ilhaha.yueyishou.model.vo.recycler;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/9/26 14:53
 * @Version 1.0
 */
@Data
public class RecyclerBaseInfoVo {
    /**
     * 回收员工号
     */
    private String recyclerNo;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 电话
     */
    private String phone;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 1:男 2：女
     */
    private Integer gender;
    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态，1正常，0禁用
     */
    private Integer status;
}
