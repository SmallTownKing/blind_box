package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.AdminFukubukuroRuleResponse;
import com.damochaohe.play.dto.AdminFukubukuroRuleSaveRequest;
import com.damochaohe.play.dto.AdminHundredDrawResponse;
import com.damochaohe.play.dto.AdminHundredDrawSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolResponse;
import com.damochaohe.play.dto.AdminPlayPoolRewardResponse;
import com.damochaohe.play.dto.AdminPlayPoolRewardSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolSaveRequest;
import com.damochaohe.play.entity.FukubukuroRuleEntity;
import com.damochaohe.play.entity.HundredDrawConfigEntity;
import com.damochaohe.play.entity.PlayPoolEntity;
import com.damochaohe.play.entity.PlayPoolRewardEntity;
import com.damochaohe.play.enums.PayModeEnum;
import com.damochaohe.play.mapper.FukubukuroRuleMapper;
import com.damochaohe.play.mapper.HundredDrawConfigMapper;
import com.damochaohe.play.mapper.PlayPoolMapper;
import com.damochaohe.play.mapper.PlayPoolRewardMapper;
import com.damochaohe.play.service.AdminPlayPoolManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台奖池配置实现。
 */
@Service
@RequiredArgsConstructor
public class AdminPlayPoolManageServiceImpl implements AdminPlayPoolManageService {

    private final PlayPoolMapper playPoolMapper;
    private final PlayPoolRewardMapper playPoolRewardMapper;
    private final AdminPlayPoolWriteServiceImpl adminPlayPoolWriteService;
    private final FukubukuroRuleMapper fukubukuroRuleMapper;
    private final HundredDrawConfigMapper hundredDrawConfigMapper;

    @Override
    public List<AdminPlayPoolResponse> listPools() {
        return playPoolMapper.selectList(new LambdaQueryWrapper<PlayPoolEntity>()
                        .orderByAsc(PlayPoolEntity::getSortNo)
                        .orderByDesc(PlayPoolEntity::getId))
                .stream()
                .map(item -> AdminPlayPoolResponse.builder()
                        .id(item.getId())
                        .poolName(item.getPoolName())
                        .poolType(item.getPoolType())
                        .categoryId(item.getCategoryId())
                        .status(item.getStatus())
                        .bannerLandingPage(item.getBannerLandingPage())
                        .drawModeConfig(item.getDrawModeConfig())
                        .payModeConfig(item.getPayModeConfig())
                        .trialEnabled(item.getTrialEnabled())
                        .animationEnabled(item.getAnimationEnabled())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void savePool(AdminPlayPoolSaveRequest request) {
        if (!PayModeEnum.isValidConfig(request.getPayModeConfig())) {
            throw new BusinessException("支付方式配置非法，仅支持 BALANCE,GOLD,MAGIC,COUPON");
        }
        PlayPoolEntity entity = request.getId() == null ? new PlayPoolEntity() : playPoolMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("奖池配置不存在");
        }
        entity.setPoolName(request.getPoolName());
        entity.setPoolType(request.getPoolType());
        entity.setCategoryId(request.getCategoryId());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        entity.setBannerLandingPage(request.getBannerLandingPage());
        entity.setDrawModeConfig(request.getDrawModeConfig());
        entity.setPayModeConfig(request.getPayModeConfig());
        entity.setTrialEnabled(request.getTrialEnabled());
        entity.setAnimationEnabled(request.getAnimationEnabled());
        if (request.getId() == null) {
            playPoolMapper.insert(entity);
        } else {
            playPoolMapper.updateById(entity);
        }
    }

    @Override
    public List<AdminPlayPoolRewardResponse> listRewards(Long poolId) {
        return playPoolRewardMapper.selectList(new LambdaQueryWrapper<PlayPoolRewardEntity>()
                        .eq(PlayPoolRewardEntity::getPoolId, poolId)
                        .orderByAsc(PlayPoolRewardEntity::getSortNo)
                        .orderByDesc(PlayPoolRewardEntity::getId))
                .stream()
                .map(item -> AdminPlayPoolRewardResponse.builder()
                        .id(item.getId())
                        .poolId(item.getPoolId())
                        .productId(item.getProductId())
                        .rewardName(item.getRewardName())
                        .rewardLevel(item.getRewardLevel())
                        .probability(item.getProbability())
                        .stock(item.getStock())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveReward(AdminPlayPoolRewardSaveRequest request) {
        PlayPoolRewardEntity entity = request.getId() == null ? new PlayPoolRewardEntity() : playPoolRewardMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("奖池奖品项不存在");
        }
        entity.setPoolId(request.getPoolId());
        entity.setProductId(request.getProductId());
        entity.setRewardName(request.getRewardName());
        entity.setRewardLevel(request.getRewardLevel());
        entity.setProbability(request.getProbability());
        entity.setStock(request.getStock());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            playPoolRewardMapper.insert(entity);
        } else {
            playPoolRewardMapper.updateById(entity);
        }
        adminPlayPoolWriteService.validateProbabilityTotal(request.getPoolId());
    }

    @Override
    public List<AdminFukubukuroRuleResponse> listFukubukuroRules(Long poolId) {
        return fukubukuroRuleMapper.selectList(new LambdaQueryWrapper<FukubukuroRuleEntity>()
                        .eq(FukubukuroRuleEntity::getPoolId, poolId)
                        .orderByAsc(FukubukuroRuleEntity::getSortNo)
                        .orderByDesc(FukubukuroRuleEntity::getId))
                .stream()
                .map(item -> AdminFukubukuroRuleResponse.builder()
                        .id(item.getId())
                        .poolId(item.getPoolId())
                        .rarityType(item.getRarityType())
                        .noHitTip(item.getNoHitTip())
                        .autoSelectPriceLimit(item.getAutoSelectPriceLimit())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveFukubukuroRule(AdminFukubukuroRuleSaveRequest request) {
        FukubukuroRuleEntity entity = request.getId() == null ? new FukubukuroRuleEntity() : fukubukuroRuleMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("福袋玩法规则不存在");
        }
        entity.setPoolId(request.getPoolId());
        entity.setRarityType(request.getRarityType());
        entity.setNoHitTip(request.getNoHitTip());
        entity.setAutoSelectPriceLimit(request.getAutoSelectPriceLimit());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            fukubukuroRuleMapper.insert(entity);
        } else {
            fukubukuroRuleMapper.updateById(entity);
        }
    }

    @Override
    public List<AdminHundredDrawResponse> listHundredDrawConfigs(Long poolId) {
        return hundredDrawConfigMapper.selectList(new LambdaQueryWrapper<HundredDrawConfigEntity>()
                        .eq(HundredDrawConfigEntity::getPoolId, poolId)
                        .orderByDesc(HundredDrawConfigEntity::getId))
                .stream()
                .map(item -> AdminHundredDrawResponse.builder()
                        .id(item.getId())
                        .poolId(item.getPoolId())
                        .pageTitle(item.getPageTitle())
                        .pageSubtitle(item.getPageSubtitle())
                        .bannerUrl(item.getBannerUrl())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveHundredDrawConfig(AdminHundredDrawSaveRequest request) {
        HundredDrawConfigEntity entity = request.getId() == null ? new HundredDrawConfigEntity() : hundredDrawConfigMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("百连抽页面配置不存在");
        }
        entity.setPoolId(request.getPoolId());
        entity.setPageTitle(request.getPageTitle());
        entity.setPageSubtitle(request.getPageSubtitle());
        entity.setBannerUrl(request.getBannerUrl());
        entity.setStatus(request.getStatus());
        if (request.getId() == null) {
            hundredDrawConfigMapper.insert(entity);
        } else {
            hundredDrawConfigMapper.updateById(entity);
        }
    }
}
