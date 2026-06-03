package com.damochaohe.play.service;

import com.damochaohe.play.dto.AdminPlayPoolResponse;
import com.damochaohe.play.dto.AdminFukubukuroRuleResponse;
import com.damochaohe.play.dto.AdminFukubukuroRuleSaveRequest;
import com.damochaohe.play.dto.AdminHundredDrawResponse;
import com.damochaohe.play.dto.AdminHundredDrawSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolRewardResponse;
import com.damochaohe.play.dto.AdminPlayPoolRewardSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolSaveRequest;

import java.util.List;

/**
 * 后台奖池配置服务。
 */
public interface AdminPlayPoolManageService {

    List<AdminPlayPoolResponse> listPools();

    void savePool(AdminPlayPoolSaveRequest request);

    List<AdminPlayPoolRewardResponse> listRewards(Long poolId);

    void saveReward(AdminPlayPoolRewardSaveRequest request);

    List<AdminFukubukuroRuleResponse> listFukubukuroRules(Long poolId);

    void saveFukubukuroRule(AdminFukubukuroRuleSaveRequest request);

    List<AdminHundredDrawResponse> listHundredDrawConfigs(Long poolId);

    void saveHundredDrawConfig(AdminHundredDrawSaveRequest request);
}
