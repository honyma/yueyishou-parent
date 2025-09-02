package com.ilhaha.yueyishou.customer.wechat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;


/**
 * @Author ilhaha
 * @Create 2024/9/28 20:29
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    Result<Boolean> placeOrder(OrderPlaceForm orderPlaceForm);

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    Result<Boolean> cancelOrder(OrderCancelForm orderCancelForm);

    /**
     * 顾客评论
     *
     * @return
     */
    Result<Boolean> review(OrderReviewForm orderReviewForm);

    /**
     * 顾客根据订单号获取订单详情
     */
    Result<OrderDetailsVo> getOrderDetail(String orderNo);

    /**
     * 顾客获取对应订单列表
     *
     * @return
     */
    Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize);
}
