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
     * ע������
     */
    private static final long serialVersionUID = 2348563724146162921L;

    /**
     * ������ʶ
     */
    private String id;

    /**
     * ��ʵ����
     */
    private String realName;
    /**
     * �Ա�
     */
    private String sex;
    /**
     * ����
     */
    private int age;
    /**
     * �ֻ���
     */
    private String mobile;
    /**
     * �ֻ�����֤״̬
     */
    private String mobileStatus;
    /**
     * ���֤��
     */
    private String idCard;
    /**
     * ���֤����֤״̬
     */
    private String idCardStatus;
    /**
     * �û�״̬
     */
    private String status;
    /**
     * ����
     */
    private String email;
    /**
     * ������֤״̬
     */
    private String emailStatus;
    /**
     * �Ƽ���
     */
    private String referrer;
    /**
     * �ƹ�����
     */
    private int referCount;
    /**
     * ʡ��
     */
    private String province;
    /**
     * ����
     */
    private String city;
    /**
     * ע��IP
     */
    private String registerIp;

    /**
     * ע����Դ
     */
    private String client;

    /**
     * ����ʱ��
     */
    private Date createTime;
    /**
     * ����ʱ��
     */
    private Date updateTime;

    /**
     * ������
     */
    private int channelCode;

    /**
     * ��������
     */
    private String channelName;

    /**
     * ��������
     */
    private String tradePassword;

    /**
     * ��Ա�������
     */
    private String memberNumber;

    /**
     * ��Ա����
     */
    private String memberType;

    /**
     * ��¼�����Ƿ��Ѹ���
     */
    private String pwdChanged;


    /**
     * ����ʱ��
     */
    private Date openAccountTime;

    /**
     * ��Ͷʱ��
     */
    private Date firstInvestmentTime;

    /**
     * �Ƿ�ͨ��ʽ����
     */
    private String chainSaleEnabled;

    /**
     * ��ͨ��ʽ����ʱ��
     */
    private Date chainSaleEnabledTime;

    /**
     * ϵͳ��0����������1���¹�����
     */
    private System system;

    /**
     * @return ���� id
     */

    public String getId() {
        return id;
    }

    /**
     * @param ��id���и�ֵ
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return ���� realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param ��realName���и�ֵ
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return ���� sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param ��sex���и�ֵ
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return ���� age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param ��age���и�ֵ
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return ���� mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param ��mobile���и�ֵ
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return ���� mobileStatus
     */
    public String getMobileStatus() {
        return mobileStatus;
    }

    /**
     * @param ��mobileStatus���и�ֵ
     */
    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    /**
     * @return ���� idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param ��idCard���и�ֵ
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return ���� idCardStatus
     */
    public String getIdCardStatus() {
        return idCardStatus;
    }

    /**
     * @param ��idCardStatus���и�ֵ
     */
    public void setIdCardStatus(String idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    /**
     * @return ���� status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param ��status���и�ֵ
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return ���� email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param ��email���и�ֵ
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return ���� emailStatus
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * @param ��emailStatus���и�ֵ
     */
    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    /**
     * @return ���� referrer
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * @param ��referrer���и�ֵ
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    /**
     * @return ���� referCount
     */
    public int getReferCount() {
        return referCount;
    }

    /**
     * @param ��referCount���и�ֵ
     */
    public void setReferCount(int referCount) {
        this.referCount = referCount;
    }

    /**
     * @return ���� province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param ��province���и�ֵ
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return ���� city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param ��city���и�ֵ
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return ���� registerIp
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * @param ��registerIp���и�ֵ
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * @return ���� createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param ��createTime���и�ֵ
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return ���� updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param ��updateTime���и�ֵ
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return ���� channelCode
     */
    public int getChannelCode() {
        return channelCode;
    }

    /**
     * @param ��channelCode���и�ֵ
     */
    public void setChannelCode(int channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @return ���� channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param ��channelName���и�ֵ
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    /**
     * @return ���� tradePassword
     */
    public String getTradePassword() {
        return tradePassword;
    }

    /**
     * @param ��tradePassword���и�ֵ
     */
    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    /**
     * @return ���� memberNumber
     */
    public String getMemberNumber() {
        return memberNumber;
    }

    /**
     * @param ��memberNumber���и�ֵ
     */
    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    /**
     * @return ���� memberType
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * @param ��memberType���и�ֵ
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    /**
     * @return ���� pwdChanged
     */
    public String getPwdChanged() {
        return pwdChanged;
    }

    /**
     * @param ��pwdChanged���и�ֵ
     */
    public void setPwdChanged(String pwdChanged) {
        this.pwdChanged = pwdChanged;
    }

    /**
     * @return ���� client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param ��client���и�ֵ
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