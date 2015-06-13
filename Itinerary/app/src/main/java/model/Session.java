package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by zmhbh on 6/8/15.
 */
public class Session implements Serializable {
    private int _id;
    private String sessionName;
    private String sessionRoomTime;
    private String sessionSelected;  // 0 = not selected, 1 = selected
    private String sessionTitle;
    private String sessionChair;
    private LinkedHashMap<String, Paper> sessionContent;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Session(int _id, String sessionName, String sessionRoomTime,
                   String sessionSelected, String sessionTitle, String sessionChair) {
        this._id = _id;
        this.sessionName = sessionName;
        this.sessionRoomTime = sessionRoomTime;
        this.sessionSelected = sessionSelected;
        this.sessionTitle = sessionTitle;
        this.sessionChair = sessionChair;

        sessionContent = new LinkedHashMap<String, Paper>();


    }


    public void populateSession(String sessionTitle, String sessionRoomTime, String sessionChair) {
        this.sessionTitle = sessionTitle;
        this.sessionRoomTime = sessionRoomTime;
        this.sessionChair = sessionChair;
    }

    public String getSessionSelected() {
        return sessionSelected;
    }

    public void setSessionSelected(String sessionSelected) {
        this.sessionSelected = sessionSelected;
    }

    public void populatePaper(Paper paper) {

        sessionContent.put(paper.getUniqueID(), paper);

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

    public Set<Entry<String, Paper>> getPaperEntrySet() {
        return sessionContent.entrySet();
    }


}
