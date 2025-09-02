package com.ilhaha.yueyishou.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ilhaha.yueyishou.model.entity.customer.CustomerAddress;

import java.util.List;

public interface ICustomerAddressService extends IService<CustomerAddress> {

    /**
     * 获取当前登录的顾客地址列表
     *
     * @return
     */
    List<CustomerAddress> getAddressList(Long customerId);

    /**
     * 根据id查询地址信息
     *
     * @param addressId
     * @return
     */
    CustomerAddress getAddressDetail(Integer addressId);

    /**
     * 获取当前顾客的默认地址
     *
     * @param customerId
     * @return
     */
    CustomerAddress getDefaultAddress(Long customerId);

    /**
     * 添加地址
     *
     * @param customerAddress
     * @return
     */
    void addAddress(CustomerAddress customerAddress);

    /**
     * 编辑地址信息
     *
     * @param customerAddress
     * @return
     */
    void updateAddress(CustomerAddress customerAddress);
}
