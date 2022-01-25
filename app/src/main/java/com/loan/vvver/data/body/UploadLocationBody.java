package com.loan.vvver.data.body;

public class UploadLocationBody {

    /**
     * userId : 77
     * cipher : 9b7b3104aff1df444c3f7a9ea375c69c
     * downlat : 45,2456
     * downlng : 236,215
     */

    private String userId;
    private String cipher;
    private String downlat;
    private String downlng;

    public UploadLocationBody(String userId, String cipher, String downlat, String downlng) {
        this.userId = userId;
        this.cipher = cipher;
        this.downlat = downlat;
        this.downlng = downlng;
    }
}
