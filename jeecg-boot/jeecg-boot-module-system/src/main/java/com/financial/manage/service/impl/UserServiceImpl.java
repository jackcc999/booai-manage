package com.financial.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.financial.manage.entity.FinancialOffer;
import com.financial.manage.entity.User;
import com.financial.manage.mapper.UserMapper;
import com.financial.manage.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService
{
    @Override
    public Map<Integer, String> getUserMapByIds(List<Integer> ids)
    {
        if(ids.size() == 0)
        {
            return new HashMap<>();
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(User::getId, ids);
        return baseMapper.selectList(wrapper).stream().collect(Collectors.toMap(User::getId, User::getUsername));
    }
}
