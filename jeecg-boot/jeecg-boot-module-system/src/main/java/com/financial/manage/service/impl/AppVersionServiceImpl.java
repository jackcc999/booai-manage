package com.financial.manage.service.impl;

import com.financial.manage.entity.AppVersion;
import com.financial.manage.mapper.AppVersionMapper;
import com.financial.manage.service.IAppVersionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 版本管理
 * @Author: jeecg-boot
 * @Date:   2025-09-08
 * @Version: V1.0
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements IAppVersionService
{

}
