package com.loan.vvver.data.body;

public class RePayMentOneBody {
    /**
     * userId : 55
     * mode : 1
     * cipher : 01278116ae6577eab4afe544234c3c59
     * repaymentMoney : 100
     */

    private String userId;
    private int mode;
    private String cipher;

    public RePayMentOneBody(String userId, int mode, String cipher) {
        this.userId = userId;
        this.mode = mode;
        this.cipher = cipher;
    }
}
