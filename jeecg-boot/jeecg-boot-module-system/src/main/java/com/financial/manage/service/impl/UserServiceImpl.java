package com.financial.manage.service.impl;

import com.financial.manage.entity.User;
import com.financial.manage.mapper.UserMapper;
import com.financial.manage.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService
{

}
