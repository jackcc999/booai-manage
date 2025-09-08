package com.financial.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.financial.manage.entity.ContentType;
import com.financial.manage.mapper.ContentTypeMapper;
import com.financial.manage.service.IContentTypeService;
import org.springframework.stereotype.Service;

/**
 * @Description: mi_content_type
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Service
public class ContentTypeServiceImpl extends ServiceImpl<ContentTypeMapper, ContentType> implements IContentTypeService
{
    @Override
    public ContentType getContentTypeByFilename(String filename)
    {
        ContentType contentType = new ContentType();
        contentType.setCanView(0);

        return contentType;

//        int index = filename.lastIndexOf('.');
//        String subfix = index == -1 ? ".default" : filename.substring(index);
//
//        ContentType contentType = baseMapper.getContentTypeBySubfix(subfix);
//        return contentType == null ? getDefaultContentType() : contentType;
    }

    private ContentType getDefaultContentType()
    {
        return baseMapper.getContentTypeBySubfix(".default");
    }
}
