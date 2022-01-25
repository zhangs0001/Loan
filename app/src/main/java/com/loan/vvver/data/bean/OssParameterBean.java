package com.loan.vvver.data.bean;

public class OssParameterBean {

    /**
     * OSSInfo : {"name":"indv-cash","account":"LTAI5tPRa4XBxAHRkAKeBFys","url":"oss-ap-southeast-1.aliyuncs.com","pass":"IU6uR2v0t9KuXSQRWx9Em0WKfNiJh9"}
     */

    private OSSInfoBean OSSInfo;

    public OSSInfoBean getOSSInfo() {
        return OSSInfo;
    }

    public void setOSSInfo(OSSInfoBean OSSInfo) {
        this.OSSInfo = OSSInfo;
    }

    @Override
    public String toString() {
        return "OssParameterBean{" +
                "OSSInfo=" + OSSInfo +
                '}';
    }

    public static class OSSInfoBean {
        /**
         * name : indv-cash
         * account : LTAI5tPRa4XBxAHRkAKeBFys
         * url : oss-ap-southeast-1.aliyuncs.com
         * pass : IU6uR2v0t9KuXSQRWx9Em0WKfNiJh9
         */

        private String name;
        private String account;
        private String url;
        private String pass;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        @Override
        public String toString() {
            return "OSSInfoBean{" +
                    "name='" + name + '\'' +
                    ", account='" + account + '\'' +
                    ", url='" + url + '\'' +
                    ", pass='" + pass + '\'' +
                    '}';
        }
    }
}
