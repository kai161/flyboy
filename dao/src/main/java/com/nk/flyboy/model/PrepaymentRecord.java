package com.nk.flyboy.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created on 2017/7/24.
 */
public class PrepaymentRecord {

    private Long id;

    private Long vcardId;

    private Long userId;

    private String billId;

    private String payId;

    private Integer payChannel;

    private BigDecimal amount;

    private Integer recordType;

    private Date createTime;

    private BigDecimal preVcardAvailable;

    private BigDecimal postVcardAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVcardId() {
        return vcardId;
    }

    public void setVcardId(Long vcardId) {
        this.vcardId = vcardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPreVcardAvailable() {
        return preVcardAvailable;
    }

    public void setPreVcardAvailable(BigDecimal preVcardAvailable) {
        this.preVcardAvailable = preVcardAvailable;
    }

    public BigDecimal getPostVcardAvailable() {
        return postVcardAvailable;
    }

    public void setPostVcardAvailable(BigDecimal postVcardAvailable) {
        this.postVcardAvailable = postVcardAvailable;
    }
}
