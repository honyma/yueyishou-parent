package com.ilhaha.yueyishou.model.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("t_customer_address")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomerAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 顾客Id
     */
    private Long customerId;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 联系人性别
     */
    private Integer gender;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 区/县编码
     */
    private String districtCode;
    /**
     * 区/县名称
     */
    private String districtName;
    /**
     * 详细地址，如街道、楼栋号
     */
    private String address;
    /**
     * 完整地址，包括省、市、区和详细地址
     */
    private String fullAddress;
    /**
     * 其他备注信息
     */
    private String remark;
    /**
     * 是否为默认地址，0表示否，1表示是
     */
    private Integer isDefault;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 伟度
     */
    private BigDecimal latitude;
}
