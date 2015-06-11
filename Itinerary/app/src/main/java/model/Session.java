package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
/**
 * Created by zmhbh on 6/8/15.
 */
public class Session {
    private String sessionTitle;
    private String sessionName;
    private String sessionRoomTime;
    private String sessionChair;
    private LinkedHashMap<String, Paper> sessionContent;



    public Session(String sessionName) {
        this.sessionName = sessionName;
        sessionContent = new LinkedHashMap<String, Paper>();
    }


    public void populateSession(String sessionTitle, String sessionRoomTime, String sessionChair) {
        this.sessionTitle=sessionTitle;
        this.sessionRoomTime=sessionRoomTime;
        this.sessionChair=sessionChair;
    }

    public void populatePapers(Paper[] papers){
        for(Paper paper:papers) {
            sessionContent.put(paper.getUniqueID(), paper);
        }
    }

    public Session(String sessionTitle, String sessionRoomTime) {
        super();

        this.sessionTitle = sessionTitle;
        this.sessionRoomTime = sessionRoomTime;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
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

    public String getSessionChair() {
        return sessionChair;
    }

    public void setSessionChair(String sessionChair) {
        this.sessionChair = sessionChair;
    }

    public Set<Entry<String, Paper>> getPaperEntrySet(){
        return sessionContent.entrySet();
    }


}
