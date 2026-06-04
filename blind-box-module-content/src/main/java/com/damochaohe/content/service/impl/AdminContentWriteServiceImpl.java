package com.damochaohe.content.service.impl;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.content.dto.AdminBannerSaveRequest;
import com.damochaohe.content.dto.AdminFloatingWindowSaveRequest;
import com.damochaohe.content.dto.AdminHomeEntrySaveRequest;
import com.damochaohe.content.dto.AdminPopupSaveRequest;
import com.damochaohe.content.dto.AdminSplashSaveRequest;
import com.damochaohe.content.dto.AdminWinnerTickerSaveRequest;
import com.damochaohe.content.entity.ContentBannerEntity;
import com.damochaohe.content.entity.ContentPopupEntity;
import com.damochaohe.content.entity.ContentSplashAdEntity;
import com.damochaohe.content.mapper.ContentBannerMapper;
import com.damochaohe.content.mapper.ContentPopupMapper;
import com.damochaohe.content.mapper.ContentSplashAdMapper;
import com.damochaohe.content.service.AdminContentWriteService;
import com.damochaohe.infra.entity.SysDictEntity;
import com.damochaohe.infra.mapper.SysDictMapper;
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
    private final SysDictMapper sysDictMapper;

    private static final String HOME_ENTRY_DICT_TYPE = "home_entry";
    private static final String HOME_FLOATING_DICT_TYPE = "home_floating";
    private static final String HOME_WINNER_TICKER_DICT_TYPE = "home_winner_ticker";

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

    @Override
    public void saveHomeEntry(AdminHomeEntrySaveRequest request) {
        saveDictItem(request.getId(), HOME_ENTRY_DICT_TYPE, request.getName(), request.getTargetPath(), request.getStatus(), request.getSortNo(), request.getIconUrl());
    }

    @Override
    public void saveFloatingWindow(AdminFloatingWindowSaveRequest request) {
        saveDictItem(request.getId(), HOME_FLOATING_DICT_TYPE, request.getTitle(), request.getTargetPath(), request.getStatus(), request.getSortNo(), request.getImageUrl());
    }

    @Override
    public void saveWinnerTicker(AdminWinnerTickerSaveRequest request) {
        saveDictItem(request.getId(), HOME_WINNER_TICKER_DICT_TYPE, request.getContent(), request.getContent(), request.getStatus(), request.getSortNo(), "");
    }

    private void saveDictItem(Long id, String dictType, String label, String value, Integer status, Integer sortNo, String remark) {
        SysDictEntity entity = id == null ? new SysDictEntity() : sysDictMapper.selectById(id);
        if (id != null && entity == null) {
            throw new BusinessException("配置不存在");
        }
        if (entity == null) {
            entity = new SysDictEntity();
        }
        entity.setDictType(dictType);
        entity.setLabel(label);
        entity.setValue(value);
        entity.setStatus(status);
        entity.setSortNo(sortNo);
        entity.setRemark(remark);
        if (id == null) {
            sysDictMapper.insert(entity);
        } else {
            sysDictMapper.updateById(entity);
        }
    }
}
