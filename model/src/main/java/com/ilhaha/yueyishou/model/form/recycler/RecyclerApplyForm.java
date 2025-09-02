package com.ilhaha.yueyishou.model.form.recycler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/9/24 21:36
 * @Version 1.0
 */
@Data
public class RecyclerApplyForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -1920687661828603760L;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
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
}
