package model;

/**
 * Created by zmhbh on 6/8/15.
 */
public class Session {
    private String sessionType;
    private String sessionName;
    private String sessionRoomTime;


    public Session (String sessionType, String sessionRoomTime){
        super();

        this.sessionType=sessionType;
        this.sessionRoomTime=sessionRoomTime;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionRoomTime() {
        return sessionRoomTime;
    }

    public void setSessionRoomTime(String sessionRoomTime) {
        this.sessionRoomTime = sessionRoomTime;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

}
