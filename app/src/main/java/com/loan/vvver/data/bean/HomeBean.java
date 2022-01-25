package com.loan.vvver.data.bean;

import java.util.List;

public class HomeBean {

    /**
     * expireTime :
     * policy_url :
     * isConfirmRepaidDoneYet :
     * newOrderDay : 7
     * creditLimit : 10,000
     * isShouldShowRate : 1
     * versionCode : 8
     * isUploadedVideo : 0
     * discountedMoney : 0
     * isLoSuccess : 0
     * cellphone : 18601958380
     * finalMoney : 0
     * notificationinfo : Hi, welcome to 58DONG APP
     * maxMoney : 3,000
     * userName : CASH-AND89
     * loValue : 3,000.00
     * points : {"7":[0.005,0.38]}
     * expiredDay : 0
     * List : [{"money":"3,000.00","day":"7"},{"money":"3,200.00","day":"7"},{"money":"3,400.00","day":"7"},{"money":"3,600.00","day":"7"},{"money":"3,800.00","day":"7"},{"money":"4,000.00","day":"7"}]
     * maxCount : 0
     * iscode : 7
     * finalActualMoney : 0
     * loDate : 7
     * thirtyDayValue : 0.291
     * points3 : 0.38
     * isLoComeToAccount : 0
     * points1 : 7
     * isRepaid : 0
     * logCount : 16
     * points2 : 0.005
     * loanSuccessCount : 0
     * minMoney : 3,000
     * flag : 1
     * expiredState : 1
     * expired30 : 0
     * step3 : {"step3":1}
     * newOrderMoney : 3,000.00
     * step2 : {"step2":1,"idcard":"1254354876451348"}
     * step1 : {"step1":1,"card":"********8464","bankName":"是","bankUserName":"zhangs"}
     * say : 我是CASH-AND89,在**APP借3,000.00钱
     * expired15 : 0
     * step4 : {"step4":1}
     * fifteenDayValue : 0.1955
     * bankList : [{"bankname":"TECHCOMBANK -Chi nhánh Thắng Lợi PGD Bùi Thị Xuân\tBÙI THỊ XUÂN","account":"LUU HUU KIEN","cardnumber":"19036  17915  5015"},{},{}]
     * expiredInterestFee : 0
     */

    private String expireTime;
    private String policy_url;
    private String isConfirmRepaidDoneYet;
    private String newOrderDay;
    private String creditLimit;
    private int isShouldShowRate;
    private int versionCode;
    private String isUploadedVideo;
    private String discountedMoney;
    private String isLoSuccess;
    private String cellphone;
    private String finalMoney;
    private String notificationinfo;
    private String maxMoney;
    private String userName;
    private String loValue;
    private String points;
    private String expiredDay;
    private int maxCount;
    private int iscode;
    private String finalActualMoney;
    private String loDate;
    private String thirtyDayValue;
    private String points3;
    private int isLoComeToAccount;
    private String points1;
    private String isRepaid;
    private int logCount;
    private String points2;
    private int loanSuccessCount;
    private String minMoney;
    private int flag;
    private int expiredState;
    private String expired30;
    private Step3Bean step3;
    private String newOrderMoney;
    private Step2Bean step2;
    private Step1Bean step1;
    private String say;
    private String expired15;
    private Step4Bean step4;
    private String fifteenDayValue;
    private String expiredInterestFee;
    private java.util.List<ListBean> List;
    private java.util.List<BankListBean> bankList;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getPolicy_url() {
        return policy_url;
    }

    public void setPolicy_url(String policy_url) {
        this.policy_url = policy_url;
    }

    public String getIsConfirmRepaidDoneYet() {
        return isConfirmRepaidDoneYet;
    }

    public void setIsConfirmRepaidDoneYet(String isConfirmRepaidDoneYet) {
        this.isConfirmRepaidDoneYet = isConfirmRepaidDoneYet;
    }

    public String getNewOrderDay() {
        return newOrderDay;
    }

    public void setNewOrderDay(String newOrderDay) {
        this.newOrderDay = newOrderDay;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getIsShouldShowRate() {
        return isShouldShowRate;
    }

    public void setIsShouldShowRate(int isShouldShowRate) {
        this.isShouldShowRate = isShouldShowRate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getIsUploadedVideo() {
        return isUploadedVideo;
    }

    public void setIsUploadedVideo(String isUploadedVideo) {
        this.isUploadedVideo = isUploadedVideo;
    }

    public String getDiscountedMoney() {
        return discountedMoney;
    }

    public void setDiscountedMoney(String discountedMoney) {
        this.discountedMoney = discountedMoney;
    }

    public String getIsLoSuccess() {
        return isLoSuccess;
    }

    public void setIsLoSuccess(String isLoSuccess) {
        this.isLoSuccess = isLoSuccess;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(String finalMoney) {
        this.finalMoney = finalMoney;
    }

    public String getNotificationinfo() {
        return notificationinfo;
    }

    public void setNotificationinfo(String notificationinfo) {
        this.notificationinfo = notificationinfo;
    }

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoValue() {
        return loValue;
    }

    public void setLoValue(String loValue) {
        this.loValue = loValue;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getIscode() {
        return iscode;
    }

    public void setIscode(int iscode) {
        this.iscode = iscode;
    }

    public String getFinalActualMoney() {
        return finalActualMoney;
    }

    public void setFinalActualMoney(String finalActualMoney) {
        this.finalActualMoney = finalActualMoney;
    }

    public String getLoDate() {
        return loDate;
    }

    public void setLoDate(String loDate) {
        this.loDate = loDate;
    }

    public String getThirtyDayValue() {
        return thirtyDayValue;
    }

    public void setThirtyDayValue(String thirtyDayValue) {
        this.thirtyDayValue = thirtyDayValue;
    }

    public String getPoints3() {
        return points3;
    }

    public void setPoints3(String points3) {
        this.points3 = points3;
    }

    public int getIsLoComeToAccount() {
        return isLoComeToAccount;
    }

    public void setIsLoComeToAccount(int isLoComeToAccount) {
        this.isLoComeToAccount = isLoComeToAccount;
    }

    public String getPoints1() {
        return points1;
    }

    public void setPoints1(String points1) {
        this.points1 = points1;
    }

    public String getIsRepaid() {
        return isRepaid;
    }

    public void setIsRepaid(String isRepaid) {
        this.isRepaid = isRepaid;
    }

    public int getLogCount() {
        return logCount;
    }

    public void setLogCount(int logCount) {
        this.logCount = logCount;
    }

    public String getPoints2() {
        return points2;
    }

    public void setPoints2(String points2) {
        this.points2 = points2;
    }

    public int getLoanSuccessCount() {
        return loanSuccessCount;
    }

    public void setLoanSuccessCount(int loanSuccessCount) {
        this.loanSuccessCount = loanSuccessCount;
    }

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getExpiredState() {
        return expiredState;
    }

    public void setExpiredState(int expiredState) {
        this.expiredState = expiredState;
    }

    public String getExpired30() {
        return expired30;
    }

    public void setExpired30(String expired30) {
        this.expired30 = expired30;
    }

    public Step3Bean getStep3() {
        return step3;
    }

    public void setStep3(Step3Bean step3) {
        this.step3 = step3;
    }

    public String getNewOrderMoney() {
        return newOrderMoney;
    }

    public void setNewOrderMoney(String newOrderMoney) {
        this.newOrderMoney = newOrderMoney;
    }

    public Step2Bean getStep2() {
        return step2;
    }

    public void setStep2(Step2Bean step2) {
        this.step2 = step2;
    }

    public Step1Bean getStep1() {
        return step1;
    }

    public void setStep1(Step1Bean step1) {
        this.step1 = step1;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public String getExpired15() {
        return expired15;
    }

    public void setExpired15(String expired15) {
        this.expired15 = expired15;
    }

    public Step4Bean getStep4() {
        return step4;
    }

    public void setStep4(Step4Bean step4) {
        this.step4 = step4;
    }

    public String getFifteenDayValue() {
        return fifteenDayValue;
    }

    public void setFifteenDayValue(String fifteenDayValue) {
        this.fifteenDayValue = fifteenDayValue;
    }

    public String getExpiredInterestFee() {
        return expiredInterestFee;
    }

    public void setExpiredInterestFee(String expiredInterestFee) {
        this.expiredInterestFee = expiredInterestFee;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public List<BankListBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankListBean> bankList) {
        this.bankList = bankList;
    }

    public static class Step3Bean {
        /**
         * step3 : 1
         */

        private int step3;

        public int getStep3() {
            return step3;
        }

        public void setStep3(int step3) {
            this.step3 = step3;
        }
    }

    public static class Step2Bean {
        /**
         * step2 : 1
         * idcard : 1254354876451348
         */

        private int step2;
        private String idcard;

        public int getStep2() {
            return step2;
        }

        public void setStep2(int step2) {
            this.step2 = step2;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }
    }

    public static class Step1Bean {
        /**
         * step1 : 1
         * card : ********8464
         * bankName : 是
         * bankUserName : zhangs
         */

        private int step1;
        private String card;
        private String bankName;
        private String bankUserName;

        public int getStep1() {
            return step1;
        }

        public void setStep1(int step1) {
            this.step1 = step1;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankUserName() {
            return bankUserName;
        }

        public void setBankUserName(String bankUserName) {
            this.bankUserName = bankUserName;
        }
    }

    public static class Step4Bean {
        /**
         * step4 : 1
         */

        private int step4;

        public int getStep4() {
            return step4;
        }

        public void setStep4(int step4) {
            this.step4 = step4;
        }
    }

    public static class ListBean {
        /**
         * money : 3,000.00
         * day : 7
         */

        private String money;
        private String day;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }

    public static class BankListBean {
        /**
         * bankname : TECHCOMBANK -Chi nhánh Thắng Lợi PGD Bùi Thị Xuân	BÙI THỊ XUÂN
         * account : LUU HUU KIEN
         * cardnumber : 19036  17915  5015
         */

        private String bankname;
        private String account;
        private String cardnumber;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }
    }
}
