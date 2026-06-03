package com.damochaohe.content.service;

import com.damochaohe.content.dto.AdminBannerConfigResponse;
import com.damochaohe.content.dto.AdminPopupConfigResponse;
import com.damochaohe.content.dto.AdminSplashConfigResponse;

import java.util.List;

/**
 * 后台首页内容管理服务。
 */
public interface AdminContentManageService {

    /**
     * 查询 Banner 配置。
     *
     * @return 配置列表
     */
    List<AdminBannerConfigResponse> listBanners();

    /**
     * 查询弹窗配置。
     *
     * @return 配置列表
     */
    List<AdminPopupConfigResponse> listPopups();

    /**
     * 查询开屏广告配置。
     *
     * @return 配置列表
     */
    List<AdminSplashConfigResponse> listSplashAds();
}
