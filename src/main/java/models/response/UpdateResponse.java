package models.response;

import java.util.Date;

public class UpdateResponse {
    private String msgType;
    private Date date;
    private String htmlDescription;

    public String getMsgType() {
        return msgType;
    }

    public Date getDate() {
        return date;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }
}
