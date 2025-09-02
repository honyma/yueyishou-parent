package com.ilhaha.yueyishou.model.entity.recycler;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_recycler_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RecyclerInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 回收员工号
     */
    private String recyclerNo;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 电话
     */
    private String phone;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 1:男 2：女
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 身份证号码
     */
    private String idcardNo;
    /**
     * 身份证地址
     */
    private String idcardAddress;
    /**
     * 身份证有效期
     */
    private Date idcardExpire;
    /**
     * 身份证正面
     */
    private String idcardFrontUrl;
    /**
     * 身份证背面
     */
    private String idcardBackUrl;
    /**
     * 手持身份证
     */
    private String idcardHandUrl;
    /**
     * 正脸照
     */
    private String fullFaceUrl;
    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态，1正常，0禁用
     */
    private Integer status;
}
