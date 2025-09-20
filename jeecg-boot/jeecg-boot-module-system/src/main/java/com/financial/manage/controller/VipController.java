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
import com.financial.manage.entity.Vip;
import com.financial.manage.service.IVipService;

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
 * @Description: vip
 * @Author: jeecg-boot
 * @Date:   2025-09-20
 * @Version: V1.0
 */
@Api(tags="vip")
@RestController
@RequestMapping("/manage/vip")
@Slf4j
public class VipController extends JeecgController<Vip, IVipService>
{
	@Autowired
	private IVipService vipService;

	/**
	 * 分页列表查询
	 *
	 * @param vip
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "vip-分页列表查询")
	@ApiOperation(value="vip-分页列表查询", notes="vip-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Vip>> queryPageList(Vip vip, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<Vip> queryWrapper = QueryGenerator.initQueryWrapper(vip, req.getParameterMap());
		Page<Vip> page = new Page<Vip>(pageNo, pageSize);
		IPage<Vip> pageList = vipService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param vip
	 * @return
	 */
	@AutoLog(value = "vip-添加")
	@ApiOperation(value="vip-添加", notes="vip-添加")
	//@RequiresPermissions("com.financial:vip:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Vip vip)
	{
		vipService.save(vip);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vip
	 * @return
	 */
	@AutoLog(value = "vip-编辑")
	@ApiOperation(value="vip-编辑", notes="vip-编辑")
	//@RequiresPermissions("com.financial:vip:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Vip vip)
	{
		vip.setCreateAt(null);
		vip.setUpdateAt(null);
		vipService.updateById(vip);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "vip-通过id删除")
	@ApiOperation(value="vip-通过id删除", notes="vip-通过id删除")
	//@RequiresPermissions("com.financial:vip:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		//vipService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "vip-批量删除")
	@ApiOperation(value="vip-批量删除", notes="vip-批量删除")
	//@RequiresPermissions("com.financial:vip:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		//this.vipService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "vip-通过id查询")
	@ApiOperation(value="vip-通过id查询", notes="vip-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Vip> queryById(@RequestParam(name="id",required=true) String id)
	{
		Vip vip = vipService.getById(id);
		if(vip == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(vip);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vip
    */
    //@RequiresPermissions("com.financial:vip:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Vip vip)
    {
        return super.exportXls(request, vip, Vip.class, "vip");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("vip:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, Vip.class);
    }

	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result<List<Vip>> getList()
	{
		QueryWrapper<Vip> wrapper = new QueryWrapper<>();
		wrapper.lambda().orderByAsc(Vip::getSort);

		return Result.OK("", vipService.list(wrapper));
	}
}
