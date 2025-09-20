package com.financial.manage.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: user_vip
 * @Author: dgwl
 * @Date:   2025-09-20
 * @Version: V1.0
 */
@Data
@TableName("user_vip")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_vip对象", description="user_vip")
public class UserVip implements Serializable
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
	 * VIP ID
	 */
	@Excel(name = "VIP ID", width = 15)
    @ApiModelProperty(value = "VIP ID")
    private java.lang.Integer vipId;

	/**
	 * VIP价格
	 */
	@Excel(name = "VIP价格", width = 15)
    @ApiModelProperty(value = "VIP价格")
    private java.math.BigDecimal price;

	/**
	 * 开始日期
	 */
	@Excel(name = "开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date beginDate;

	/**
	 * 结束日期
	 */
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

	/**
	 * 创建时间
	 */
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createAt;

	/**
	 * 更新时间
	 */
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateAt;
}
