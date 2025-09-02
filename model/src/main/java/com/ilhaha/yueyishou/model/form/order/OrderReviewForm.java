package com.ilhaha.yueyishou.model.form.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ilhaha
 * @Create 2024/10/24 14:37
 * @Version 1.0
 * <p>
 * 顾客评论表单类
 */
@Data
public class OrderReviewForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1499441770869891105L;

    /**
     * 订单号
     */
    @NotBlank
    private String orderNo;
    /**
     * 回收员ID
     */
    private Long recyclerId;
    /**
     * 顾客ID
     */
    private Long customerId;
    /**
     * 评分，1星~5星
     */
    @NotNull
    private Integer rate;
    /**
     * 评论内容
     */
    @NotBlank
    private String content;
}
