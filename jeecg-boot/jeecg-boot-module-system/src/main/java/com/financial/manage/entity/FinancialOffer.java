package com.financial.manage.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: financial_offer
 * @Author: dgwl
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Data
@TableName("financial_offer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="financial_offer对象", description="financial_offer")
public class FinancialOffer implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private java.lang.Integer id;

	/**
	 * 活动分类
	 */
	@Excel(name = "活动分类", width = 15)
    @ApiModelProperty(value = "活动分类")
    private java.lang.String category;

	/**
	 * 二级分类
	 */
	@Excel(name = "二级分类", width = 15)
	@ApiModelProperty(value = "二级分类")
	private String childCategory;

	/**
	 * 标题
	 */
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private java.lang.String title;

	/**
	 * 描述
	 */
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String description;

	/**
	 * 活动提供机构名称
	 */
	@Excel(name = "活动提供机构名称", width = 15)
    @ApiModelProperty(value = "活动提供机构名称")
    private java.lang.String provider;

	/**
	 * 奖励列表（JSON）
	 */
	@Excel(name = "奖励列表（JSON）", width = 15)
    @ApiModelProperty(value = "奖励列表（JSON）")
    private java.lang.String rewardList;

	/**
	 * 权益列表（JSON）
	 */
	@Excel(name = "权益列表（JSON）", width = 15)
    @ApiModelProperty(value = "权益列表（JSON）")
    private java.lang.String benefitList;

	/**
	 * 活动链接URL
	 */
	@Excel(name = "活动链接URL", width = 15)
    @ApiModelProperty(value = "活动链接URL")
    private java.lang.String url;

	/**
	 * 活动截止日期
	 */
	@Excel(name = "活动截止日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "活动截止日期")
    private java.util.Date expirationDate;

	/**
	 * 排序因子，值越小越靠前
	 */
	@Excel(name = "排序因子，值越小越靠前", width = 15)
    @ApiModelProperty(value = "排序因子，值越小越靠前")
    private java.lang.Integer sortOrder;

	/**
	 * 消费金额要求
	 */
	@Excel(name = "消费金额要求", width = 15)
    @ApiModelProperty(value = "消费金额要求")
    private java.math.BigDecimal spendingAmount;

	/**
	 * 完成消费要求的时间（天）
	 */
	@Excel(name = "完成消费要求的时间（天）", width = 15)
    @ApiModelProperty(value = "完成消费要求的时间（天）")
    private java.lang.Integer spendingDurationDays;

	/**
	 * 存款金额要求
	 */
	@Excel(name = "存款金额要求", width = 15)
    @ApiModelProperty(value = "存款金额要求")
    private java.math.BigDecimal depositAmount;

	/**
	 * 完成存款要求的时间（天）
	 */
	@Excel(name = "完成存款要求的时间（天）", width = 15)
    @ApiModelProperty(value = "完成存款要求的时间（天）")
    private java.lang.Integer depositDurationDays;

	/**
	 * 年费金额
	 */
	@Excel(name = "年费金额", width = 15)
    @ApiModelProperty(value = "年费金额")
    private java.math.BigDecimal annualFee;

	/**
	 * 免年费条件说明
	 */
	@Excel(name = "免年费条件说明", width = 15)
    @ApiModelProperty(value = "免年费条件说明")
    private java.lang.String feeWaiverCondition;

	/**
	 * 返现金额
	 */
	@Excel(name = "返现金额", width = 15)
	@ApiModelProperty(value = "返现金额")
	private java.math.BigDecimal returnAmount;

	/**
	 * 返现说明
	 */
	@Excel(name = "返现说明", width = 15)
	@ApiModelProperty(value = "返现金额")
	private String returnRemark;

	/**
	 * 获得积分
	 */
	@Excel(name = "获得积分", width = 15)
	@ApiModelProperty(value = "获得积分")
	private java.lang.Integer getPoint;

	/**
	 * 积分说明
	 */
	@Excel(name = "积分说明", width = 15)
	@ApiModelProperty(value = "积分说明")
	private String pointRemark;


	/**
	 * 活动状态：0-隐藏，1-展示
	 */
	@Excel(name = "活动状态：0-隐藏，1-展示", width = 15)
    @ApiModelProperty(value = "活动状态：0-隐藏，1-展示")
    private java.lang.Integer status;

	/**
	 * 创建时间
	 */
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createdAt;

	/**
	 * 更新时间
	 */
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updatedAt;
}
