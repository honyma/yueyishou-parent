package com.ilhaha.yueyishou.customer.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:32
 * @Version 1.0
 */
public interface CustomerService {
    /**
     * 账户列表查询
     *
     * @param customerAccountForm
     * @return
     */
    Result<Page<CustomerAccount>> queryPageList(CustomerAccountForm customerAccountForm, Integer pageNo, Integer pageSize);
}
