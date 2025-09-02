package com.ilhaha.yueyishou.customer.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.entity.customer.CustomerInfo;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/2 16:05
 * @Version 1.0
 */
@FeignClient("service-server")
public interface CustomerInfoFeignClient {

    /**
     * 小程序授权登录
     *
     * @param code
     * @return
     */
    @GetMapping("/customerInfo/login/{code}")
    Result<String> login(@PathVariable("code") String code);

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    @GetMapping("/customerInfo/check/phone/{code}/{customerId}")
    Result<Boolean> checkPhone(@PathVariable("code") String code, @PathVariable("customerId") Long customerId);

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/customerInfo/login/info/{customerId}")
    Result<CustomerLoginInfoVo> getLoginInfo(@PathVariable("customerId") Long customerId);

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/customerInfo/my/center/info/{customerId}")
    Result<CustomerMyVo> getMyCenterInfo(@PathVariable Long customerId);

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    @PostMapping("/customerInfo/update/base/info")
    Result<Boolean> updateBaseInfo(@RequestBody CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 客户分页列表查询
     *
     * @param customerInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/customerInfo/list")
    Result<Page<CustomerInfo>> queryPageList(@RequestBody CustomerInfo customerInfo,
                                             @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(name = "pageSize", required = false) Integer pageSize);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/customerInfo/queryById")
    Result<CustomerInfo> queryById(@RequestParam(name = "id") Long id);
}
