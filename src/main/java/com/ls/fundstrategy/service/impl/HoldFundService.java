package com.ls.fundstrategy.service.impl;

import com.alibaba.fastjson.JSON;
import com.ls.fundstrategy.constant.ResultConstant;
import com.ls.fundstrategy.mapper.HoldFundsMapper;
import com.ls.fundstrategy.model.database.HoldFunds;
import com.ls.fundstrategy.model.response.ApiResponse;
import com.ls.fundstrategy.model.response.FundItem;
import com.ls.fundstrategy.model.response.TTFundGZ;
import com.ls.fundstrategy.service.IHoldFundsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ApiResponse<List<FundItem>> getAll() {
        List<FundItem> items;
        List<HoldFunds> funds = mHoldFundsMapper.findAll();
        items = funds.stream().map(this::fundToItem).collect(Collectors.toList());
        return ApiResponse.success(items);
    }

    private FundItem fundToItem(HoldFunds fund){
        FundItem item = new FundItem();
        item.setId(fund.getFundId());
        item.setName(fund.getFundName());
        item.setCode(fund.getFundCode());
        item.setLastOperateValue(fund.getLastOpeateValue() == null ? fund.getInitValue() : fund.getLastOpeateValue());
        TTFundGZ ttFundGZ = getTTFund(fund.getFundCode());
        item.setGsValue(ttFundGZ.getGsz());
        item.setCurrentValue(ttFundGZ.getDwjz());
        item.setVolatility(calcVolatility(item));
        return item;
    }

    private double calcVolatility(FundItem item){
        if(item.getLastOperateValue().isNaN() || item.getGsValue().isNaN()){
            return 0;
        }
        double lastValue = item.getLastOperateValue();
        double gsValue = item.getGsValue();
        double onePercent = lastValue / 100;
        double upValue = gsValue - lastValue;
        return upValue / onePercent;
    }

    private TTFundGZ getTTFund(String code){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://fundgz.1234567.com.cn/js/{1}.js?rt={2}}",String.class,code,System.currentTimeMillis() / 1000);
            String noJson = responseEntity.getBody();
            String json = noJson.substring(noJson.indexOf("{"),noJson.lastIndexOf("}") + 1);
            return JSON.parseObject(json,TTFundGZ.class);
        }catch (Exception e){
            return null;
        }

    }

    @Transactional
    @Override
    public ApiResponse<Boolean> addFund(String fundName,
                                        String fundCode,
                                        Double holdCount,
                                        Double initValue) {
        TTFundGZ ttFundGZ = getTTFund(fundCode);
        if(ttFundGZ == null){
            return ApiResponse.fail("基金交易所未找到该基金");
        }
        if(mHoldFundsMapper.existsFundByCode(fundCode) > 0){
            return ApiResponse.fail(ResultConstant.Code.EXISTS,"基金已存在");
        }
        HoldFunds holdFund = new HoldFunds();
        holdFund.setFundName(fundName);
        holdFund.setFundCode(fundCode);
        holdFund.setHoldCount(holdCount);
        holdFund.setCurrentValue(ttFundGZ.getDwjz());
        holdFund.setInitValue(initValue);
        holdFund.setHoldValue(initValue);
        return ApiResponse.success(mHoldFundsMapper.insert(holdFund) > 0);
    }
}
