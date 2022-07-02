package com.xbhy.workorder.enums;

import java.util.Objects;

/**
 * Created on 2022/7/2.
 *
 * @author zz
 */
public enum DataExchangeTypeEnum {
    QUOTATION_PARSE(1, "报价解析"),
    ORDER_CALC(2, "入单计算"),
    BILL_CALC(3, "对账计算"),
    INVOICE(4, "开票资料转换"),
    SEARCH_PARSE(5, "搜索结果格式化"),
    SEARCH_PARSE1(6, "搜索结果格式化（无价格、货期）"),
    ;

    DataExchangeTypeEnum(Integer code, String descr) {
        this.code = code;
        this.description = descr;
    }
    private Integer code;
    private String description;

    public static DataExchangeTypeEnum getByCode(Integer code) {
        for (DataExchangeTypeEnum value : values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }
}
