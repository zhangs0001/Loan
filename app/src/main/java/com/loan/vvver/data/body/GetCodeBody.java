package com.loan.vvver.data.body;

public class GetCodeBody {

    /**
     * {
     * 	"cellphone" : "0245698234",
     * 	"cipher" : "6c50ab87778046543286658a55f6270a"
     * }
     * */
    private String cellphone;
    private String cipher;

    public GetCodeBody(String cellphone, String cipher) {
        this.cellphone = cellphone;
        this.cipher = cipher;
    }

    @Override
    public String toString() {
        return "GetCodeBody{" +
                "cellphone='" + cellphone + '\'' +
                ", cipher='" + cipher + '\'' +
                '}';
    }
}
