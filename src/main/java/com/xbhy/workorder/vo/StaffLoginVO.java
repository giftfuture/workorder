package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "用户登录",description = "用户登录")
public class StaffLoginVO implements Serializable {
    /**
     * 登录名
     */
    @NotBlank(message = "登录名不可为空")
    @ApiModelProperty(value="登录名",name="loginName",required=true)
    private String loginName;
    /**
     * 登录密码
     */
    @NotBlank(message = "密码不可为空")
    @ApiModelProperty(value="密码",name="paswd",required=true)
    private String passwd;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不可为空")
    @ApiModelProperty(value="验证码",name="verifynCode",required=true)
    private String verifynCode;

    /**
     * 记住我
     */
    private Boolean rememberMe;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 最近登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
}
