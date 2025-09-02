package com.ilhaha.yueyishou.customer.wechat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/7 20:32
 * @Version 1.0
 */
public interface CustomerService {

    /**
     * 小程序登录
     *
     * @param code
     * @return
     */
    Result<String> login(String code);

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    Result<Boolean> checkPhone(String code);

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @return
     */
    Result<CustomerLoginInfoVo> getLoginInfo();

    /**
     * 获取顾客我的页面初始信息
     *
     * @return
     */
    Result<CustomerMyVo> getMyCenterInfo();

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    Result<Boolean> updateBaseInfo(CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm);

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    Result<Boolean> withdrawAccountBonus(CustomerWithdrawForm customerWithdrawForm);

    /**
     * 顾客查询提现记录
     *
     * @return
     */
    Result<Page<CustomerBonusExchange>> getWithdrawBonusList();

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    Result<List<CustomerAddress>> getAddressList();

    /**
     * 查询地址信息
     *
     * @param addressId
     * @return
     */
    Result<CustomerAddress> getAddressDetail(Integer addressId);

    /**
     * 获取当前顾客的默认地址
     *
     * @return
     */
    Result<CustomerAddress> getDefaultAddress();

    /**
     * 添加地址
     *
     * @param customerAddress
     * @return
     */
    Result<String> addAddress(CustomerAddress customerAddress);

    /**
     * 编辑地址信息
     *
     * @param customerAddress
     * @return
     */
    Result<String> updateAddress(CustomerAddress customerAddress);
}
