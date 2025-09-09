package com.financial.manage.service;

import com.financial.manage.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
public interface IUserService extends IService<User>
{
    Map<Integer, String> getUserMapByIds(List<Integer> ids);
}
