package com.xbhy.workorder.controller;

import com.xbhy.workorder.entity.SysRole;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.AjaxResult;
import com.xbhy.workorder.vo.StaffVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.xbhy.workorder.vo.AjaxResult.error;

/**
 * 用户信息
 *
 * @author ycp
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class SysUserController {
    private String prefix = "admin";



    //@RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    /**
     * @param user
     * @return
     */
    //@RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
        user.setCompanyId(ShiroUtils.getSysUser().getCompanyId());
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * @param user
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user) {
        user.setCompanyId(ShiroUtils.getSysUser().getCompanyId());
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    /**
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<SysRole> sysRoles = roleService.selectRoleAll();
        List<SysPost> sysPosts = postService.selectPostAll();
        mmap.put("roles", sysRoles);
        mmap.put("posts", sysPosts);
        mmap.put("companyId", ShiroUtils.getSysUser().getCompanyId());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        Integer checkLoginNameUnique = userService.checkLoginNameUnique(user.getLoginName());
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(checkLoginNameUnique)) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
       /* else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }*/
        String encryptpwd = passwordService.encryptPassword(user.getLoginName(), UserConstants.initPWD, user.getSalt());
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(encryptpwd);
        user.setCreateBy(ShiroUtils.getUserId());
        user.setUpdateBy(ShiroUtils.getUserId());
        user.setUserType(UserConstants.UserTypes.NORMAL.getType() + "");
        user.setRegisterIp(IPUtil.getIP());
        int result = userService.insertUser(user);
       /* String [] deptIds =  StringUtils.isNotBlank(user.getDepts()) ? user.getDepts().split(",") : null;
        if(deptIds != null && deptIds.length > 0){
            saveUserDept(user,deptIds);
        }*/
        SysDept dept = user.getDept();
        dept.setDeptId(user.getDeptId());
        dept.setCompanyId(user.getCompanyId());
        List<SysDept> subDept = sysDeptDao.getSubDept(dept);
        Set deptSet = new TreeSet<String>();

        subDept.stream().forEach(x -> {
            //判断分级
            String ancestors = x.getAncestors() + ",";
            if (x.getDeptId().equals(dept.getDeptId()) || ancestors.contains("," + dept.getDeptId() + ",")) {
                deptSet.add(x.getDeptId());
            }
        });

        //判断数据集是否为空
        String dataright = user.getDatarights();
        if (StringUtils.isNotBlank(dataright)) {
            String[] split = dataright.split(",");
            for (int i = 0; i < split.length; i++) {
                deptSet.add(FormatUtil.stringToInteger(split[i]));
            }
        }
        dataright = StringUtils.join(deptSet, ",");
        user.setDatarights(dataright);
        String[] datarights=dataright.split(",");
        if (datarights != null && datarights.length > 0) {
            saveUserDgpass(user, datarights);
        }
        return toAjax(result);
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {

        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated StaffVO user) {
        StaffVO sysUser = userService.selectUserById(user.getUserId());
        List<SysRole> roles = roleService.selectEffectRolesByUserId(user.getUserId());
        sysUser.setRoles(roles);
//        sysUser.setAdmin(sysUser.isAdmin(sysUser.getUserId()));
//        log.info("sysUser.isAdmin():{}",sysUser.isAdmin(user.getUserId()));
        boolean admin = sysUser.isAdmin(user.getUserId());
        if (StringUtils.isNotNull(user.getUserId()) && admin) {
//            user.setLoginName(StringUtils.EMPTY);
//            user.setRoleIds(null);
            //  return error("不允许修改超级管理员用户");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
       /* else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }*/
        user.setUpdateBy(ShiroUtils.getUserId());
        String[] deptIds = StringUtils.isNotBlank(user.getDepts()) ? user.getDepts().split(",") : null;
        if (deptIds != null && deptIds.length > 0) {
            saveUserDept(user, deptIds);
        }
        SysDept dept = user.getDept();
        dept.setDeptId(user.getDeptId());
        dept.setCompanyId(user.getCompanyId());
        List<SysDept> subDept = sysDeptDao.getSubDept(dept);
        Set deptSet = new TreeSet<String>();

        subDept.stream().forEach(x -> {
            //判断分级
            String ancestors = x.getAncestors() + ",";
            if (x.getDeptId().equals(dept.getDeptId()) || ancestors.contains("," + dept.getDeptId() + ",")) {
                deptSet.add(x.getDeptId());
            }
        });

        //判断数据集是否为空
        String dataright = user.getDatarights();
        if (StringUtils.isNotBlank(dataright)) {
            dataright = dataright + "," + StringUtils.join(deptSet, ",");
        } else {
            dataright = StringUtils.join(deptSet, ",");
        }
        user.setDatarights(dataright);
        String[] datarights = StringUtils.isNotBlank(user.getDatarights()) ? user.getDatarights().split(",") : null;
        if (datarights != null && datarights.length > 0) {
            saveUserDgpass(user, datarights);
        }
        int result = userService.updateUser(user);
        if (user.getUserId().equals(ShiroUtils.getUserId())) {
            List<SysUserDgpass> sysUserDgpassList = dgpassService.selectUserDgpassAllByUserId(user.getUserId());
            SysUser sessionUser = ShiroUtils.getSysUser();
            sessionUser.setSysUserDgpassList(sysUserDgpassList);
            ShiroUtils.setSysUser(sessionUser);
        }
        return toAjax(result);
    }

    /**
     * @param userId
     * @param mmap
     * @return
     */
    //@RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    /**
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0) {
            if (ShiroUtils.getUserId() == user.getUserId()) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    /**
     * @param ids
     * @return
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {

            return toAjax(userService.deleteUserById(Long.valueOf(ids)));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public Integer checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public Integer checkPhoneUnique(SysUser user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public Integer checkEmailUnique(SysUser user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user) {
        return toAjax(userService.changeStatus(user));
    }

    @PostMapping("/checkNewPhoneUnique")
    @ResponseBody
    public AjaxResult checkNewPhoneUnique(String newPhone) {
        return userService.checkNewPhoneUnique(newPhone);
    }

    /**
     * @param sysUserModel
     * @return
     */
    @RequiresPermissions("system:user:updatePhone")
    @ApiOperation("修改手机号")
    @PostMapping("/updatePhone")
    @ResponseBody
    public Result updatePhone(@RequestBody SysUserModel sysUserModel) {
        return userService.updatePhone(sysUserModel);
    }


    /**
     * @param sysUserModel
     * @return
     */
    @RequiresPermissions("system:user:updatePassword")
    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    @ResponseBody
    public Result updatePassword(@RequestBody SysUserModel sysUserModel) {
        return userService.updatePassword(sysUserModel);
    }


    @ApiOperation("修改密码")
    @PostMapping("/updatePassword2")
    @ResponseBody
    public Result updatePassword2(@RequestBody SysUserModel sysUserModel) {
        return userService.updatePassword2(sysUserModel);
    }


    @PostMapping("/verificationOriginalPhone")
    @ResponseBody
    public Result verificationOriginalPhone(@RequestBody SysUserModel sysUserModel) {
        return userService.verificationOriginalPhone(sysUserModel);
    }


    /**
     * @param mmap
     * @return
     */
    @ApiOperation("查询个人信息")
    @GetMapping("/findByUserId")
    public String findByUserId(ModelMap mmap) {
//        SysUser sysUser = ShiroUtils.getSysUser();
//        //判断是否是公司用户是的话跳入公司
//        if("1".equals(sysUser.getUserType())){
//            mmap.put("company", sysCompanyService.companyDetail(sysUser.getCompanyId().toString()));
//            return "system/company/sysCompanyDetail";
//        }
        SysUserModelResponse byUserId = userService.findByUserId();
        mmap.put("user", byUserId);
        return "system/user/userDetail";
    }

    /**修改支付手机号
     * @param mmap
     * @return
     */
    @RequiresPermissions("system:user:editPayPhone")
    @GetMapping("/editUserWalletPhone")
    public String updataWalletPhone(ModelMap mmap) {
        mmap.put("user", userService.findByUserId());
        return "system/user/editUserWalletPhone";
    }

    /**
     * @param mmap
     * @return
     */
    @GetMapping("/updatePassword")
    public String updatePassword(ModelMap mmap) {
        mmap.put("user", userService.findByUserId());
        return "system/user/editUserPassword";
    }

    @GetMapping("/updatePassword2")
    public String updatePassword2(ModelMap mmap) {

        return "webCode//editUserPassword2";
    }


    /**
     * @param mmap
     * @return
     */
    @GetMapping("/updatePhone")
    public String updatePhone(ModelMap mmap) {
        mmap.put("user", userService.findByUserId());
        return "system/user/editUserPhone";
    }

    /**
     * 保存用户部门关系
     *
     * @param user
     * @param deptIds
     */
    private void saveUserDept(SysUser user, String[] deptIds) {
        SysUserDept sysUserDept = new SysUserDept();
        sysUserDept.setUserId(user.getUserId());
        sysUserDept.setCompanyId(user.getCompanyId());
        sysUserDept.setExcludeDepts(user.getDepts());
        Map<Integer, SysUserDept> userDeptMap = sysUserDeptService.selectUserDeptDeptMap(sysUserDept);
        List<SysUserDept> exculdeDeptList = sysUserDeptService.selectUserExculdeDeptTree(sysUserDept);
        Set<Integer> deptIdSet = userDeptMap.keySet();
        for (String deptId : deptIds) {
            if (deptIdSet.contains(Integer.valueOf(deptId))) {
                SysUserDept existsUserDept = sysUserDeptService.selectUserDeptByDept(user.getUserId(), Integer.valueOf(deptId), user.getCompanyId());
                if (existsUserDept != null) {
                    if (existsUserDept.getStatus() >= 1) {
                        existsUserDept.setStatus(0);
                    }
                    if (existsUserDept.getDelFlag() >= 1) {
                        existsUserDept.setDelFlag(0);
                    }
                    existsUserDept.setUpdateBy(user.getUserId());
                    sysUserDeptService.updateUserDept(existsUserDept);
                }
            } else {
                SysUserDept userDept = new SysUserDept();
                userDept.setCompanyId(user.getCompanyId());
                userDept.setUserId(user.getUserId());
                userDept.setCreateBy(ShiroUtils.getUserId());
                userDept.setUpdateBy(ShiroUtils.getUserId());
                userDept.setDeptId(Integer.valueOf(deptId));
                sysUserDeptService.insertUserDept(userDept);
                SysUserDept existsUserDept = sysUserDeptService.selectUserDeptByDept(user.getUserId(), Integer.valueOf(deptId), user.getCompanyId());
                if (existsUserDept == null) {
                    sysUserDeptService.updateUserDept(existsUserDept);
                } else {
                    sysUserDeptService.updateUserDept(existsUserDept);
                }
            }
        }
        for (SysUserDept userDept : exculdeDeptList) {
            userDept.setUpdateBy(ShiroUtils.getUserId());
            sysUserDeptService.deleteUserDept(userDept);
        }
    }

    /**
     * 保存用户数据权限
     *
     * @param user
     * @param datarights
     */
    private void saveUserDgpass(SysUser user, String[] datarights) {
        SysUser sysUser = ShiroUtils.getSysUser();
        SysUserDgpass sysUserDgpass = new SysUserDgpass();
        sysUserDgpass.setUserId(user.getUserId());
        sysUserDgpass.setCompanyId(user.getCompanyId());
        sysUserDgpass.setExcludeDepts(user.getDatarights());
        Map<Integer, SysUserDgpass> sysUserDgpassMap = sysUserDgpassService.selectUserDgpassMap(sysUserDgpass);
        List<SysUserDgpass> exculdeDgpassList = sysUserDgpassService.selectUserExculdeDgpassTree(sysUserDgpass);
        Set<Integer> datarightIds = sysUserDgpassMap.keySet();
        for (String dataright : datarights) {
            if (datarightIds.contains(Integer.valueOf(dataright))) {
                SysUserDgpass existsUserDgpass = sysUserDgpassService.selectUserDgpassByDept(user.getUserId(), Integer.valueOf(dataright), user.getCompanyId());
                if (existsUserDgpass != null) {
                    if (existsUserDgpass.getStatus() >= 1) {
                        existsUserDgpass.setStatus(0);
                    }
                    if (existsUserDgpass.getDelFlag() >= 1) {
                        existsUserDgpass.setDelFlag(0);
                    }
                    existsUserDgpass.setUpdateBy(sysUser.getUserId());
                    sysUserDgpassService.updateUserDgpass(existsUserDgpass);
                }
            } else {
                SysUserDgpass userDgpass = new SysUserDgpass();
                userDgpass.setCompanyId(user.getCompanyId());
                userDgpass.setUserId(user.getUserId());
                userDgpass.setCreateBy(sysUser.getUserId());
                userDgpass.setUpdateBy(sysUser.getUserId());
                userDgpass.setDeptId(Integer.valueOf(dataright));
                userDgpass.setStatus(0);
                userDgpass.setDelFlag(0);
                SysUserDgpass existsUserDgpass = sysUserDgpassService.selectUserDgpassByDept(user.getUserId(), Integer.valueOf(dataright), user.getCompanyId());
                if (existsUserDgpass == null) {
                    sysUserDgpassService.insertUserDgpass(userDgpass);
                } else {
                    sysUserDgpassService.updateUserDgpass(existsUserDgpass);
                }
            }
        }
        for (SysUserDgpass userDgpass : exculdeDgpassList) {
            userDgpass.setUpdateBy(sysUser.getUserId());
            sysUserDgpassService.deleteUserDgpass(userDgpass);
        }
    }

}