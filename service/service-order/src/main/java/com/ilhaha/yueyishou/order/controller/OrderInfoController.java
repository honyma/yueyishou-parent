package com.ilhaha.yueyishou.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.model.entity.order.OrderInfo;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;
import com.ilhaha.yueyishou.order.service.IOrderInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderInfo")
@Slf4j
public class OrderInfoController {

    @Resource
    private IOrderInfoService orderInfoService;

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    @PostMapping("/arrive/{orderNo}")
    public Result<Boolean> arrive(@PathVariable("orderNo") String orderNo) {
        return Result.ok(orderInfoService.arrive(orderNo));
    }

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    @PostMapping("/matching")
    public Result<List<OrderMatchingVo>> retrieveMatchingOrders(@RequestBody OrderMatchingForm orderMatchingForm) {
        return Result.ok(orderInfoService.retrieveMatchingOrders(orderMatchingForm));
    }

    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    @PostMapping("/settlement")
    public Result<Boolean> settlement(@RequestBody OrderSettlementForm orderSettlementForm) {
        return Result.ok(orderInfoService.settlement(orderSettlementForm));
    }

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    @PostMapping("/place")
    public Result<Boolean> placeOrder(@RequestBody OrderPlaceForm orderPlaceForm) {
        return Result.ok(orderInfoService.placeOrder(orderPlaceForm));
    }

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    @PostMapping("/cancel")
    public Result<Boolean> cancelOrder(@RequestBody OrderCancelForm orderCancelForm) {
        return Result.ok(orderInfoService.cancelOrder(orderCancelForm));
    }

    /**
     * 顾客根据订单号获取订单详情
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/detail/{orderNo}")
    public Result<OrderDetailsVo> getOrderDetail(@PathVariable("orderNo") String orderNo) {
        return Result.ok(orderInfoService.getOrderDetail(orderNo));
    }

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/my/center/info/{customerId}")
    public Result<OrderMyVo> getMyCenterInfo(@PathVariable Long customerId) {
        return Result.ok(orderInfoService.getMyCenterInfo(customerId));
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 订单分页列表查询
     *
     * @param orderMgrQueryForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/list")
    public Result<Page<OrderMgrQueryVo>> queryPageList(@RequestBody OrderMgrQueryForm orderMgrQueryForm,
                                                       @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return Result.ok(orderInfoService.queryPageList(orderMgrQueryForm, pageNo, pageSize));
    }

    /**
     * 通过id查询订单信息（共用）
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<OrderInfo> queryById(@RequestParam(name = "id") String id) {
        OrderInfo orderInfo = orderInfoService.getById(id);
        if (orderInfo == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(orderInfo);
    }
}
