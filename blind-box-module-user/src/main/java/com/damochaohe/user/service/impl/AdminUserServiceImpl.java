package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damochaohe.common.model.PageResponse;
import com.damochaohe.user.dto.AdminUserPageResponse;
import com.damochaohe.user.dto.AdminUserQuery;
import com.damochaohe.user.entity.UserAccountEntity;
import com.damochaohe.user.mapper.UserAccountMapper;
import com.damochaohe.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户管理实现。
 *
 * <p>当前基于用户基础表提供分页查询。</p>
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserAccountMapper userAccountMapper;

    @Override
    public PageResponse<AdminUserPageResponse> pageUsers(AdminUserQuery query) {
        LambdaQueryWrapper<UserAccountEntity> wrapper = new LambdaQueryWrapper<UserAccountEntity>()
                .like(query.getNickname() != null && !query.getNickname().isBlank(), UserAccountEntity::getNickname, query.getNickname())
                .like(query.getMobile() != null && !query.getMobile().isBlank(), UserAccountEntity::getMobile, query.getMobile())
                .eq(Objects.nonNull(query.getStatus()), UserAccountEntity::getStatus, query.getStatus())
                .orderByDesc(UserAccountEntity::getId);

        Page<UserAccountEntity> page = userAccountMapper.selectPage(new Page<>(query.getPageNum(), query.getPageSize()), wrapper);

        return PageResponse.<AdminUserPageResponse>builder()
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .total(page.getTotal())
                .records(page.getRecords().stream()
                        .map(item -> AdminUserPageResponse.builder()
                                .userId(item.getId())
                                .nickname(item.getNickname())
                                .mobile(item.getMobile())
                                .memberLevel(item.getCurrentMemberLevel())
                                .status(item.getStatus())
                                .registerSource(item.getRegisterSource())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
