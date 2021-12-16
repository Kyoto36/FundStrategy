package com.ls.fundstrategy.service;

import com.ls.fundstrategy.model.database.HoldFunds;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.model.response.FundItem;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 16:26
 * @desc
 */
public interface IHoldFundsService {
    ApiResponse<List<FundItem>> getAll();

    ApiResponse<Boolean> addFund(String fundName,
                                 String fundCode,
                                 Double holdCount,
                                 Double initValue);
}
