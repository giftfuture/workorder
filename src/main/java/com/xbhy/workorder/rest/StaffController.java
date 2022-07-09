package com.xbhy.workorder.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xbhy.workorder.config.BaseConfig;
import com.xbhy.workorder.constant.Constants;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.exception.BizException;
import com.xbhy.workorder.service.StaffOrderService;
import com.xbhy.workorder.service.StaffRoleService;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


/**
 * (Staff)表控制层
 *
 * @author 
 * @since 2022-06-26 22:40:40
 */
@Slf4j
@RestController
@RequestMapping("/staf")
public class StaffController {
    /**
     * 服务对象
     */
    @Resource
    private StaffService staffService;

    @Resource
    private StaffOrderService staffOrderService;

    @Resource
    private StaffRoleService staffRoleService;

    /**
     * 获取所有在职员工Map
     * @return
     */
    @GetMapping("/queryAll")
    public ResponseVO<Map<Long,String>> queryAll() {
        return ResponseVO.success(staffService.queryAll());
    }

    @GetMapping("/checkLogin")
    public String checkLogin(HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录人信息
        StaffVO currentUser = ShiroUtils.getStaff();
        if(currentUser == null){
            return "/checkLogin";
        }else {
            return null;
        }
    }

    /**
     * 登录验证
     * 验证之后调用shiro权限进行登录
     * @param
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseVO login(@RequestBody @Validated StaffLoginVO staffLoginVO,HttpServletRequest httpServletRequest) {
        try {
            String captchaId = (String) httpServletRequest.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            String validateCode = httpServletRequest.getParameter("validateCode");
            if (validateCode == null || validateCode == null || !validateCode.equals(captchaId)){
                String msg = "图片验证码错误";
                return ResponseVO.fail(msg);
            } else {
                httpServletRequest.getSession().removeAttribute("kaptchaCodeMath");
                StaffVO staffVO = staffService.selectByLoginName(staffLoginVO.getLoginName());
                if (Optional.ofNullable(staffVO).isEmpty()){
                    return ResponseVO.fail(BaseConfig.getLoginNameNoExist());
                }
               // UserPassWordOrVerigicationCode token = new UserPassWordOrVerigicationCode(userName, password, verificationCode, rememberMe);
                Subject subject = SecurityUtils.getSubject();
               // subject.login(token);
                StaffVO sysUser = (StaffVO) subject.getPrincipals().getPrimaryPrincipal();
                //用户角色数据权限
                List<SysRole> roles = sysUser.getSysRoleList();
                List<Integer> roleIds = new ArrayList<>();
                roles.stream().forEach(x -> {
                    roleIds.add(x.getRoleId());
                });
                ShiroUtils.setStaff(sysUser);
                return ResponseVO.success(sysUser);
            }
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return ResponseVO.fail(msg);
        }
    }
    /**
     * 修改数据
     *
     * @param staffVO 实体对象
     * @return 修改结果
     */

    @PutMapping(value = "/update")
    @ApiOperation(value = "用户更新")
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVO<StaffVO> update(@RequestBody StaffVO staffVO) {
        staffVO.setUpdateBy(ShiroUtils.getStaffId());
        StaffVO updateStaffVO = staffService.update(staffVO);
        if (Optional.ofNullable(updateStaffVO).isPresent()) {
            return ResponseVO.success(updateStaffVO);
        }
        return ResponseVO.fail(BaseConfig.getUpdateStaffFailed());
    }

    /**
     * 更新密码
     *
     * @param updatePwdVO
     * @return
     */
    @PutMapping(value = "/updatePwd", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新用户密码")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<Boolean> updatePwd(@RequestBody @ApiParam(name = "密码更新") @Validated UpdatePwdVO updatePwdVO) throws BizException{
        String password = updatePwdVO.getPaswd();
        String newPwd = updatePwdVO.getNewPwd();
        String confirmNewPwd = updatePwdVO.getNewPwd();
        //新密码与确认密码必须相同
        if (!StringUtils.equals(newPwd.trim(),confirmNewPwd.trim())) {
            throw new BizException(ResponseVO.fail(BaseConfig.getConfirmEqualPwd()));
        }
        //新旧密码不能相同
        if (StringUtils.equals(password.trim(),newPwd.trim())) {
            throw new BizException(ResponseVO.fail(BaseConfig.getNotEqualOldPwd()));
        }
        //验证该操作用户是否存在
        StaffVO staffVO = staffService.selectByLoginName(updatePwdVO.getLoginName());
        if (Optional.ofNullable(staffVO).isEmpty()) {
            throw new BizException(ResponseVO.fail(BaseConfig.getLoginNameIndex()));
        }
        //旧密码正确，进行修改密码操作
        return staffService.updatePwd(updatePwdVO) ? ResponseVO.success(BaseConfig.getUpdatePwdSuccess()):ResponseVO.fail(BaseConfig.getUpdatePwdFailed());
    }



    @ApiOperation(value = "注销")
    @GetMapping(value = "/logout")
    public void logout() {
        //ShiroUtils.
       // return staffService.logout();
    }

    @ApiOperation(value = "获取登录用户信息")
    @RequiresAuthentication
    @GetMapping(value = "/showMe")
    public Object showMe() {

        return ResponseVO.success("记住我");//staffService.showMe()
    }


}

