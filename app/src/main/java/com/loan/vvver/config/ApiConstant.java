package com.loan.vvver.config;

public class ApiConstant {
    //线上服务器
    public static String BASEURL = "http://test.foretunevn.com/servlet/api/";
    //测试服务器
    //public static String BASEURL = "http://192.168.1.47:8090/servlet/api/";
    //公共配置
    public static String UrlConfig = "AppActionApi?function=";
    //获取验证码
    public static String getverificationcode = UrlConfig + "CashSendOTP";
    //登录
    public static String login = UrlConfig + "CashSMSLogin";
    //上传身份证照片
    public static String UploadIDphoto = UrlConfig + "CashSubIdCard";
    //银行信息
    public static String BankInformation = UrlConfig + "CashSubBank";
    //上传紧急联系人信息
    public static String UploadContactInformation = UrlConfig + "CashSubContact";
    //上传通讯录信息
    public static String UploadAddressBookInformation = UrlConfig + "GetAddressBook";
    //提交借款信息
    public static String SubmitLoanInformation = UrlConfig + "CashSubBorrMsg";
    //HomePage
    public static String HomePage = UrlConfig + "CashHomePage";
    //是否完善信息
    public static String IsCertification = UrlConfig + "IsCertification";
    //个人贷款列表
    public static String MyLoan = UrlConfig + "AmountPeriod";
    //个人信息
    public static String OwnInFo = UrlConfig + "SearchUserInfo";
    //OSS请求参数
    public static String OSSParameter = UrlConfig + "CashConfigInfo";
    //上传工作信息
    public static String UploadWorkInFo = UrlConfig + "CashWorkInfo";
    //客服
    public static String CustomerService = UrlConfig + "ContactCustomerService";
    //还款接口
    public static String RePayMent = UrlConfig + "CashRepaymentLoan";
    //提交还款凭证
    public static String certificate = UrlConfig + "RefundMoney";
    //上传视频
    public static String ShootVideo = UrlConfig + "CashVideoUrlCheck";
    //上传位置信息
    public static String uploadlocation = UrlConfig + "CashUserIpCheck";
    //首页banner
    public static String HomeBanner = UrlConfig + "Carousel";

//    public String XZ(int position) {
//        switch (position) {
//            case 1:
//                return UrlConfig + "CashSendOTP";
//            case 2:
//                return "CashSendOTP";
//            case 3:
//                return "CashSendOTP";
//            case 4:
//                return "CashSendOTP";
//        }
//        return null;
//    }
}
