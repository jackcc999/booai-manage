package com.financial.manage.service;

import com.financial.manage.entity.FinancialOffer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: financial_offer
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
public interface IFinancialOfferService extends IService<FinancialOffer>
{
    Map<Integer, String> getFinancialOfferMapByIds(List<Integer> ids);
}
