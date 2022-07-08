package com.xbhy.workorder.rest;

import com.google.common.collect.Lists;
import com.xbhy.workorder.config.BaseConfig;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.enums.RoleKey;
import com.xbhy.workorder.exception.BizException;
import com.xbhy.workorder.service.StaffOrderService;
import com.xbhy.workorder.service.StaffRoleService;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.service.SysRoleService;
import com.xbhy.workorder.util.IpUtils;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.xbhy.workorder.vo.AjaxResult.error;
import static com.xbhy.workorder.vo.AjaxResult.success;

/**
 * 用户信息
 *
 * @author
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    private String prefix = "admin";

    @Autowired
    private StaffService staffService;
    @Autowired
    private SysRoleService sysRoleService;
    @Resource
    private StaffRoleService staffRoleService;
    @Resource
    private StaffOrderService staffOrderService;

    //@RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }


    /**
     * 重置密码
     *
     * @param resetPwdVO
     * @return
     */
    @PutMapping(value = "/resetPwd", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "重置密码")
    public ResponseVO<Boolean> resetPwd(@RequestBody @ApiParam(name = "密码重置") @Validated ResetPwdVO resetPwdVO) throws BizException {
        //如果是管理员(有重置权限)
        String newPwd = resetPwdVO.getNewPwd();
        String confirmNewPwd = resetPwdVO.getNewPwd();
        //密码与确认密码必须相同
        if (!StringUtils.equals(newPwd.trim(),confirmNewPwd.trim())) {
            throw new BizException(ResponseVO.fail(BaseConfig.getConfirmEqualPwd()));
        }
        //查询该用户是否存在
        StaffVO staffVO = staffService.selectByLoginName(resetPwdVO.getLoginName());
        if (Optional.ofNullable(staffVO).isEmpty()) {
            throw new BizException(ResponseVO.fail(BaseConfig.getLoginNameNoExist()));
        }
        //条件验证完毕，进行重置密码操作
        if (staffService.resetPwd(resetPwdVO)) {
            return ResponseVO.success(BaseConfig.getResetPwdSuccess());
        }
        throw new BizException(ResponseVO.fail(BaseConfig.getResetPwdFailed()));
    }





    /**
     * 分页查询
     *
     * @param staffVO       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseVO<Page<StaffVO>> queryByPage(StaffVO staffVO, PageRequest pageRequest) {
        return ResponseVO.success(this.staffService.queryByPage(staffVO, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public ResponseVO<StaffVO> queryById(@PathVariable("id") Long id) {
        return ResponseVO.success(this.staffService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param staff 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseVO<StaffVO> add(StaffVO staff) {
        return ResponseVO.success(this.staffService.insert(staff));
    }

    /**
     * 编辑数据
     *
     * @param staffVO 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseVO<StaffVO> edit(StaffVO staffVO) {
        return ResponseVO.success(this.staffService.update(staffVO));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseVO<Boolean> deleteById(Long id) {
        return ResponseVO.success(this.staffService.deleteById(id));
    }


    /**
     * 新增保存用户
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseVO addSave(@Validated StaffVO staff, HttpServletRequest request) {
        StaffVO staffVO = staffService.selectByLoginName(staff.getLoginName());
        if (Optional.ofNullable(staffVO).isPresent()) {
            return ResponseVO.fail("新增用户'" + staff.getLoginName() + "'失败，登录账号已存在");
        }
        String encryptpwd = BCrypt.hashpw(staff.getPasswd(), BCrypt.gensalt());
        StaffVO.builder().passwd(encryptpwd).createBy(ShiroUtils.getStaffId()).updateBy(ShiroUtils.getStaffId())
        .loginIp(IpUtils.getIpAddress(request)).build();
        StaffVO staffAdded = staffService.insert(staff);
        return ResponseVO.success(staffAdded);
    }

    /**
     * 修改保存用户
     */

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVO<StaffVO> editSave(@Validated StaffVO staff) {
        StaffVO staffVO = staffService.queryById(staff.getId());
        List<StaffRoleVO> roles = staffRoleService.queryByStaffId(staff.getId());
       // staffVO.set(roles);
//        sysUser.setAdmin(sysUser.isAdmin(sysUser.getUserId()));
//        log.info("sysUser.isAdmin():{}",sysUser.isAdmin(user.getUserId()));
        boolean admin = roles.stream().map(StaffRoleVO::getRoleKey).collect(Collectors.toSet()).contains(RoleKey.ADMIN.getKey());
        if (admin) {
//            user.setLoginName(StringUtils.EMPTY);
//            user.setRoleIds(null);
            //  return error("不允许修改超级管理员用户");
        }
       /* else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }*/
        staff.setUpdateBy(ShiroUtils.getStaffId());
        StaffVO staffUpdated = staffService.update(staff);
        if (staffUpdated.getId().equals(ShiroUtils.getStaffId())) {
            StaffVO sessionUser = ShiroUtils.getStaff();
            ShiroUtils.setStaff(sessionUser);
        }
        return ResponseVO.success(staffUpdated);
    }

    /**
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseVO<Boolean> remove(String ids) {
        try {
            return ResponseVO.success(staffService.deleteById(Long.valueOf(ids)));
        } catch (Exception e) {
            return ResponseVO.fail(e.getMessage());
        }
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




}