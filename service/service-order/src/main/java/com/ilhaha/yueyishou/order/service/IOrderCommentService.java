package com.ilhaha.yueyishou.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.order.OrderComment;
import com.ilhaha.yueyishou.model.form.order.OrderCommentForm;
import com.ilhaha.yueyishou.model.form.order.OrderReviewForm;
import com.ilhaha.yueyishou.model.vo.order.OrderCommentVo;


public interface IOrderCommentService extends IService<OrderComment> {

    /**
     * 顾客评价
     *
     * @return
     */
    Boolean review(OrderReviewForm orderReviewForm);

    /**
     * 查询订单评论详情
     *
     * @param orderNo
     * @return
     */
    OrderCommentVo getCommentDetail(String orderNo);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 查询评论信息列表
     *
     * @param orderCommentForm
     * @return
     */
    Page<OrderComment> queryPageList(OrderCommentForm orderCommentForm, Integer pageNo, Integer pageSize);
}
