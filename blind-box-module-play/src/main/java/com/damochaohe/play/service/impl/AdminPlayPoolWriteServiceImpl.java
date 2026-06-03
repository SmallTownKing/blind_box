package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.entity.PlayPoolEntity;
import com.damochaohe.play.entity.PlayPoolRewardEntity;
import com.damochaohe.play.mapper.PlayPoolMapper;
import com.damochaohe.play.mapper.PlayPoolRewardMapper;
import com.damochaohe.play.service.AdminPlayPoolWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 奖池玩法写服务实现。
 */
@Service
@RequiredArgsConstructor
public class AdminPlayPoolWriteServiceImpl implements AdminPlayPoolWriteService {

    private final PlayPoolMapper playPoolMapper;
    private final PlayPoolRewardMapper playPoolRewardMapper;

    @Override
    public void deletePool(Long id) {
        PlayPoolEntity entity = playPoolMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("奖池配置不存在");
        }
        playPoolMapper.deleteById(id);
        playPoolRewardMapper.delete(new LambdaQueryWrapper<PlayPoolRewardEntity>()
                .eq(PlayPoolRewardEntity::getPoolId, id));
    }

    @Override
    public void updatePoolStatus(Long id, Integer status) {
        PlayPoolEntity entity = playPoolMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("奖池配置不存在");
        }
        entity.setStatus(status);
        playPoolMapper.updateById(entity);
    }

    @Override
    public void deleteReward(Long id) {
        PlayPoolRewardEntity entity = playPoolRewardMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("奖池奖品项不存在");
        }
        playPoolRewardMapper.deleteById(id);
        validateProbabilityTotal(entity.getPoolId());
    }

    @Override
    public void updateRewardStatus(Long id, Integer status) {
        PlayPoolRewardEntity entity = playPoolRewardMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("奖池奖品项不存在");
        }
        entity.setStatus(status);
        playPoolRewardMapper.updateById(entity);
        validateProbabilityTotal(entity.getPoolId());
    }

    /**
     * 校验启用状态奖品项的概率总和。
     *
     * <p>当前约束总概率不能超过 100。</p>
     *
     * @param poolId 奖池 ID
     */
    public void validateProbabilityTotal(Long poolId) {
        List<PlayPoolRewardEntity> rewardList = playPoolRewardMapper.selectList(new LambdaQueryWrapper<PlayPoolRewardEntity>()
                .eq(PlayPoolRewardEntity::getPoolId, poolId)
                .eq(PlayPoolRewardEntity::getStatus, 1));
        BigDecimal total = rewardList.stream()
                .map(PlayPoolRewardEntity::getProbability)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException("启用状态奖品项概率总和不能超过 100");
        }
    }
}
