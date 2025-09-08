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
import com.financial.manage.entity.AppVersion;
import com.financial.manage.service.IAppVersionService;

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
 * @Description: 版本管理
 * @Author: jeecg-boot
 * @Date:   2025-09-08
 * @Version: V1.0
 */
@Api(tags="版本管理")
@RestController
@RequestMapping("/manage/appVersion")
@Slf4j
public class AppVersionController extends JeecgController<AppVersion, IAppVersionService>
{
	@Autowired
	private IAppVersionService appVersionService;

	/**
	 * 分页列表查询
	 *
	 * @param appVersion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "版本管理-分页列表查询")
	@ApiOperation(value="版本管理-分页列表查询", notes="版本管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AppVersion>> queryPageList(AppVersion appVersion, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<AppVersion> queryWrapper = QueryGenerator.initQueryWrapper(appVersion, req.getParameterMap());
		Page<AppVersion> page = new Page<AppVersion>(pageNo, pageSize);
		IPage<AppVersion> pageList = appVersionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param appVersion
	 * @return
	 */
	@AutoLog(value = "版本管理-添加")
	@ApiOperation(value="版本管理-添加", notes="版本管理-添加")
	//@RequiresPermissions("com.financial:app_version:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AppVersion appVersion)
	{
		appVersionService.save(appVersion);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param appVersion
	 * @return
	 */
	@AutoLog(value = "版本管理-编辑")
	@ApiOperation(value="版本管理-编辑", notes="版本管理-编辑")
	//@RequiresPermissions("com.financial:app_version:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AppVersion appVersion)
	{
		appVersionService.updateById(appVersion);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "版本管理-通过id删除")
	@ApiOperation(value="版本管理-通过id删除", notes="版本管理-通过id删除")
	//@RequiresPermissions("com.financial:app_version:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		appVersionService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "版本管理-批量删除")
	@ApiOperation(value="版本管理-批量删除", notes="版本管理-批量删除")
	//@RequiresPermissions("com.financial:app_version:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.appVersionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "版本管理-通过id查询")
	@ApiOperation(value="版本管理-通过id查询", notes="版本管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AppVersion> queryById(@RequestParam(name="id",required=true) String id)
	{
		AppVersion appVersion = appVersionService.getById(id);
		if(appVersion == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(appVersion);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param appVersion
    */
    //@RequiresPermissions("com.financial:app_version:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AppVersion appVersion)
    {
        return super.exportXls(request, appVersion, AppVersion.class, "版本管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("app_version:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, AppVersion.class);
    }
}
