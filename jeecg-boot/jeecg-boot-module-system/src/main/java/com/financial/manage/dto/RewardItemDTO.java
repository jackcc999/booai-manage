package com.financial.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "奖励项")
public class RewardItemDTO
{
    @Schema(description = "奖励名称，例如：5% Cash Back")
    private String name;

    @Schema(description = "奖励描述，例如：适用于餐厅消费")
    private String description;

    @Schema(description = "估算价值")
    private BigDecimal estimatedValue;

    @Schema(description = "权益")
    private BigDecimal rights;

    @Schema(description = "奖励 ")
    private BigDecimal reward;
}
