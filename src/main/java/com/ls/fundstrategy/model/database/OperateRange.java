package com.ls.fundstrategy.model.database;

import lombok.Data;

/**
 * 基金操作区间表
 */
@Data
public class OperateRange {
    private Integer rangeId; // 主键ID
    private Integer rangeSort; // 排序，每次基金赎回，操作区间就会加1，然后就会根据区间找到对应的排序
    private Double upperLimit; // 可操作上限，基金涨幅大于等于多少时，可赎回
    private Double lowerLimit; // 可操作下限，基金跌幅大于等于多少时，可加仓
    private Float lowerRate; // 操作下限比率，基金跌幅大于等于下限时，应该按几倍跌幅加仓
    private Integer belongToFund; // 多属于某只基金的操作区间，为空表示全部适用
}
