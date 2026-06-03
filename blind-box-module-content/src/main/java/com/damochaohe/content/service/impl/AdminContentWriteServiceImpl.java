package com.damochaohe.content.service.impl;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.content.dto.AdminBannerSaveRequest;
import com.damochaohe.content.dto.AdminPopupSaveRequest;
import com.damochaohe.content.dto.AdminSplashSaveRequest;
import com.damochaohe.content.entity.ContentBannerEntity;
import com.damochaohe.content.entity.ContentPopupEntity;
import com.damochaohe.content.entity.ContentSplashAdEntity;
import com.damochaohe.content.mapper.ContentBannerMapper;
import com.damochaohe.content.mapper.ContentPopupMapper;
import com.damochaohe.content.mapper.ContentSplashAdMapper;
import com.damochaohe.content.service.AdminContentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 后台内容配置写服务实现。
 */
@Service
@RequiredArgsConstructor
public class AdminContentWriteServiceImpl implements AdminContentWriteService {

    private final ContentBannerMapper contentBannerMapper;
    private final ContentPopupMapper contentPopupMapper;
    private final ContentSplashAdMapper contentSplashAdMapper;

    @Override
    public void saveBanner(AdminBannerSaveRequest request) {
        ContentBannerEntity entity = request.getId() == null ? new ContentBannerEntity() : contentBannerMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("Banner 配置不存在");
        }
        entity.setTitle(request.getTitle());
        entity.setImageUrl(request.getImageUrl());
        entity.setTargetPath(request.getTargetPath());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            contentBannerMapper.insert(entity);
        } else {
            contentBannerMapper.updateById(entity);
        }
    }

    @Override
    public void savePopup(AdminPopupSaveRequest request) {
        ContentPopupEntity entity = request.getId() == null ? new ContentPopupEntity() : contentPopupMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("弹窗配置不存在");
        }
        entity.setTitle(request.getTitle());
        entity.setImageUrl(request.getImageUrl());
        entity.setTargetPath(request.getTargetPath());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            contentPopupMapper.insert(entity);
        } else {
            contentPopupMapper.updateById(entity);
        }
    }

    @Override
    public void saveSplash(AdminSplashSaveRequest request) {
        ContentSplashAdEntity entity = request.getId() == null ? new ContentSplashAdEntity() : contentSplashAdMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("开屏广告配置不存在");
        }
        entity.setTitle(request.getTitle());
        entity.setImageUrl(request.getImageUrl());
        entity.setTargetPath(request.getTargetPath());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            contentSplashAdMapper.insert(entity);
        } else {
            contentSplashAdMapper.updateById(entity);
        }
    }
}
