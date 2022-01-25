package com.loan.vvver.data.bean;

public class OwnInFoBean {

    /**
     * cardNo : 52154876481613184346468
     * sex :
     * mobilePhone : 12548764325
     * bankname : 平安银行
     * age : 11-01-2022
     * cellPhone :
     * realname : 姗姗
     */

    private String cardNo;
    private String sex;
    private String mobilePhone;
    private String bankname;
    private String age;
    private String cellPhone;
    private String realname;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Override
    public String toString() {
        return "OwnInFoBean{" +
                "cardNo='" + cardNo + '\'' +
                ", sex='" + sex + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", bankname='" + bankname + '\'' +
                ", age='" + age + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", realname='" + realname + '\'' +
                '}';
    }
}
