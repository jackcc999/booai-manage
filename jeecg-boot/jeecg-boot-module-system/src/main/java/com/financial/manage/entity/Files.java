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
 * @Description: mi_files
 * @Author: jeecg-boot
 * @Date: 2023-07-30
 * @Version: V1.0
 */
@Data
@TableName("mi_files")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "mi_files对象", description = "mi_files")
public class Files implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 文件名称
     */
    @Excel(name = "文件名称", width = 15)
    @ApiModelProperty(value = "文件名称")
    private String filename;
    /**
     * 文件URL地址
     */
    @Excel(name = "文件URL地址", width = 15)
    @ApiModelProperty(value = "文件URL地址")
    private String url;
    /**
     * 文件大小
     */
    @Excel(name = "文件大小", width = 15)
    @ApiModelProperty(value = "文件大小")
    private Long size;
    /**
     * 文件类型
     */
    @Excel(name = "文件类型", width = 15)
    @ApiModelProperty(value = "文件类型")
    private String type;
    /**
     * 文件绝对路径
     */
    @Excel(name = "文件绝对路径", width = 15)
    @ApiModelProperty(value = "文件绝对路径")
    private String absoluteFile;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
