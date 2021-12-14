package com.ls.fundstrategy.model.response;

import com.ls.fundstrategy.model.database.OperateRange;
import lombok.Data;

import java.util.List;

/**
 * @author ls
 * @date 2021/12/14 11:42
 * @desc 根据基金分类的操作区间
 */
@Data
public class FundOperateRange {
    private Integer fundId;
    private String fundName;
    private List<OperateRange> operateRanges;
}
