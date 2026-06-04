package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damochaohe.common.model.PageResponse;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.user.dto.AdminUserDetailResponse;
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
                .eq(query.getRegisterSource() != null && !query.getRegisterSource().isBlank(), UserAccountEntity::getRegisterSource, query.getRegisterSource())
                .isNull(Objects.equals(query.getGuestUser(), 1), UserAccountEntity::getMobile)
                .isNotNull(Objects.equals(query.getGuestUser(), 0), UserAccountEntity::getMobile)
                .isNotNull(Objects.equals(query.getMobileBound(), 1), UserAccountEntity::getMobile)
                .isNull(Objects.equals(query.getMobileBound(), 0), UserAccountEntity::getMobile)
                .isNotNull(Objects.equals(query.getPasswordSet(), 1), UserAccountEntity::getPasswordHash)
                .isNull(Objects.equals(query.getPasswordSet(), 0), UserAccountEntity::getPasswordHash)
                .orderByDesc(UserAccountEntity::getId);

        Page<UserAccountEntity> page = userAccountMapper.selectPage(new Page<>(query.getPageNum(), query.getPageSize()), wrapper);

        return PageResponse.<AdminUserPageResponse>builder()
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .total(page.getTotal())
                .records(page.getRecords().stream()
                        .map(item -> AdminUserPageResponse.builder()
                                .userId(item.getId())
                                .userNo(item.getUserNo())
                                .nickname(item.getNickname())
                                .avatar(item.getAvatar())
                                .mobile(item.getMobile())
                                .mobileBound(item.getMobile() != null && !item.getMobile().isBlank())
                                .email(item.getEmail())
                                .birthday(item.getBirthday())
                                .passwordSet(item.getPasswordHash() != null && !item.getPasswordHash().isBlank())
                                .memberLevel(item.getCurrentMemberLevel())
                                .status(item.getStatus())
                                .registerSource(item.getRegisterSource())
                                .createdAt(item.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public AdminUserDetailResponse getUserDetail(Long userId) {
        UserAccountEntity entity = userAccountMapper.selectById(userId);
        if (entity == null) {
            throw new BusinessException("用户不存在");
        }
        return AdminUserDetailResponse.builder()
                .userId(entity.getId())
                .userNo(entity.getUserNo())
                .nickname(entity.getNickname())
                .avatar(entity.getAvatar())
                .mobile(entity.getMobile())
                .mobileBound(entity.getMobile() != null && !entity.getMobile().isBlank())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .birthdayEditable(entity.getBirthday() == null)
                .passwordSet(entity.getPasswordHash() != null && !entity.getPasswordHash().isBlank())
                .memberLevel(entity.getCurrentMemberLevel())
                .status(entity.getStatus())
                .registerSource(entity.getRegisterSource())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
