package com.loan.vvver.data.body;

public class CertificateBody {


    /**
     * userId : 43
     * userHkqd : hkpz
     * cipher : 12eca5386354f014d4c581c547f16b19
     */

    private String userId;
    private String userHkqd;
    private String cipher;

    public CertificateBody(String userId, String userHkqd, String cipher) {
        this.userId = userId;
        this.userHkqd = userHkqd;
        this.cipher = cipher;
    }
}
