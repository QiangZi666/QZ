package com.qianfeng.common;

public enum DateEnum {
    YEAR("year"),
    SEASON("season"),
    MONTH("month"),
    WEEK("week"),
    DAY("day"),
    HOUR("hour");
    public final String typeName;

    DateEnum(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 根据typeName来放回时间枚举
     */

    public static DateEnum valueDoName(String name) {
        for (DateEnum dateEnum : values()) {
            if (name.equals(dateEnum.typeName)) {
                return dateEnum;
            }
        }
        return null;
    }
}
