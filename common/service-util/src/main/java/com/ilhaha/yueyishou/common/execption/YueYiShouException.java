package com.ilhaha.yueyishou.common.execption;

import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义全局异常类
 *
 */
@Data
public class YueYiShouException extends RuntimeException {

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public YueYiShouException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public YueYiShouException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "YueYiShouException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
