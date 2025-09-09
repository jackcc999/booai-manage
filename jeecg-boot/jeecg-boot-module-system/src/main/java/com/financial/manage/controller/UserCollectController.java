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

import cn.com.dgwl.tools.v2.util.BeanUtil;
import com.financial.manage.dto.UserCollectDTO;
import com.financial.manage.service.IFinancialOfferService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import com.financial.manage.entity.UserCollect;
import com.financial.manage.service.IUserCollectService;

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
 * @Description: 用户收藏
 * @Author: jeecg-boot
 * @Date:   2025-09-09
 * @Version: V1.0
 */
@Api(tags="用户收藏")
@RestController
@RequestMapping("/manage/userCollect")
@Slf4j
public class UserCollectController extends JeecgController<UserCollect, IUserCollectService>
{
	@Autowired
	private IUserCollectService userCollectService;

	@Autowired
	private IFinancialOfferService financialOfferService;


	/**
	 * 分页列表查询
	 *
	 * @param userCollect
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户收藏-分页列表查询")
	@ApiOperation(value="用户收藏-分页列表查询", notes="用户收藏-分页列表查询")
	@GetMapping(value = "/list")
	public Result<Page<UserCollectDTO>> queryPageList(UserCollect userCollect, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<UserCollect> queryWrapper = QueryGenerator.initQueryWrapper(userCollect, req.getParameterMap());
		Page<UserCollect> page = new Page<UserCollect>(pageNo, pageSize);
		IPage<UserCollect> pageList = userCollectService.page(page, queryWrapper);

		Map<Integer, String> financialMap = financialOfferService.getFinancialOfferMapByIds(pageList.getRecords().stream().map(UserCollect::getOfferId).collect(Collectors.toList()));

		Page<UserCollectDTO> pageInfo = new Page<>();
		pageInfo.setTotal(pageList.getTotal()).setSize(pageSize).setCurrent(pageNo).setRecords(pageList.getRecords().stream().map(item -> {
			UserCollectDTO dto = BeanUtil.copy(item, UserCollectDTO.class);
			dto.setTitle(financialMap.get(item.getOfferId()));

			return dto;
		}).collect(Collectors.toList()));

		return Result.OK(pageInfo);
	}

	/**
	 *   添加
	 *
	 * @param userCollect
	 * @return
	 */
	@AutoLog(value = "用户收藏-添加")
	@ApiOperation(value="用户收藏-添加", notes="用户收藏-添加")
	//@RequiresPermissions("com.financial:user_collect:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserCollect userCollect)
	{
		userCollectService.save(userCollect);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param userCollect
	 * @return
	 */
	@AutoLog(value = "用户收藏-编辑")
	@ApiOperation(value="用户收藏-编辑", notes="用户收藏-编辑")
	//@RequiresPermissions("com.financial:user_collect:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserCollect userCollect)
	{
		userCollectService.updateById(userCollect);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户收藏-通过id删除")
	@ApiOperation(value="用户收藏-通过id删除", notes="用户收藏-通过id删除")
	//@RequiresPermissions("com.financial:user_collect:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		userCollectService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户收藏-批量删除")
	@ApiOperation(value="用户收藏-批量删除", notes="用户收藏-批量删除")
	//@RequiresPermissions("com.financial:user_collect:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.userCollectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户收藏-通过id查询")
	@ApiOperation(value="用户收藏-通过id查询", notes="用户收藏-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserCollect> queryById(@RequestParam(name="id",required=true) String id)
	{
		UserCollect userCollect = userCollectService.getById(id);
		if(userCollect == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(userCollect);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userCollect
    */
    //@RequiresPermissions("com.financial:user_collect:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserCollect userCollect)
    {
        return super.exportXls(request, userCollect, UserCollect.class, "用户收藏");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("user_collect:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, UserCollect.class);
    }
}
