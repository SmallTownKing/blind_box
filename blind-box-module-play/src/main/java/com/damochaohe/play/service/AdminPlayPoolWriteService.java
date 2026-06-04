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

    /**
     * 删除福袋玩法规则。
     *
     * @param id 规则 ID
     */
    void deleteFukubukuroRule(Long id);

    /**
     * 修改福袋玩法规则状态。
     *
     * @param id 规则 ID
     * @param status 状态
     */
    void updateFukubukuroRuleStatus(Long id, Integer status);

    /**
     * 删除百连抽页面配置。
     *
     * @param id 配置 ID
     */
    void deleteHundredDrawConfig(Long id);

    /**
     * 修改百连抽页面配置状态。
     *
     * @param id 配置 ID
     * @param status 状态
     */
    void updateHundredDrawConfigStatus(Long id, Integer status);
}
