package utility;

import java.util.LinkedHashMap;

import model.*;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by zmhbh on 6/10/15.
 */
public abstract class ContentContainer {

    private static LinkedHashMap<String, Program> programContainer = new LinkedHashMap<String, Program>();

    // test
    // in the future, it is used for sqlite database
    public void populateContainer() {
        String[] programName = {"Saturday, June 27, 2015", "Sunday, June 28, 2015", "Monday, June 29, 2015"};
        String[] timeSlotName_Saturday = {"9:00-10:00", "10:00-10:15", "10:15-11:15", "11:15-11:20", "11:20-12:20"};
        String[] timeSlotName_Sunday = {"8:15-9:15", "9:15-9:40", "9:40-11:30", "11:30-12:30", "12:30-13:30", "13:30-14:40"};
        String[] timeSlotName_Monday = {"8:00-9:00", "9:00-9:30", "9:30-11:20", "11:20-12:20", "12:20-13:20", "13:20-14:30"};
        LinkedHashMap<String, String[]> secondLevel = new LinkedHashMap<String, String[]>();
        secondLevel.put(programName[0], timeSlotName_Saturday);
        secondLevel.put(programName[1], timeSlotName_Sunday);
        secondLevel.put(programName[2], timeSlotName_Monday);

        //1. populate program
        for (int i = 0; i < programName.length; i++) {
            populateProgramName(programName[i]);
            //2. populate timeSlot
            for (int j = 0; j < secondLevel.get(programName[i]).length; j++) {
            populateTimeSlotName(programName[i],secondLevel.get(programName[i])[j]);

                for(int k=0;k<7;k++){
                    populateSessionName(programName[i],secondLevel.get(programName[i])[j],String.valueOf(k));
                }
            }
        }


    }

    //Multiple Level population-- program, timeslot
    //first, program
    public void populateProgramName(String programName) {
        programContainer.put(programName, new Program(programName));
    }

    //second, timeslot for each program
    public void populateTimeSlotName(String programName, String timeSlotName) {
        Program program = programContainer.get(programName);
        program.populateTimeSlotName(timeSlotName);
    }

    //third, session for each timeslot
    public void populateSessionName(String programName, String timeSlotName, String sessionName) {
        TimeSlot timeslot = programContainer.get(programName).getTimeSlot(timeSlotName);
        timeslot.populateSessionName(sessionName);
    }

    // session related
    public void populateSession(String programName, String timeSlotName, String sessionName// index
            , String sessionTitle, String sessionTimeRoom, String sessionChair, Paper[] papers) {
        Session session = programContainer.get(programName).getTimeSlot(timeSlotName).getSession(sessionName);
        session.populateSession(sessionTitle, sessionTimeRoom, sessionChair);
        session.populatePapers(papers);
    }

//
//    public void populateProgram(Program program) {
//        String programName = program.getProgramName();
//        programContainer.put(programName, program);
//
//
//    }

    public static LinkedHashMap<String, Program> getProgramContainer() {
        return programContainer;
    }


    public Set<Entry<String, Program>> getProgramEntrySet() {
        return programContainer.entrySet();
    }


}

