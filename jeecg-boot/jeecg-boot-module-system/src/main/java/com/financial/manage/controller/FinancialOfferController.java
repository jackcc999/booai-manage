package com.financial.manage.controller;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.dgwl.tools.v2.util.JsonUtil;
import cn.com.dgwl.tools.v2.util.StringUtil;
import com.financial.manage.dto.RewardItemDTO;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import com.financial.manage.entity.FinancialOffer;
import com.financial.manage.service.IFinancialOfferService;

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
 * @Description: financial_offer
 * @Author: jeecg-boot
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Api(tags="financial_offer")
@RestController
@RequestMapping("/manage/financialOffer")
@Slf4j
public class FinancialOfferController extends JeecgController<FinancialOffer, IFinancialOfferService>
{
	@Autowired
	private IFinancialOfferService financialOfferService;

	/**
	 * 分页列表查询
	 *
	 * @param financialOffer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "financial_offer-分页列表查询")
	@ApiOperation(value="financial_offer-分页列表查询", notes="financial_offer-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FinancialOffer>> queryPageList(FinancialOffer financialOffer, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo, @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req)
	{
		QueryWrapper<FinancialOffer> queryWrapper = QueryGenerator.initQueryWrapper(financialOffer, req.getParameterMap());
		Page<FinancialOffer> page = new Page<FinancialOffer>(pageNo, pageSize);
		IPage<FinancialOffer> pageList = financialOfferService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param financialOffer
	 * @return
	 */
	@AutoLog(value = "financial_offer-添加")
	@ApiOperation(value="financial_offer-添加", notes="financial_offer-添加")
	//@RequiresPermissions("com.financial:financial_offer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FinancialOffer financialOffer)
	{
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(financialOffer.getTitle());
		joiner.add(financialOffer.getProvider());
		joiner.add(financialOffer.getDepositAmount().toPlainString());
		joiner.add(financialOffer.getReturnAmount().toString());
		joiner.add(financialOffer.getGetPoint().toString());

		financialOffer.setKeyword(joiner.toString());

		financialOfferService.save(financialOffer);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param financialOffer
	 * @return
	 */
	@AutoLog(value = "financial_offer-编辑")
	@ApiOperation(value="financial_offer-编辑", notes="financial_offer-编辑")
	//@RequiresPermissions("com.financial:financial_offer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FinancialOffer financialOffer)
	{
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(financialOffer.getTitle());
		joiner.add(financialOffer.getProvider());
		joiner.add(financialOffer.getDepositAmount().toPlainString());
		joiner.add(financialOffer.getReturnAmount().toString());
		joiner.add(financialOffer.getGetPoint().toString());

		financialOffer.setKeyword(joiner.toString());

		financialOfferService.updateById(financialOffer);
		return Result.OK("编辑成功");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "financial_offer-通过id删除")
	@ApiOperation(value="financial_offer-通过id删除", notes="financial_offer-通过id删除")
	//@RequiresPermissions("com.financial:financial_offer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id)
	{
		financialOfferService.removeById(id);
		return Result.OK("删除成功");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "financial_offer-批量删除")
	@ApiOperation(value="financial_offer-批量删除", notes="financial_offer-批量删除")
	//@RequiresPermissions("com.financial:financial_offer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids)
	{
		this.financialOfferService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "financial_offer-通过id查询")
	@ApiOperation(value="financial_offer-通过id查询", notes="financial_offer-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FinancialOffer> queryById(@RequestParam(name="id",required=true) String id)
	{
		FinancialOffer financialOffer = financialOfferService.getById(id);
		if(financialOffer == null)
		{
			return Result.error("未找到对应数据");
		}
		return Result.OK(financialOffer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param financialOffer
    */
    //@RequiresPermissions("com.financial:financial_offer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FinancialOffer financialOffer)
    {
        return super.exportXls(request, financialOffer, FinancialOffer.class, "financial_offer");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("financial_offer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response)
    {
        return super.importExcel(request, response, FinancialOffer.class);
    }
}
