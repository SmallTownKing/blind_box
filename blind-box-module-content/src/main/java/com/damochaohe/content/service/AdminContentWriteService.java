package com.damochaohe.content.service;

import com.damochaohe.content.dto.AdminBannerSaveRequest;
import com.damochaohe.content.dto.AdminFloatingWindowSaveRequest;
import com.damochaohe.content.dto.AdminHomeEntrySaveRequest;
import com.damochaohe.content.dto.AdminPopupSaveRequest;
import com.damochaohe.content.dto.AdminSplashSaveRequest;
import com.damochaohe.content.dto.AdminWinnerTickerSaveRequest;

/**
 * 后台内容配置写服务。
 */
public interface AdminContentWriteService {

    /**
     * 保存 Banner。
     *
     * @param request 请求参数
     */
    void saveBanner(AdminBannerSaveRequest request);

    /**
     * 保存弹窗。
     *
     * @param request 请求参数
     */
    void savePopup(AdminPopupSaveRequest request);

    /**
     * 保存开屏广告。
     *
     * @param request 请求参数
     */
    void saveSplash(AdminSplashSaveRequest request);

    void saveHomeEntry(AdminHomeEntrySaveRequest request);

    void saveFloatingWindow(AdminFloatingWindowSaveRequest request);

    void saveWinnerTicker(AdminWinnerTickerSaveRequest request);
}
