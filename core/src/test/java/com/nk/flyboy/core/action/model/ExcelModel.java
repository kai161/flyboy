package com.nk.flyboy.core.action.model;

/**
 * Created by kai on 2016/12/22.
 */
public class ExcelModel {

    private String vcardId;
    public String contractId;
    public String amount;

    public String getVcardId() {
        return vcardId;
    }

    public void setVcardId(String vcardId) {
        this.vcardId = vcardId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
