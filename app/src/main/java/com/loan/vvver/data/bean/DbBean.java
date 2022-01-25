package com.loan.vvver.data.bean;

/*
TODO com.numberone.cashone.utils 
TODO Time: 2021/11/24 17:00 
TODO Name: zhang
*/

public class DbBean {
    /**
     * dbTen : qwer
     * dbDienthoai : 123456
     */

    private String dbTen;
    private String dbDienthoai;

    public DbBean(String dbTen, String dbDienthoai) {
        this.dbTen = dbTen;
        this.dbDienthoai = dbDienthoai;
    }

    public String getDbTen() {
        return dbTen;
    }

    public void setDbTen(String dbTen) {
        this.dbTen = dbTen;
    }

    public String getDbDienthoai() {
        return dbDienthoai;
    }

    public void setDbDienthoai(String dbDienthoai) {
        this.dbDienthoai = dbDienthoai;
    }

}
