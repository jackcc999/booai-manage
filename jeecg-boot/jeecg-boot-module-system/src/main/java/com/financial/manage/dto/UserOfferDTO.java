package com.financial.manage.dto;

import com.financial.manage.entity.UserOffer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserOfferDTO extends UserOffer
{
    @ApiModelProperty("用户名称")
    private String username;
}
