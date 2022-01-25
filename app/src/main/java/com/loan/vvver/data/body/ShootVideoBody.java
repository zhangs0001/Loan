package com.loan.vvver.data.body;

public class ShootVideoBody {

    /**
     * userId : 77
     * videoUrl : hkpz
     * cipher : 9b7b3104aff1df444c3f7a9ea375c69c
     */

    private String userId;
    private String videoUrl;
    private String cipher;

    public ShootVideoBody(String userId, String videoUrl, String cipher) {
        this.userId = userId;
        this.videoUrl = videoUrl;
        this.cipher = cipher;
    }
}
