package com.financial.manage.dto;

import com.financial.manage.entity.UserCollect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCollectDTO extends UserCollect
{
    @ApiModelProperty(value = "标题")
    private String offerTitle;

    @ApiModelProperty(value = "用户名")
    private String username;
}
