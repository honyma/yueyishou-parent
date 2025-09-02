package com.ilhaha.yueyishou.model.form.customer;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ilhaha
 * @Create 2024/10/23 22:15
 * @Version 1.0
 * <p>
 * 顾客支出/收入表单类
 */
@Data
public class CustomerWithdrawForm implements Serializable {

    @Serial
    private static final long serialVersionUID = -4559851184998889138L;

    /**
     * 顾客Id
     */
    private Long customerId;

    /**
     * 支出/收入金额
     */
    @NonNull
    private BigDecimal amount;
}
