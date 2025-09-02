package com.ilhaha.yueyishou.customer.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.anno.WechatLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.wechat.service.OrderService;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/28 20:28
 * @Version 1.0
 */
@RestController(value = "cWechatOrderController")
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    @PostMapping("/place")
    @WechatLoginVerification
    public Result<Boolean> placeOrder(@RequestBody OrderPlaceForm orderPlaceForm) {
        return orderService.placeOrder(orderPlaceForm);
    }

    /**
     * 顾客评论
     *
     * @return
     */
    @PostMapping("/review")
    @WechatLoginVerification
    public Result<Boolean> review(@RequestBody OrderReviewForm orderReviewForm) {
        return orderService.review(orderReviewForm);
    }

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    @PostMapping("/cancel")
    @WechatLoginVerification
    public Result<Boolean> cancelOrder(@RequestBody OrderCancelForm orderCancelForm) {
        return orderService.cancelOrder(orderCancelForm);
    }

    /**
     * 顾客根据订单号获取订单详情(共用)
     */
    @GetMapping("/detail/{orderNo}")
    @WechatLoginVerification
    public Result<OrderDetailsVo> getOrderDetail(@PathVariable("orderNo") String orderNo) {
        return orderService.getOrderDetail(orderNo);
    }

    /**
     * 顾客获取订单列表
     *
     * @return
     */
    @WechatLoginVerification
    @PostMapping("customer/list")
    public Result<Page<OrderMgrQueryVo>> queryPageList(@RequestBody OrderMgrQueryForm orderMgrQueryForm,
                                                       @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return orderService.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
