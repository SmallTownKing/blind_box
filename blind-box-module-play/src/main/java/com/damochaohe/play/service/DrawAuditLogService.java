package com.damochaohe.play.service;

import com.damochaohe.play.dto.DrawAuditLogResponse;

import java.util.List;

public interface DrawAuditLogService {

    List<DrawAuditLogResponse> listAuditLogs();
}
