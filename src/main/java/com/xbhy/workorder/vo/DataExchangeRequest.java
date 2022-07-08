package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Created on 2022/7/2.
 *
 * @author zz
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataExchangeRequest {
    // 待处理文本
    private String text;
    // 处理类型 1-报价解析 2-入单计算 3-对账计算 4-开票资料转换 5-搜索结果格式化 6-搜索结果格式化（无价格、货期）
    private Integer type;
}
