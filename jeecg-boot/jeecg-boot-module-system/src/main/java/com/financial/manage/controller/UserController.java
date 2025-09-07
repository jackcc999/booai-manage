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
import com.financial.manage.entity.User;
import com.financial.manage.service.IUserService;

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
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Api(tags="user")
@RestController
@RequestMapping("/manage/user")
@Slf4j
public class UserController extends JeecgController<User, IUserService>
{
	@Autowired
	private IUserService userService;

	/**
	 * 分页列表查询
	 *
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "user-分页列表查询")
	@ApiOperation(value="user-分页列表查询", notes="user-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<User>> queryPageList(User user, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<User> queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
		Page<User> page = new Page<User>(pageNo, pageSize);
		IPage<User> pageList = userService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param user
	 * @return
	 */
	@AutoLog(value = "user-添加")
	@ApiOperation(value="user-添加", notes="user-添加")
	//@RequiresPermissions("com.financial:user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody User user)
	{
		userService.save(user);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param user
	 * @return
	 */
	@AutoLog(value = "user-编辑")
	@ApiOperation(value="user-编辑", notes="user-编辑")
	//@RequiresPermissions("com.financial:user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody User user)
	{
		userService.updateById(user);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "user-通过id删除")
	@ApiOperation(value="user-通过id删除", notes="user-通过id删除")
	//@RequiresPermissions("com.financial:user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		userService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "user-批量删除")
	@ApiOperation(value="user-批量删除", notes="user-批量删除")
	//@RequiresPermissions("com.financial:user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.userService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "user-通过id查询")
	@ApiOperation(value="user-通过id查询", notes="user-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<User> queryById(@RequestParam(name="id",required=true) String id)
	{
		User user = userService.getById(id);
		if(user == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(user);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param user
    */
    //@RequiresPermissions("com.financial:user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, User user)
    {
        return super.exportXls(request, user, User.class, "user");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, User.class);
    }
}
