package com.xbhy.workorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * (OrderLog)实体类
 *
 * @author makejava
 * @since 2022-06-28 10:37:34
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLog implements Serializable {
    private static final long serialVersionUID = -51591202592665781L;
    
    private Long id;
    /**
     * 工单ID
     */
    private Long orderId;
    /**
     * 修改前附件
     */
    private String preAttach;
    /**
     * 修改前备注
     */
    private String preRemark;
    /**
     * 修改前对账备注
     */
    private String preAccountRemark;
    /**
     * 修改发生时间
     */
    private Date createTime;
    /**
     * 上次修改时间
     */
    private Date lastUpdateTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改前状态
     */
    private String preStatus;
    /**
     * 修改后状态
     */
    private String status;
    /**
     * 修改前文本
     */
    private String preContent;
    /**
     * 修改后文本
     */
    private String context;
    /**
     * 修改前钱票状态
     */
    private String preTicketStatus;
    /**
     * 修改后钱票状态
     */
    private String ticketStatus;
    /**
     * 修改前输入金额
     */
    private Double preInAmount;
    /**
     * 修改后输入金额
     */
    private Double inAmount;
    /**
     * 修改前到货通知
     */
    private String preArriveNotice;
    /**
     * 修改后到货通知
     */
    private String arriveNotice;
    /**
     * 修改后附件
     */
    private String attach;
    /**
     * 修改后备注
     */
    private String remark;
    /**
     * 修改后对账备注
     */
    private String accountRemark;
    /**
     * 修改人
     */
    private Long createBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPreAttach() {
        return preAttach;
    }

    public void setPreAttach(String preAttach) {
        this.preAttach = preAttach;
    }

    public String getPreRemark() {
        return preRemark;
    }

    public void setPreRemark(String preRemark) {
        this.preRemark = preRemark;
    }

    public String getPreAccountRemark() {
        return preAccountRemark;
    }

    public void setPreAccountRemark(String preAccountRemark) {
        this.preAccountRemark = preAccountRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(String preStatus) {
        this.preStatus = preStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreContent() {
        return preContent;
    }

    public void setPreContent(String preContent) {
        this.preContent = preContent;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPreTicketStatus() {
        return preTicketStatus;
    }

    public void setPreTicketStatus(String preTicketStatus) {
        this.preTicketStatus = preTicketStatus;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Double getPreInAmount() {
        return preInAmount;
    }

    public void setPreInAmount(Double preInAmount) {
        this.preInAmount = preInAmount;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public String getPreArriveNotice() {
        return preArriveNotice;
    }

    public void setPreArriveNotice(String preArriveNotice) {
        this.preArriveNotice = preArriveNotice;
    }

    public String getArriveNotice() {
        return arriveNotice;
    }

    public void setArriveNotice(String arriveNotice) {
        this.arriveNotice = arriveNotice;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

}

