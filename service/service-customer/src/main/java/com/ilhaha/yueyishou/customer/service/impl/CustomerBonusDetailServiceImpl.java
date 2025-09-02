package com.ilhaha.yueyishou.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.customer.mapper.CustomerBonusDetailMapper;
import com.ilhaha.yueyishou.customer.service.ICustomerBonusDetailService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusDetail;
import org.springframework.stereotype.Service;

@Service
public class CustomerBonusDetailServiceImpl extends ServiceImpl<CustomerBonusDetailMapper, CustomerBonusDetail> implements ICustomerBonusDetailService {
}
