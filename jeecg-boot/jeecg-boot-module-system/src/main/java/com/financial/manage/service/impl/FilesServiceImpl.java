package com.financial.manage.service.impl;

import cn.com.dgwl.tools.v2.util.StringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.financial.manage.entity.Files;
import com.financial.manage.mapper.FilesMapper;
import com.financial.manage.service.IFilesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: mi_files
 * @Author: jeecg-boot
 * @Date: 2023-07-30
 * @Version: V1.0
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService
{
    @Override
    public List<Files> getFilesListByIds(List<String> ids)
    {
        if(ids == null || ids.size() == 0)
        {
            return new ArrayList<>();
        }

        ids = ids.stream().filter(StringUtil::isNotEmpty).collect(Collectors.toList());
        if(ids.size() == 0)
        {
            return new ArrayList<>();
        }

        return baseMapper.getFilesListByIds(ids);
    }
}
