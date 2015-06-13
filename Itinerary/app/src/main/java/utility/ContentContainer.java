package utility;

import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.LinkedHashMap;

import database.ExternalDbOpenHelper;
import model.*;

import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;
import android.database.Cursor;

/**
 * Created by zmhbh on 6/10/15.
 */
public abstract class ContentContainer {

    private static LinkedHashMap<String, Program> programContainer = new LinkedHashMap<String, Program>();

    private SQLiteDatabase database;


    public ContentContainer(SQLiteDatabase database) {
        this.database = database;
    }


    // test
    // in the future, it is used for sqlite database
    public void populateContainer() {
/*        String[] programName = {"Saturday, June 27, 2015", "Sunday, June 28, 2015", "Monday, June 29, 2015"};
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
        }*/


        Cursor cursor_program, cursor_timeSlot, cursor_session, cursor_paper;
        String tempProgramName;
        String tempTimeSlotName;
        String tempSessionName;
        //1. get all program names
        cursor_program = database.query(
                ExternalDbOpenHelper.PROGRAM_TABLE,
                new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN},
                null, null, null, null, null);
        while (cursor_program.moveToNext()) {

            tempProgramName = cursor_program.getString(1);
            populateProgramName(tempProgramName);
            //2. get all timeslot names
            cursor_timeSlot = database.query(
                    ExternalDbOpenHelper.TIMESLOT_TABLE,
                    new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN},
                    "program_id=?",
                    new String[]{cursor_program.getString(0)},
                    null, null, null);

            while (cursor_timeSlot.moveToNext()) {
                tempTimeSlotName = cursor_timeSlot.getString(1);
                populateTimeSlotName(tempProgramName, tempTimeSlotName);

                //3. get all sessions
                cursor_session = database.query(
                        ExternalDbOpenHelper.SESSION_TABLE,
                        new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN, ExternalDbOpenHelper.SESSION_ROOM,
                                ExternalDbOpenHelper.SESSION_SELECTED, ExternalDbOpenHelper.SESSION_TITLE, ExternalDbOpenHelper.SESSION_CHAIR,
                                ExternalDbOpenHelper.SESSION_PAPERS},
                        "timeslot_id=?",
                        new String[]{cursor_timeSlot.getString(0)},
                        null, null, null);
                while (cursor_session.moveToNext()) {
                    tempSessionName = cursor_session.getString(1);
                    //store session
                    Session session = new Session(cursor_session.getInt(0),
                            cursor_session.getString(1),
                            cursor_session.getString(2),
                            cursor_session.getString(3),
                            cursor_session.getString(4),
                            cursor_session.getString(5));
                    populateSession(tempProgramName, tempTimeSlotName, session);

                    String papersInfo = cursor_session.getString(6);
                    String paperUniqueID;
                    if ((!papersInfo.equals("0")) && (papersInfo != null)) {
                        //4. get all papers
                        String[] paperIDs = papersInfo.split(",");

                        //trim
                        for (int i = 0; i < paperIDs.length; i++) {
                            paperUniqueID = paperIDs[i].trim();
                            cursor_paper = database.query(
                                    ExternalDbOpenHelper.PAPER_TABLE,
                                    new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.PAPER_UNIQUE_ID, ExternalDbOpenHelper.PAPER_TITLE,
                                            ExternalDbOpenHelper.PAPER_AUTHOR, ExternalDbOpenHelper.PAPER_AFFILIATION,
                                            ExternalDbOpenHelper.PAPER_AUTHOR_WITH_AFFILIATION, ExternalDbOpenHelper.PAPER_ABSTRACT},
                                    "unique_id=?",
                                    new String[]{paperUniqueID},
                                    null, null, null);

                            while (cursor_paper.moveToNext()) {
                                Paper paper = new Paper(cursor_paper.getInt(0),
                                        cursor_paper.getString(1),
                                        cursor_paper.getString(2),
                                        cursor_paper.getString(3),
                                        cursor_paper.getString(4),
                                        cursor_paper.getString(5),
                                        cursor_paper.getString(6));
                                populatePaper(tempProgramName, tempTimeSlotName, tempSessionName, paper);
                            }
                            cursor_paper.close();
                        }
                    }


                }
                cursor_session.close();

            }
            cursor_timeSlot.close();

        }
        cursor_program.close();


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

    public void populateSession(String programName, String timeSlotName, Session session) {
        TimeSlot timeSlot = programContainer.get(programName).getTimeSlot(timeSlotName);
        timeSlot.populateSession(session);
    }

    public void populatePaper(String programName, String timeSlotName, String sessionName, Paper paper) {
        Session session = programContainer.get(programName).getTimeSlot(timeSlotName).getSession(sessionName);
        session.populatePaper(paper);
    }

//    //third, session for each timeslot
//    public void populateSessionName(String programName, String timeSlotName, String sessionName) {
//        TimeSlot timeslot = programContainer.get(programName).getTimeSlot(timeSlotName);
//        timeslot.populateSessionName(sessionName);
//    }

//    // session related
//    public void populateSession(String programName, String timeSlotName, String sessionName// index
//            , String sessionTitle, String sessionTimeRoom, String sessionChair, Paper[] papers) {
//        Session session = programContainer.get(programName).getTimeSlot(timeSlotName).getSession(sessionName);
//        session.populateSession(sessionTitle, sessionTimeRoom, sessionChair);
//        session.populatePapers(papers);
//    }

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

