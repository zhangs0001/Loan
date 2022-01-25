package com.loan.vvver.data.bean;

public class RepaymentTwoBean {

    /**
     * Url : http://api.glob-pay.com/m?orderNo=GBI111194316264
     * mode : 2
     */

    private String Url;
    private int mode;

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

    @Override
    public String toString() {
        return "RepaymentBean{" +
                "Url='" + Url + '\'' +
                ", mode=" + mode +
                '}';
    }
}
