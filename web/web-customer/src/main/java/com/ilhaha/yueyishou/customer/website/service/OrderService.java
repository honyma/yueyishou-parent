package com.ilhaha.yueyishou.customer.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.form.order.OrderMgrQueryForm;
import com.ilhaha.yueyishou.model.vo.order.OrderMgrQueryVo;


/**
 * @Author ilhaha
 * @Create 2024/9/28 20:29
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 订单列表查询
     *
     * @return
     */
    Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize);
}
