package com.xbhy.workorder.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (OrderInfo)实体类
 *
 * @author 
 * @since 2022-06-28 10:37:33
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "工单信息",description = "工单信息")
public class OrderInfoVO implements Serializable {
    private static final long serialVersionUID = -69188880550575803L;
    /**
     * 序号
     */
    private Long id;
    /**
     * 工单号
     */
    private String orderNo;
    /**
     * 不够完整位数的工单号(用于模糊搜索)
     */
    private String orderNoPrefix;
    /**
     * 标签(公共颜色:1,个人颜色:2)
     */
    private String orderTag;

    /**
     * 搜索框内容
     */
    private String searchContent;
    /**
     * 工单内容文本
     */
    private String content;

    /**
     * 文本关键字列表
     */
    private List<List<String>> contentList;

    /**
     * 备注关键字列表
     */
    private List<List<String>> remarkList;
    /**
     * 发货文本
     */
    private String sendContent;
    /**
     * 工单状态
     */
    private String status;
    /**
     * 钱票状态
     */
    private String ticketStatus;
    /**
     * 输入金额
     */
    private Double inAmount;
    /**
     * 到货通知
     */
    private String arriveNotice;
    /**
     * 创建时间 
     */
    private Date createTime;

    /**
     * 创建时间开始(用于检索)
     */
    private Date createTimeBegin;
    /**
     * 创建时间结束(用于检索)
     */
    private Date createTimeEnd;
    /**
     * 对账备注
     */
    private String accountRemark;
    /**
     * 工单备注
     */
    private String remark;
    /**
     * 附件图片
     */
    private String pics;
    /**
     * 工单组别有发货组、开票和资料组、订货组、订货组、打款组、进货组、价格组
     */
    private Integer orderSort;
    /**
     * 是否删除：0=可用(默认)1=已删除
     */
    private Integer deleted;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     *创建人(检索用)
     */
    private String creators;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTag() {
        return orderTag;
    }

    public void setOrderTag(String orderTag) {
        this.orderTag = orderTag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public String getArriveNotice() {
        return arriveNotice;
    }

    public void setArriveNotice(String arriveNotice) {
        this.arriveNotice = arriveNotice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
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

