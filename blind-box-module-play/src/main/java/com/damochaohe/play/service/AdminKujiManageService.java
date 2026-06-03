package com.damochaohe.play.service;

import com.damochaohe.common.model.PageResponse;
import com.damochaohe.play.dto.AdminKujiActivityResponse;
import com.damochaohe.play.dto.AdminKujiActivitySaveRequest;
import com.damochaohe.play.dto.AdminKujiFinalRuleResponse;
import com.damochaohe.play.dto.AdminKujiFinalRuleQuery;
import com.damochaohe.play.dto.AdminKujiFinalRuleSaveRequest;
import com.damochaohe.play.dto.AdminKujiLockResponse;
import com.damochaohe.play.dto.AdminKujiLockSaveRequest;
import com.damochaohe.play.dto.AdminKujiTargetRewardResponse;
import com.damochaohe.play.dto.AdminKujiTargetRewardQuery;
import com.damochaohe.play.dto.AdminKujiTargetRewardSaveRequest;
import com.damochaohe.play.dto.AdminKujiTierResponse;
import com.damochaohe.play.dto.AdminKujiTierSaveRequest;

import java.util.List;

/**
 * 一番赏后台配置服务。
 */
public interface AdminKujiManageService {

    List<AdminKujiActivityResponse> listActivities();

    void saveActivity(AdminKujiActivitySaveRequest request);

    void deleteActivity(Long id);

    void updateActivityStatus(Long id, Integer status);

    List<AdminKujiTierResponse> listTiers(Long activityId);

    void saveTier(AdminKujiTierSaveRequest request);

    void deleteTier(Long id);

    void updateTierStatus(Long id, Integer status);

    PageResponse<AdminKujiFinalRuleResponse> pageFinalRules(AdminKujiFinalRuleQuery query);

    void saveFinalRule(AdminKujiFinalRuleSaveRequest request);

    List<AdminKujiLockResponse> listLocks(Long activityId);

    void saveLock(AdminKujiLockSaveRequest request);

    PageResponse<AdminKujiTargetRewardResponse> pageTargetRewards(AdminKujiTargetRewardQuery query);

    void saveTargetReward(AdminKujiTargetRewardSaveRequest request);
}
