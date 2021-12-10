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
     * 新增和修改区间
     * @param operateRange
     * @return
     */
    @PostMapping("/range")
    public ApiResponse<Boolean> range(@RequestBody OperateRange operateRange){
        return ApiResponse.success(mOperateRangeService.range(operateRange));
    }

    /**
     * 拷贝一个基金下的区间规则到另一个基金下
     * TODO 另一个基金下的原有规则应该删除，还是无法完成此次操作
     * @param sourceFundId
     * @param targetFundId
     * @return
     */
    @PostMapping("/copyRangeTo")
    public ApiResponse<Boolean> copyRangeTo(@RequestParam(required = false) Integer sourceFundId,@RequestParam Integer targetFundId){
        return ApiResponse.success(mOperateRangeService.copyRangeTo(sourceFundId,targetFundId));
    }

}
