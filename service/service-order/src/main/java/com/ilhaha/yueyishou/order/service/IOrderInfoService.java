package com.ilhaha.yueyishou.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.order.OrderInfo;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;

import java.util.List;

public interface IOrderInfoService extends IService<OrderInfo> {
    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    Boolean arrive(String orderNo);

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    List<OrderMatchingVo> retrieveMatchingOrders(OrderMatchingForm orderMatchingForm);

    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    Boolean settlement(OrderSettlementForm orderSettlementForm);

    /**
     * 顾客下单
     *
     * @param orderPlaceForm
     * @return
     */
    Boolean placeOrder(OrderPlaceForm orderPlaceForm);

    /**
     * 顾客取消订单
     *
     * @param orderCancelForm
     * @return
     */
    Boolean cancelOrder(OrderCancelForm orderCancelForm);

    /**
     * 顾客根据订单号获取订单详情
     *
     * @param orderNo
     * @return
     */
    OrderDetailsVo getOrderDetail(String orderNo);

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    OrderMyVo getMyCenterInfo(Long customerId);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 订单分页列表查询
     *
     * @param orderMgrQueryForm
     * @return
     */
    Page<OrderMgrQueryVo> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize);
}
