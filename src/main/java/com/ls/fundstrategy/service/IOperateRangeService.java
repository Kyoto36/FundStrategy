package com.ls.fundstrategy.service;

import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.model.response.ApiResponse;

import java.util.List;

public interface IOperateRangeService {
    List<OperateRange> getAll();

    ApiResponse<Boolean> addRange(
            Double upperLimit,
            Double lowerLimit,
            Float lowerRate,
            Integer belongToFund);

    ApiResponse<Boolean> updateRange(
            Integer rangeId,
            Double upperLimit,
            Double lowerLimit,
            Float lowerRate,
            Integer belongToFund);

    ApiResponse<Boolean> deleteRange(Integer rangId);

    ApiResponse<Boolean> copyRangeTo(Integer sourceFundId,Integer targetFundId);

}
