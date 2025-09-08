package com.financial.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.financial.manage.entity.Files;

import java.util.List;

/**
 * @Description: mi_files
 * @Author: jeecg-boot
 * @Date: 2023-07-30
 * @Version: V1.0
 */
public interface IFilesService extends IService<Files>
{
    public Files getFilesInfoById(String id);

    public List<Files> getFilesListByIds(List<String> ids);
}
