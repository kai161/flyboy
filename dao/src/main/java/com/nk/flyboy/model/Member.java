package com.nk.flyboy.model;


import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Member implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2348563724146162921L;

    /**
     * 主键标识
     */
    private String id;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private int age;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 手机号认证状态
     */
    private String mobileStatus;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 身份证号认证状态
     */
    private String idCardStatus;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱认证状态
     */
    private String emailStatus;
    /**
     * 推荐人
     */
    private String referrer;
    /**
     * 推广人数
     */
    private int referCount;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 注册来源
     */
    private String client;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 渠道码
     */
    private int channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 交易密码
     */
    private String tradePassword;

    /**
     * 会员自增编号
     */
    private String memberNumber;

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 登录密码是否已更改
     */
    private String pwdChanged;


    /**
     * 开户时间
     */
    private Date openAccountTime;

    /**
     * 首投时间
     */
    private Date firstInvestmentTime;

    /**
     * 是否开通链式传播
     */
    private String chainSaleEnabled;

    /**
     * 开通链式传播时间
     */
    private Date chainSaleEnabledTime;

    /**
     * 系统（0：金联所，1：月供宝）
     */
    private System system;

    /**
     * @return 返回 id
     */

    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param 对realName进行赋值
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return 返回 sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param 对sex进行赋值
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return 返回 age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param 对age进行赋值
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return 返回 mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param 对mobile进行赋值
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 返回 mobileStatus
     */
    public String getMobileStatus() {
        return mobileStatus;
    }

    /**
     * @param 对mobileStatus进行赋值
     */
    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    /**
     * @return 返回 idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param 对idCard进行赋值
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return 返回 idCardStatus
     */
    public String getIdCardStatus() {
        return idCardStatus;
    }

    /**
     * @param 对idCardStatus进行赋值
     */
    public void setIdCardStatus(String idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    /**
     * @return 返回 status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param 对status进行赋值
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 返回 email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param 对email进行赋值
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 返回 emailStatus
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * @param 对emailStatus进行赋值
     */
    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    /**
     * @return 返回 referrer
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * @param 对referrer进行赋值
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    /**
     * @return 返回 referCount
     */
    public int getReferCount() {
        return referCount;
    }

    /**
     * @param 对referCount进行赋值
     */
    public void setReferCount(int referCount) {
        this.referCount = referCount;
    }

    /**
     * @return 返回 province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param 对province进行赋值
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return 返回 city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param 对city进行赋值
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 返回 registerIp
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * @param 对registerIp进行赋值
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * @return 返回 createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param 对createTime进行赋值
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 返回 updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param 对updateTime进行赋值
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 返回 channelCode
     */
    public int getChannelCode() {
        return channelCode;
    }

    /**
     * @param 对channelCode进行赋值
     */
    public void setChannelCode(int channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @return 返回 channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param 对channelName进行赋值
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    /**
     * @return 返回 tradePassword
     */
    public String getTradePassword() {
        return tradePassword;
    }

    /**
     * @param 对tradePassword进行赋值
     */
    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    /**
     * @return 返回 memberNumber
     */
    public String getMemberNumber() {
        return memberNumber;
    }

    /**
     * @param 对memberNumber进行赋值
     */
    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    /**
     * @return 返回 memberType
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * @param 对memberType进行赋值
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    /**
     * @return 返回 pwdChanged
     */
    public String getPwdChanged() {
        return pwdChanged;
    }

    /**
     * @param 对pwdChanged进行赋值
     */
    public void setPwdChanged(String pwdChanged) {
        this.pwdChanged = pwdChanged;
    }

    /**
     * @return 返回 client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param 对client进行赋值
     */
    public void setClient(String client) {
        this.client = client;
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Date getFirstInvestmentTime() {
        return firstInvestmentTime;
    }

    public void setFirstInvestmentTime(Date firstInvestmentTime) {
        this.firstInvestmentTime = firstInvestmentTime;
    }

    public String getChainSaleEnabled() {
        return chainSaleEnabled;
    }

    public void setChainSaleEnabled(String chainSaleEnabled) {
        this.chainSaleEnabled = chainSaleEnabled;
    }

    public Date getChainSaleEnabledTime() {
        return chainSaleEnabledTime;
    }

    public void setChainSaleEnabledTime(Date chainSaleEnabledTime) {
        this.chainSaleEnabledTime = chainSaleEnabledTime;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }


}