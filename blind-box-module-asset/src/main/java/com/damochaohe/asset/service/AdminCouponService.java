package com.damochaohe.asset.service;

import com.damochaohe.asset.dto.AdminCouponIssueRequest;
import com.damochaohe.asset.dto.AdminCouponConsumeRecordResponse;
import com.damochaohe.asset.dto.AdminCouponTemplateResponse;
import com.damochaohe.asset.dto.AdminCouponTemplateSaveRequest;
import com.damochaohe.asset.dto.AdminUserCouponQuery;
import com.damochaohe.asset.dto.AdminUserCouponResponse;

import java.util.List;

public interface AdminCouponService {
    List<AdminCouponTemplateResponse> listTemplates();
    void saveTemplate(AdminCouponTemplateSaveRequest request);
    String issueCoupon(AdminCouponIssueRequest request);
    List<AdminUserCouponResponse> listUserCoupons(AdminUserCouponQuery query);
    List<AdminCouponConsumeRecordResponse> listConsumeRecords(Long userId);
}
