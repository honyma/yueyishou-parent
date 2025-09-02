package com.ilhaha.yueyishou.customer.service.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.common.constant.CustomerConstant;
import com.ilhaha.yueyishou.common.constant.PublicConstant;
import com.ilhaha.yueyishou.common.constant.RedisConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.common.util.PhoneNumberUtils;
import com.ilhaha.yueyishou.customer.mapper.CustomerInfoMapper;
import com.ilhaha.yueyishou.customer.service.ICustomerAccountService;
import com.ilhaha.yueyishou.customer.service.ICustomerInfoService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAccount;
import com.ilhaha.yueyishou.model.entity.customer.CustomerInfo;
import com.ilhaha.yueyishou.model.form.customer.CustomerAccountForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerInfoForm;
import com.ilhaha.yueyishou.model.form.customer.CustomerUpdateBaseInfoForm;
import com.ilhaha.yueyishou.model.vo.customer.CustomerLoginInfoVo;
import com.ilhaha.yueyishou.model.vo.customer.CustomerMyVo;
import com.ilhaha.yueyishou.model.vo.order.OrderMyVo;
import com.ilhaha.yueyishou.order.client.OrderInfoFeignClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

    @Resource
    private WxMaService wxMaService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ICustomerAccountService customerAccountService;

    @Resource
    private OrderInfoFeignClient orderInfoFeignClient;

    /**
     * 小程序授权登录
     *
     * @param code
     * @returnz
     */
    //@GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public String login(String code) {
        String openid;
        String sessionKey;
        try {
            // 根据code请求微信接口后台，获取openId
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            openid = sessionInfo.getOpenid();
            sessionKey = sessionInfo.getSessionKey();
            log.info("用户微信授权登录openId={}, sessionkey={}", openid, sessionKey);
        } catch (WxErrorException e) {
            throw new YueYiShouException(ResultCodeEnum.INCORRECT_LOGIN_INFORMATION);
        }
        // 判断乘客是否注册过
        LambdaQueryWrapper<CustomerInfo> customerInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerInfoLambdaQueryWrapper.eq(CustomerInfo::getWxOpenId, openid);
        CustomerInfo customerInfo = this.getOne(customerInfoLambdaQueryWrapper);
        // 没注册过，记录乘客信息
        if (ObjectUtils.isEmpty(customerInfo)) {
            customerInfo = new CustomerInfo();
            customerInfo.setNickname(CustomerConstant.DEFAULT_NICKNAME);
            customerInfo.setAvatarUrl(CustomerConstant.DEFAULT_AVATAR);
            customerInfo.setWxOpenId(openid);
            customerInfo.setPhone(PhoneNumberUtils.generateRandomPhoneNumber());
            this.save(customerInfo);
            // 给新顾客创建账户
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomerId(customerInfo.getId());
            customerAccountService.save(customerAccount);
            // 给新顾客赠送服务抵扣劵
            /*List<CouponInfo> couponInfoListDB = couponInfoFeignClient.getListByIds(CouponIssueConstant.COUPON_FREE_ISSUE_ID).getData();
            if (!ObjectUtils.isEmpty(couponInfoListDB)) {
                List<FreeIssueForm> freeIssueFormList = new ArrayList<>();
                for (CouponInfo couponInfo : couponInfoListDB) {
                    FreeIssueForm freeIssueForm = new FreeIssueForm();
                    freeIssueForm.setCustomerId(customerInfo.getId());
                    freeIssueForm.setCouponId(couponInfo.getId());
                    freeIssueForm.setReceiveTime(new Date());
                    BeanUtils.copyProperties(couponInfo, freeIssueForm);
                    if (!CouponConstant.EXPIRES_DAYS_AFTER_COLLECTION.equals(couponInfo.getExpireTime())) {
                        // 计算过期时间：当前日期 + 有效天数
                        LocalDateTime expireTime = LocalDateTime.now().plusDays(couponInfo.getExpireTime());

                        // 将 LocalDateTime 转换为 Date 类型
                        Date expireDate = Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant());

                        // 设置过期时间
                        freeIssueForm.setExpireTime(expireDate);
                    }
                    freeIssueFormList.add(freeIssueForm);
                }
                customerCouponFeignClient.freeIssue(freeIssueFormList);
            }*/
        } else {
            // 判断顾客是否是禁用状态
            if (PublicConstant.DISABLED_STATUS.equals(customerInfo.getStatus())) {
                throw new YueYiShouException(ResultCodeEnum.ACCOUNT_STOP);
            }
        }
        // 根据顾客id生成token
        Long customerInfoId = customerInfo.getId();
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 将顾客token存到redis，校验登录时使用
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY_PREFIX + token,
                customerInfoId.toString(),
                RedisConstant.USER_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 小程序申请获取并验证手机号
     *
     * @param code       动态令牌
     * @param customerId 客户ID
     */
    @Override
    public Boolean checkPhone(String code, Long customerId) {
        String phoneNumber;
        try {
            // 根据code请求微信接口后台，获取手机号
            WxMaPhoneNumberInfo phoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(code);
            phoneNumber = phoneNumberInfo.getPhoneNumber();
            log.info("用户微信申请获取并验证手机号,令牌code={}, phoneNumber={}", code, phoneNumber);
        } catch (WxErrorException e) {
            throw new YueYiShouException(ResultCodeEnum.INCORRECT_LOGIN_INFORMATION);
        }
        LambdaUpdateWrapper<CustomerInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(CustomerInfo::getId, customerId).set(CustomerInfo::getPhone, phoneNumber);
        return this.update(lambdaUpdateWrapper);
    }

    /**
     * 获取顾客登录之后的顾客信息
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerLoginInfoVo getLoginInfo(Long customerId) {
        CustomerInfo customerInfoDB = this.getById(customerId);
        if (ObjectUtils.isEmpty(customerInfoDB)) throw new YueYiShouException(ResultCodeEnum.CUSTOMER_INFO_NOT_EXIST);
        CustomerLoginInfoVo customerLoginInfoVo = new CustomerLoginInfoVo();
        BeanUtils.copyProperties(customerInfoDB, customerLoginInfoVo);
        return customerLoginInfoVo;
    }

    /**
     * 更新顾客基本信息
     *
     * @param customerUpdateBaseInfoForm
     * @return
     */
    @Override
    public Boolean updateBaseInfo(CustomerUpdateBaseInfoForm customerUpdateBaseInfoForm) {
        LambdaUpdateWrapper<CustomerInfo> customerInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        customerInfoLambdaUpdateWrapper.eq(CustomerInfo::getId, customerUpdateBaseInfoForm.getId())
                .set(CustomerInfo::getNickname, customerUpdateBaseInfoForm.getNickname())
                .set(CustomerInfo::getAvatarUrl, customerUpdateBaseInfoForm.getAvatarUrl());
        return this.update(customerInfoLambdaUpdateWrapper);
    }

    /**
     * 获取顾客我的个人中心页面初始信息
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerMyVo getMyCenterInfo(Long customerId) {
        CustomerMyVo customerMyVo = new CustomerMyVo();
        // 获取参与回收次数和碳排放量
        OrderMyVo orderMyVo = orderInfoFeignClient.getMyCenterInfo(customerId).getData();
        customerMyVo.setRecycleCount(orderMyVo.getRecycleCount());
        customerMyVo.setCarbonEmissions(orderMyVo.getCarbonEmissions());
        // 获取账户奖励金余额和回收总收入
        CustomerAccountForm customerAccountForm = new CustomerAccountForm();
        customerAccountForm.setCustomerId(customerId);
        Page<CustomerAccount> customerAccountPage = customerAccountService.queryPageList(customerAccountForm, null, null);
        CustomerAccount customerAccount = customerAccountPage.getRecords().get(0);
        customerMyVo.setBonusBalanceAmount(customerAccount.getBonusBalanceAmount());
        customerMyVo.setTotalRecycleAmount(customerAccount.getTotalRecycleIncome());
        return customerMyVo;
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 分页查询
     *
     * @param customerInfoForm
     * @return
     */
    @Override
    public Page<CustomerInfo> queryPageList(CustomerInfoForm customerInfoForm, Integer pageNo, Integer pageSize) {
        LambdaQueryWrapper<CustomerInfo> customerInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerInfoLambdaQueryWrapper.like(StringUtils.hasText(customerInfoForm.getNickname()),
                CustomerInfo::getNickname, customerInfoForm.getNickname());
        customerInfoLambdaQueryWrapper.eq(StringUtils.hasText(customerInfoForm.getPhone()),
                CustomerInfo::getPhone, customerInfoForm.getPhone());
        customerInfoLambdaQueryWrapper.eq(!ObjectUtils.isEmpty(customerInfoForm.getStatus()),
                CustomerInfo::getStatus, customerInfoForm.getStatus());
        customerInfoLambdaQueryWrapper.orderByDesc(CustomerInfo::getCreateTime);
        //全量查询
        if (pageNo == null || pageSize == null) {
            List<CustomerInfo> customerInfoList = this.list(customerInfoLambdaQueryWrapper);
            Page<CustomerInfo> page = new Page<>(1, customerInfoList.size(), customerInfoList.size());
            return page.setRecords(customerInfoList);
        }
        //分页查询
        Page<CustomerInfo> page = new Page<>(pageNo, pageSize);

        return this.page(page, customerInfoLambdaQueryWrapper);
    }
}
