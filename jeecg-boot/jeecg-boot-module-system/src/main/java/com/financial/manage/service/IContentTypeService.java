package com.financial.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.financial.manage.entity.ContentType;

/**
 * @Description: mi_content_type
 * @Author: jeecg-boot
 * @Date: 2023-08-03
 * @Version: V1.0
 */
public interface IContentTypeService extends IService<ContentType>
{
    public ContentType getContentTypeByFilename(String filename);
}
