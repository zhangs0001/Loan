package com.loan.vvver.data.body;

public class HomeBody {
    /**
     * {
     * "userId" : "8",
     * "cipher" : "85555b843933abd6de8075d182010209"
     * }
     */

    private String userId;
    private String cipher;

    public HomeBody(String userId, String cipher) {
        this.userId = userId;
        this.cipher = cipher;
    }

    @Override
    public String toString() {
        return "HomeBody{" +
                "userId='" + userId + '\'' +
                ", cipher='" + cipher + '\'' +
                '}';
    }
}
