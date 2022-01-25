package com.loan.vvver.data.body;

public class RePayMentTwoBody {

    /**
     * userId : 55
     * mode : 1
     * cipher : 01278116ae6577eab4afe544234c3c59
     * repaymentMoney : 100
     */

    private String userId;
    private int mode;
    private String cipher;
    private String repaymentMoney;

    public RePayMentTwoBody(String userId, int mode, String cipher, String repaymentMoney) {
        this.userId = userId;
        this.mode = mode;
        this.cipher = cipher;
        this.repaymentMoney = repaymentMoney;
    }
}
