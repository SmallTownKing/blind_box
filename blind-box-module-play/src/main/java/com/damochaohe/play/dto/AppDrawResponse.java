package com.damochaohe.play.dto;

import com.damochaohe.asset.dto.AssetOverviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户端抽赏响应。
 */
@Data
@Builder
@Schema(description = "用户端抽赏响应")
public class AppDrawResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "奖池 ID")
    private Long poolId;

    @Schema(description = "抽取次数")
    private Integer drawCount;

    @Schema(description = "支付方式组合")
    private String payModes;

    @Schema(description = "本次支付金额")
    private BigDecimal totalCost;

    @Schema(description = "抽赏结果")
    private List<AppDrawRewardItem> rewards;

    @Schema(description = "交易记录")
    private AppTradeRecordResponse tradeRecord;

    @Schema(description = "入仓记录")
    private List<AppWarehouseItemResponse> warehouseItems;

    @Schema(description = "扣减后的资产快照")
    private AssetOverviewResponse assetOverview;

    @Schema(description = "支付拆账明细")
    private List<PaymentSplitDetail> paymentSplits;

    @Schema(description = "结果摘要")
    private String resultSummary;
}
