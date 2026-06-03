package com.damochaohe.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.content.dto.AdminBannerConfigResponse;
import com.damochaohe.content.dto.AdminPopupConfigResponse;
import com.damochaohe.content.dto.AdminSplashConfigResponse;
import com.damochaohe.content.entity.ContentBannerEntity;
import com.damochaohe.content.entity.ContentPopupEntity;
import com.damochaohe.content.entity.ContentSplashAdEntity;
import com.damochaohe.content.mapper.ContentBannerMapper;
import com.damochaohe.content.mapper.ContentPopupMapper;
import com.damochaohe.content.mapper.ContentSplashAdMapper;
import com.damochaohe.content.service.AdminContentManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台首页内容管理实现。
 *
 * <p>当前基于首页内容配置表返回数据。</p>
 */
@Service
@RequiredArgsConstructor
public class AdminContentManageServiceImpl implements AdminContentManageService {

    private final ContentBannerMapper contentBannerMapper;
    private final ContentPopupMapper contentPopupMapper;
    private final ContentSplashAdMapper contentSplashAdMapper;

    @Override
    public List<AdminBannerConfigResponse> listBanners() {
        return contentBannerMapper.selectList(new LambdaQueryWrapper<ContentBannerEntity>()
                        .orderByAsc(ContentBannerEntity::getSortNo)
                        .orderByDesc(ContentBannerEntity::getId))
                .stream()
                .map(item -> AdminBannerConfigResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .imageUrl(item.getImageUrl())
                        .targetPath(item.getTargetPath())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminPopupConfigResponse> listPopups() {
        return contentPopupMapper.selectList(new LambdaQueryWrapper<ContentPopupEntity>()
                        .orderByAsc(ContentPopupEntity::getSortNo)
                        .orderByDesc(ContentPopupEntity::getId))
                .stream()
                .map(item -> AdminPopupConfigResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .imageUrl(item.getImageUrl())
                        .targetPath(item.getTargetPath())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminSplashConfigResponse> listSplashAds() {
        return contentSplashAdMapper.selectList(new LambdaQueryWrapper<ContentSplashAdEntity>()
                        .orderByAsc(ContentSplashAdEntity::getSortNo)
                        .orderByDesc(ContentSplashAdEntity::getId))
                .stream()
                .map(item -> AdminSplashConfigResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .imageUrl(item.getImageUrl())
                        .targetPath(item.getTargetPath())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
