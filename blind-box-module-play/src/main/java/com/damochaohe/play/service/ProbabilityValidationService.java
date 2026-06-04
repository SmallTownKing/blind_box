package com.damochaohe.play.service;

import com.damochaohe.play.dto.ProbabilityValidationResult;

public interface ProbabilityValidationService {

    ProbabilityValidationResult validatePoolProbability(Long poolId, java.math.BigDecimal configuredPricePerDraw);
}
