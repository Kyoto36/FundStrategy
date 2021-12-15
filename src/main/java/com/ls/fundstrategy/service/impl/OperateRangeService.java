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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperateRangeService implements IOperateRangeService {

    private OperateRangeMapper mOperateRangeMapper;
    private HoldFundsMapper mHoldFundsMapper;

    private Map<Integer,HoldFunds> getFundMap(List<HoldFunds> funds){
        Map<Integer,HoldFunds> fundMap = new HashMap<>();
        funds.forEach(fund -> {
            fundMap.put(fund.getFundId(),fund);
        });
        return fundMap;
    }

    @Override
    public ApiResponse<List<FundOperateRange>> getAll() {
        List<OperateRange> operateRanges = mOperateRangeMapper.findAll();
        List<FundOperateRange> fundOperateRanges = new ArrayList<>();
        Map<Integer,List<OperateRange>> map = operateRanges.stream().collect(Collectors.groupingBy(OperateRange::getBelongToFund));
        if(map.size() <= 0){
            return ApiResponse.success(fundOperateRanges);
        }

        Map<Integer,HoldFunds> fundMap = getFundMap(mHoldFundsMapper.getFundsByIds(new ArrayList<>(map.keySet())));

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

    @Transactional
    @Override
    public ApiResponse<Boolean> idSort(String idSort, Integer fundId) {
        String[] idStrs = idSort.split(",");
        List<Integer> ids = Arrays.stream(idStrs).map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer,HoldFunds> fundMap = getFundMap(mHoldFundsMapper.selectBatchIds(ids));

        return null;
    }

    @Override
    public void noTransactional(int aaa) {
        exec(aaa);
    }

    @Override
    @Transactional
    public void transactional(int aaa) {
        exec(aaa);
    }

    private void exec(int aaa){
        OperateRange range;
        for (int i = 0; i < aaa; i++){
            range = new OperateRange();
            range.setRangeSort(1);
            range.setUpperLimit((double) i);
            range.setLowerLimit(i * 0.01);
            range.setLowerRate((float) i);
            mOperateRangeMapper.insert(range);
        }
    }

    @Override
    public void testInsert(int aaa) {
        mOperateRangeMapper.testInsert(aaa);
    }
}
