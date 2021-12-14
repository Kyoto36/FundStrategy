package com.ls.fundstrategy.common.annotation;

import com.ls.fundstrategy.common.constant.BusinessType;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD }) //可以作用在类上，也可以作用在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 描述
     */
    public String des() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;
}
