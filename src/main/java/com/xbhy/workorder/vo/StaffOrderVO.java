package com.xbhy.workorder.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (StaffOrder)实体类
 *
 * @author makejava
 * @since 2022-06-28 10:37:42
 */
@Data
public class StaffOrderVO implements Serializable {
    private static final long serialVersionUID = -31856756177628467L;
    
    private Long id;
    /**
     * 员工编号
     */
    private Long staffId;
    /**
     * 工单组编号
     */
    private Long orderSortId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Boolean deleted;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 更新者
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getOrderSortId() {
        return orderSortId;
    }

    public void setOrderSortId(Long orderSortId) {
        this.orderSortId = orderSortId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

