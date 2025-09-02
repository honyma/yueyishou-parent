package com.ilhaha.yueyishou.customer.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.customer.service.ICustomerAccountService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerSettlementForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerAccount")
@Slf4j
public class CustomerAccountController {

    @Resource
    private ICustomerAccountService customerAccountService;

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    @PostMapping("/onWithdraw")
    public Result<Boolean> onWithdraw(@RequestBody CustomerWithdrawForm customerWithdrawForm) {
        return Result.ok(customerAccountService.onWithdraw(customerWithdrawForm));
    }

    /**
     * 顾客查询提现记录
     *
     * @param customerId
     * @return
     */
    @GetMapping("/customerAccount/withdraw/list/{customerId}")
    public Result<Page<CustomerBonusExchange>> getWithdrawList(@PathVariable Long customerId) {
        return Result.ok(customerAccountService.getWithdrawList(customerId));
    }

    /**
     * 顾客动账处理
     *
     * @param customerSettlementForm
     * @return
     */
    @PostMapping("/settlement")
    public void settlement(@RequestBody CustomerSettlementForm customerSettlementForm) {
        customerAccountService.settlement(customerSettlementForm);
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 账户列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<CustomerAccount>> queryPageList(@RequestBody CustomerAccountForm customerAccountForm,
                                                         @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                         @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return Result.ok(customerAccountService.queryPageList(customerAccountForm, pageNo, pageSize));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<CustomerAccount> queryById(@RequestParam(name = "id") String id) {
        CustomerAccount customerAccount = customerAccountService.getById(id);
        if (customerAccount == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(customerAccount);
    }
}
