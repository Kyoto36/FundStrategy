package com.ls.fundstrategy.service;

import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.model.response.FundOperateRange;

import java.util.List;

public interface IOperateRangeService {
    ApiResponse<List<FundOperateRange>> getAll();

    ApiResponse<Boolean> addRange(Double upperLimit,
                                  Double lowerLimit,
                                  Float lowerRate,
                                  Integer belongToFund);

    ApiResponse<Boolean> updateRange(Integer rangeId,
                                     Double upperLimit,
                                     Double lowerLimit,
                                     Float lowerRate,
                                     Integer belongToFund);

    ApiResponse<Boolean> deleteRange(Integer rangId);

    ApiResponse<Boolean> copyRangeTo(Integer sourceFundId,Integer targetFundId);

    ApiResponse<Boolean> idSort(String idSort,Integer fundId);

    void noTransactional(int aaa);

    void transactional(int aaa);

    void testInsert(int aaa);

}
