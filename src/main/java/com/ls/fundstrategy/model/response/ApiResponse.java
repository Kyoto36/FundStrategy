package com.ls.fundstrategy.model.response;

import com.ls.fundstrategy.constant.ResultConstant;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(T data){
        ApiResponse<T> result = new ApiResponse<>();
        result.code = ResultConstant.Code.SUCCESS;
        result.msg = "SUCCESS";
        result.data = data;
        return result;
    }

    public static <T> ApiResponse<T> fail(String msg){
        ApiResponse<T> result = new ApiResponse<>();
        result.code = ResultConstant.Code.FAIL;
        result.msg = msg;
        return result;
    }

    public static <T> ApiResponse<T> fail(int code,String msg){
        ApiResponse<T> result = new ApiResponse<>();
        result.code = code;
        result.msg = msg;
        return result;
    }
}
