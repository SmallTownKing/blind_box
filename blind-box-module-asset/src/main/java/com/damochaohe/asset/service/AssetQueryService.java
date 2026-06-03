package com.damochaohe.asset.service;

import com.damochaohe.asset.dto.AssetOverviewResponse;

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
}
