package com.ilhaha.yueyishou.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerInfo;
import com.ilhaha.yueyishou.model.form.customer.CustomerInfoForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;

public interface ICustomerInfoService extends IService<CustomerInfo> {

    /**
     * 小程序授权登录
     *
     * @param code
     * @return
     */
    String login(String code);

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    Boolean checkPhone(String code, Long customerId);

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @param customerId
     * @return
     */
    CustomerLoginInfoVo getLoginInfo(Long customerId);

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    Boolean updateBaseInfo(CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm);

    /**
     * 获取顾客我的个人中心页面初始信息
     *
     * @param customerId
     * @return
     */
    CustomerMyVo getMyCenterInfo(Long customerId);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 分页查询
     *
     * @param customerInfoForm
     * @return
     */
    Page<CustomerInfo> queryPageList(CustomerInfoForm customerInfoForm, Integer pageNo, Integer pageSize);
}
