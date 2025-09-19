package com.financial.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.com.dgwl.tools.v2.excel.annotation.Excel;
import cn.com.dgwl.tools.v2.excel.annotation.XssHeader;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
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
@XssHeader(fontColor = "#ffffff", backgroundColor = "#4472C4")
public class FinancialOffer implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
	@Excel(index = "A", name = "ID")
	private Integer id;
	/**
	 * 卡分类
	 */
	@ApiModelProperty(value = "卡分类")
	@Excel(index = "B", name = "卡分类")
	private String category;
	/**
	 * 二级分类
	 */
	@ApiModelProperty(value = "二级分类")
	@Excel(index = "C", name = "二级分类")
	private String childCategory;
	/**
	 * 开户活动标题
	 */
	@ApiModelProperty(value = "开户活动标题")
	@Excel(index = "D", name = "开户活动标题")
	private String title;
	/**
	 * 金融机构名称
	 */
	@ApiModelProperty(value = "金融机构名称")
	@Excel(index = "E", name = "金融机构名称")
	private String provider;
	/**
	 * 活动URL链接
	 */
	@ApiModelProperty(value = "活动URL链接")
	@Excel(index = "F", name = "活动URL链接")
	private String url;
	/**
	 * 存款金额要求
	 */
	@ApiModelProperty(value = "存款金额要求")
	@Excel(index = "G", name = "存款金额要求")
	private BigDecimal depositAmount;
	/**
	 * 存款要求时间
	 */
	@ApiModelProperty(value = "存款要求时间")
	@Excel(index = "H", name = "存款要求时间")
	private Integer depositDurationDays;
	/**
	 * 存款持有天数
	 */
	@ApiModelProperty(value = "存款持有天数")
	@Excel(index = "I", name = "存款持有天数")
	private Integer depositHoldDays;
	/**
	 * 返现金额
	 */
	@ApiModelProperty(value = "返现金额")
	@Excel(index = "J", name = "返现金额")
	private BigDecimal returnAmount;
	/**
	 * 返现说明
	 */
	@ApiModelProperty(value = "返现金额")
	@Excel(index = "K", name = "返现说明")
	private String returnRemark;
	/**
	 * 消费金额要求
	 */
	@ApiModelProperty(value = "消费金额要求")
	@Excel(index = "L", name = "消费金额要求")
	private BigDecimal spendingAmount;
	/**
	 * 消费要求时间
	 */
	@ApiModelProperty(value = "消费要求时间")
	@Excel(index = "M", name = "消费要求时间")
	private Integer spendingDurationDays;
	/**
	 * 获得积分
	 */
	@ApiModelProperty(value = "获得积分")
	@Excel(index = "N", name = "获得积分")
	private Integer getPoint;
	/**
	 * 积分说明
	 */
	@ApiModelProperty(value = "积分说明")
	@Excel(index = "O", name = "积分说明")
	private String pointRemark;
	/**
	 * 活动截止日期
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "活动截止日期")
	@Excel(index = "P", name = "活动截止日期", dateFormat = "yyyy-MM-dd")
	private java.util.Date expirationDate;
	/**
	 * 年费金额
	 */
	@ApiModelProperty(value = "年费金额")
	@Excel(index = "Q", name = "年费金额")
	private BigDecimal annualFee;
	/**
	 * 年费说明
	 */
	@ApiModelProperty(value = "年费说明")
	@Excel(index = "R", name = "年费说明")
	private String annualRemark;
	/**
	 * 月费金额
	 */
	@ApiModelProperty(value = "月费金额")
	@Excel(index = "S", name = "月费金额")
	private BigDecimal monthFee;
	/**
	 * 月费说明
	 */
	@ApiModelProperty(value = "月费说明")
	@Excel(index = "T", name = "月费说明")
	private String monthRemark;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@Excel(index = "U", name = "排序")
	private Integer sortOrder;
	/**
	 * 免年费条件说明
	 */
	@ApiModelProperty(value = "免年费条件说明")
	@Excel(index = "V", name = "免年费条件说明")
	private String feeWaiverCondition;
	/**
	 * 活动状态
	 */
	@ApiModelProperty(value = "活动状态")
	@Excel(index = "W", name = "活动状态")
	private Integer status;
	/**
	 * 奖励列表
	 */
	@ApiModelProperty(value = "奖励列表")
	@Excel(index = "X", name = "奖励列表")
	private String rewardList;
	/**
	 * 权益列表
	 */
	@ApiModelProperty(value = "权益列表")
	@Excel(index = "Y", name = "权益列表")
	private String benefitList;
	/**
	 * 活动描述
	 */
	@ApiModelProperty(value = "活动描述")
	@Excel(index = "Z", name = "活动描述")
	private String description;







	/**
	 * 关键词 搜索用
	 */
	@ApiModelProperty(value = "关键词 搜索用")
	private String keyword;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createdAt;
	/**
	 * 更新时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updatedAt;
}
