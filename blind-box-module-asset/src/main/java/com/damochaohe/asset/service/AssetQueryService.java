package com.damochaohe.asset.service;

import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.dto.AssetFlowResponse;
import com.damochaohe.asset.dto.AdminAssetAdjustRequest;
import com.damochaohe.asset.dto.AdminAssetAdjustResponse;

/**
 * 资产查询服务。
 */
public interface AssetQueryService {

    /**
     * 查询用户资产总览。
     *
     * @param userId 用户 ID
     * @return 资产总览
     */
    AssetOverviewResponse getAssetOverview(Long userId);

    /**
     * 查询资产流水。
     *
     * @param userId 用户 ID
     * @return 资产流水
     */
    AssetFlowResponse getAssetFlows(Long userId);

    /**
     * 模拟扣减抽赏资产。
     *
     * @param userId 用户 ID
     * @param totalCost 总金额
     * @param payModes 支付方式组合
     * @return 扣减后的资产快照
     */
    AssetOverviewResponse simulateDeductForDraw(Long userId, java.math.BigDecimal totalCost, String payModes);

    /**
     * 后台人工调整资产。
     *
     * @param request 调整请求
     * @return 调整结果
     */
    AdminAssetAdjustResponse adminAdjustAsset(AdminAssetAdjustRequest request);
}
