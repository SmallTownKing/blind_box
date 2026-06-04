package com.damochaohe.play.service;

import com.damochaohe.play.dto.AppDrawRequest;
import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppDeliveryApplyRequest;
import com.damochaohe.play.dto.AppDeliveryApplyResponse;
import com.damochaohe.play.dto.AppHundredDrawPageResponse;

/**
 * 用户端玩法服务。
 */
public interface AppPlayService {

    /**
     * 福袋抽赏。
     *
     * @param userId 用户 ID
     * @param request 抽赏参数
     * @return 抽赏结果
     */
    AppDrawResponse drawFukubukuro(Long userId, AppDrawRequest request);

    /**
     * 一番赏抽赏。
     *
     * @param userId 用户 ID
     * @param request 抽赏参数
     * @return 抽赏结果
     */
    AppDrawResponse drawKuji(Long userId, AppDrawRequest request);

    /**
     * 查询百连抽页面数据。
     *
     * @param poolId 奖池 ID
     * @return 页面数据
     */
    AppHundredDrawPageResponse getHundredDrawPage(Long poolId);

    AppDeliveryApplyResponse applyDelivery(Long userId, AppDeliveryApplyRequest request);
}
