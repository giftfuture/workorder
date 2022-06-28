package com.xbhy.workorder.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xbhy.workorder.constant.Constants;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.AjaxResult;
import com.xbhy.workorder.vo.StaffVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/staff")
public class StaffController {
    /**
     * 服务对象
     */
    @Resource
    private StaffService staffService;

    @GetMapping("/checkLogin")
    public String checkLogin(HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录人信息
        StaffVO currentUser = ShiroUtils.getStaff();
        if(currentUser == null){
            return "webCode/checkLogin";
        }else {
            return null;
        }
    }
    @ApiOperation("修改密码")
    @PostMapping("/updatePassword2")
    @ResponseBody
    public Result updatePassword2(@RequestBody SysUserModel sysUserModel){
        return userService.updatePassword2(sysUserModel);
    }
    /**
     * 登录验证
     * 验证之后调用shiro权限进行登录
     * @param
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String userName = httpServletRequest.getParameter("usrname");
            String password = httpServletRequest.getParameter("paswd");
            String verificationCode = httpServletRequest.getParameter("verificationCode");
            Boolean rememberMe = Boolean.valueOf(httpServletRequest.getParameter("rememberMe"));
            String captchaId = (String) httpServletRequest.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            String validateCode = httpServletRequest.getParameter("validateCode");
            if (validateCode == null || validateCode == null || !validateCode.equals(captchaId)){
                String msg = "图片验证码错误";
                return error(msg);
            } else {
                httpServletRequest.getSession().removeAttribute("kaptchaCodeMath");
                int count = sysUserDao.findCountByLoginName(userName);
                if (count == 0 ){
                    String msg = "用户或密码错误";
                    return error(msg);
                }
                UserPassWordOrVerigicationCode token = new UserPassWordOrVerigicationCode(userName, password, verificationCode, rememberMe);
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                SysUser sysUser = (SysUser) subject.getPrincipals().getPrimaryPrincipal();
                //部门数据权限
                List<SysUserDgpass> sysUserDgpassList = dgpassService.selectUserDgpassAllByUserId(sysUser.getUserId());
                //用户角色数据权限
                List<SysRole> roles = sysUser.getRoles();
                List<Integer> roleIds = new ArrayList<>();
                roles.stream().forEach(x -> {
                    roleIds.add(x.getRoleId());
                });
                List<SysRoleDgpass> roleDgpasses = sysRoleDgpassDao.getByRoleIds2(roleIds);
                Map<Integer,String> map = new HashMap<>();
                sysUserDgpassList.forEach(x ->{
                    map.put(x.getDeptId(),x.getDeptName());
                });
                roleDgpasses.forEach(x ->{
                    map.put(x.getDeptId(),x.getDeptName());
                });
                sysUserDgpassList.clear();
                for (HashMap.Entry<Integer,String> ma : map.entrySet()){
                    SysUserDgpass dgpass = new SysUserDgpass();
                    dgpass.setDeptId(ma.getKey());
                    dgpass.setDeptName(ma.getValue());
                    sysUserDgpassList.add(dgpass);
                }
                sysUser.setSysUserDgpassList(sysUserDgpassList);
                ShiroUtils.setSysUser(sysUser);
                return success();
            }
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }
    /**
     * 修改数据
     *
     * @param updateUserVO 实体对象
     * @return 修改结果
     */
    @Override
    @PutMapping(value = "/update")
    @ApiOperation(value = "用户更新")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<UserBasicVO> update(@RequestBody @Validated UpdateUserVO updateUserVO) {
        UserCenterValid.checkParams(updateUserVO);
        Long updater = JwtUtil.getCurrentUserId();
        String clientId = JwtUtil.getClientId();
        updateUserVO.setUpdater(updater);
        updateUserVO.setClientId(clientId);
        UserBasicDTO userBasicDTO = userBasicService.update(UpdateUserVOMapstruct.INSTANCE.toVO(updateUserVO));
        UserBasicVO userBasicVO = UserBasicVOMapstruct.INSTANCE.toVO(userBasicDTO);
        if (Optional.ofNullable(userBasicVO).isPresent()) {
            return success(userBasicVO);
        }
        throw UserCenterException.biz(failed(updateUserVO, CodeX._60033));
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @Override
    @PutMapping(value = "/delete")
    @ApiOperation(value = "批量逻辑删除")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<List<Long>> delete(@RequestParam("idList") @ApiParam(name = "用户ID集合") List<Long> idList) {
        try {
            if (userBasicService.logicDeleteBatch(idList)) {
                return success(idList);
            }
        } catch (Exception e) {
            throw UserCenterException.biz(failed(idList, CodeX._60034,e.getMessage()));
        }
        throw UserCenterException.biz(failed(idList, CodeX._60034));
    }
    /**
     * 更新密码
     *
     * @param updatePwdVO
     * @return
     */
    @Override
    @PutMapping(value = "/updatePwd", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新用户密码")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> updatePwd(@RequestBody @ApiParam(name = "密码更新") @Validated UpdatePwdVO updatePwdVO) throws UserCenterException,BizException{
        String password = updatePwdVO.getPassword();
        String newPwd = updatePwdVO.getNewPwd();
        String confirmNewPwd = updatePwdVO.getNewPwd();
        //新密码与确认密码必须相同
        if (!StringUtils.equals(newPwd.trim(),confirmNewPwd.trim())) {
            throw new UserCenterException(failed(CodeX._60019));
        }
        //新旧密码不能相同
        if (StringUtils.equals(password.trim(),newPwd.trim())) {
            throw new UserCenterException(failed(CodeX._60020));
        }
        //验证该操作用户是否存在
        UserBasicDTO userBasic = userBasicService.fetchById(updatePwdVO.getId());
        if (Optional.ofNullable(userBasic).isEmpty()) {
            throw new UserCenterException(failed(CodeX._60021));
        }
        //旧密码正确，进行修改密码操作
        //原来MD5加密
        if(CommEnum.EncryptType.MD5.getType().equals(userBasic.getEncrypt())) {
            if (StringUtils.equals(password.trim(), userBasic.getPassword().trim())) {
                updatePwdVO.setEncrypt(CommEnum.EncryptType.SCRYPT.getType());
                updatePwdVO.setPassword(EncryptUtil.bcrypt(newPwd.trim()));
            } else {
                throw new UserCenterException(failed(CodeX._60023));
            }
        }
        //原来双层MD5加密比较
        if(CommEnum.EncryptType.MD5_2.getType().equals(userBasic.getEncrypt())){
            if (StringUtils.equals(password.trim(),EncryptUtil.md5(userBasic.getPassword().trim()))) {
                updatePwdVO.setEncrypt(CommEnum.EncryptType.SCRYPT.getType());
                updatePwdVO.setPassword(EncryptUtil.bcrypt(newPwd.trim()));
            }else {
                throw new UserCenterException(failed(CodeX._60023));
            }
        }
        //原来SCRYPT加密
        if(CommEnum.EncryptType.SCRYPT.getType().equals(userBasic.getEncrypt())) {
            if (EncryptUtil.checkBcrypt(password.trim(), userBasic.getPassword().trim())) {
                updatePwdVO.setPassword(EncryptUtil.bcrypt(newPwd.trim()));
            } else {
                throw new UserCenterException(failed(CodeX._60023));
            }
        }
        //验证加密方式是否为枚举值
        if(Arrays.asList(CommEnum.EncryptType.values()).stream().map(CommEnum.EncryptType::getType).collect(Collectors.toList()).contains(userBasic.getEncrypt())) {
            Long updater = JwtUtil.getCurrentUserId();
            updatePwdVO.setUpdater(updater);
            //条件验证完毕，更新密码操作
            if (userBasicService.updatePwd(updatePwdVO)) {
                return success();
            }
        }
        throw new UserCenterException(failed(CodeX._60022));
    }

    /**
     * 重置密码
     *
     * @param resetPwdVO
     * @return
     */
    @Override
    @PutMapping(value = "/resetPwd", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "重置密码")
    public CommonResult<Boolean> resetPwd(@RequestBody @ApiParam(name = "密码重置") @Validated ResetPwdVO resetPwdVO) throws UserCenterException, BizException{
        List<String> clientIds = Lists.newArrayList();
        clientIds.add(resetPwdVO.getClientId());
        CommonResult<Boolean> result = trustAuthServerApi.isClientTrustServer(clientIds);
        if (!result.isSuccessful()){
            throw UserCenterException.biz(result);
        }
        Boolean trust = result.getData();
        //如果是管理员(有重置权限)
        if (!trust) {
            throw new UserCenterException(failed(CodeX._60024));
        }
        String newPwd = resetPwdVO.getNewPwd();
        String confirmNewPwd = resetPwdVO.getNewPwd();
        //密码与确认密码必须相同
        if (!StringUtils.equals(newPwd.trim(),confirmNewPwd.trim())) {
            throw UserCenterException.biz(failed(CodeX._60019));
        }
        //查询该用户是否存在
        UserBasicDTO userBasic = userBasicService.selectById(resetPwdVO.getId());
        if (Optional.ofNullable(userBasic).isEmpty()) {
            throw UserCenterException.biz(failed(CodeX._60021));
        }
        //条件验证完毕，进行重置密码操作
        resetPwdVO.setPassword(EncryptUtil.bcrypt(newPwd));
        Long updater = JwtUtil.getCurrentUserId();
        resetPwdVO.setUpdater(updater);
        //设置新的加密方式
        resetPwdVO.setEncrypt(CommEnum.EncryptType.SCRYPT.getType());
        //重置密码
        if (userBasicService.resetPwd(resetPwdVO)) {
            return success();
        }
        throw new UserCenterException(failed(CodeX._60025));
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询用户信息")
    public CommonResult<UserBasicNoRoleVO> selectById(@PathVariable(value = "id") @ApiParam(name = "用户ID") Long id) throws UserCenterException, BizException{
        UserBasicDTO userBasicDTO = userBasicService.selectById(id);
        String clientId = JwtUtil.getClientId();
        if(!Objects.isNull(userBasicDTO)){
            userBasicDTO.setClientId(clientId);
        }
        UserBasicNoRoleVO userBasicVO = UserBasicNoRoleVOMapstruct.INSTANCE.toVO(userBasicDTO);
        return success(userBasicVO);
    }

    /**
     * 分页查询所有数据
     *
     * @param pageVO 分页对象
     * @return 所有数据
     */
    @Override
    @PostMapping("/page")
    @ResponseBody
    @ApiOperation(value = "分页查询用户信息")
    public CommonResult<PageVO<UserBasicVO>> page(@ApiParam(name = "用户信息分页参数") @RequestBody @Validated @Valid PageVO<PageQueryUserVO> pageVO) throws UserCenterException, BizException {
        PageQueryUserVO pageQueryUserVO =  pageVO.getParams();
        Map<String, Object> userRoleMap = Maps.newHashMap();
        buildRoleMap(userRoleMap, pageQueryUserVO);
        if (null == pageQueryUserVO || StringUtils.isBlank(pageQueryUserVO.getClientId())){
            throw new UserCenterException(failed(CodeX._60012));
        }
        String clientIds = pageQueryUserVO.getClientId();
        CommonResult<Boolean> result = trustAuthServerApi.isClientTrustServer(Arrays.asList(clientIds.split(",")));
        if (!result.isSuccessful()){
            throw UserCenterException.biz(result);
        }
        if (!result.getData()) {
            throw new UserCenterException(failed(CodeX._60011));
        }
        UserBasicDTO userBasicDTO = PageUserVOMapstruct.INSTANCE.toDto(pageQueryUserVO);
        if(!Objects.isNull(userBasicDTO)) {
            userBasicDTO.setClientId(StringUtils.EMPTY);
            userBasicDTO.setClientIds(clientIds);
        }
        //执行分页查询操作
        PageDTO<UserBasicDTO> pageDTO = userBasicService.selectPage(PageUserVOMapstruct.INSTANCE.toPageDto(pageVO), userBasicDTO);
        PageVO<UserBasicVO> page = UserBasicVOMapstruct.INSTANCE.toPageVO(pageDTO);
        return success(page);
    }

    /**
     * 通过手机号查询单条数据
     *
     * @param mobile 手机号
     * @return 单条数据
     */
    @GetMapping("/mobile/{mobile}")
    @ApiOperation(value = "通过手机号查询用户信息")
    public CommonResult<UserBasicNoRoleVO> selectByMobile(@PathVariable(value = "mobile") @ApiParam(name = "手机号") String mobile) throws UserCenterException, BizException{
        UserBasicDTO userBasicDTO = userBasicService.selectByMobile(mobile);
        String clientId = JwtUtil.getClientId();
        if(!Objects.isNull(userBasicDTO)){
            userBasicDTO.setClientId(clientId);
        }
        UserBasicNoRoleVO userBasicVO = UserBasicNoRoleVOMapstruct.INSTANCE.toVO(userBasicDTO);
        return success(userBasicVO);
    }

    /**
     * 通过用户名查询单条数据
     *
     * @param userName 用户名
     * @return 单条数据
     */
    @GetMapping("/usrName/{userName}")
    @ApiOperation(value = "通过用户名查询用户信息")
    public CommonResult<UserBasicNoRoleVO> selectByUserName(@PathVariable(value = "userName") @ApiParam(name = "用户名") String userName) throws UserCenterException, BizException{
        UserBasicDTO userBasicDTO = userBasicService.selectByUserName(userName);
        String clientId = JwtUtil.getClientId();
        if(!Objects.isNull(userBasicDTO)){
            userBasicDTO.setClientId(clientId);
        }
        UserBasicNoRoleVO userBasicVO = UserBasicNoRoleVOMapstruct.INSTANCE.toVO(userBasicDTO);
        return success(userBasicVO);
    }

    @ApiOperation(value = "注销")
    @GetMapping(value = "/logout")
    public Object logout() {
        return userService.logout();
    }

    @ApiOperation(value = "获取登录用户信息")
    @RequiresAuthentication
    @GetMapping(value = "/showMe")
    public Object showMe() {
        return ResponseUtil.ok(userService.showMe());
    }
    /**
     * 分页查询
     *
     * @param staff       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Staff>> queryByPage(Staff staff, PageRequest pageRequest) {
        return ResponseEntity.ok(this.staffService.queryByPage(staff, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Staff> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.staffService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param staff 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Staff> add(Staff staff) {
        return ResponseEntity.ok(this.staffService.insert(staff));
    }

    /**
     * 编辑数据
     *
     * @param staff 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Staff> edit(Staff staff) {
        return ResponseEntity.ok(this.staffService.update(staff));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.staffService.deleteById(id));
    }

}

