package com.loan.vvver.data.bean;

import java.util.List;

public class RepaymentOneBean {

    /**
     * Url :
     * bankList : [{"bankname":"bank name","account":"account  number","cardnumber":"card number"},{},{}]
     * mode : 1
     */

    private String Url;
    private int mode;
    private List<BankListBean> bankList;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<BankListBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankListBean> bankList) {
        this.bankList = bankList;
    }

    public static class BankListBean {
        /**
         * bankname : bank name
         * account : account  number
         * cardnumber : card number
         */

        private String bankname;
        private String account;
        private String cardnumber;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }
    }
}
