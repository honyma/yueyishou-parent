package com.ilhaha.yueyishou.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.common.constant.PublicConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.customer.mapper.CustomerAddressMapper;
import com.ilhaha.yueyishou.customer.service.ICustomerAddressService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CustomerAddressServiceImpl extends ServiceImpl<CustomerAddressMapper, CustomerAddress> implements ICustomerAddressService {

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    @Override
    public List<CustomerAddress> getAddressList(Long customerId) {
        LambdaQueryWrapper<CustomerAddress> customerAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerAddressLambdaQueryWrapper.eq(CustomerAddress::getCustomerId, customerId);
        return this.list(customerAddressLambdaQueryWrapper);
    }

    /**
     * 根据id查询地址信息
     *
     * @param addressId
     * @return
     */
    @Override
    public CustomerAddress getAddressDetail(Integer addressId) {
        CustomerAddress customerAddressDB = this.getById(addressId);
        if (ObjectUtils.isEmpty(customerAddressDB))
            throw new YueYiShouException(ResultCodeEnum.CUSTOMER_ADDRESS_NOT_EXIST);
        return customerAddressDB;
    }

    /**
     * 获取当前顾客的默认地址
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerAddress getDefaultAddress(Long customerId) {
        LambdaQueryWrapper<CustomerAddress> customerAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerAddressLambdaQueryWrapper.eq(CustomerAddress::getCustomerId, customerId)
                .eq(CustomerAddress::getIsDefault, PublicConstant.DEFAULT_YES_VALUE);
        return this.getOne(customerAddressLambdaQueryWrapper);
    }

    /**
     * 添加地址
     *
     * @param customerAddress
     * @return
     */
    @Override
    public void addAddress(CustomerAddress customerAddress) {
        // 判断是否有默认地址
        switchDefaultAddress(customerAddress);
        // 添加新地址
        this.save(customerAddress);
    }

    /**
     * 编辑地址信息
     *
     * @param customerAddress
     * @return
     */
    @Override
    public void updateAddress(CustomerAddress customerAddress) {
        // 判断是否有默认地址
        switchDefaultAddress(customerAddress);
        // 更新地址信息
        this.updateById(customerAddress);
    }

    /**
     * 切换默认地址
     *
     * @param customerAddress
     */
    private void switchDefaultAddress(CustomerAddress customerAddress) {
        // 判断新增加的地址是否确认为默认地址
        if (!PublicConstant.DEFAULT_YES_VALUE.equals(customerAddress.getIsDefault())) {
            return;
        }
        LambdaUpdateWrapper<CustomerAddress> customerAddressLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        customerAddressLambdaUpdateWrapper.eq(CustomerAddress::getCustomerId, customerAddress.getCustomerId())
                .eq(CustomerAddress::getIsDefault, PublicConstant.DEFAULT_YES_VALUE)
                .set(CustomerAddress::getIsDefault, PublicConstant.DEFAULT_NO_VALUE);
        this.update(customerAddressLambdaUpdateWrapper);
    }
}
