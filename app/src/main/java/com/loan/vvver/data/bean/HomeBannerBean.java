package com.loan.vvver.data.bean;

public class HomeBannerBean {

    /**
     * pic1 : https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban1.png
     * pic3 : https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban3.png
     * pic2 : https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban2.png
     */

    private String pic1;
    private String pic3;
    private String pic2;

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    @Override
    public String toString() {
        return "HomeBannerBean{" +
                "pic1='" + pic1 + '\'' +
                ", pic3='" + pic3 + '\'' +
                ", pic2='" + pic2 + '\'' +
                '}';
    }
}
