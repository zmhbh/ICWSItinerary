package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by zmhbh on 6/8/15.
 */
public class Program {
    private String programName;
    private LinkedHashMap<String, TimeSlot> programContent;


    public Program(String programName) {
        this.programName = programName;
        programContent = new LinkedHashMap<String, TimeSlot>();
    }

    public void populateTimeSlotName(String timeSlotName) {
        programContent.put(timeSlotName, new TimeSlot(timeSlotName));
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public TimeSlot getTimeSlot(String timeSlotName) {
        return programContent.get(timeSlotName);
    }


    public Set<Entry<String, TimeSlot>> getTimeSlotEntrySet(){
        return programContent.entrySet();
    }

/*private String programDetail;
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
    }*/
}
