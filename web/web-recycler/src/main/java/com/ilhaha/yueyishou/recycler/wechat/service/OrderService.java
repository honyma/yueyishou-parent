package com.ilhaha.yueyishou.recycler.wechat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.order.*;
import com.ilhaha.yueyishou.model.vo.order.*;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/10/12 16:41
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 回收员回收完后对订单进行结算
     *
     * @param orderSettlementForm
     * @return
     */
    Result<Boolean> settlement(OrderSettlementForm orderSettlementForm);

    /**
     * 回收员到达回收点开始服务订单
     *
     * @param orderNo
     * @return
     */
    Result<Boolean> arrive(String orderNo);

    /**
     * 回收员获取符合接单的订单
     *
     * @param orderMatchingForm
     * @return
     */
    Result<List<OrderMatchingVo>> retrieveMatchingOrders(OrderMatchingForm orderMatchingForm);

    /**
     * 回收员获取对应订单列表
     *
     * @return
     */
    Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize);
}
