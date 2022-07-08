package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "重置密码",description = "重置密码")
public class ResetPwdVO{
    //主键

    @NotBlank(message = "登录名不可为空")
    @ApiModelProperty(value="登录名",name="loginName",required=true)
    private String loginName;
    //新密码
    @NotBlank(message = "新密码不可为空")
    @ApiModelProperty(value="新密码",name="newPwd",required=true)
    private String newPwd;
    //确认新密码
    @NotBlank(message = "确认新密码不可为空")
    @ApiModelProperty(value="确认新密码",name="confirmPwd",required=true)
    private String confirmPwd;
    @ApiModelProperty(value="更新人",name="updateBy",hidden = true)
    private Long updateBy;

}
