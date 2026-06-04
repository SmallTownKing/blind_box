package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.play.dto.DrawAuditLogResponse;
import com.damochaohe.play.entity.DrawAuditLogEntity;
import com.damochaohe.play.mapper.DrawAuditLogMapper;
import com.damochaohe.play.service.DrawAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrawAuditLogServiceImpl implements DrawAuditLogService {

    private final DrawAuditLogMapper drawAuditLogMapper;

    @Override
    public List<DrawAuditLogResponse> listAuditLogs() {
        return drawAuditLogMapper.selectList(new LambdaQueryWrapper<DrawAuditLogEntity>()
                        .orderByDesc(DrawAuditLogEntity::getId))
                .stream()
                .map(item -> DrawAuditLogResponse.builder()
                        .tradeNo(item.getTradeNo())
                        .userId(item.getUserId())
                        .poolId(item.getPoolId())
                        .probabilitySnapshot(item.getProbabilitySnapshot())
                        .targetRewardHit(item.getTargetRewardHit())
                        .finalRewardHit(item.getFinalRewardHit())
                        .stockChangeSnapshot(item.getStockChangeSnapshot())
                        .build())
                .toList();
    }
}
