package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damochaohe.common.model.PageResponse;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.AdminKujiActivityResponse;
import com.damochaohe.play.dto.AdminKujiActivitySaveRequest;
import com.damochaohe.play.dto.AdminKujiFinalRuleResponse;
import com.damochaohe.play.dto.AdminKujiFinalRuleQuery;
import com.damochaohe.play.dto.AdminKujiFinalRuleSaveRequest;
import com.damochaohe.play.dto.AdminKujiLockResponse;
import com.damochaohe.play.dto.AdminKujiLockSaveRequest;
import com.damochaohe.play.dto.AdminKujiTargetRewardResponse;
import com.damochaohe.play.dto.AdminKujiTargetRewardQuery;
import com.damochaohe.play.dto.AdminKujiTargetRewardSaveRequest;
import com.damochaohe.play.dto.AdminKujiTierResponse;
import com.damochaohe.play.dto.AdminKujiTierSaveRequest;
import com.damochaohe.play.entity.KujiActivityEntity;
import com.damochaohe.play.entity.KujiFinalRewardRuleEntity;
import com.damochaohe.play.entity.KujiLockRecordEntity;
import com.damochaohe.play.entity.KujiRewardTierEntity;
import com.damochaohe.play.entity.KujiTargetRewardEntity;
import com.damochaohe.play.mapper.KujiActivityMapper;
import com.damochaohe.play.mapper.KujiFinalRewardRuleMapper;
import com.damochaohe.play.mapper.KujiLockRecordMapper;
import com.damochaohe.play.mapper.KujiRewardTierMapper;
import com.damochaohe.play.mapper.KujiTargetRewardMapper;
import com.damochaohe.play.service.AdminKujiManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 一番赏后台配置实现。
 */
@Service
@RequiredArgsConstructor
public class AdminKujiManageServiceImpl implements AdminKujiManageService {

    private final KujiActivityMapper kujiActivityMapper;
    private final KujiRewardTierMapper kujiRewardTierMapper;
    private final KujiLockRecordMapper kujiLockRecordMapper;
    private final KujiFinalRewardRuleMapper kujiFinalRewardRuleMapper;
    private final KujiTargetRewardMapper kujiTargetRewardMapper;

    @Override
    public List<AdminKujiActivityResponse> listActivities() {
        return kujiActivityMapper.selectList(new LambdaQueryWrapper<KujiActivityEntity>()
                        .orderByAsc(KujiActivityEntity::getSortNo)
                        .orderByDesc(KujiActivityEntity::getId))
                .stream()
                .map(item -> AdminKujiActivityResponse.builder()
                        .id(item.getId())
                        .activityName(item.getActivityName())
                        .categoryId(item.getCategoryId())
                        .status(item.getStatus())
                        .lockBoxEnabled(item.getLockBoxEnabled())
                        .boxTotalStock(item.getBoxTotalStock())
                        .boxRemainStock(item.getBoxRemainStock())
                        .purchaseLimit(item.getPurchaseLimit())
                        .robotEnabled(item.getRobotEnabled())
                        .robotDisplayConfig(item.getRobotDisplayConfig())
                        .fanGroupJumpUrl(item.getFanGroupJumpUrl())
                        .visibleUserConfig(item.getVisibleUserConfig())
                        .participateUserConfig(item.getParticipateUserConfig())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveActivity(AdminKujiActivitySaveRequest request) {
        KujiActivityEntity entity = request.getId() == null ? new KujiActivityEntity() : kujiActivityMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("一番赏活动不存在");
        }
        entity.setActivityName(request.getActivityName());
        entity.setCategoryId(request.getCategoryId());
        entity.setStatus(request.getStatus());
        entity.setLockBoxEnabled(request.getLockBoxEnabled());
        entity.setBoxTotalStock(request.getBoxTotalStock());
        entity.setBoxRemainStock(request.getBoxRemainStock());
        entity.setPurchaseLimit(request.getPurchaseLimit());
        entity.setSortNo(request.getSortNo());
        entity.setRobotEnabled(request.getRobotEnabled());
        entity.setRobotDisplayConfig(request.getRobotDisplayConfig());
        entity.setFanGroupJumpUrl(request.getFanGroupJumpUrl());
        entity.setVisibleUserConfig(request.getVisibleUserConfig());
        entity.setParticipateUserConfig(request.getParticipateUserConfig());
        if (request.getId() == null) {
            kujiActivityMapper.insert(entity);
        } else {
            kujiActivityMapper.updateById(entity);
        }
    }

    @Override
    public void deleteActivity(Long id) {
        KujiActivityEntity entity = kujiActivityMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("一番赏活动不存在");
        }
        kujiActivityMapper.deleteById(id);
    }

    @Override
    public void updateActivityStatus(Long id, Integer status) {
        KujiActivityEntity entity = kujiActivityMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("一番赏活动不存在");
        }
        entity.setStatus(status);
        kujiActivityMapper.updateById(entity);
    }

    @Override
    public List<AdminKujiTierResponse> listTiers(Long activityId) {
        return kujiRewardTierMapper.selectList(new LambdaQueryWrapper<KujiRewardTierEntity>()
                        .eq(KujiRewardTierEntity::getActivityId, activityId)
                        .orderByAsc(KujiRewardTierEntity::getSortNo)
                        .orderByDesc(KujiRewardTierEntity::getId))
                .stream()
                .map(item -> AdminKujiTierResponse.builder()
                        .id(item.getId())
                        .activityId(item.getActivityId())
                        .tierCode(item.getTierCode())
                        .tierName(item.getTierName())
                        .probability(item.getProbability())
                        .totalStock(item.getTotalStock())
                        .remainStock(item.getRemainStock())
                        .status(item.getStatus())
                        .specialRewardEnabled(item.getSpecialRewardEnabled())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveTier(AdminKujiTierSaveRequest request) {
        KujiRewardTierEntity entity = request.getId() == null ? new KujiRewardTierEntity() : kujiRewardTierMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("一番赏奖项层级不存在");
        }
        entity.setActivityId(request.getActivityId());
        entity.setTierCode(request.getTierCode());
        entity.setTierName(request.getTierName());
        entity.setProbability(request.getProbability());
        entity.setTotalStock(request.getTotalStock());
        entity.setRemainStock(request.getRemainStock());
        entity.setStatus(request.getStatus());
        entity.setSpecialRewardEnabled(request.getSpecialRewardEnabled());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            kujiRewardTierMapper.insert(entity);
        } else {
            kujiRewardTierMapper.updateById(entity);
        }
        validateProbabilityTotal(request.getActivityId());
    }

    @Override
    public void deleteTier(Long id) {
        KujiRewardTierEntity entity = kujiRewardTierMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("一番赏奖项层级不存在");
        }
        kujiRewardTierMapper.deleteById(id);
        validateProbabilityTotal(entity.getActivityId());
    }

    @Override
    public void updateTierStatus(Long id, Integer status) {
        KujiRewardTierEntity entity = kujiRewardTierMapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("一番赏奖项层级不存在");
        }
        entity.setStatus(status);
        kujiRewardTierMapper.updateById(entity);
        validateProbabilityTotal(entity.getActivityId());
    }

    @Override
    public PageResponse<AdminKujiFinalRuleResponse> pageFinalRules(AdminKujiFinalRuleQuery query) {
        Page<KujiFinalRewardRuleEntity> page = kujiFinalRewardRuleMapper.selectPage(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<KujiFinalRewardRuleEntity>()
                        .eq(Objects.nonNull(query.getActivityId()), KujiFinalRewardRuleEntity::getActivityId, query.getActivityId())
                        .orderByDesc(KujiFinalRewardRuleEntity::getId));
        return PageResponse.<AdminKujiFinalRuleResponse>builder()
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .total(page.getTotal())
                .records(page.getRecords().stream()
                        .map(item -> AdminKujiFinalRuleResponse.builder()
                                .id(item.getId())
                                .activityId(item.getActivityId())
                                .finalTierCode(item.getFinalTierCode())
                                .finalRewardName(item.getFinalRewardName())
                                .triggerCondition(item.getTriggerCondition())
                                .status(item.getStatus())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void saveFinalRule(AdminKujiFinalRuleSaveRequest request) {
        if (!"LAST_ONE".equals(request.getTriggerCondition())) {
            throw new BusinessException("当前最终赏触发条件仅支持 LAST_ONE");
        }
        KujiFinalRewardRuleEntity entity = request.getId() == null ? new KujiFinalRewardRuleEntity() : kujiFinalRewardRuleMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("最终赏规则不存在");
        }
        entity.setActivityId(request.getActivityId());
        entity.setFinalTierCode(request.getFinalTierCode());
        entity.setFinalRewardName(request.getFinalRewardName());
        entity.setTriggerCondition(request.getTriggerCondition());
        entity.setStatus(request.getStatus());
        if (request.getId() == null) {
            kujiFinalRewardRuleMapper.insert(entity);
        } else {
            kujiFinalRewardRuleMapper.updateById(entity);
        }
    }

    @Override
    public List<AdminKujiLockResponse> listLocks(Long activityId) {
        return kujiLockRecordMapper.selectList(new LambdaQueryWrapper<KujiLockRecordEntity>()
                        .eq(KujiLockRecordEntity::getActivityId, activityId)
                        .orderByDesc(KujiLockRecordEntity::getId))
                .stream()
                .map(item -> AdminKujiLockResponse.builder()
                        .id(item.getId())
                        .activityId(item.getActivityId())
                        .userId(item.getUserId())
                        .lockStatus(item.getLockStatus())
                        .lockExpireTime(item.getLockExpireTime())
                        .releaseReason(item.getReleaseReason())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveLock(AdminKujiLockSaveRequest request) {
        KujiLockRecordEntity entity = request.getId() == null ? new KujiLockRecordEntity() : kujiLockRecordMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("锁箱记录不存在");
        }
        entity.setActivityId(request.getActivityId());
        entity.setUserId(request.getUserId());
        entity.setLockStatus(request.getLockStatus());
        entity.setLockExpireTime(request.getLockExpireTime());
        entity.setReleaseReason(request.getReleaseReason());
        if (request.getId() == null) {
            kujiLockRecordMapper.insert(entity);
        } else {
            kujiLockRecordMapper.updateById(entity);
        }
    }

    @Override
    public PageResponse<AdminKujiTargetRewardResponse> pageTargetRewards(AdminKujiTargetRewardQuery query) {
        Page<KujiTargetRewardEntity> page = kujiTargetRewardMapper.selectPage(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<KujiTargetRewardEntity>()
                        .eq(Objects.nonNull(query.getActivityId()), KujiTargetRewardEntity::getActivityId, query.getActivityId())
                        .orderByDesc(KujiTargetRewardEntity::getId));
        return PageResponse.<AdminKujiTargetRewardResponse>builder()
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .total(page.getTotal())
                .records(page.getRecords().stream()
                        .map(item -> AdminKujiTargetRewardResponse.builder()
                                .id(item.getId())
                                .activityId(item.getActivityId())
                                .targetUserId(item.getTargetUserId())
                                .targetUserType(item.getTargetUserType())
                                .robotIdentity(item.getRobotIdentity())
                                .rewardTierId(item.getRewardTierId())
                                .specialRewardEnabled(item.getSpecialRewardEnabled())
                                .status(item.getStatus())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void saveTargetReward(AdminKujiTargetRewardSaveRequest request) {
        KujiTargetRewardEntity entity = request.getId() == null ? new KujiTargetRewardEntity() : kujiTargetRewardMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("指定中奖配置不存在");
        }
        entity.setActivityId(request.getActivityId());
        entity.setTargetUserId(request.getTargetUserId());
        entity.setTargetUserType(request.getTargetUserType());
        entity.setRobotIdentity(request.getRobotIdentity());
        entity.setRewardTierId(request.getRewardTierId());
        entity.setSpecialRewardEnabled(request.getSpecialRewardEnabled());
        entity.setStatus(request.getStatus());
        if (request.getId() == null) {
            kujiTargetRewardMapper.insert(entity);
        } else {
            kujiTargetRewardMapper.updateById(entity);
        }
    }

    private void validateProbabilityTotal(Long activityId) {
        BigDecimal total = kujiRewardTierMapper.selectList(new LambdaQueryWrapper<KujiRewardTierEntity>()
                        .eq(KujiRewardTierEntity::getActivityId, activityId)
                        .eq(KujiRewardTierEntity::getStatus, 1))
                .stream()
                .map(KujiRewardTierEntity::getProbability)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(new BigDecimal("100")) > 0) {
            throw new BusinessException("一番赏启用状态奖项概率总和不能超过 100");
        }
    }
}
