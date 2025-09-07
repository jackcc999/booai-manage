package com.financial.manage.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description: 用户参与的活动
 * @Author: dgwl
 * @Date:   2025-08-21
 * @Version: V1.0
 */
@Data
@TableName("user_offer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_offer对象", description="用户参与的活动")
public class UserOffer implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private java.lang.Integer id;

	/**
	 * 用户ID
	 */
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;

	/**
	 * 活动ID
	 */
	@Excel(name = "活动ID", width = 15)
    @ApiModelProperty(value = "活动ID")
    private java.lang.Integer offerId;

	/**
	 * 状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）
	 */
	@Excel(name = "状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）", width = 15)
    @ApiModelProperty(value = "状态（APPLYING: 申请中, COMPLETED: 已完成, REJECTED: 已拒绝, REWARDING: 领取奖励中）")
    private java.lang.String status;

	/**
	 * 活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）
	 */
	@Excel(name = "活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）", width = 15)
    @ApiModelProperty(value = "活动分类（BANK：银行，CREDIT_CARD：信用卡，BROKER：券商）")
    private java.lang.String offerCategory;

	/**
	 * 活动标题
	 */
	@Excel(name = "活动标题", width = 15)
    @ApiModelProperty(value = "活动标题")
    private java.lang.String offerTitle;

	/**
	 * 活动描述
	 */
	@Excel(name = "活动描述", width = 15)
    @ApiModelProperty(value = "活动描述")
    private java.lang.String offerDescription;

	/**
	 * 活动提供机构名称
	 */
	@Excel(name = "活动提供机构名称", width = 15)
    @ApiModelProperty(value = "活动提供机构名称")
    private java.lang.String offerProvider;

	/**
	 * 活动链接URL
	 */
	@Excel(name = "活动链接URL", width = 15)
    @ApiModelProperty(value = "活动链接URL")
    private java.lang.String offerUrl;

	/**
	 * 奖励列表（JSON）
	 */
	@Excel(name = "奖励列表（JSON）", width = 15)
    @ApiModelProperty(value = "奖励列表（JSON）")
    private java.lang.String offerRewardList;

	/**
	 * 权益列表（JSON）
	 */
	@Excel(name = "权益列表（JSON）", width = 15)
    @ApiModelProperty(value = "权益列表（JSON）")
    private java.lang.String offerBenefitList;

	/**
	 * 审批时间
	 */
	@Excel(name = "审批时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审批时间")
    private java.util.Date approvalTime;

	/**
	 * 完成时间
	 */
	@Excel(name = "完成时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
    private java.util.Date completedTime;

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
