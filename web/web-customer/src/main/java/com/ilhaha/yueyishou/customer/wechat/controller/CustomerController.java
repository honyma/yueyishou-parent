package com.ilhaha.yueyishou.customer.wechat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.anno.WechatLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.customer.wechat.service.CustomerService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:29
 * @Version 1.0
 */
@RestController(value = "cWechatCustomerController")
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 小程序登录
     *
     * @param code
     * @return
     */
    @GetMapping("/login/{code}")
    public Result<String> login(@PathVariable("code") String code) {
        return customerService.login(code);
    }

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/check/phone/{code}")
    public Result<Boolean> checkPhone(@PathVariable("code") String code) {
        return customerService.checkPhone(code);
    }

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/login/info")
    public Result<CustomerLoginInfoVo> getLoginInfo() {
        return customerService.getLoginInfo();
    }

    /**
     * 获取顾客我的个人中心页面初始信息
     *
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/my/center/info")
    public Result<CustomerMyVo> getMyCenterInfo() {
        return customerService.getMyCenterInfo();
    }

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/update/base/info")
    public Result<Boolean> updateBaseInfo(@RequestBody CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm) {
        return customerService.updateBaseInfo(customerUpdateBaseInfoForm);
    }

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    @WechatLoginVerification
    @PostMapping("/account/bonus/withdraw")
    public Result<Boolean> onWithdraw(@RequestBody CustomerWithdrawForm customerWithdrawForm) {
        return customerService.withdrawAccountBonus(customerWithdrawForm);
    }

    /**
     * 顾客查询提现记录
     *
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/account/withdraw/list")
    public Result<Page<CustomerBonusExchange>> getWithdrawList() {
        return customerService.getWithdrawBonusList();
    }

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    @WechatLoginVerification
    @GetMapping(value = "/address/list")
    public Result<List<CustomerAddress>> getAddressList() {
        return customerService.getAddressList();
    }

    /**
     * 查询地址信息
     *
     * @param addressId
     * @return
     */
    @WechatLoginVerification
    @GetMapping(value = "/address/detail/{addressId}")
    public Result<CustomerAddress> getAddressDetail(@PathVariable("addressId") Integer addressId) {
        return customerService.getAddressDetail(addressId);
    }

    /**
     * 获取当前顾客的默认地址
     *
     * @return
     */
    @WechatLoginVerification
    @GetMapping("/address/default")
    public Result<CustomerAddress> getDefaultAddress() {
        return customerService.getDefaultAddress();
    }

    /**
     * 添加地址
     *
     * @param customerAddress
     * @return
     */
    @WechatLoginVerification
    @PostMapping(value = "/address/add")
    public Result<String> addAddress(@RequestBody CustomerAddress customerAddress) {
        return customerService.addAddress(customerAddress);
    }

    /**
     * 编辑地址信息
     *
     * @param customerAddress
     * @return
     */
    @WechatLoginVerification
    @PostMapping(value = "/address/edit")
    public Result<String> editAddress(@RequestBody CustomerAddress customerAddress) {
        return customerService.updateAddress(customerAddress);
    }
}
