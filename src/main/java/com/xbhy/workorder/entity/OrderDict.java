package com.xbhy.workorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * (OrderDict)实体类
 *
 * @author 
 * @since 2022-06-28 10:37:31
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDict implements Serializable {
    private static final long serialVersionUID = -10129082614062537L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 工单组别
     */
    private Long orderSortId;

    /**
     * 工单组别标签
     */
    private String sortTag;
    /**
     * 状态枚举
     */
    private String statusDict;
    /**
     * 钱票状态枚举
     */
    private String ticketStatusDict;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Boolean deleted;
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
     * 更新时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderSortId() {
        return orderSortId;
    }

    public void setOrderSortId(Long orderSortId) {
        this.orderSortId = orderSortId;
    }

    public String getStatusDict() {
        return statusDict;
    }

    public void setStatusDict(String statusDict) {
        this.statusDict = statusDict;
    }

    public String getTicketStatusDict() {
        return ticketStatusDict;
    }

    public void setTicketStatusDict(String ticketStatusDict) {
        this.ticketStatusDict = ticketStatusDict;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSortTag() {
        return sortTag;
    }

    public void setSortTag(String sortTag) {
        this.sortTag = sortTag;
    }
}

