package com.damochaohe.play.service;

/**
 * 奖池玩法写服务。
 */
public interface AdminPlayPoolWriteService {

    /**
     * 删除奖池。
     *
     * @param id 奖池 ID
     */
    void deletePool(Long id);

    /**
     * 修改奖池状态。
     *
     * @param id 奖池 ID
     * @param status 状态
     */
    void updatePoolStatus(Long id, Integer status);

    /**
     * 删除奖池奖品项。
     *
     * @param id 奖品项 ID
     */
    void deleteReward(Long id);

    /**
     * 修改奖池奖品项状态。
     *
     * @param id 奖品项 ID
     * @param status 状态
     */
    void updateRewardStatus(Long id, Integer status);
}
