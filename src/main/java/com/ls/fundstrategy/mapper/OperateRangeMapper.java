package com.ls.fundstrategy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.fundstrategy.model.database.CopyRangeToParam;
import com.ls.fundstrategy.model.database.OperateRange;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperateRangeMapper extends BaseMapper<OperateRange> {

    List<OperateRange> findAll();

    int updateRange(OperateRange operateRange);

    void copyRangeTo(CopyRangeToParam copyRangeToParam);

}
