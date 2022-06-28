package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "用户登录",description = "用户登录")
public class StaffLoginVO implements Serializable {


    /**
     * 登录名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String passwd;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 最近登录时间
     */
    private Date loginTime;
}
