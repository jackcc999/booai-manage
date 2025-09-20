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
 * @Description: vip
 * @Author: dgwl
 * @Date:   2025-09-20
 * @Version: V1.0
 */
@Data
@TableName("vip")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="vip对象", description="vip")
public class Vip implements Serializable
{
    private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;

	/**
	 * 会员名称
	 */
	@Excel(name = "会员名称", width = 15)
    @ApiModelProperty(value = "会员名称")
    private java.lang.String name;

	/**
	 * 会员等级
	 */
	@Excel(name = "会员等级", width = 15)
    @ApiModelProperty(value = "会员等级")
    private java.lang.String level;

	/**
	 * 会员周期 单位：月
	 */
	@Excel(name = "会员周期 单位：月", width = 15)
    @ApiModelProperty(value = "会员周期 单位：月")
    private java.lang.Integer period;

	/**
	 * 会员价格
	 */
	@Excel(name = "会员价格", width = 15)
    @ApiModelProperty(value = "会员价格")
    private java.math.BigDecimal price;

	/**
	 * 会员描述
	 */
	@Excel(name = "会员描述", width = 15)
    @ApiModelProperty(value = "会员描述")
    private java.lang.String description;

	/**
	 * 排序 小到大
	 */
	@Excel(name = "排序 小到大", width = 15)
    @ApiModelProperty(value = "排序 小到大")
    private java.lang.Integer sort;

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
