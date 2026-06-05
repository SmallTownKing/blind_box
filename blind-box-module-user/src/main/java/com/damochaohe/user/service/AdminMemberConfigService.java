package com.damochaohe.user.service;

import com.damochaohe.user.dto.AdminMemberLevelConfigResponse;
import com.damochaohe.user.dto.AdminMemberLevelConfigSaveRequest;

import java.util.List;

public interface AdminMemberConfigService {
    List<AdminMemberLevelConfigResponse> listMemberLevels();
    void saveMemberLevel(AdminMemberLevelConfigSaveRequest request);
}
