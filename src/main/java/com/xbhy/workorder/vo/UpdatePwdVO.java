package com.xbhy.workorder.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@ApiModel(value = "更新密码",description = "更新密码")
public class UpdatePwdVO {
    //主键
    @NotNull(message = "用户ID不可为空")
    @ApiModelProperty(value="用户ID",name="id",required=true)
    private Long id;
    //密码,L:50
    @NotBlank(message = "原密码不可为空")
    @ApiModelProperty(value="密码",name="paswd",required=true)
    private String paswd;
    //新密码
    @NotBlank(message = "新密码不可为空")
    @ApiModelProperty(value="新密码",name="newPwd",required=true)
    private String newPwd;
    //确认新密码
    @NotBlank(message = "确认密码不可为空")
    @ApiModelProperty(value="确认新密码",name="confirmNewPwd",required=true)
    private String confirmNewPwd;
    @ApiModelProperty(value="更新人",name="updateBy",hidden = true)
    private Long updateBy;
}
