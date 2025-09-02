package com.ilhaha.yueyishou.customer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.customer.service.ICustomerInfoService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerInfo;
import com.ilhaha.yueyishou.model.form.customer.CustomerInfoForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerInfo")
@Slf4j
public class CustomerInfoController {

    @Autowired
    private ICustomerInfoService customerInfoService;

    /**
     * 小程序授权登录
     *
     * @param code
     * @return
     */
    @GetMapping("/login/{code}")
    public Result<String> login(@PathVariable("code") String code) {
        return Result.ok(customerInfoService.login(code));
    }

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code
     * @return
     */
    @GetMapping("/check/phone/{code}/{customerId}")
    public Result<Boolean> checkPhone(@PathVariable("code") String code, @PathVariable("customerId") Long customerId) {
        return Result.ok(customerInfoService.checkPhone(code, customerId));
    }

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/login/info/{customerId}")
    public Result<CustomerLoginInfoVo> getLoginInfo(@PathVariable("customerId") Long customerId) {
        return Result.ok(customerInfoService.getLoginInfo(customerId));
    }

    /**
     * 获取顾客我的页面初始信息
     *
     * @param customerId
     * @return
     */
    @GetMapping("/my/center/info/{customerId}")
    public Result<CustomerMyVo> getMyCenterInfo(@PathVariable Long customerId) {
        return Result.ok(customerInfoService.getMyCenterInfo(customerId));
    }

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    @PostMapping("/update/base/info")
    public Result<Boolean> updateBaseInfo(@RequestBody CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm) {
        return Result.ok(customerInfoService.updateBaseInfo(customerUpdateBaseInfoForm));
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 客户分页列表查询
     *
     * @param customerInfoForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/list")
    public Result<Page<CustomerInfo>> queryPageList(@RequestBody CustomerInfoForm customerInfoForm,
                                                    @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return Result.ok(customerInfoService.queryPageList(customerInfoForm, pageNo, pageSize));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<CustomerInfo> queryById(@RequestParam(name = "id") Long id) {
        CustomerInfo customerInfo = customerInfoService.getById(id);
        if (customerInfo == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(customerInfo);
    }

    /**
     * 编辑
     *
     * @param customerInfo
     * @return
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CustomerInfo customerInfo) {
        customerInfoService.updateById(customerInfo);
        return Result.ok("编辑成功!");
    }
}
