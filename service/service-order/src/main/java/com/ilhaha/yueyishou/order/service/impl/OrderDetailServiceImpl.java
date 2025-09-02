package com.ilhaha.yueyishou.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.model.entity.order.OrderDetail;
import com.ilhaha.yueyishou.order.mapper.OrderDetailMapper;
import com.ilhaha.yueyishou.order.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/11/19 11:49
 * @Version 1.0
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
}
