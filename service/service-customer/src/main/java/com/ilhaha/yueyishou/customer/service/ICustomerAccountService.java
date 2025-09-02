package com.ilhaha.yueyishou.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerSettlementForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;

public interface ICustomerAccountService extends IService<CustomerAccount> {
    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    Boolean onWithdraw(CustomerWithdrawForm customerWithdrawForm);

    /**
     * 顾客查询提现记录
     *
     * @param customerId
     * @return
     */
    Page<CustomerBonusExchange> getWithdrawList(Long customerId);

    /**
     * 顾客动账处理
     *
     * @param customerSettlementForm
     * @return
     */
    void settlement(CustomerSettlementForm customerSettlementForm);


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 账户列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CustomerAccount> queryPageList(CustomerAccountForm customerAccountForm, Integer pageNo, Integer pageSize);
}
