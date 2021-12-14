package com.ls.fundstrategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.fundstrategy.model.database.CopyRangeToParam;
import com.ls.fundstrategy.model.database.OperateRange;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OperateRangeMapper extends BaseMapper<OperateRange> {

    List<OperateRange> findAll();

    Integer getHighestSortByFundId(int fundId);

    Integer getBelongToFundById(int rangeId);

    int existsRangeById(int rangeId);

    int updateRange(OperateRange operateRange);

    int deleteRange(int rangeId);

    void copyRangeTo(CopyRangeToParam copyRangeToParam);

    void testInsert(int aaa);

}
