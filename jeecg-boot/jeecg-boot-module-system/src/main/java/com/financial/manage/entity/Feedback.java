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
 * @Description: 用户反馈
 * @Author: dgwl
 * @Date:   2025-09-21
 * @Version: V1.0
 */
@Data
@TableName("feedback")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="feedback对象", description="用户反馈")
public class Feedback implements Serializable
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
	 * 反馈内容
	 */
	@Excel(name = "反馈内容", width = 15)
    @ApiModelProperty(value = "反馈内容")
    private java.lang.String content;

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
