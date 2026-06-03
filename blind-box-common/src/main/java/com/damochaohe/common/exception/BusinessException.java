package com.damochaohe.common.exception;

import com.damochaohe.common.response.ResultCode;
import lombok.Getter;

/**
 * 业务异常。
 *
 * <p>用于主动中断业务流程，并向全局异常处理器传递业务码和业务提示。</p>
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }
}
