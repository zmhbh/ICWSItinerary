package model.event;

/**
 * Created by zmhbh on 6/15/15.
 */
public class Event {
    private int id; // it is unique generated from remote mysql database
    private String theme;
    private String time;
    private String place;
    private String content;
    private String host_email;

    public Event(int id, String theme, String time, String place, String content, String host_email) {
        this.id = id;
        this.theme = theme;
        this.time = time;
        this.place = place;
        this.content = content;
        this.host_email = host_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost_email() {
        return host_email;
    }

    public void setHost_email(String host_email) {
        this.host_email = host_email;
    }
}
