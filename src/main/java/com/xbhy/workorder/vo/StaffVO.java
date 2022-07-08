package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xbhy.workorder.entity.SysRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Staff)实体类
 *
 * @author 
 * @since 2022-06-28 11:20:18
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffVO implements Serializable {
    private static final long serialVersionUID = 779443444370459476L;
    
    private Long id;
    /**
     * 员工姓名
     */
    private String staffName;
    
    private String passwd;
    /**
     *  身份证号
     */
    private String idCard;
    /**
     * 员工编号
     */
    private String staffNo;
    /**
     * 性别0:女,1:男
     */
    private Integer gender;
    /**
     *  出生年月
     */
    private Date birth;
    /**
     *  手机号
     */
    private String phone;
    /**
     * 状态0:在职,1:离职
     */
    private String status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Boolean deleted;
    /**
     * 登录名
     */
    private String loginName;
    /**
     *  英文名
     */
    private String engName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private Long updateBy;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 最近登录时间
     */
    private Date loginTime;

    private List<SysRole> sysRoleList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }
}

