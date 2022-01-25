package com.loan.vvver.data.bean;

public class ServiceBean {

    /**
     * ServicePhone : 123456789
     */

    private String ServicePhone;

    public String getServicePhone() {
        return ServicePhone;
    }

    public void setServicePhone(String ServicePhone) {
        this.ServicePhone = ServicePhone;
    }

    @Override
    public String toString() {
        return "ServiceBean{" +
                "ServicePhone='" + ServicePhone + '\'' +
                '}';
    }
}
