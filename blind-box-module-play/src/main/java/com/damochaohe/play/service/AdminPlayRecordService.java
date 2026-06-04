package com.damochaohe.play.service;

import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppTradeRecordResponse;
import com.damochaohe.play.dto.AppWarehouseItemResponse;
import com.damochaohe.play.dto.AdminDeliveryQuery;
import com.damochaohe.play.dto.AdminDeliveryRecordResponse;

import java.util.List;

/**
 * 后台玩法记录查询服务。
 */
public interface AdminPlayRecordService {

    /**
     * 查询抽赏记录。
     *
     * @return 抽赏记录列表
     */
    List<AppDrawResponse> listDrawRecords();

    /**
     * 查询交易记录。
     *
     * @return 交易记录列表
     */
    List<AppTradeRecordResponse> listTradeRecords();

    /**
     * 查询入仓记录。
     *
     * @return 入仓记录列表
     */
    List<AppWarehouseItemResponse> listWarehouseRecords();

    /**
     * 查询提货记录。
     *
     * @return 提货记录列表
     */
    List<AdminDeliveryRecordResponse> listDeliveryRecords(AdminDeliveryQuery query);
}
