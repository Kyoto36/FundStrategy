package com.ls.fundstrategy.service.impl;

import com.ls.fundstrategy.mapper.HoldFundsMapper;
import com.ls.fundstrategy.mapper.OperateRangeMapper;
import com.ls.fundstrategy.model.database.CopyRangeToParam;
import com.ls.fundstrategy.model.database.HoldFunds;
import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.model.response.FundOperateRange;
import com.ls.fundstrategy.service.IOperateRangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperateRangeService implements IOperateRangeService {

    private OperateRangeMapper mOperateRangeMapper;
    private HoldFundsMapper mHoldFundsMapper;

    @Override
    public ApiResponse<List<FundOperateRange>> getAll() {
        List<OperateRange> operateRanges = mOperateRangeMapper.findAll();
        List<FundOperateRange> fundOperateRanges = new ArrayList<>();
        Map<Integer,List<OperateRange>> map = operateRanges.stream().collect(Collectors.groupingBy(OperateRange::getBelongToFund));
        if(map.size() <= 0){
            return ApiResponse.success(fundOperateRanges);
        }

        List<HoldFunds> funds = mHoldFundsMapper.getFundsByIds(new ArrayList<>(map.keySet()));
        Map<Integer,HoldFunds> fundMap = new HashMap<>();
        funds.forEach(fund -> {
            fundMap.put(fund.getFundId(),fund);
        });

        map.keySet().forEach(key -> {
            FundOperateRange fundOperateRange = new FundOperateRange();
            int fundId = key;
            HoldFunds fund = fundMap.get(fundId);
            String fundName = "";
            if(fund != null){
                fundName = fund.getFundName();
            }
            if(key == -1) {
                fundName = "通用规则";
            }
            fundOperateRange.setFundId(fundId);
            fundOperateRange.setFundName(fundName);
            fundOperateRange.setOperateRanges(map.get(fundId));
            fundOperateRanges.add(fundOperateRange);
        });

        return ApiResponse.success(fundOperateRanges);
    }

    @Override
    public ApiResponse<Boolean> addRange(
            Double upperLimit,
            Double lowerLimit,
            Float lowerRate,
            Integer belongToFund) {
        OperateRange range = new OperateRange();
        range.setUpperLimit(upperLimit);
        range.setLowerLimit(lowerLimit);
        range.setLowerRate(lowerRate);
        range.setBelongToFund(belongToFund);
        Integer temp = mOperateRangeMapper.getHighestSortByFundId(belongToFund == null ? -1 : belongToFund);
        int highestSort = temp == null ? 0 : temp;
        range.setRangeSort(highestSort + 1);
        return ApiResponse.success(mOperateRangeMapper.insert(range) > 0);
    }

    @Override
    public ApiResponse<Boolean> updateRange(
            Integer rangeId,
            Double upperLimit,
            Double lowerLimit,
            Float lowerRate,
            Integer belongToFund) {
        if(rangeId == null || mOperateRangeMapper.existsRangeById(rangeId) <= 0){
            return addRange(upperLimit, lowerLimit, lowerRate, belongToFund);
        }

        OperateRange range = new OperateRange();
        range.setRangeId(rangeId);
        range.setUpperLimit(upperLimit);
        range.setLowerLimit(lowerLimit);
        range.setLowerRate(lowerRate);
        range.setBelongToFund(belongToFund);
        if(!Objects.equals(mOperateRangeMapper.getBelongToFundById(rangeId), belongToFund)){
            Integer temp = mOperateRangeMapper.getHighestSortByFundId(belongToFund == null ? -1 : belongToFund);
            int highestSort = temp == null ? 0 : temp;
            range.setRangeSort(highestSort + 1);
        }
        return ApiResponse.success(mOperateRangeMapper.updateRange(range) > 0);
    }

    @Override
    public ApiResponse<Boolean> deleteRange(Integer rangeId) {
        mOperateRangeMapper.deleteRange(rangeId);
        return ApiResponse.success(true);
    }

    @Override
    public ApiResponse<Boolean> copyRangeTo(Integer sourceFundId, Integer targetFundId) {
        CopyRangeToParam param = new CopyRangeToParam();
        param.setSourceFundId(sourceFundId);
        param.setTargetFundId(targetFundId);
        mOperateRangeMapper.copyRangeTo(param);
        return ApiResponse.success(param.getState() == 1);
    }

    @Override
    public ApiResponse<Boolean> idSort(String idSort, Integer fundId) {
        return null;
    }


}
