package com.financial.manage.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import com.financial.manage.entity.Information;
import com.financial.manage.service.IInformationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 咨询
 * @Author: jeecg-boot
 * @Date:   2025-09-06
 * @Version: V1.0
 */
@Api(tags="咨询")
@RestController
@RequestMapping("/manage/information")
@Slf4j
public class InformationController extends JeecgController<Information, IInformationService>
{
	@Autowired
	private IInformationService informationService;

	/**
	 * 分页列表查询
	 *
	 * @param information
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "咨询-分页列表查询")
	@ApiOperation(value="咨询-分页列表查询", notes="咨询-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Information>> queryPageList(Information information, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<Information> queryWrapper = QueryGenerator.initQueryWrapper(information, req.getParameterMap());
		Page<Information> page = new Page<Information>(pageNo, pageSize);
		IPage<Information> pageList = informationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param information
	 * @return
	 */
	@AutoLog(value = "咨询-添加")
	@ApiOperation(value="咨询-添加", notes="咨询-添加")
	//@RequiresPermissions("com.financial:information:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Information information)
	{
		informationService.save(information);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param information
	 * @return
	 */
	@AutoLog(value = "咨询-编辑")
	@ApiOperation(value="咨询-编辑", notes="咨询-编辑")
	//@RequiresPermissions("com.financial:information:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Information information)
	{
		informationService.updateById(information);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "咨询-通过id删除")
	@ApiOperation(value="咨询-通过id删除", notes="咨询-通过id删除")
	//@RequiresPermissions("com.financial:information:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		informationService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "咨询-批量删除")
	@ApiOperation(value="咨询-批量删除", notes="咨询-批量删除")
	//@RequiresPermissions("com.financial:information:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.informationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "咨询-通过id查询")
	@ApiOperation(value="咨询-通过id查询", notes="咨询-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Information> queryById(@RequestParam(name="id",required=true) String id)
	{
		Information information = informationService.getById(id);
		if(information == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(information);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param information
    */
    //@RequiresPermissions("com.financial:information:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Information information)
    {
        return super.exportXls(request, information, Information.class, "咨询");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("information:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, Information.class);
    }
}
