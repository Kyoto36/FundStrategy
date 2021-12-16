package com.ls.fundstrategy;

import com.ls.fundstrategy.service.IHoldFundsService;
import com.ls.fundstrategy.service.impl.HoldFundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

/**
 * @author ls
 * @date 2021/12/16 13:33
 * @desc
 */
@SpringBootTest
public class HoldFundsTest {

    @Autowired
    private IHoldFundsService mHoldFundsService;

    @BeforeEach
    public void before(){

    }

    @Test
    public void testAll() {
        mHoldFundsService.getAll().getData().forEach(System.out::println);
    }
}
