package com.damochaohe.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一接口返回体。
 *
 * @param <T> data 字段实际承载的数据类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一接口响应对象")
public class ApiResponse<T> {

    @Schema(description = "业务状态码，0 表示成功")
    private Integer code;

    @Schema(description = "业务提示信息")
    private String message;

    @Schema(description = "实际业务数据")
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> fail(ResultCode resultCode) {
        return ApiResponse.<T>builder()
                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> fail(ResultCode resultCode, String message) {
        return ApiResponse.<T>builder()
                .code(resultCode.getCode())
                .message(message)
                .build();
    }
}
