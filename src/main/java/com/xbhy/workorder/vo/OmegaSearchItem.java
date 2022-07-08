package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created on 2021/8/24.
 *
 * @author zz_dxm
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmegaSearchItem {
    private String code;
    private List<String> desc;
    private String price;
    private String duration;
}

