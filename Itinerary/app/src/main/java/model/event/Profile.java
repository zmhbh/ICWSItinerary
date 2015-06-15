package model.event;

/**
 * Created by zmhbh on 6/15/15.
 */
public class Profile {
    private String fullname;
    private String title;
    private String college;
    private String email;  // unique string

    public Profile(String fullname, String title, String college, String email) {
        this.fullname = fullname;
        this.title = title;
        this.college = college;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
