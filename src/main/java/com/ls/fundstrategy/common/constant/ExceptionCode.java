package com.ls.fundstrategy.common.constant;

/**
 * <描述功能>
 *  异常码
 * @author Lang
 * @Classname ExceptionCode
 * @Version 1.0.0
 * @Date 2021/12/17 22:25
 **/
public enum ExceptionCode {
    //
    SUCCESS(0, "成功"),
    BASE_VALID_PARAM(-9, "统一验证参数异常"),
    JWT_SIGNATURE(40002, "不合法的token，请认真比对 token 的签名"),
    JWT_ILLEGAL_ARGUMENT(40003, "缺少token参数"),
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回内容
     */
    private String msg;

    ExceptionCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
