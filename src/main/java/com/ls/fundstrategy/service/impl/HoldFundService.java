package com.ls.fundstrategy.service.impl;

import com.ls.fundstrategy.mapper.HoldFundsMapper;
import com.ls.fundstrategy.model.database.HoldFunds;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.service.IHoldFundsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 16:29
 * @desc
 */
@AllArgsConstructor
@Service
public class HoldFundService implements IHoldFundsService {

    private HoldFundsMapper mHoldFundsMapper;

    @Override
    public ApiResponse<List<HoldFunds>> getAll() {
        return ApiResponse.success(mHoldFundsMapper.findAll());
    }

    @Override
    public ApiResponse<Boolean> addFund(String fundName,
                                        String fundCode,
                                        Double holdCount,
                                        Double currentValue) {
        HoldFunds holdFund = new HoldFunds();
        holdFund.setFundName(fundName);
        holdFund.setFundCode(fundCode);
        holdFund.setHoldCount(holdCount);
        holdFund.setCurrentValue(currentValue);
        holdFund.setInitValue(currentValue);
        holdFund.setHoldValue(currentValue);
        return ApiResponse.success(mHoldFundsMapper.insert(holdFund) > 0);
    }
}
