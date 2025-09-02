package com.ilhaha.yueyishou.customer.website.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.anno.WebsiteLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.website.service.CustomerService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:29
 * @Version 1.0
 */
@RestController(value = "cWebsiteCustomerController")
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 账户列表查询
     *
     * @param customerAccountForm
     * @return
     */
    @WebsiteLoginVerification
    @GetMapping("/account/list")
    public Result<Page<CustomerAccount>> queryPageList(@RequestBody CustomerAccountForm customerAccountForm,
                                                       @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return customerService.queryPageList(customerAccountForm, pageNo, pageSize);
    }
}
