package com.financial.manage.controller;

import cn.com.dgwl.tools.v2.util.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.financial.manage.dto.UserOfferDTO;
import com.financial.manage.entity.User;
import com.financial.manage.entity.UserOffer;
import com.financial.manage.service.IUserOfferService;
import com.financial.manage.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 用户参与的活动
 * @Author: jeecg-boot
 * @Date:   2025-08-21
 * @Version: V1.0
 */
@Api(tags="用户参与的活动")
@RestController
@RequestMapping("/manage/userOffer")
@Slf4j
public class UserOfferController extends JeecgController<UserOffer, IUserOfferService>
{
	@Autowired
	private IUserOfferService userOfferService;

	@Autowired
	private IUserService userService;

	/**
	 * 分页列表查询
	 *
	 * @param userOffer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户参与的活动-分页列表查询")
	@ApiOperation(value="用户参与的活动-分页列表查询", notes="用户参与的活动-分页列表查询")
	@GetMapping(value = "/list")
	public Result<Page<UserOfferDTO>> queryPageList(UserOffer userOffer, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<UserOffer> queryWrapper = QueryGenerator.initQueryWrapper(userOffer, req.getParameterMap());
		Page<UserOffer> page = new Page<UserOffer>(pageNo, pageSize);
		IPage<UserOffer> pageList = userOfferService.page(page, queryWrapper);


		List<UserOfferDTO> dataList = new ArrayList<>();
		for(UserOffer offer : pageList.getRecords())
		{
			User user = userService.getById(offer.getUserId());

			UserOfferDTO dto = BeanUtil.copy(offer, UserOfferDTO.class);
			dto.setUsername(user.getUsername());
			dataList.add(dto);
		}

		Page<UserOfferDTO> pageInfo = new Page<>();
		pageInfo.setTotal(pageList.getTotal()).setSize(pageSize).setCurrent(pageNo).setRecords(dataList);

		return Result.OK(pageInfo);
	}

	/**
	 *   添加
	 *
	 * @param userOffer
	 * @return
	 */
	@AutoLog(value = "用户参与的活动-添加")
	@ApiOperation(value="用户参与的活动-添加", notes="用户参与的活动-添加")
	//@RequiresPermissions("com.financial:user_offer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserOffer userOffer)
	{
		userOfferService.save(userOffer);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param userOffer
	 * @return
	 */
	@AutoLog(value = "用户参与的活动-编辑")
	@ApiOperation(value="用户参与的活动-编辑", notes="用户参与的活动-编辑")
	@RequiresPermissions("com.financial:user_offer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserOffer userOffer)
	{
		userOfferService.updateById(userOffer);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户参与的活动-通过id删除")
	@ApiOperation(value="用户参与的活动-通过id删除", notes="用户参与的活动-通过id删除")
	@RequiresPermissions("com.financial:user_offer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		userOfferService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户参与的活动-批量删除")
	@ApiOperation(value="用户参与的活动-批量删除", notes="用户参与的活动-批量删除")
	@RequiresPermissions("com.financial:user_offer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.userOfferService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户参与的活动-通过id查询")
	@ApiOperation(value="用户参与的活动-通过id查询", notes="用户参与的活动-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserOffer> queryById(@RequestParam(name="id",required=true) String id)
	{
		UserOffer userOffer = userOfferService.getById(id);
		if(userOffer == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(userOffer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userOffer
    */
    //@RequiresPermissions("com.financial:user_offer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserOffer userOffer)
    {
        return super.exportXls(request, userOffer, UserOffer.class, "用户参与的活动");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("user_offer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, UserOffer.class);
    }
}
