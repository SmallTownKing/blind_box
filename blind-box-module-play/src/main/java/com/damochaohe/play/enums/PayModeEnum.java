package com.damochaohe.play.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 奖池支付方式枚举。
 */
public enum PayModeEnum {

    BALANCE,
    GOLD,
    MAGIC,
    COUPON;

    /**
     * 校验支付方式配置是否合法。
     *
     * @param config 逗号分隔的支付方式配置
     * @return 是否合法
     */
    public static boolean isValidConfig(String config) {
        if (config == null || config.isBlank()) {
            return true;
        }
        Set<String> validValues = Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toSet());
        return Arrays.stream(config.split(","))
                .map(String::trim)
                .allMatch(validValues::contains);
    }
}
