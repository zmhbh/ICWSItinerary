package model;

import java.util.ArrayList;

/**
 * Created by zmhbh on 6/8/15.
 */
public class Program {
    private String programDetail;
    private ArrayList<TimeSlot> timeSlotArrayList;

    public Program(String programDetail, ArrayList<TimeSlot> timeSlotArrayList){
        super();
        this.programDetail=programDetail;
        this.timeSlotArrayList=timeSlotArrayList;
    }

    public String getProgramDetail() {
        return programDetail;
    }

    public void setProgramDetail(String programDetail) {
        this.programDetail = programDetail;
    }

    public ArrayList<TimeSlot> getTimeSlotArrayList() {
        return timeSlotArrayList;
    }

    public void setTimeSlotArrayList(ArrayList<TimeSlot> timeSlotArrayList) {
        this.timeSlotArrayList = timeSlotArrayList;
    }
}
