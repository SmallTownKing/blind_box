package com.damochaohe.play.service;

import com.damochaohe.asset.dto.CouponConsumeDetail;
import com.damochaohe.play.dto.PaymentSplitDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商用版支付拆账服务。
 */
public interface CommercialPaymentService {

    CommercialPaymentResult consume(Long userId, BigDecimal totalAmount, List<String> payTypes, List<Long> userCouponIds, String bizNo);

    class CommercialPaymentResult {
        public BigDecimal payableAmount;
        public List<PaymentSplitDetail> paymentSplits;
        public List<CouponConsumeDetail> couponConsumeDetails;
    }
}
