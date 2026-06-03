package com.damochaohe.content.service;

import com.damochaohe.content.dto.AdminBannerSaveRequest;
import com.damochaohe.content.dto.AdminPopupSaveRequest;
import com.damochaohe.content.dto.AdminSplashSaveRequest;

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
}
