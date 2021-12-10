package com.ls.fundstrategy.service;

import com.ls.fundstrategy.model.database.OperateRange;

import java.util.List;

public interface IOperateRangeService {
    List<OperateRange> getAll();

    boolean range(OperateRange operateRange);

    boolean copyRangeTo(Integer sourceFundId,Integer targetFundId);

}
