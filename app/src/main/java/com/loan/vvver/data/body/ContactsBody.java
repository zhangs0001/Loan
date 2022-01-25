package com.loan.vvver.data.body;

import java.util.List;

public class ContactsBody {

    /**
     * userId : 1
     * cipher : 3725f1a8e8f57e6540dd44bdf31ca4c7
     * db : [{"dbTen":"qwer1","dbDienthoai":"123456"},{"dbTen":"qwer4","dbDienthoai":"123456"}]
     * contacts1 : [{"dbTen":"qwer1","dbDienthoai":"123456"},{"dbTen":"qwer4","dbDienthoai":"123456"}]
     * sms : [{"dbTen":"qwer1","dbDienthoai":"123456"},{"dbTen":"qwer4","dbDienthoai":"123456"}]
     */

    private String userId;
    private String cipher;
    private List<DbBean> db;
    private List<Contacts1Bean> contacts1;
    private List<SmsBean> sms;

    public ContactsBody(String userId, String cipher, List<DbBean> db, List<Contacts1Bean> contacts1, List<SmsBean> sms) {
        this.userId = userId;
        this.cipher = cipher;
        this.db = db;
        this.contacts1 = contacts1;
        this.sms = sms;
    }

    public static class DbBean {
        /**
         * dbTen : qwer1
         * dbDienthoai : 123456
         */

        private String dbTen;
        private String dbDienthoai;

        public DbBean() {
        }

        public DbBean(String dbTen, String dbDienthoai) {
            this.dbTen = dbTen;
            this.dbDienthoai = dbDienthoai;
        }
    }

    public static class Contacts1Bean {
        /**
         * dbTen : qwer1
         * dbDienthoai : 123456
         */

        private String type;//dttype   DIAL-主叫，DIALED-被叫
        private String name;//姓名
        private String number;//手机号
        private String callDuration;//通话时长
        private String callDateStr;//通话时间


        public Contacts1Bean(String type, String name, String number, String callDuration, String callDateStr) {
            this.type = type;
            this.name = name;
            this.number = number;
            this.callDuration = callDuration;
            this.callDateStr = callDateStr;
        }

        @Override
        public String toString() {
            return "Contacts1Bean{" +
                    "dttype='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", number='" + number + '\'' +
                    ", callDuration='" + callDuration + '\'' +
                    ", callDateStr='" + callDateStr + '\'' +
                    '}';
        }
    }

    public static class SmsBean {
        /**
         * dbTen : qwer1
         * dbDienthoai : 123456
         */

        private String type;//短信状态
        private String phone;//电话号码
        private String person;//姓名
        private String content;//短信内容
        private String smstime;//短信时间

        public SmsBean(String type, String phone, String person, String content, String smstime) {
            this.type = type;
            this.phone = phone;
            this.person = person;
            this.content = content;
            this.smstime = smstime;
        }

        @Override
        public String toString() {
            return "SmsBean{" +
                    "type='" + type + '\'' +
                    ", phone='" + phone + '\'' +
                    ", person='" + person + '\'' +
                    ", content='" + content + '\'' +
                    ", sms_time='" + smstime + '\'' +
                    '}';
        }
    }
}
