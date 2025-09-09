package com.financial.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.financial.manage.entity.FinancialOffer;
import com.financial.manage.entity.UserCollect;
import com.financial.manage.mapper.FinancialOfferMapper;
import com.financial.manage.service.IFinancialOfferService;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: financial_offer
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Service
public class FinancialOfferServiceImpl extends ServiceImpl<FinancialOfferMapper, FinancialOffer> implements IFinancialOfferService
{
    @Override
    public Map<Integer, String> getFinancialOfferMapByIds(List<Integer> ids)
    {
        if(ids.size() == 0)
        {
            return new HashMap<>();
        }

        QueryWrapper<FinancialOffer> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(FinancialOffer::getId, ids);
        return baseMapper.selectList(wrapper).stream().collect(Collectors.toMap(FinancialOffer::getId, FinancialOffer::getTitle));
    }
}
