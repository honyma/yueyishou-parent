package com.ilhaha.yueyishou.model.vo.customer;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ilhaha
 * @Create 2024/10/23 23:03
 * @Version 1.0
 * <p>
 * 顾客账户明细Vo
 */
@Data
public class CustomerBonusDetailVo {

    /**
     * 账户明细id
     */
    private Long id;
    /**
     * 明细说明
     */
    private String comment;
    /**
     * 奖励金明细类型：1-回收收入 2-奖励金提现扣减 3-签到得奖励金 4-抽奖得奖励金 5-商家手动补发
     */
    private String type;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    private Date createTime;
}
