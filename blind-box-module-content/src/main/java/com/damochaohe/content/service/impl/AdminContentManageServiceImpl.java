package com.damochaohe.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.content.dto.AdminBannerConfigResponse;
import com.damochaohe.content.dto.AdminFloatingWindowConfigResponse;
import com.damochaohe.content.dto.AdminHomeEntryConfigResponse;
import com.damochaohe.content.dto.AdminPopupConfigResponse;
import com.damochaohe.content.dto.AdminSplashConfigResponse;
import com.damochaohe.content.dto.AdminWinnerTickerConfigResponse;
import com.damochaohe.content.entity.ContentBannerEntity;
import com.damochaohe.content.entity.ContentPopupEntity;
import com.damochaohe.content.entity.ContentSplashAdEntity;
import com.damochaohe.content.mapper.ContentBannerMapper;
import com.damochaohe.content.mapper.ContentPopupMapper;
import com.damochaohe.content.mapper.ContentSplashAdMapper;
import com.damochaohe.content.service.AdminContentManageService;
import com.damochaohe.infra.entity.SysDictEntity;
import com.damochaohe.infra.mapper.SysDictMapper;
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
    private final SysDictMapper sysDictMapper;

    private static final String HOME_ENTRY_DICT_TYPE = "home_entry";
    private static final String HOME_FLOATING_DICT_TYPE = "home_floating";
    private static final String HOME_WINNER_TICKER_DICT_TYPE = "home_winner_ticker";

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

    @Override
    public List<AdminHomeEntryConfigResponse> listHomeEntries() {
        return listDictItems(HOME_ENTRY_DICT_TYPE).stream()
                .map(item -> AdminHomeEntryConfigResponse.builder()
                        .id(item.getId())
                        .name(item.getLabel())
                        .iconUrl(item.getRemark())
                        .targetPath(item.getValue())
                        .status(item.getStatus())
                        .sortNo(item.getSortNo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminFloatingWindowConfigResponse> listFloatingWindows() {
        return listDictItems(HOME_FLOATING_DICT_TYPE).stream()
                .map(item -> AdminFloatingWindowConfigResponse.builder()
                        .id(item.getId())
                        .title(item.getLabel())
                        .imageUrl(item.getRemark())
                        .targetPath(item.getValue())
                        .status(item.getStatus())
                        .sortNo(item.getSortNo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminWinnerTickerConfigResponse> listWinnerTickers() {
        return listDictItems(HOME_WINNER_TICKER_DICT_TYPE).stream()
                .map(item -> AdminWinnerTickerConfigResponse.builder()
                        .id(item.getId())
                        .content(item.getLabel())
                        .status(item.getStatus())
                        .sortNo(item.getSortNo())
                        .build())
                .collect(Collectors.toList());
    }

    private List<SysDictEntity> listDictItems(String dictType) {
        return sysDictMapper.selectList(new LambdaQueryWrapper<SysDictEntity>()
                .eq(SysDictEntity::getDictType, dictType)
                .orderByAsc(SysDictEntity::getSortNo)
                .orderByDesc(SysDictEntity::getId));
    }
}
