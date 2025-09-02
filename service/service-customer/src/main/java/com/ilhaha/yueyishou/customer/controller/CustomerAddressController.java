package com.ilhaha.yueyishou.customer.controller;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.service.ICustomerAddressService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customerAddress")
public class CustomerAddressController {

    @Resource
    private ICustomerAddressService customerAddressService;

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    @GetMapping(value = "/list/{customerId}")
    public Result<List<CustomerAddress>> getAddressList(@PathVariable("customerId") Long customerId) {
        return Result.ok(customerAddressService.getAddressList(customerId));
    }

    /**
     * 查询地址信息
     *
     * @param addressId
     * @return
     */
    @GetMapping(value = "/detail/{addressId}")
    public Result<CustomerAddress> getAddressDetail(@PathVariable("addressId") Integer addressId) {
        return Result.ok(customerAddressService.getAddressDetail(addressId));
    }

    /**
     * 获取当前顾客的默认地址
     *
     * @param customerId
     * @return
     */
    @GetMapping("/default/{customerId}")
    public Result<CustomerAddress> getDefaultAddress(@PathVariable("customerId") Long customerId) {
        return Result.ok(customerAddressService.getDefaultAddress(customerId));
    }

    /**
     * 添加地址
     *
     * @param customerAddress
     * @return
     */
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CustomerAddress customerAddress) {
        customerAddressService.addAddress(customerAddress);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑地址信息
     *
     * @param customerAddress
     * @return
     */
    @PostMapping(value = "/edit")
    public Result<String> edit(@RequestBody CustomerAddress customerAddress) {
        customerAddressService.updateAddress(customerAddress);
        return Result.ok("编辑成功!");
    }
}
