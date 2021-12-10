package com.ls.fundstrategy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ls.fundstrategy.mapper")
@SpringBootApplication
public class FundStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundStrategyApplication.class, args);
    }

}
