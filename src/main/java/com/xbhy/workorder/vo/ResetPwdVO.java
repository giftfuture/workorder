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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "修改密码",description = "修改密码")
public class ResetPwdVO{
    //主键
    @NotNull(message = "用户ID不可为空")
    @ApiModelProperty(value="用户ID",name="id",required=true)
    private Long id;
    //密码,L:50
    @ApiModelProperty(value="密码",name="password")
    private String paswd;
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
