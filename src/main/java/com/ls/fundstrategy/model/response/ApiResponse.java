package com.ls.fundstrategy.model.response;

import com.ls.fundstrategy.constant.ResultConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private static final String SUCCESS = "SUCCESS";
    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .code(ResultConstant.Code.SUCCESS)
                .msg(SUCCESS)
                .data(data).build();
    }

    public static <T> ApiResponse<T> validateFail(String msg){
        return ApiResponse.<T>builder().code(ResultConstant.Code.FAIL).msg(msg).build();
    }

    public static <T> ApiResponse<T> validateFail(String msg, T data){
        return ApiResponse.<T>builder().code(ResultConstant.Code.FAIL).msg(msg).data(data).build();
    }

    public static <T> ApiResponse<T> of(int code, String msg, T data){
        return ApiResponse.<T>builder().code(code).msg(msg).data(data).build();
    }
}
