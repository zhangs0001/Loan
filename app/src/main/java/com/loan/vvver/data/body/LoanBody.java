package com.loan.vvver.data.body;

import java.io.Serializable;

public class LoanBody implements Serializable {
    /**
     * userId : 30
     * cipher : a205c8efbc16179a83a87577209bb6ec
     * borrMoney : 1000.000
     * borrDate : 180
     */

    private String userId;
    private String cipher;
    private String borrMoney;
    private String borrDate;

    public LoanBody(String userId, String cipher, String borrMoney, String borrDate) {
        this.userId = userId;
        this.cipher = cipher;
        this.borrMoney = borrMoney;
        this.borrDate = borrDate;
    }
}
