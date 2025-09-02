package com.ilhaha.yueyishou.recycler.wechat.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;
import com.ilhaha.yueyishou.order.client.OrderInfoFeignClient;
import com.ilhaha.yueyishou.recycler.wechat.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/10/12 16:41
 * @Version 1.0
 */
@Service(value = "rWechatOrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderInfoFeignClient orderInfoFeignClient;

    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    @Override
    public Result<Boolean> settlement(OrderSettlementForm orderSettlementForm) {
        orderSettlementForm.setRecyclerId(AuthContextHolder.getRecyclerId());
        return orderInfoFeignClient.settlement(orderSettlementForm);
    }

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    @Override
    public Result<Boolean> arrive(String orderNo) {
        return orderInfoFeignClient.arrive(orderNo);
    }

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    @Override
    public Result<List<OrderMatchingVo>> retrieveMatchingOrders(OrderMatchingForm orderMatchingForm) {
        orderMatchingForm.setCustomerId(AuthContextHolder.getCustomerId());
        orderMatchingForm.setRecyclerId(AuthContextHolder.getRecyclerId());
        return orderInfoFeignClient.retrieveMatchingOrders(orderMatchingForm);
    }

    /**
     * 回收员获取对应订单列表
     *
     * @return
     */
    @Override
    public Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize) {
        orderMgrQueryForm.setRecyclerId(AuthContextHolder.getRecyclerId());
        return orderInfoFeignClient.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
