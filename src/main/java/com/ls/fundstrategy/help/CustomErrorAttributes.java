package com.ls.fundstrategy.help;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ls
 * @date 2021-12-11
 * @desc 拦截错误，返回统一json格式
 */
@Component
@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Map<String,Object> newAttr = new HashMap<>();
        newAttr.put("code",errorAttributes.get("status"));
        newAttr.put("msg",errorAttributes.get("error"));
        newAttr.put("data",null);
        return newAttr;
    }
}
