package com.financial.manage.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @Description: 活动广场
 * @Author: dgwl
 * @Date:   2025-09-21
 * @Version: V1.0
 */
@Data
@TableName("square")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="square对象", description="活动广场")
public class Square implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private java.lang.Integer id;

	/**
	 * 用户ID
	 */
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;

	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	@Excel(width = 15, name = "机构名称")
	private String provider;

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
    private java.lang.String childCategory;

	/**
	 * 活动链接URL
	 */
	@Excel(name = "活动链接URL", width = 15)
    @ApiModelProperty(value = "活动链接URL")
    private java.lang.String url;

	/**
	 * 开户权益
	 */
	@Excel(name = "开户权益", width = 15)
    @ApiModelProperty(value = "开户权益")
    private java.lang.String benefitList;

	/**
	 * 活动状态
	 */
	@Excel(name = "活动状态", width = 15)
    @ApiModelProperty(value = "活动状态")
    private java.lang.Integer status;

	/**
	 * 是否已删除（0：未删除，1：已删除）
	 */
	@Excel(name = "是否已删除（0：未删除，1：已删除）", width = 15)
    @ApiModelProperty(value = "是否已删除（0：未删除，1：已删除）")
	@TableLogic
    private java.lang.Integer isDeleted;

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
