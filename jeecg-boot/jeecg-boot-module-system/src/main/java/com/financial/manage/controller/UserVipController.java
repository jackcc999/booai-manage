package com.financial.manage.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.dgwl.tools.v2.util.BeanUtil;
import cn.com.dgwl.tools.v2.util.DateUtil;
import cn.com.dgwl.tools.v2.util.StringUtil;
import com.financial.manage.dto.UserCollectDTO;
import com.financial.manage.dto.UserVipDTO;
import com.financial.manage.entity.User;
import com.financial.manage.entity.Vip;
import com.financial.manage.service.IUserService;
import com.financial.manage.service.IVipService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.financial.manage.entity.UserVip;
import com.financial.manage.service.IUserVipService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: user_vip
 * @Author: jeecg-boot
 * @Date:   2025-09-20
 * @Version: V1.0
 */
@Api(tags="user_vip")
@RestController
@RequestMapping("/manage/userVip")
@Slf4j
public class UserVipController extends JeecgController<UserVip, IUserVipService>
{
	@Autowired
	private IUserVipService userVipService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IVipService vipService;


	/**
	 * 分页列表查询
	 *
	 * @param userVip
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "user_vip-分页列表查询")
	@ApiOperation(value="user_vip-分页列表查询", notes="user_vip-分页列表查询")
	@GetMapping(value = "/list")
	public Result<Page<UserVipDTO>> queryPageList(UserVip userVip, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<UserVip> queryWrapper = QueryGenerator.initQueryWrapper(userVip, req.getParameterMap());
		Page<UserVip> page = new Page<UserVip>(pageNo, pageSize);
		IPage<UserVip> pageList = userVipService.page(page, queryWrapper);


		Page<UserVipDTO> pageInfo = new Page<>();
		pageInfo.setTotal(pageList.getTotal()).setSize(pageSize).setCurrent(pageNo).setRecords(pageList.getRecords().stream().map(item -> {
			UserVipDTO dto = BeanUtil.copy(item, UserVipDTO.class);
			dto.setVipName(vipService.getById(item.getVipId()).getName());

			return dto;
		}).collect(Collectors.toList()));

		return Result.OK(pageInfo);
	}

	/**
	 *   添加
	 *
	 * @param userVip
	 * @return
	 */
	@AutoLog(value = "user_vip-添加")
	@ApiOperation(value="user_vip-添加", notes="user_vip-添加")
	//@RequiresPermissions("com.financial:user_vip:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserVip userVip)
	{
		Vip vip = vipService.getById(userVip.getVipId());
		if(vip == null)
		{
			return Result.error("VIP不存在");
		}

		User user = userService.getById(userVip.getUserId());
		if(user == null)
		{
			return Result.error("用户不存在");
		}

		QueryWrapper<UserVip> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(UserVip::getUserId, userVip.getUserId())
				.orderByDesc(UserVip::getEndDate);
		List<UserVip> userVips = userVipService.list(wrapper);

		UserVip exitInfo = userVips.isEmpty() ? null : userVips.get(0);
		if(exitInfo != null && DateUtil.toLocalDate(exitInfo.getEndDate()).isBefore(LocalDate.now()))
		{
			exitInfo = null;
		}

		LocalDate beginDate = LocalDate.now();
		if(exitInfo != null)
		{
			beginDate = DateUtil.toLocalDate(exitInfo.getEndDate()).plusDays(1);
		}


		userVip.setPrice(vip.getPrice());
		userVip.setBeginDate(DateUtil.toDate(beginDate));
		userVip.setEndDate(DateUtil.toDate(beginDate.plusMonths(vip.getPeriod()).plusDays(-1)));

		userVipService.save(userVip);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param userVip
	 * @return
	 */
	@AutoLog(value = "user_vip-编辑")
	@ApiOperation(value="user_vip-编辑", notes="user_vip-编辑")
	//@RequiresPermissions("com.financial:user_vip:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserVip userVip)
	{
		QueryWrapper<UserVip> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(UserVip::getUserId, userVip.getUserId())
				.ne(UserVip::getId, userVip.getId());
		List<UserVip> userVips = userVipService.list(wrapper);
		for(UserVip info : userVips)
		{
			if(DateUtil.overlapsWith(info.getBeginDate(), info.getEndDate(), userVip.getBeginDate(), userVip.getEndDate()))
			{
				return Result.error(StringUtil.format("该时间段和已有的时间段有交叉[{}~{}]", DateUtil.format(info.getBeginDate(), DateUtil.FORMAT_DATE), DateUtil.format(info.getEndDate(), DateUtil.FORMAT_DATE)));
			}
		}

		userVip.setCreatedAt(null);
		userVip.setUpdatedAt(new Date());
		userVipService.updateById(userVip);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "user_vip-通过id删除")
	@ApiOperation(value="user_vip-通过id删除", notes="user_vip-通过id删除")
	//@RequiresPermissions("com.financial:user_vip:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		//userVipService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "user_vip-批量删除")
	@ApiOperation(value="user_vip-批量删除", notes="user_vip-批量删除")
	//@RequiresPermissions("com.financial:user_vip:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		//this.userVipService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "user_vip-通过id查询")
	@ApiOperation(value="user_vip-通过id查询", notes="user_vip-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserVip> queryById(@RequestParam(name="id",required=true) String id)
	{
		UserVip userVip = userVipService.getById(id);
		if(userVip == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(userVip);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userVip
    */
    //@RequiresPermissions("com.financial:user_vip:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserVip userVip)
    {
        return super.exportXls(request, userVip, UserVip.class, "user_vip");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("user_vip:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return null;//return super.importExcel(request, response, UserVip.class);
    }
}
