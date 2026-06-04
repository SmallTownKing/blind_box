package com.damochaohe.play.service.impl;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.AdminPoolPublishRequest;
import com.damochaohe.play.dto.ProbabilityValidationResult;
import com.damochaohe.play.entity.PlayPoolEntity;
import com.damochaohe.play.mapper.PlayPoolMapper;
import com.damochaohe.play.service.AdminPoolPublishService;
import com.damochaohe.play.service.ProbabilityValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminPoolPublishServiceImpl implements AdminPoolPublishService {

    private final PlayPoolMapper playPoolMapper;
    private final ProbabilityValidationService probabilityValidationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(AdminPoolPublishRequest request) {
        PlayPoolEntity poolEntity = playPoolMapper.selectById(request.getPoolId());
        if (poolEntity == null) {
            throw new BusinessException("奖池不存在");
        }
        ProbabilityValidationResult result = probabilityValidationService.validatePoolProbability(request.getPoolId(), request.getConfiguredPricePerDraw());
        if (!Boolean.TRUE.equals(result.getPassed())) {
            throw new BusinessException("奖池发布失败：" + result.getMessage());
        }
        poolEntity.setStatus(1);
        playPoolMapper.updateById(poolEntity);
    }
}
