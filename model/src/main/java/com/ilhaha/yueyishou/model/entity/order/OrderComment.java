package com.ilhaha.yueyishou.model.entity.order;


import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("t_order_comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
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
    private Integer rate;
    /**
     * 评论内容
     */
    private String content;

}
