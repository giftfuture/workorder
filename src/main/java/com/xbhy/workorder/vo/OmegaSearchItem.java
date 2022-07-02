package com.xbhy.workorder.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 2021/8/24.
 *
 * @author zz_dxm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmegaSearchItem {
    private String code;
    private List<String> desc;
    private String price;
    private String duration;
}

