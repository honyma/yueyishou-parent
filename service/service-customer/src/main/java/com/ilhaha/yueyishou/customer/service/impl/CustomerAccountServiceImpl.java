package com.ilhaha.yueyishou.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.common.constant.CustomerConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.common.util.IDUtil;
import com.ilhaha.yueyishou.customer.mapper.CustomerAccountMapper;
import com.ilhaha.yueyishou.customer.service.ICustomerAccountService;
import com.ilhaha.yueyishou.customer.service.ICustomerBonusDetailService;
import com.ilhaha.yueyishou.customer.service.ICustomerBonusExchangeService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusDetail;
import com.ilhaha.yueyishou.model.entity.customer.CustomerBonusExchange;
import com.ilhaha.yueyishou.model.enums.BonusDetailType;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerSettlementForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerWithdrawForm;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class CustomerAccountServiceImpl extends ServiceImpl<CustomerAccountMapper, CustomerAccount> implements ICustomerAccountService {

    @Resource
    private ICustomerBonusExchangeService customerBonusExchangeService;

    @Resource
    private ICustomerBonusDetailService customerBonusDetailService;

    /**
     * 顾客提现到微信零钱
     *
     * @param customerWithdrawForm
     * @return
     */
    @Override
    @Transactional
    public Boolean onWithdraw(CustomerWithdrawForm customerWithdrawForm) {
        // 1. 查询顾客账户
        CustomerAccount customerAccountDB = this.getOne(new LambdaQueryWrapper<CustomerAccount>().eq(CustomerAccount::getCustomerId, customerWithdrawForm.getCustomerId()));

        // 2. 判断提现金额
        if (CustomerConstant.MIN_WITHDRAW_AMOUNT.compareTo(customerWithdrawForm.getAmount()) > 0) {
            throw new YueYiShouException(ResultCodeEnum.WITHDRAW_NOT_REGULATION);
        }
        if (customerWithdrawForm.getAmount().compareTo(CustomerConstant.MAX_WITHDRAW_AMOUNT) > 0) {
            throw new YueYiShouException(ResultCodeEnum.WITHDRAW_NOT_REGULATION);
        }
        if (customerWithdrawForm.getAmount().compareTo(customerAccountDB.getBonusBalanceAmount()) > 0) {
            throw new YueYiShouException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }
        BigDecimal newBonusBalanceAmount = customerAccountDB.getBonusBalanceAmount().subtract(customerWithdrawForm.getAmount());

        // 3. 更新账户余额
        LambdaUpdateWrapper<CustomerAccount> customerAccountLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        customerAccountLambdaUpdateWrapper.eq(CustomerAccount::getCustomerId, customerWithdrawForm.getCustomerId())
                .set(CustomerAccount::getBonusBalanceAmount, newBonusBalanceAmount)
                .set(CustomerAccount::getUpdateTime, new Date());
        this.update(customerAccountLambdaUpdateWrapper);

        // 4.添加奖励金提现记录和明细记录
        CustomerBonusExchange customerBonusExchange = new CustomerBonusExchange();
        customerBonusExchange.setCustomerId(customerWithdrawForm.getCustomerId());
        customerBonusExchange.setTradeNo(IDUtil.generateOrderNumber());
        customerBonusExchange.setAmount(customerWithdrawForm.getAmount());
        customerBonusExchangeService.save(customerBonusExchange);

        CustomerBonusDetail customerBonusDetail = new CustomerBonusDetail();
        customerBonusDetail.setCustomerId(customerWithdrawForm.getCustomerId());
        customerBonusDetail.setAssociatedNo(customerBonusExchange.getTradeNo());
        customerBonusDetail.setType(BonusDetailType.WITHDRAWAL_DEDUCTION.getType());
        customerBonusDetail.setAmount(customerWithdrawForm.getAmount());
        customerBonusDetailService.save(customerBonusDetail);

        return Boolean.TRUE;
    }

    /**
     * 顾客查询提现记录
     *
     * @param customerId
     * @return
     */
    @Override
    public Page<CustomerBonusExchange> getWithdrawList(Long customerId) {
        LambdaQueryWrapper<CustomerBonusExchange> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CustomerBonusExchange::getCustomerId, customerId);
        List<CustomerBonusExchange> customerBonusExchangeList = customerBonusExchangeService.list(lambdaQueryWrapper);
        Page<CustomerBonusExchange> page = new Page<>(1, customerBonusExchangeList.size(), customerBonusExchangeList.size());
        page.setRecords(customerBonusExchangeList);
        return page;
    }

    /**
     * 顾客动账处理
     *
     * @param customerSettlementForm
     * @return
     */
    @Override
    @Transactional
    public void settlement(CustomerSettlementForm customerSettlementForm) {
        Long customerId = customerSettlementForm.getCustomerId();
        BigDecimal settleAmount = customerSettlementForm.getAmount();
        // 1. 查询顾客账户
        CustomerAccount customerAccountDB = this.getOne(new LambdaQueryWrapper<CustomerAccount>().eq(CustomerAccount::getCustomerId, customerId));

        // 2. 计算新的余额
        BigDecimal newBonusBalanceAmount = null;
        BigDecimal newTotalRecycleIncome = null;
        Integer type = customerSettlementForm.getType();
        BigDecimal movementBonusAmount = null;
        if (BonusDetailType.ITEMS_RECYCLE_INCOME.getType().equals(type)) {
            //动账金额
            movementBonusAmount = settleAmount.divide(CustomerConstant.BONUS_CALCULATE_DIVIDE, 2, RoundingMode.HALF_UP);
            newBonusBalanceAmount = customerAccountDB.getBonusBalanceAmount().add(movementBonusAmount);
            newTotalRecycleIncome = customerAccountDB.getTotalRecycleIncome().add(settleAmount);
        } else if (BonusDetailType.SIGN_IN_TO_GET.getType().equals(type)
                || BonusDetailType.LOTTERY_TO_GET.getType().equals(type)
                || BonusDetailType.MERCHANT_MANUAL_DELIVER.getType().equals(type)) {
            //动账金额
            movementBonusAmount = customerSettlementForm.getAmount();
            newBonusBalanceAmount = customerAccountDB.getBonusBalanceAmount().add(movementBonusAmount);
        }

        // 3. 更新账户余额
        LambdaUpdateWrapper<CustomerAccount> customerAccountLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        customerAccountLambdaUpdateWrapper.eq(CustomerAccount::getCustomerId, customerId)
                .set(!ObjectUtils.isEmpty(newBonusBalanceAmount), CustomerAccount::getBonusBalanceAmount, newBonusBalanceAmount)
                .set(!ObjectUtils.isEmpty(newTotalRecycleIncome), CustomerAccount::getTotalRecycleIncome, newTotalRecycleIncome)
                .set(CustomerAccount::getUpdateTime, new Date());

        // 4.增加奖励金明细
        if (movementBonusAmount == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        CustomerBonusDetail customerBonusDetail = new CustomerBonusDetail();
        customerBonusDetail.setCustomerId(customerId);
        customerBonusDetail.setAssociatedNo(customerSettlementForm.getAssociatedNo());
        customerBonusDetail.setType(type);
        customerBonusDetail.setAmount(movementBonusAmount);
        customerBonusDetailService.save(customerBonusDetail);

        this.update(customerAccountLambdaUpdateWrapper);
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 账户列表查询
     *
     * @param customerAccountForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<CustomerAccount> queryPageList(CustomerAccountForm customerAccountForm, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<CustomerAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!ObjectUtils.isEmpty(customerAccountForm.getCustomerId()),
                CustomerAccount::getCustomerId, customerAccountForm.getCustomerId());
        lambdaQueryWrapper.orderByDesc(CustomerAccount::getCreateTime);
        //全量查询
        if (pageNo == null || pageSize == null) {
            List<CustomerAccount> customerAccountList = this.list(lambdaQueryWrapper);
            Page<CustomerAccount> page = new Page<>(1, customerAccountList.size(), customerAccountList.size());
            return page.setRecords(customerAccountList);
        }
        //分页查询
        Page<CustomerAccount> page = new Page<>(pageNo, pageSize);

        return this.page(page, lambdaQueryWrapper);
    }
}
