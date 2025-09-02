package com.ilhaha.yueyishou.customer.website.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.client.CustomerAccountFeignClient;
import com.ilhaha.yueyishou.customer.website.service.CustomerService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:33
 * @Version 1.0
 */
@Service(value = "cWebsiteCustomerServiceImpl")
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerAccountFeignClient customerAccountFeignClient;

    /**
     * 账户列表查询
     *
     * @param customerAccountForm
     * @return
     */
    @Override
    public Result<Page<CustomerAccount>> queryPageList(CustomerAccountForm customerAccountForm, Integer pageNo, Integer pageSize) {
        return customerAccountFeignClient.queryPageList(customerAccountForm, pageNo, pageSize);
    }
}
