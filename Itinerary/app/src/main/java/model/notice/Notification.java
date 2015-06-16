package model.notice;

/**
 * Created by zmhbh on 6/15/15.
 */
public class Notification {
    private String notice;
    private String time;
    private String detail;

    public Notification(String notice, String time, String detail) {
        this.notice = notice;
        this.time = time;
        this.detail = detail;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
