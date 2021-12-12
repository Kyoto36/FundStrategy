package com.ls.fundstrategy.contorller;

import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.model.database.OperateRange;
import com.ls.fundstrategy.service.IOperateRangeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ls
 * @date 2021-12-11
 * @desc 基金操作区间接口
 */
@AllArgsConstructor
@RestController
@RequestMapping("/operate-range")
public class OperateRangeController {

    private IOperateRangeService mOperateRangeService;

    /**
     * 查询全部区间
     * @return
     */
    @GetMapping("/getAll")
    public ApiResponse<List<OperateRange>> getAll(){
        return ApiResponse.success(mOperateRangeService.getAll());
    }

    /**
     * 新增区间
     * @param upperLimit
     * @param lowerLimit
     * @param lowerRate
     * @param belongToFund
     * TODO 基金不存在怎么返回
     * @return
     */
    @PostMapping("/addRange")
    public ApiResponse<Boolean> addRange(
            @RequestParam("upperLimit") Double upperLimit,
            @RequestParam("lowerLimit") Double lowerLimit,
            @RequestParam("lowerRate") Float lowerRate,
            @RequestParam(name = "belongToFund",required = false) Integer belongToFund){
        return mOperateRangeService.addRange(upperLimit, lowerLimit, lowerRate, belongToFund);
    }

    /**
     * 更新区间
     * @param rangeId
     * @param upperLimit
     * @param lowerLimit
     * @param lowerRate
     * @param belongToFund
     * TODO 基金不存在怎么返回
     * @return
     */
    @PostMapping("/updateRange")
    public ApiResponse<Boolean> updateRange(
            @RequestParam("rangeId") Integer rangeId,
            @RequestParam("upperLimit") Double upperLimit,
            @RequestParam("lowerLimit") Double lowerLimit,
            @RequestParam("lowerRate") Float lowerRate,
            @RequestParam(name = "belongToFund",required = false) Integer belongToFund){
        return mOperateRangeService.updateRange(rangeId, upperLimit, lowerLimit, lowerRate, belongToFund);
    }

    /**
     * 删除区间
     * @param rangeId
     * @return
     */
    @DeleteMapping("/deleteRange")
    public ApiResponse<Boolean> deleteRange(@RequestParam("rangeId") Integer rangeId){
        return mOperateRangeService.deleteRange(rangeId);
    }


    /**
     * 拷贝一个基金下的区间规则到另一个基金下
     * TODO 另一个基金下的原有规则应该删除，还是无法完成此次操作
     * @param sourceFundId
     * @param targetFundId
     * @return
     */
    @PostMapping("/copyRangeTo")
    public ApiResponse<Boolean> copyRangeTo(
            @RequestParam(name = "sourceFundId",required = false) Integer sourceFundId,
            @RequestParam("targetFundId") Integer targetFundId){
        return mOperateRangeService.copyRangeTo(sourceFundId,targetFundId);
    }

}
