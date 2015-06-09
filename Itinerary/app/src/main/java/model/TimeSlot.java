package model;

import java.util.ArrayList;

/**
 * Created by zmhbh on 6/8/15.
 */
public class TimeSlot {
    private String timeDetail;
    private ArrayList<Session> sessionArrayList;

    public TimeSlot (String timeDetail, ArrayList<Session> sessionArrayList){
        super();
        this.timeDetail=timeDetail;
        this.sessionArrayList=sessionArrayList;
    }

    public String getTimeDetail() {
        return timeDetail;
    }

    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }

    public ArrayList<Session> getSessionArrayList() {
        return sessionArrayList;
    }

    public void setSessionArrayList(ArrayList<Session> sessionArrayList) {
        this.sessionArrayList = sessionArrayList;
    }
}
