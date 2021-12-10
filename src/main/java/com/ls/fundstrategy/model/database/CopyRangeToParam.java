package com.ls.fundstrategy.model.database;

import lombok.Data;

@Data
public class CopyRangeToParam {
    private Integer sourceFundId;
    private Integer targetFundId;
    private Integer state;
}
