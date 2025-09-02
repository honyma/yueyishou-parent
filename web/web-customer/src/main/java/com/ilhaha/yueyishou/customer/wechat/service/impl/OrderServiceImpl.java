package com.ilhaha.yueyishou.customer.wechat.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import com.ilhaha.yueyishou.customer.wechat.service.OrderService;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;
import com.ilhaha.yueyishou.order.client.OrderCommentFeignClient;
import com.ilhaha.yueyishou.order.client.OrderInfoFeignClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/9/28 20:29
 * @Version 1.0
 */
@Service(value = "cWechatOrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderInfoFeignClient orderInfoFeignClient;

    @Resource
    private OrderCommentFeignClient orderCommentFeignClient;

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    @Override
    public Result<Boolean> placeOrder(OrderPlaceForm orderPlaceForm) {
        orderPlaceForm.setCustomerId(AuthContextHolder.getCustomerId());
        return orderInfoFeignClient.placeOrder(orderPlaceForm);
    }

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    @Override
    public Result<Boolean> cancelOrder(OrderCancelForm orderCancelForm) {
        return orderInfoFeignClient.cancelOrder(orderCancelForm);
    }

    /**
     * 顾客评论
     *
     * @return
     */
    @Override
    public Result<Boolean> review(OrderReviewForm orderReviewForm) {
        return orderCommentFeignClient.review(orderReviewForm);
    }

    /**
     * 顾客根据订单号获取订单详情
     */
    @Override
    public Result<OrderDetailsVo> getOrderDetail(String orderNo) {
        return orderInfoFeignClient.getOrderDetail(orderNo);
    }

    /**
     * 顾客获取对应订单列表
     *
     * @return
     */
    @Override
    public Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize) {
        orderMgrQueryForm.setCustomerId(AuthContextHolder.getCustomerId());
        return orderInfoFeignClient.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
