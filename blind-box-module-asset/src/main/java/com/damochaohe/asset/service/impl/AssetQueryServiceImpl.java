package com.damochaohe.asset.service.impl;

import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.service.AssetQueryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 资产查询服务实现。
 *
 * <p>当前为演示数据，后续接入统一账户表 [`user_asset_account`](backend-system-design.md)。</p>
 */
@Service
public class AssetQueryServiceImpl implements AssetQueryService {

    @Override
    public AssetOverviewResponse getAssetOverview(Long userId) {
        return AssetOverviewResponse.builder()
                .balanceAmount(new BigDecimal("128.50"))
                .goldCoinAmount(8800)
                .magicCrystalAmount(5200)
                .build();
    }
}
