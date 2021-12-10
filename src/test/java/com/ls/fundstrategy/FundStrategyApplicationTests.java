package com.ls.fundstrategy;

import com.ls.fundstrategy.mapper.OperateRangeMapper;
import com.ls.fundstrategy.model.database.OperateRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FundStrategyApplicationTests {

    @Autowired
    private OperateRangeMapper mOperateRangeMapper;

    @Test
    void add(){
        OperateRange range = new OperateRange();
        range.setRangeSort(1);
        range.setUpperLimit(0.2);
        range.setLowerLimit(0.05);
        range.setLowerRate(2F);
        mOperateRangeMapper.insert(range);
    }

    @Test
    void findAll() {
        List<OperateRange> ranges = mOperateRangeMapper.findAll();
        System.out.println(ranges);
    }

    @Test
    void findId(){
        OperateRange operateRange = mOperateRangeMapper.selectById(1);
        System.out.println(operateRange);
    }

}
