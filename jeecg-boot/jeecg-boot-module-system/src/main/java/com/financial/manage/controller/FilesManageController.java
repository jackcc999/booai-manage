package com.financial.manage.controller;

import cn.com.dgwl.tools.v2.domain.MultipartFile;
import cn.com.dgwl.tools.v2.servlet.HttpServlet;
import cn.com.dgwl.tools.v2.util.BeanUtil;
import cn.com.dgwl.tools.v2.util.DateUtil;
import cn.com.dgwl.tools.v2.util.FileUtil;
import cn.com.dgwl.tools.v2.util.StringUtil;
import com.financial.manage.biz.IFilesBiz;
import com.financial.manage.dto.FilesDTO;
import com.financial.manage.dto.IdsDTO;
import com.financial.manage.entity.ContentType;
import com.financial.manage.entity.Files;
import com.financial.manage.service.IContentTypeService;
import com.financial.manage.service.IFilesService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/files")
public class FilesManageController
{
    @Value(value = "${jeecg.path.upload}")
    private String uploadPath;

    @Autowired
    private IFilesService filesService;

    @Autowired
    private IContentTypeService contentTypeService;

    @Autowired
    private IFilesBiz filesBiz;


    /**
     * 文件上传
     *
     * @param request
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public Result<FilesDTO> doUpload(HttpServletRequest request, @RequestParam String type) throws Exception
    {
        MultipartFile multipartFile = HttpServlet.getUploadFile("file");
        FilesDTO filesDTO = filesBiz.saveFileInfoByMultipart(type, multipartFile);

        filesDTO.setAbsoluteFile(null);
        return Result.OK("", filesDTO);
    }

    /**
     * 根据文件ID获取文件列表
     *
     * @param idsVo
     * @return
     */
    @RequestMapping(value = "/getFilesList", method = RequestMethod.POST)
    public Result<List<FilesDTO>> getFilesList(@RequestBody IdsDTO idsVo)
    {
        List<FilesDTO> dataList = new ArrayList<>();
        for(String id : idsVo.getIds())
        {
            Files files = filesService.getById(id);
            if(files == null)
            {
                continue;
            }

            ContentType contentType = contentTypeService.getContentTypeByFilename(files.getFilename());

            FilesDTO filesDTO = BeanUtil.copy(files, FilesDTO.class);
            filesDTO.setSize(StringUtil.formatFileSize(files.getSize()));
            filesDTO.setCanView(contentType.getCanView());
            filesDTO.setCreateTime(DateUtil.format(files.getCreateTime()));

            dataList.add(filesDTO);
        }

        return Result.OK("", dataList);
    }

    /**
     * 文件预览
     *
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public void view(HttpServletResponse response, @RequestParam String id) throws Exception
    {
        Files files = filesService.getFilesInfoById(id);
        if(files == null)
        {
            HttpServlet.say(Result.error("文件不存在", ""));
            return;
        }


        ContentType contentType = contentTypeService.getContentTypeByFilename(files.getFilename());
        HttpServlet.say(response, FileUtil.getContentByte(files.getAbsoluteFile()), contentType.getContentType(), 200);
    }

    /**
     * 文件下载 不用登录
     *
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam String id) throws Exception
    {
        Files files = filesService.getFilesInfoById(id);
        if(files == null)
        {
            HttpServlet.say(Result.error("文件不存在", ""));
            return;
        }

        HttpServlet.download(files.getAbsoluteFile(), files.getFilename());
    }
}
