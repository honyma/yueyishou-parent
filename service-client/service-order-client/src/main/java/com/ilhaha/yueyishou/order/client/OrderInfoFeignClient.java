package com.ilhaha.yueyishou.order.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.model.entity.order.OrderInfo;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.order.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/11 14:29
 * @Version 1.0
 */
@FeignClient("service-server")
public interface OrderInfoFeignClient {
    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    @PostMapping("/orderInfo/settlement")
    Result<Boolean> settlement(@RequestBody OrderSettlementForm orderSettlementForm);

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    @PostMapping("/orderInfo/arrive/{orderNo}")
    Result<Boolean> arrive(@PathVariable("orderNo") String orderNo);

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    @PostMapping("/orderInfo/matching")
    Result<List<OrderMatchingVo>> retrieveMatchingOrders(@RequestBody OrderMatchingForm orderMatchingForm);

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    @PostMapping("/orderInfo/place")
    Result<Boolean> placeOrder(@RequestBody OrderPlaceForm orderPlaceForm);

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    @PostMapping("/orderInfo/cancel")
    Result<Boolean> cancelOrder(@RequestBody OrderCancelForm orderCancelForm);

    /**
     * 顾客根据订单号获取订单详情
     */
    @GetMapping("/orderInfo/detail/{orderNo}")
    Result<OrderDetailsVo> getOrderDetail(@PathVariable("orderNo") String orderNo);

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/orderInfo/my/center/info/{customerId}")
    Result<OrderMyVo> getMyCenterInfo(@PathVariable Long customerId);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 订单分页列表查询
     *
     * @param orderMgrQueryForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/orderInfo/list")
    Result<Page<OrderMgrQueryVo>> queryPageList(@RequestBody OrderMgrQueryForm orderMgrQueryForm,
                                                @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(name = "pageSize", required = false) Integer pageSize);

    /**
     * 通过id查询订单信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/orderInfo/queryById")
    Result<OrderInfo> queryById(@RequestParam(name = "id") String id);
}
