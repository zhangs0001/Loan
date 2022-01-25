package com.loan.vvver.data.body;

public class UploadDocumentsBody {
    /*
     * {
     * 	"userId" : "1",
     * 	"numId" : "546879546215",
     * 	"addr" : "qwer",
     * 	"birthday" : "29-12-2021",
     * 	"cipher" : "3725f1a8e8f57e6540dd44bdf31ca4c7",
     * 	"pOne" : "身份证正面",
     * 	"pTwo" : "反面",
     * 	"pThree" : "合照"
     * }
     * */

    private String userId;
    private String numId;
    private String addr;
    private String birthday;
    private String cipher;
    private String pOne;
    private String pTwo;
    private String pThree;

    public UploadDocumentsBody(String userId, String numId, String addr, String birthday, String cipher, String pOne, String pTwo, String pThree) {
        this.userId = userId;
        this.numId = numId;
        this.addr = addr;
        this.birthday = birthday;
        this.cipher = cipher;
        this.pOne = pOne;
        this.pTwo = pTwo;
        this.pThree = pThree;
    }
}
