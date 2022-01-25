package com.loan.vvver.data.body;

public class WorkBody {
    /**
     * {
     * "workName" : "qwer", 公司名称
     * "userid" : "8",
     * "tel" : "0546879546",公司电话
     * "cipher" : "d9eb8a01b97376bd5371e811c5b7cc37",
     * "address" : "qwerqwer",公司地址
     * "position" : "qwer",职位
     * "pay" : "qwer",薪资范围
     * "time" : "qwer",入职时间
     * "industry" : "qwerqwer",行业类型
     * "p1" : "qwer",工作证正面
     * "p2" : "qwer",工作证背面
     * "p3" : "qwer"手持工作证
     * }
     */

    private String workName;
    private String userId;
    private String tel;
    private String cipher;
    private String address;
    private String position;
    private String pay;
    private String time;
    private String industry;
    private String p1;
    private String p2;
    private String p3;

    public WorkBody(String workName, String userId, String tel, String cipher, String address, String position, String pay, String time, String industry, String p1, String p2, String p3) {
        this.workName = workName;
        this.userId = userId;
        this.tel = tel;
        this.cipher = cipher;
        this.address = address;
        this.position = position;
        this.pay = pay;
        this.time = time;
        this.industry = industry;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
