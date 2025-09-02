package com.ilhaha.yueyishou.customer.wechat.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import com.ilhaha.yueyishou.customer.client.CustomerAccountFeignClient;
import com.ilhaha.yueyishou.customer.client.CustomerAddressFeignClient;
import com.ilhaha.yueyishou.customer.client.CustomerInfoFeignClient;
import com.ilhaha.yueyishou.customer.wechat.service.CustomerService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:33
 * @Version 1.0
 */
@Service(value = "cWechatCustomerServiceImpl")
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerInfoFeignClient customerInfoFeignClient;

    @Resource
    private CustomerAccountFeignClient customerAccountFeignClient;

    @Resource
    private CustomerAddressFeignClient customerAddressFeignClient;

    /**
     * 小程序登录
     *
     * @param code
     * @return
     */
    @Override
    public Result<String> login(String code) {
        // 1.远程调用,获取顾客登录token
        return customerInfoFeignClient.login(code);
    }

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    @Override
    public Result<Boolean> checkPhone(String code) {
        // 获取当前登录的顾客ID
        Long customerId = AuthContextHolder.getCustomerId();
        return customerInfoFeignClient.checkPhone(code, customerId);
    }

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @return
     */
    @Override
    public Result<CustomerLoginInfoVo> getLoginInfo() {
        // 获取当前登录的顾客ID
        Long customerId = AuthContextHolder.getCustomerId();
        // 远程调用获取顾客信息
        return customerInfoFeignClient.getLoginInfo(customerId);
    }

    /**
     * 获取顾客我的页面初始信息
     *
     * @return
     */
    @Override
    public Result<CustomerMyVo> getMyCenterInfo() {
        return customerInfoFeignClient.getMyCenterInfo(AuthContextHolder.getCustomerId());
    }

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    @Override
    public Result<Boolean> updateBaseInfo(CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm) {
        customerUpdateBaseInfoForm.setId(AuthContextHolder.getCustomerId());
        return customerInfoFeignClient.updateBaseInfo(customerUpdateBaseInfoForm);
    }

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    @Override
    public Result<Boolean> withdrawAccountBonus(CustomerWithdrawForm customerWithdrawForm) {
        customerWithdrawForm.setCustomerId(AuthContextHolder.getCustomerId());
        return customerAccountFeignClient.onWithdraw(customerWithdrawForm);
    }

    /**
     * 顾客查询提现记录
     *
     * @return
     */
    @Override
    public Result<Page<CustomerBonusExchange>> getWithdrawBonusList() {
        return customerAccountFeignClient.getWithdrawList(AuthContextHolder.getCustomerId());
    }

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    @Override
    public Result<List<CustomerAddress>> getAddressList() {
        Long customerId = AuthContextHolder.getCustomerId();
        return customerAddressFeignClient.getAddressList(customerId);
    }

    /**
     * 查询地址信息
     *
     * @return
     */
    @Override
    public Result<CustomerAddress> getAddressDetail(Integer addressId) {
        return customerAddressFeignClient.getAddressDetail(addressId);
    }

    /**
     * 获取当前顾客的默认地址
     *
     * @return
     */
    @Override
    public Result<CustomerAddress> getDefaultAddress() {
        return customerAddressFeignClient.getDefaultAddress(AuthContextHolder.getCustomerId());
    }

    /**
     * 新增新地址
     *
     * @param customerAddress
     * @return
     */
    @Override
    public Result<String> addAddress(CustomerAddress customerAddress) {
        customerAddress.setCustomerId(AuthContextHolder.getCustomerId());
        return customerAddressFeignClient.add(customerAddress);
    }

    /**
     * 修改地址
     *
     * @param customerAddress
     * @return
     */
    @Override
    public Result<String> updateAddress(CustomerAddress customerAddress) {
        customerAddress.setCustomerId(AuthContextHolder.getCustomerId());
        return customerAddressFeignClient.edit(customerAddress);
    }
}
