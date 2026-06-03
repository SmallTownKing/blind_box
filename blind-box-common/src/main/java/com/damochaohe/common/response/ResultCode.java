package com.damochaohe.common.response;

import lombok.Getter;

/**
 * 统一响应状态码定义。
 *
 * <p>说明：
 * 1. 该枚举用于约束系统对外返回的标准状态码；
 * 2. 前后端联调时应优先依据 code 判断业务结果；
 * 3. message 用于提供默认提示信息，必要时可由业务覆盖。</p>
 */
@Getter
public enum ResultCode {

    SUCCESS(0, "操作成功"),
    BAD_REQUEST(400, "请求参数有误"),
    UNAUTHORIZED(401, "未登录或登录已失效"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    SYSTEM_ERROR(500, "系统繁忙，请稍后重试"),
    BUSINESS_ERROR(10000, "业务处理失败");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
