package com.ls.fundstrategy.service.impl;

import com.ls.fundstrategy.mapper.OperateRangeMapper;
import com.ls.fundstrategy.model.database.CopyRangeToParam;
import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.service.IOperateRangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OperateRangeService implements IOperateRangeService {

    private OperateRangeMapper mOperateRangeMapper;

    @Override
    public List<OperateRange> getAll() {
        return mOperateRangeMapper.findAll();
    }

    @Override
    public boolean range(OperateRange operateRange) {
        if(operateRange.getRangeId() == null){
            return mOperateRangeMapper.insert(operateRange) > 0;
        }
        else{
            return mOperateRangeMapper.updateRange(operateRange) > 0;
        }
    }

    @Override
    public boolean copyRangeTo(Integer sourceFundId, Integer targetFundId) {
        CopyRangeToParam param = new CopyRangeToParam();
        param.setSourceFundId(sourceFundId);
        param.setTargetFundId(targetFundId);
        mOperateRangeMapper.copyRangeTo(param);
        return param.getState() == 1;
    }


}
