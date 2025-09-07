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
 * @Description: user
 * @Author: dgwl
 * @Date:   2025-08-20
 * @Version: V1.0
 */
@Data
@TableName("user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user对象", description="user")
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private java.lang.Integer id;

	/**
	 * 用户名
	 */
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private java.lang.String username;

	/**
	 * 邮箱地址
	 */
	@Excel(name = "邮箱地址", width = 15)
    @ApiModelProperty(value = "邮箱地址")
    private java.lang.String email;

	/**
	 * Google ID
	 */
	@Excel(name = "Google ID", width = 15)
    @ApiModelProperty(value = "Google ID")
    private java.lang.String googleId;

	/**
	 * Apple ID
	 */
	@Excel(name = "Apple ID", width = 15)
    @ApiModelProperty(value = "Apple ID")
    private java.lang.String appleId;

	/**
	 * 登录类型（GOOGLE：Google，APPLE：Apple，GUEST：游客）
	 */
	@Excel(name = "登录类型（GOOGLE：Google，APPLE：Apple，GUEST：游客）", width = 15)
    @ApiModelProperty(value = "登录类型（GOOGLE：Google，APPLE：Apple，GUEST：游客）")
    private java.lang.String loginType;

	/**
	 * 设备类型 1：ios、2：Android
	 */
	@Excel(name = "设备类型 1：ios、2：Android", width = 15)
	@ApiModelProperty(value = "设备类型 1：ios、2：Android")
	private java.lang.Integer deviceType;

	/**
	 * 设备ID
	 */
	@Excel(name = "设备ID", width = 15)
	@ApiModelProperty(value = "设备ID")
	private java.lang.String deviceCode;

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
