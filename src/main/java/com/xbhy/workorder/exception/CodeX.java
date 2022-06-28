package com.xbhy.workorder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeX implements IErrorCode {

    _60001(60001, "网关异常:{0}", ""),
    _60002(60002, "{0}不能为空！", ""),
    _60003(60003, "解析请求参数异常！", ""),
    _60004(60004, "{0}查询异常！", ""),
    _60005(60005, "{0}新增异常！", ""),
    _60006(60006, "{0}更新异常！", ""),
    _60007(60007, "{0}删除异常！", ""),
    _60008(60008, "{0}", ""),
    _60009(60009, "当前账号下没有用户", ""),
    _60010(60010, "用户信息分页异常", ""),
    _60011(60011, "查询用户信息分页权限不足", ""),
    _60012(60012, "请求客户端参数为空", ""),
    _60013(60013, "通过ID查询用户信息异常", ""),
    _60014(60014, "通过手机号查询用户信息异常", ""),
    _60015(60015, "通过用户名查询用户信息异常", ""),
    _60016(60016, "通过邮箱查询用户信息异常", ""),
    _60017(60017, "通过手机号和客户端ID查询异常", ""),
    _60018(60018, "通过用户名和客户端ID查询异常", ""),
    _60019(60019, "新密码与确认密码不一致!", ""),
    _60020(60020, "新密码不能与原密码相同！", ""),
    _60021(60021, "当前用户不存在！", ""),
    _60022(60022, "密码修改异常！", ""),
    _60023(60023, "原始密码不正确！", ""),
    _60024(60024, "密码重置权限不足！", ""),
    _60025(60025, "密码重置失败！", ""),
    _60026(60026, "手机号、邮箱或用户名至少一个不为空。", ""),
    _60027(60027, "密码与确认密码不一致。", ""),
    _60028(60028, "手机号已存在。", ""),
    _60029(60029, "邮箱已存在。", ""),
    _60030(60030, "用户名已存在。", ""),
    _60031(60031, "批量新增用户异常！", ""),
    _60032(60032, "用户批量更新异常！", ""),
    _60033(60033, "用户更新异常！", ""),
    _60034(60034, "用户批量删除异常！", ""),
    _60035(60035, "注册/添加用户失败！", ""),
    _60036(60036, "邮箱验证码不正确。", ""),
    _60037(60037, "手机验证码不正确。", ""),
    _60038(60038, "注册方式不存在。", ""),
    _60039(60039, "不能包含除数字、字母和下划线以外的非法字符。", ""),
    _60040(60040, "手机号格式不正确。", ""),
    _60041(60041, "邮箱格式不正确。", ""),
    _60042(60042, "用户名不可为空。", ""),
    _60043(60043, "手机号不可为空。", ""),
    _60044(60044, "添加用户的用户名或手机号或邮箱分别被不同的人使用", ""),
    _60045(60045, "用户已存在", ""),
    _60046(60046, "信任名单配置为空,暂无法添加其他应用用户信息", ""),
    _60047(60047, "邮箱不可为空。", ""),
    _60048(60048, "当前注册/添加的用户名、手机号或邮箱已存在。", ""),
    _60054(60054, "用户不存在", ""),
    _60055(60055, "用户名手机号不能同时为空", ""),
    _60058(60058, "验证码错误", ""),
    _60059(60059, "获取Redis验证码异常", ""),
    _60060(60060, "身份证件格式不正确。", ""),
    _60061(60061, "证件有效期格式不正确。", ""),
    _60062(60062,"当前时间不在证件有效期范围内",""),
    _60063(60063,"座机号码格式不正确",""),
    _60064(60064,"性别输入只能为(1:男,2:女,3:保密)",""),







    ;
    /**
     * 键
     */
    @Getter
    private final  Integer code;

    /**
     * 消息(中文)
     */
    @Getter
    private final  String msg;

    /**
     * 消息(英文)
     */
    @Getter
    private final  String enMsg;



}