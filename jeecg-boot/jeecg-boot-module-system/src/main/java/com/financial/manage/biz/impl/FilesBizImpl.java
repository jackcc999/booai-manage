package com.financial.manage.biz.impl;

import cn.com.dgwl.tools.v2.domain.MultipartFile;
import cn.com.dgwl.tools.v2.domain.UpFile;
import cn.com.dgwl.tools.v2.files.FileUploader;
import cn.com.dgwl.tools.v2.net.Http;
import cn.com.dgwl.tools.v2.util.Base64Util;
import cn.com.dgwl.tools.v2.util.BeanUtil;
import cn.com.dgwl.tools.v2.util.DateUtil;
import cn.com.dgwl.tools.v2.util.StringUtil;
import cn.com.dgwl.tools.v2.util.UrlUtil;
import com.financial.manage.biz.IFilesBiz;
import com.financial.manage.dto.FilesDTO;
import com.financial.manage.entity.ContentType;
import com.financial.manage.entity.Files;
import com.financial.manage.service.IContentTypeService;
import com.financial.manage.service.IFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilesBizImpl implements IFilesBiz
{
    private final static String CACHE_KEY = "FileInfo:";

    @Value(value = "${jeecg.path.upload}")
    private String uploadPath;

    @Autowired
    private IFilesService filesService;

    @Autowired
    private IContentTypeService contentTypeService;

    @Override
    public FilesDTO saveFileInfoByBase64(String type, String base64, String subFix) throws Exception
    {
        if(StringUtil.isEmpty(base64))
        {
            return FilesDTO.empty();
        }

        if(UrlUtil.isUrl(base64))
        {
            int idx = base64.lastIndexOf('/');
            if(idx > -1)
            {
                String id = base64.substring(idx + 1);
                idx = id.lastIndexOf('.');
                if(idx > -1)
                {
                    id = id.substring(0, idx);
                }

                Files files = filesService.getFilesInfoById(id);
                if(files != null)
                {
                    ContentType contentType = contentTypeService.getContentTypeByFilename(files.getFilename());
                    FilesDTO filesVo = BeanUtil.copy(files, FilesDTO.class);
                    filesVo.setSize(StringUtil.formatFileSize(files.getSize()));
                    filesVo.setCanView(contentType.getCanView());
                    filesVo.setCreateTime(DateUtil.format(files.getCreateTime()));

                    return filesVo;
                }

                byte[] data = new Http().url(base64).get().getBytes();
                base64 = Base64Util.encodeToString(data);
            }
        }


        base64 = Base64Util.safeBase64Decode(base64);
        UpFile upFile = FileUploader.uploadFile(type, Base64Util.decode(base64), subFix, uploadPath);
        ContentType contentType = contentTypeService.getContentTypeByFilename(upFile.getAbsoluteFile());

        Files files = new Files();
        files.setId(upFile.getId());
        files.setFilename(upFile.getFilename());
        files.setUrl(upFile.getUrl());
        files.setSize(upFile.getSize());
        files.setType(type);
        files.setAbsoluteFile(upFile.getAbsoluteFile());
        filesService.save(files);


        FilesDTO filesVo = BeanUtil.copy(upFile, FilesDTO.class);
        filesVo.setId(files.getId());
        filesVo.setFilename(files.getFilename());
        filesVo.setUrl(files.getUrl());
        filesVo.setAbsoluteFile(files.getAbsoluteFile());
        filesVo.setContentType(contentType.getContentType());
        filesVo.setSize(StringUtil.formatFileSize(files.getSize()));
        filesVo.setCanView(contentType.getCanView());
        filesVo.setCreateTime(DateUtil.format(files.getCreateTime()));
        return filesVo;
    }

    @Override
    public FilesDTO saveFileInfoByMultipart(String type, MultipartFile multipartFile) throws Exception
    {
        UpFile upFile = FileUploader.uploadFile(type, multipartFile, uploadPath);
        ContentType contentType = contentTypeService.getContentTypeByFilename(upFile.getFilename());

        Files files = new Files();
        files.setId(upFile.getId());
        files.setFilename(upFile.getFilename());
        files.setUrl(upFile.getUrl());
        files.setSize(upFile.getSize());
        files.setType(type);
        files.setAbsoluteFile(upFile.getAbsoluteFile());
        filesService.save(files);


        FilesDTO filesVo = BeanUtil.copy(upFile, FilesDTO.class);
        filesVo.setId(files.getId());
        filesVo.setFilename(files.getFilename());
        filesVo.setUrl(files.getUrl());
        filesVo.setAbsoluteFile(files.getAbsoluteFile());
        filesVo.setContentType(contentType.getContentType());
        filesVo.setSize(StringUtil.formatFileSize(files.getSize()));
        filesVo.setCanView(contentType.getCanView());
        filesVo.setCreateTime(DateUtil.format(files.getCreateTime()));
        return filesVo;
    }

    @Override
    public FilesDTO getFileInfoById(String id)
    {
        Files files = filesService.getFilesInfoById(id);
        if(files == null)
        {
            return null;
        }

        ContentType contentType = contentTypeService.getContentTypeByFilename(files.getFilename());

        FilesDTO filesVo = new FilesDTO();
        filesVo.setId(files.getId());
        filesVo.setFilename(files.getFilename());
        filesVo.setUrl(files.getUrl());
        filesVo.setAbsoluteFile(files.getAbsoluteFile());
        filesVo.setContentType(contentType.getContentType());
        filesVo.setSize(StringUtil.formatFileSize(files.getSize()));
        filesVo.setCanView(contentType.getCanView());
        filesVo.setCreateTime(DateUtil.format(files.getCreateTime()));

        return filesVo;
    }

    @Override
    public List<FilesDTO> getFileListByIds(List<String> ids)
    {
        return filesService.getFilesListByIds(ids).stream().map(this::formatFileInfo).collect(Collectors.toList());
    }

    @Override
    public Map<String, FilesDTO> getFileMap(List<String> ids)
    {
        return filesService.getFilesListByIds(ids).stream().collect(Collectors.toMap(Files::getId, this::formatFileInfo));
    }

    private FilesDTO formatFileInfo(Files files)
    {
        ContentType contentType = contentTypeService.getContentTypeByFilename(files.getFilename());

        FilesDTO vo = new FilesDTO();
        vo.setId(files.getId());
        vo.setFilename(files.getFilename());
        vo.setUrl(files.getUrl());
        vo.setSize(StringUtil.formatFileSize(files.getSize()));
        vo.setCanView(contentType.getCanView());
        vo.setCreateTime(DateUtil.format(files.getCreateTime()));

        return vo;
    }
}
