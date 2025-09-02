package com.ilhaha.yueyishou.customer.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerSettlementForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ilhaha
 * @Create 2024/9/2 16:06
 * @Version 1.0
 */
@FeignClient("service-server")
public interface CustomerAccountFeignClient {

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    @PostMapping("/customerAccount/onWithdraw")
    Result<Boolean> onWithdraw(@RequestBody CustomerWithdrawForm customerWithdrawForm);

    /**
     * 顾客查询提现记录
     *
     * @param customerId
     * @return
     */
    @GetMapping("/customerAccount/withdraw/list/{customerId}")
    Result<Page<CustomerBonusExchange>> getWithdrawList(@PathVariable Long customerId);

    /**
     * 顾客动账处理
     *
     * @param customerSettlementForm
     */
    @PostMapping("/customerAccount/settlement")
    void settlement(@RequestBody CustomerSettlementForm customerSettlementForm);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 账户列表查询
     *
     * @param customerAccountForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/customerAccount/list")
    Result<Page<CustomerAccount>> queryPageList(@RequestBody CustomerAccountForm customerAccountForm,
                                                @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(name = "pageSize", required = false) Integer pageSize);
}
