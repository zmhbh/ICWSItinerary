package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by zmhbh on 6/8/15.
 */
public class TimeSlot {
    private String timeSlotName;
    private LinkedHashMap<String, Session> timeSlotContent;

    public TimeSlot(String timeSlotName) {
        this.timeSlotName = timeSlotName;
        timeSlotContent = new LinkedHashMap<String, Session>();
    }


//    public void populateSessionName(String sessionName) {
//        timeSlotContent.put(sessionName, new Session(sessionName));
//    }

    public void populateSession(Session session){
        timeSlotContent.put(session.getSessionName(),session);
    }

    public Session getSession(String sessionName) {
        return timeSlotContent.get(sessionName);
    }

    public Set<Entry<String, Session>> getSessionEntrySet(){
        return timeSlotContent.entrySet();
    }
//    private String timeDetail;
//    private ArrayList<Session> sessionArrayList;
//
//
//
//    public TimeSlot (String timeDetail, ArrayList<Session> sessionArrayList){
//        super();
//        this.timeDetail=timeDetail;
//        this.sessionArrayList=sessionArrayList;
//    }
//
//    public String getTimeDetail() {
//        return timeDetail;
//    }
//
//    public void setTimeDetail(String timeDetail) {
//        this.timeDetail = timeDetail;
//    }
//
//    public ArrayList<Session> getSessionArrayList() {
//        return sessionArrayList;
//    }
//
//    public void setSessionArrayList(ArrayList<Session> sessionArrayList) {
//        this.sessionArrayList = sessionArrayList;
//    }
}
