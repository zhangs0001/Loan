package com.loan.vvver.data.body;

public class UpLoadMyInFoBody {
    /**
     * userId : 86
     * numId : 565487944532500
     * addr : 南山后海
     * realName : xiaoliu
     * birthday : 29-12-2021
     * cipher : 313231d92be48eb8c76978c846b8cf5a
     * email : 34234@163.com
     * pOne : 77=1641992240.jpg
     * pTwo : 77=1641992240.jpg
     * pThree : 77=1641992240.jpg
     * sex : 男
     * marriageStatus : NOT_MARRY
     * education : Secondary
     * pannumber : 546879213542
     * panName : panName
     * panFatherName : panFatherName
     */

    private String userId;
    private String numId;
    private String addr;
    private String realName;
    private String birthday;
    private String cipher;
    private String email;
    private String pOne;
    private String pTwo;
    private String pThree;
    private String sex;
    private String marriageStatus;
    private String education;
    private String pannumber;
    private String panName;
    private String panFatherName;
    private String facebookid;

    public UpLoadMyInFoBody(String userId, String numId, String addr, String realName, String birthday, String cipher, String email, String pOne,
                            String pTwo, String pThree, String sex, String marriageStatus, String education, String pannumber, String panName,
                            String panFatherName, String facebookid) {
        this.userId = userId;
        this.numId = numId;
        this.addr = addr;
        this.realName = realName;
        this.birthday = birthday;
        this.cipher = cipher;
        this.email = email;
        this.pOne = pOne;
        this.pTwo = pTwo;
        this.pThree = pThree;
        this.sex = sex;
        this.marriageStatus = marriageStatus;
        this.education = education;
        this.pannumber = pannumber;
        this.panName = panName;
        this.panFatherName = panFatherName;
        this.facebookid = facebookid;
    }
}
