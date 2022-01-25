package com.loan.vvver.data.body;

import com.loan.vvver.config.AppConstant;

import java.util.List;

public class LoginBody {
    /**
     * {
     * "cellphone" : "18854269536",
     * "cellphoneType" : 1,
     * "cipher" : "47727a1ea58f1c2481a7166925352cb6",
     * "cashotp" : "3887",
     * "installation_source" : "APP_22_01_02",
     * "platform" : "Google",
     * "register_platform" : "Android"
     * }
     */

    private String cellphone;
    private String cellphoneType;
    private String cipher;
    private String cashotp;
    private String installation_source;
    private String platform;
    private String register_platform;
    private String platform1;//手机机型: 	   string    android ios
    private String guestId;// 设备唯一标识   string    android(IMEI)
    private String guestId1;// 设备唯一标识   string    android(IMSI)
    private String deviceRooted;//设备是否刷机 : Integer     0 (no) , 1 (yes)
    private List<AppBean> appInfoList;//手机安装app列表:  Array
    private String appName;//app名
    private String appVersion;//app版本
    private String appPackageName;//app包名

    public LoginBody(String cellphone, String cellphoneType, String cipher, String cashotp,
                     String installation_source,
                     String guestId, String guestId1, List appInfoList, String appName,
                     String appVersion, String appPackageName) {
        this.cellphone = cellphone;
        this.cellphoneType = cellphoneType;
        this.cipher = cipher;
        this.cashotp = cashotp;
        this.installation_source = installation_source;
        this.platform = AppConstant.platform;
        this.register_platform = AppConstant.register_platform;

        this.platform1 = "android";
        this.guestId = guestId;
        this.guestId1 = guestId1;

        this.deviceRooted = "0";
        this.appInfoList = appInfoList;
        this.appName = appName;
        this.appVersion = appVersion;
        this.appPackageName = appPackageName;
    }

    public static class AppBean {
        private String appName;//app名称
        private String appVersion;//app版本
        private String appPackageName;//APP包名
        private String firstInstallTime;//app首次启动时间
        private String lastUpdateTime;//app最近更新时间

        public AppBean(String appName, String appVersion, String appPackageName, String firstInstallTime, String lastUpdateTime) {
            this.appName = appName;
            this.appVersion = appVersion;
            this.appPackageName = appPackageName;
            this.firstInstallTime = firstInstallTime;
            this.lastUpdateTime = lastUpdateTime;
        }

        @Override
        public String toString() {
            return "AppBean{" +
                    "appName='" + appName + '\'' +
                    ", appVersion='" + appVersion + '\'' +
                    ", appPackageName='" + appPackageName + '\'' +
                    ", firstInstallTime='" + firstInstallTime + '\'' +
                    ", lastUpdateTime='" + lastUpdateTime + '\'' +
                    '}';
        }
    }
}
