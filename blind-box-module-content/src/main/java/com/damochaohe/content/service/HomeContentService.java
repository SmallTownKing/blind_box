package com.damochaohe.content.service;

import com.damochaohe.content.dto.HomeConfigResponse;

/**
 * 首页内容服务。
 */
public interface HomeContentService {

    /**
     * 查询首页聚合配置。
     *
     * @return 首页配置
     */
    HomeConfigResponse getHomeConfig();
}
