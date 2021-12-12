package com.ls.fundstrategy.service.impl;

import com.ls.fundstrategy.mapper.OperateRangeMapper;
import com.ls.fundstrategy.model.database.CopyRangeToParam;
import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.service.IOperateRangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class OperateRangeService implements IOperateRangeService {

    private OperateRangeMapper mOperateRangeMapper;

    @Override
    public List<OperateRange> getAll() {
        return mOperateRangeMapper.findAll();
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
        if(belongToFund == null){
            range.setRangeSort(mOperateRangeMapper.getHighestSortByNull() + 1);
        }
        else{
            range.setRangeSort(mOperateRangeMapper.getHighestSortByFundId(belongToFund) + 1);
        }
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
            if(belongToFund == null){
                range.setRangeSort(mOperateRangeMapper.getHighestSortByNull() + 1);
            }
            else{
                range.setRangeSort(mOperateRangeMapper.getHighestSortByFundId(belongToFund) + 1);
            }
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


}
