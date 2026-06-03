package com.damochaohe.content.service.impl;

import com.damochaohe.content.dto.HomeConfigResponse;
import com.damochaohe.content.service.HomeContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页内容服务实现。
 *
 * <p>当前返回静态演示数据，后续对接首页内容配置表。</p>
 */
@Service
public class HomeContentServiceImpl implements HomeContentService {

    @Override
    public HomeConfigResponse getHomeConfig() {
        return HomeConfigResponse.builder()
                .splashTitle("大魔潮盒开屏广告")
                .banners(List.of(
                        "https://static.example.com/banner/banner-1.png",
                        "https://static.example.com/banner/banner-2.png"))
                .entries(List.of("星愿集赏", "福利大厅", "加入社群"))
                .build();
    }
}
