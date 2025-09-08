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
 * @Description: 版本管理
 * @Author: dgwl
 * @Date:   2025-09-08
 * @Version: V1.0
 */
@Data
@TableName("app_version")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="app_version对象", description="版本管理")
public class AppVersion implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private java.lang.Integer id;

	/**
	 * 版本名称
	 */
	@Excel(name = "版本名称", width = 15)
    @ApiModelProperty(value = "版本名称")
    private java.lang.String name;

	/**
	 * 类型 ios、android
	 */
	@Excel(name = "类型 ios、android", width = 15)
    @ApiModelProperty(value = "类型 ios、android")
    private java.lang.String type;

	/**
	 * 版本号
	 */
	@Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
    private java.lang.String version;

	/**
	 * 版本描述
	 */
	@Excel(name = "版本描述", width = 15)
    @ApiModelProperty(value = "版本描述")
    private java.lang.String description;

	/**
	 * 版本文件
	 */
	@Excel(name = "版本文件", width = 15)
    @ApiModelProperty(value = "版本文件")
    private java.lang.String url;

	/**
	 * 状态
	 */
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
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
