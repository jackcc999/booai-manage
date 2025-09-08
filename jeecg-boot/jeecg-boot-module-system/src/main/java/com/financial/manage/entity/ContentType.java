package com.financial.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: mi_content_type
 * @Author: jeecg-boot
 * @Date: 2023-08-03
 * @Version: V1.0
 */
@Data
@TableName("mi_content_type")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "mi_content_type对象", description = "mi_content_type")
public class ContentType implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private Integer id;
    /**
     * 文件后缀
     */
    @Excel(name = "文件后缀", width = 15)
    @ApiModelProperty(value = "文件后缀")
    private String subfix;
    /**
     * Content Type
     */
    @Excel(name = "Content Type", width = 15)
    @ApiModelProperty(value = "Content Type")
    private String contentType;
    /**
     * 是否支持预览 1：是、0：否
     */
    @Excel(name = "是否支持预览 1：是、0：否", width = 15)
    @ApiModelProperty(value = "是否支持预览 1：是、0：否")
    private Integer canView;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
