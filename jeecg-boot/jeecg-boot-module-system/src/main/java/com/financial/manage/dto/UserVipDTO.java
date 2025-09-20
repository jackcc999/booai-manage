package com.financial.manage.dto;

import com.financial.manage.entity.UserVip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVipDTO extends UserVip
{
    @ApiModelProperty("会员名称")
    private String vipName;
}
