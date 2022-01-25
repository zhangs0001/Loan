package com.loan.vvver.data.bean;

public class GetCodeBean {

    /**
     * recivePhone : r1d7ZjpmSV7xYMZA5vabCg==
     * random : 1978
     * randomCode : lm9JUzdUcVw=
     */

    private String recivePhone;
    private String random;
    private String randomCode;

    public String getRecivePhone() {
        return recivePhone;
    }

    public void setRecivePhone(String recivePhone) {
        this.recivePhone = recivePhone;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public String toString() {
        return "GetCodeBean{" +
                "recivePhone='" + recivePhone + '\'' +
                ", random='" + random + '\'' +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
