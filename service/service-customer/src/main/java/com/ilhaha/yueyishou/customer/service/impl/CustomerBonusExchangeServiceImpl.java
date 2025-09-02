package com.ilhaha.yueyishou.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.customer.mapper.CustomerBonusExchangeMapper;
import com.ilhaha.yueyishou.customer.service.ICustomerBonusExchangeService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import org.springframework.stereotype.Service;

@Service
public class CustomerBonusExchangeServiceImpl extends ServiceImpl<CustomerBonusExchangeMapper, CustomerBonusExchange>
        implements ICustomerBonusExchangeService {
}
