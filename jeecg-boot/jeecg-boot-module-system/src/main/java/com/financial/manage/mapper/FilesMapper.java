package com.financial.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.financial.manage.entity.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: mi_files
 * @Author: jeecg-boot
 * @Date: 2023-07-30
 * @Version: V1.0
 */
public interface FilesMapper extends BaseMapper<Files>
{
    public List<Files> getFilesListByIds(@Param("ids") List<String> ids);
}
