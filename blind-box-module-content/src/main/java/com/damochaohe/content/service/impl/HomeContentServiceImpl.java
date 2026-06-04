package com.damochaohe.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.content.dto.HomeConfigResponse;
import com.damochaohe.content.entity.ContentBannerEntity;
import com.damochaohe.content.entity.ContentPopupEntity;
import com.damochaohe.content.entity.ContentSplashAdEntity;
import com.damochaohe.content.mapper.ContentBannerMapper;
import com.damochaohe.content.mapper.ContentPopupMapper;
import com.damochaohe.content.mapper.ContentSplashAdMapper;
import com.damochaohe.content.service.HomeContentService;
import com.damochaohe.infra.entity.SysDictEntity;
import com.damochaohe.infra.mapper.SysDictMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页内容服务实现。
 *
 * <p>当前返回静态演示数据，后续对接首页内容配置表。</p>
 */
@Service
@RequiredArgsConstructor
public class HomeContentServiceImpl implements HomeContentService {

    private final ContentSplashAdMapper contentSplashAdMapper;
    private final ContentPopupMapper contentPopupMapper;
    private final ContentBannerMapper contentBannerMapper;
    private final SysDictMapper sysDictMapper;

    private static final String HOME_ENTRY_DICT_TYPE = "home_entry";
    private static final String HOME_FLOATING_DICT_TYPE = "home_floating";
    private static final String HOME_WINNER_TICKER_DICT_TYPE = "home_winner_ticker";

    @Override
    public HomeConfigResponse getHomeConfig() {
        ContentSplashAdEntity splashAd = contentSplashAdMapper.selectList(new LambdaQueryWrapper<ContentSplashAdEntity>()
                        .eq(ContentSplashAdEntity::getStatus, 1)
                        .orderByAsc(ContentSplashAdEntity::getSortNo)
                        .orderByDesc(ContentSplashAdEntity::getId))
                .stream()
                .findFirst()
                .orElse(null);

        List<HomeConfigResponse.HomeDisplayItem> popups = contentPopupMapper.selectList(new LambdaQueryWrapper<ContentPopupEntity>()
                        .eq(ContentPopupEntity::getStatus, 1)
                        .orderByAsc(ContentPopupEntity::getSortNo)
                        .orderByDesc(ContentPopupEntity::getId))
                .stream()
                .map(this::toDisplayItem)
                .collect(Collectors.toList());

        List<HomeConfigResponse.HomeDisplayItem> banners = contentBannerMapper.selectList(new LambdaQueryWrapper<ContentBannerEntity>()
                        .eq(ContentBannerEntity::getStatus, 1)
                        .orderByAsc(ContentBannerEntity::getSortNo)
                        .orderByDesc(ContentBannerEntity::getId))
                .stream()
                .map(this::toDisplayItem)
                .collect(Collectors.toList());

        List<HomeConfigResponse.HomeEntryItem> entries = listEnabledDictItems(HOME_ENTRY_DICT_TYPE)
                .stream()
                .map(item -> HomeConfigResponse.HomeEntryItem.builder()
                        .name(item.getLabel())
                        .iconUrl(item.getRemark())
                        .targetPath(normalizeInternalPath(item.getValue()))
                        .build())
                .collect(Collectors.toList());

        List<HomeConfigResponse.HomeDisplayItem> floatingWindows = listEnabledDictItems(HOME_FLOATING_DICT_TYPE)
                .stream()
                .map(item -> HomeConfigResponse.HomeDisplayItem.builder()
                        .id(item.getId())
                        .title(item.getLabel())
                        .imageUrl(item.getRemark())
                        .targetPath(normalizeInternalPath(item.getValue()))
                        .internalOnly(false)
                        .build())
                .collect(Collectors.toList());

        List<String> winnerTickers = listEnabledDictItems(HOME_WINNER_TICKER_DICT_TYPE)
                .stream()
                .map(SysDictEntity::getLabel)
                .collect(Collectors.toList());

        return HomeConfigResponse.builder()
                .splashAd(splashAd == null ? null : HomeConfigResponse.HomeDisplayItem.builder()
                        .id(splashAd.getId())
                        .title(splashAd.getTitle())
                        .imageUrl(splashAd.getImageUrl())
                        .targetPath(normalizeInternalPath(splashAd.getTargetPath()))
                        .internalOnly(true)
                        .build())
                .popups(popups)
                .banners(banners)
                .entries(entries.isEmpty() ? List.of(
                        HomeConfigResponse.HomeEntryItem.builder().name("星愿集赏").iconUrl("https://static.example.com/icon/star-wish.png").targetPath("/pages/wish/index").build(),
                        HomeConfigResponse.HomeEntryItem.builder().name("福利大厅").iconUrl("https://static.example.com/icon/welfare.png").targetPath("/pages/welfare/index").build(),
                        HomeConfigResponse.HomeEntryItem.builder().name("加入社群").iconUrl("https://static.example.com/icon/group.png").targetPath("/pages/community/join").build()) : entries)
                .floatingWindows(floatingWindows)
                .showSearchBar(true)
                .showIpEntry(false)
                .winnerTickers(winnerTickers.isEmpty() ? List.of(
                        "玩家小潮获得隐藏款手办",
                        "玩家星愿抽中欧皇款赏品",
                        "玩家盒友获得超稀有奖励") : winnerTickers)
                .winnerTickerMoreEnabled(false)
                .playTabs(List.of(
                        HomeConfigResponse.HomePlayTab.builder().name("福袋玩法").secondary(false).collapsed(false).build(),
                        HomeConfigResponse.HomePlayTab.builder().name("一番赏玩法").secondary(false).collapsed(false).build(),
                        HomeConfigResponse.HomePlayTab.builder().name("百连抽页面").secondary(true).collapsed(true).build(),
                        HomeConfigResponse.HomePlayTab.builder().name("魔王无限赏").secondary(true).collapsed(true).build()))
                .build();
    }

    private HomeConfigResponse.HomeDisplayItem toDisplayItem(ContentBannerEntity entity) {
        return HomeConfigResponse.HomeDisplayItem.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getImageUrl())
                .targetPath(normalizeInternalPath(entity.getTargetPath()))
                .internalOnly(false)
                .build();
    }

    private HomeConfigResponse.HomeDisplayItem toDisplayItem(ContentPopupEntity entity) {
        return HomeConfigResponse.HomeDisplayItem.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getImageUrl())
                .targetPath(normalizeInternalPath(entity.getTargetPath()))
                .internalOnly(false)
                .build();
    }

    private String normalizeInternalPath(String targetPath) {
        if (targetPath == null || targetPath.isBlank()) {
            return "/pages/home/index";
        }
        if (targetPath.startsWith("http://") || targetPath.startsWith("https://")) {
            return "/pages/home/index";
        }
        return targetPath;
    }

    private List<SysDictEntity> listEnabledDictItems(String dictType) {
        return sysDictMapper.selectList(new LambdaQueryWrapper<SysDictEntity>()
                .eq(SysDictEntity::getDictType, dictType)
                .eq(SysDictEntity::getStatus, 1)
                .orderByAsc(SysDictEntity::getSortNo)
                .orderByDesc(SysDictEntity::getId));
    }
}
