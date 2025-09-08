package com.financial.manage.biz;

import cn.com.dgwl.tools.v2.domain.MultipartFile;
import com.financial.manage.dto.FilesDTO;

import java.util.List;
import java.util.Map;

public interface IFilesBiz
{
    public FilesDTO saveFileInfoByBase64(String type, String base64, String subFix) throws Exception;

    public FilesDTO saveFileInfoByMultipart(String type, MultipartFile multipartFile) throws Exception;

    public FilesDTO getFileInfoById(String id);

    public List<FilesDTO> getFileListByIds(List<String> ids);

    public Map<String, FilesDTO> getFileMap(List<String> ids);
}
