package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.ProbabilityValidationResult;
import com.damochaohe.play.entity.PlayPoolRewardEntity;
import com.damochaohe.play.mapper.PlayPoolRewardMapper;
import com.damochaohe.play.service.ProbabilityValidationService;
import com.damochaohe.product.entity.ProductSpuEntity;
import com.damochaohe.product.mapper.ProductSpuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProbabilityValidationServiceImpl implements ProbabilityValidationService {

    private final PlayPoolRewardMapper playPoolRewardMapper;
    private final ProductSpuMapper productSpuMapper;

    @Override
    public ProbabilityValidationResult validatePoolProbability(Long poolId, BigDecimal configuredPricePerDraw) {
        List<PlayPoolRewardEntity> rewards = playPoolRewardMapper.selectList(new LambdaQueryWrapper<PlayPoolRewardEntity>()
                .eq(PlayPoolRewardEntity::getPoolId, poolId)
                .eq(PlayPoolRewardEntity::getStatus, 1));
        if (rewards.isEmpty()) {
            throw new BusinessException("奖池没有可校验的启用奖品");
        }

        BigDecimal probabilityTotal = BigDecimal.ZERO;
        BigDecimal expectedCostPerDraw = BigDecimal.ZERO;
        for (PlayPoolRewardEntity reward : rewards) {
            BigDecimal probability = reward.getProbability() == null ? BigDecimal.ZERO : reward.getProbability();
            probabilityTotal = probabilityTotal.add(probability);

            ProductSpuEntity product = productSpuMapper.selectById(reward.getProductId());
            BigDecimal costPrice = product == null || product.getCostPrice() == null ? BigDecimal.ZERO : product.getCostPrice();
            expectedCostPerDraw = expectedCostPerDraw.add(
                    costPrice.multiply(probability).divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP)
            );
        }

        BigDecimal price = configuredPricePerDraw == null ? new BigDecimal("10.00") : configuredPricePerDraw;
        BigDecimal grossMargin = price.subtract(expectedCostPerDraw).setScale(4, RoundingMode.HALF_UP);
        boolean probabilityPassed = probabilityTotal.compareTo(new BigDecimal("100")) <= 0;
        boolean marginPassed = grossMargin.compareTo(BigDecimal.ZERO) > 0;
        boolean passed = probabilityPassed && marginPassed;

        return ProbabilityValidationResult.builder()
                .passed(passed)
                .probabilityTotal(probabilityTotal.setScale(4, RoundingMode.HALF_UP))
                .expectedCostPerDraw(expectedCostPerDraw.setScale(4, RoundingMode.HALF_UP))
                .configuredPricePerDraw(price.setScale(2, RoundingMode.HALF_UP))
                .grossMarginPerDraw(grossMargin)
                .message(buildMessage(probabilityPassed, marginPassed, probabilityTotal, grossMargin))
                .build();
    }

    private String buildMessage(boolean probabilityPassed, boolean marginPassed, BigDecimal probabilityTotal, BigDecimal grossMargin) {
        if (!probabilityPassed) {
            return "概率总和超过 100%，当前为 " + probabilityTotal;
        }
        if (!marginPassed) {
            return "理论单抽毛利小于等于 0，当前毛利为 " + grossMargin;
        }
        return "概率与理论毛利校验通过";
    }
}
