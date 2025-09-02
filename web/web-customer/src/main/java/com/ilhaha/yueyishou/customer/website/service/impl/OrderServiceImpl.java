package com.ilhaha.yueyishou.customer.website.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.website.service.OrderService;
import com.ilhaha.yueyishou.model.form.order.OrderMgrQueryForm;
import com.ilhaha.yueyishou.model.vo.order.OrderMgrQueryVo;
import com.ilhaha.yueyishou.order.client.OrderInfoFeignClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/9/28 20:29
 * @Version 1.0
 */
@Service(value = "cWebsiteOrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderInfoFeignClient orderInfoFeignClient;

    /**
     * 订单列表查询
     *
     * @return
     */
    @Override
    public Result<Page<OrderMgrQueryVo>> queryPageList(OrderMgrQueryForm orderMgrQueryForm, Integer pageNo, Integer pageSize) {
        return orderInfoFeignClient.queryPageList(orderMgrQueryForm, pageNo, pageSize);
    }
}
