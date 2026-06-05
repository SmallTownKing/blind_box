package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.user.dto.AdminMemberLevelConfigResponse;
import com.damochaohe.user.dto.AdminMemberLevelConfigSaveRequest;
import com.damochaohe.user.entity.MemberLevelConfigEntity;
import com.damochaohe.user.mapper.MemberLevelConfigMapper;
import com.damochaohe.user.service.AdminMemberConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberConfigServiceImpl implements AdminMemberConfigService {

    private final MemberLevelConfigMapper memberLevelConfigMapper;

    @Override
    public List<AdminMemberLevelConfigResponse> listMemberLevels() {
        return memberLevelConfigMapper.selectList(new LambdaQueryWrapper<MemberLevelConfigEntity>()
                        .orderByAsc(MemberLevelConfigEntity::getSortNo)
                        .orderByAsc(MemberLevelConfigEntity::getLevelNo))
                .stream()
                .map(item -> AdminMemberLevelConfigResponse.builder()
                        .id(item.getId())
                        .levelNo(item.getLevelNo())
                        .levelName(item.getLevelName())
                        .consumeThreshold(item.getConsumeThreshold())
                        .growthThreshold(item.getGrowthThreshold())
                        .rewardConfig(item.getRewardConfig())
                        .v10ProtectEnabled(item.getV10ProtectEnabled())
                        .v10ProtectThreshold(item.getV10ProtectThreshold())
                        .status(item.getStatus())
                        .sortNo(item.getSortNo())
                        .build())
                .toList();
    }

    @Override
    public void saveMemberLevel(AdminMemberLevelConfigSaveRequest request) {
        MemberLevelConfigEntity entity = request.getId() == null ? new MemberLevelConfigEntity() : memberLevelConfigMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("会员等级配置不存在");
        }
        if (entity == null) {
            entity = new MemberLevelConfigEntity();
        }
        entity.setLevelNo(request.getLevelNo());
        entity.setLevelName(request.getLevelName());
        entity.setConsumeThreshold(request.getConsumeThreshold());
        entity.setGrowthThreshold(request.getGrowthThreshold());
        entity.setRewardConfig(request.getRewardConfig());
        entity.setV10ProtectEnabled(request.getV10ProtectEnabled());
        entity.setV10ProtectThreshold(request.getV10ProtectThreshold());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            memberLevelConfigMapper.insert(entity);
        } else {
            memberLevelConfigMapper.updateById(entity);
        }
    }
}
