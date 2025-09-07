package com.financial.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "权益项")
public class BenefitItemDTO
{
    @Schema(description = "权益名称，例如：5% Cash Back")
    private String name;

    @Schema(description = "权益描述，例如：适用于餐厅消费")
    private String description;
}
