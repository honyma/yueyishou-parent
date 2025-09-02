package com.ilhaha.yueyishou.model.vo.order;

import lombok.Data;

import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/10/24 15:37
 * @Version 1.0
 * <p>
 * 订单评论Vo
 */
@Data
public class OrderCommentVo {
    /**
     * 回收员ID
     */
    private Long recyclerId;
    /**
     * 顾客ID
     */
    private Long customerId;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date reviewTime;

    /**
     * 评价等级
     */
    private Integer rate;

}
