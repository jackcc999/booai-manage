package com.financial.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.financial.manage.entity.ContentType;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: mi_content_type
 * @Author: jeecg-boot
 * @Date: 2023-08-03
 * @Version: V1.0
 */
public interface ContentTypeMapper extends BaseMapper<ContentType>
{
    public ContentType getContentTypeBySubfix(@Param("subfix") String subfix);
}
