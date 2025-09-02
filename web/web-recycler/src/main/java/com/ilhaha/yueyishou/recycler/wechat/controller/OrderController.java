package com.ilhaha.yueyishou.recycler.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.anno.WechatLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;
import com.ilhaha.yueyishou.recycler.wechat.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/10/12 16:41
 * @Version 1.0
 */
@RestController(value = "rWechatOrderController")
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/settlement")
    public Result<Boolean> settlement(@RequestBody OrderSettlementForm orderSettlementForm) {
        return orderService.settlement(orderSettlementForm);
    }

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/arrive/{orderNo}")
    public Result<Boolean> arrive(@PathVariable("orderNo") String orderNo) {
        return orderService.arrive(orderNo);
    }

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/matching")
    public Result<List<OrderMatchingVo>> retrieveMatchingOrders(@RequestBody OrderMatchingForm orderMatchingForm) {
        return orderService.retrieveMatchingOrders(orderMatchingForm);
    }

    /**
     * 回收员获取订单列表
     *
     * @return
     */
    @WechatLoginVerification
    @PostMapping("recycler/list")
    public Result<Page<OrderMgrQueryVo>> queryPageList(@RequestBody OrderMgrQueryForm orderMgrQueryForm,
                                                      @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                      @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return orderService.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
