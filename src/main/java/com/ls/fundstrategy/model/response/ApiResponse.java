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
}
