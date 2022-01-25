package com.loan.vvver.data.body;

public class UploadBankInFoBody {

    /**
     * userName : CASH-AND1
     * userId : 4
     * userCardId : 12321321321231123213232
     * bankbs : BASD-AA
     * cipher : 6c9899e16af180131997a9be1b6c5511
     * bankName : qwer
     * branchName : qwer1
     */

    //    姓名
    private String userName;

    private String userId;
    //    银行卡号
    private String userCardId;
    //    银行编码
    private String bankbs;
    //
    private String cipher;
    //    银行名称
    private String bankName;
    //    银行支行
    private String branchName;

    public UploadBankInFoBody(String userName, String userId, String userCardId, String bankbs, String cipher, String bankName, String branchName) {
        this.userName = userName;
        this.userId = userId;
        this.userCardId = userCardId;
        this.bankbs = bankbs;
        this.cipher = cipher;
        this.bankName = bankName;
        this.branchName = branchName;
    }
}
