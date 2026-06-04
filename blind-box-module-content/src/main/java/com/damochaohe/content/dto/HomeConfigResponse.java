package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 首页配置聚合响应。
 */
@Data
@Builder
@Schema(description = "首页配置响应")
public class HomeConfigResponse {

    @Schema(description = "开屏广告")
    private HomeDisplayItem splashAd;

    @Schema(description = "首页弹窗列表")
    private List<HomeDisplayItem> popups;

    @Schema(description = "首页 Banner 列表")
    private List<HomeDisplayItem> banners;

    @Schema(description = "金刚区入口列表")
    private List<HomeEntryItem> entries;

    @Schema(description = "悬浮窗列表")
    private List<HomeDisplayItem> floatingWindows;

    @Schema(description = "是否展示搜索栏")
    private Boolean showSearchBar;

    @Schema(description = "是否展示 IP 栏")
    private Boolean showIpEntry;

    @Schema(description = "中奖轮播文案列表")
    private List<String> winnerTickers;

    @Schema(description = "中奖轮播是否展示更多入口")
    private Boolean winnerTickerMoreEnabled;

    @Schema(description = "玩法区展示数据")
    private List<HomePlayTab> playTabs;

    @Data
    @Builder
    @Schema(description = "首页展示项")
    public static class HomeDisplayItem {

        @Schema(description = "配置 ID")
        private Long id;

        @Schema(description = "标题")
        private String title;

        @Schema(description = "图片地址")
        private String imageUrl;

        @Schema(description = "跳转路径")
        private String targetPath;

        @Schema(description = "是否仅支持内部跳转")
        private Boolean internalOnly;
    }

    @Data
    @Builder
    @Schema(description = "首页入口项")
    public static class HomeEntryItem {

        @Schema(description = "入口名称")
        private String name;

        @Schema(description = "图标地址")
        private String iconUrl;

        @Schema(description = "跳转路径")
        private String targetPath;
    }

    @Data
    @Builder
    @Schema(description = "首页玩法标签")
    public static class HomePlayTab {

        @Schema(description = "玩法名称")
        private String name;

        @Schema(description = "是否二级玩法")
        private Boolean secondary;

        @Schema(description = "是否默认折叠")
        private Boolean collapsed;
    }
}
