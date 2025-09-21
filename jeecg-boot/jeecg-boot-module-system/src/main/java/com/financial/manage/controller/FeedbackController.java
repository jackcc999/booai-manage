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
import com.financial.manage.entity.Feedback;
import com.financial.manage.service.IFeedbackService;

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
 * @Description: 用户反馈
 * @Author: jeecg-boot
 * @Date:   2025-09-21
 * @Version: V1.0
 */
@Api(tags="用户反馈")
@RestController
@RequestMapping("/manage/feedback")
@Slf4j
public class FeedbackController extends JeecgController<Feedback, IFeedbackService>
{
	@Autowired
	private IFeedbackService feedbackService;

	/**
	 * 分页列表查询
	 *
	 * @param feedback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户反馈-分页列表查询")
	@ApiOperation(value="用户反馈-分页列表查询", notes="用户反馈-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Feedback>> queryPageList(Feedback feedback, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<Feedback> queryWrapper = QueryGenerator.initQueryWrapper(feedback, req.getParameterMap());
		Page<Feedback> page = new Page<Feedback>(pageNo, pageSize);
		IPage<Feedback> pageList = feedbackService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param feedback
	 * @return
	 */
	@AutoLog(value = "用户反馈-添加")
	@ApiOperation(value="用户反馈-添加", notes="用户反馈-添加")
	//@RequiresPermissions("com.financial:feedback:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Feedback feedback)
	{
		feedbackService.save(feedback);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param feedback
	 * @return
	 */
	@AutoLog(value = "用户反馈-编辑")
	@ApiOperation(value="用户反馈-编辑", notes="用户反馈-编辑")
	//@RequiresPermissions("com.financial:feedback:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Feedback feedback)
	{
		feedbackService.updateById(feedback);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户反馈-通过id删除")
	@ApiOperation(value="用户反馈-通过id删除", notes="用户反馈-通过id删除")
	//@RequiresPermissions("com.financial:feedback:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		feedbackService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户反馈-批量删除")
	@ApiOperation(value="用户反馈-批量删除", notes="用户反馈-批量删除")
	//@RequiresPermissions("com.financial:feedback:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.feedbackService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户反馈-通过id查询")
	@ApiOperation(value="用户反馈-通过id查询", notes="用户反馈-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Feedback> queryById(@RequestParam(name="id",required=true) String id)
	{
		Feedback feedback = feedbackService.getById(id);
		if(feedback == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(feedback);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param feedback
    */
    //@RequiresPermissions("com.financial:feedback:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Feedback feedback)
    {
        return super.exportXls(request, feedback, Feedback.class, "用户反馈");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("feedback:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, Feedback.class);
    }
}
