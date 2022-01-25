package com.loan.vvver.data.body;

public class UrgenPeopleBody {
    /**
     *{
     * 	"userId" : "1",
     * 	"contactOne" : "名称",
     * 	"contactTwo" : "名称",
     * 	"telOne" : "123",
     * 	"telTwo" : "1234",
     * 	"cipher" : "3725f1a8e8f57e6540dd44bdf31ca4c7",
     * 	"relationOne" : "关系",
     * 	"relationTwo" : "关系"
     * }
     * */
    private  String userId;
    private  String contactOne;
    private  String telOne;
    private  String relationOne;
    private  String contactTwo;
    private  String telTwo;
    private  String relationTwo;
    private  String cipher;

    public UrgenPeopleBody(String userId, String contactOne, String telOne, String relationOne, String contactTwo, String telTwo, String relationTwo, String cipher) {
        this.userId = userId;
        this.contactOne = contactOne;
        this.telOne = telOne;
        this.relationOne = relationOne;
        this.contactTwo = contactTwo;
        this.telTwo = telTwo;
        this.relationTwo = relationTwo;
        this.cipher = cipher;
    }
}
