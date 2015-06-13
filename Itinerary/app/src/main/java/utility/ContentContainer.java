package utility;

import android.content.ContentValues;
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

    private static LinkedHashMap<String, Program_Room> programContainer_room = new LinkedHashMap<String, Program_Room>();

    private SQLiteDatabase database;


    public ContentContainer(SQLiteDatabase database) {
        this.database = database;
    }


    // test
    // in the future, it is used for sqlite database
    public void populateContainer() {

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

    /*
    for room view
     */

    public void populateContainer_room() {
        Cursor cursor_program, cursor_room, cursor_session, cursor_paper;
        String tempProgramName;
        String tempRoomName;
        String tempSessionName;
        //1. get all program names
        cursor_program = database.query(
                ExternalDbOpenHelper.PROGRAM_TABLE,
                new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN},
                null, null, null, null, null);
        while (cursor_program.moveToNext()) {

            tempProgramName = cursor_program.getString(1);
            populateProgramName_room(tempProgramName);
            //2. get all timeslot names
            cursor_room = database.query(
                    ExternalDbOpenHelper.ROOM_TABLE,
                    new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN},
                    "program_id=?",
                    new String[]{cursor_program.getString(0)},
                    null, null, null);

            while (cursor_room.moveToNext()) {
                tempRoomName = cursor_room.getString(1);
                populateRoomName(tempProgramName, tempRoomName);

                //3. get all sessions
                cursor_session = database.query(
                        ExternalDbOpenHelper.ROOMSESSION_TABLE,
                        new String[]{ExternalDbOpenHelper.ID_COLUMN, ExternalDbOpenHelper.VALUE_COLUMN, ExternalDbOpenHelper.ROOMSESSION_TIMESLOT,
                                ExternalDbOpenHelper.ROOMSESSION_SELECTED, ExternalDbOpenHelper.ROOMSESSION_SESSION_TITLE, ExternalDbOpenHelper.ROOMSESSION_SESSION_CHAIR,
                                ExternalDbOpenHelper.ROOMSESSION_SESSION_PAPERS},
                        "room_id=?",
                        new String[]{cursor_room.getString(0)},
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
                    populateSession_room(tempProgramName, tempRoomName, session);

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
                                populatePaper_room(tempProgramName, tempRoomName, tempSessionName, paper);
                            }
                            cursor_paper.close();
                        }
                    }


                }
                cursor_session.close();

            }
            cursor_room.close();

        }
        cursor_program.close();
    }


    public void updateSessionSelected(int _id, String isSelected) {
        ContentValues values = new ContentValues();
        values.put(ExternalDbOpenHelper.SESSION_SELECTED, isSelected);
        long result = database.update(ExternalDbOpenHelper.SESSION_TABLE, values,
                "_id = ?",
                new String[]{String.valueOf(_id)});
    }

    public void updateSessionSelected_room(int _id, String isSelected) {
        ContentValues values = new ContentValues();
        values.put(ExternalDbOpenHelper.ROOMSESSION_SELECTED, isSelected);
        long result = database.update(ExternalDbOpenHelper.ROOMSESSION_TABLE, values,
                "_id = ?",
                new String[]{String.valueOf(_id)});
    }

    public void updateViewSelected(String view){
        ContentValues values = new ContentValues();
        values.put(ExternalDbOpenHelper.VALUE_COLUMN,view);
        long result = database.update(ExternalDbOpenHelper.VIEW_TABLE,values,
                "_id = ?",
                new String[]{"1"});
    }

    public String getViewSelected(){
        Cursor cursor;
        cursor = database.query(ExternalDbOpenHelper.VIEW_TABLE,
                new String[]{ExternalDbOpenHelper.VALUE_COLUMN},
                "_id =?",
                new String[]{"1"},
                null,null,null);

        String view=null;
        while(cursor.moveToNext()){
            view= cursor.getString(0);
        }
        return view;
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

    ///////////////////////////////////////////////////////////////////////////////
    /*
    for view by room
     */
    public void populateProgramName_room(String programName) {
        programContainer_room.put(programName, new Program_Room(programName));
    }

    public void populateRoomName(String programName, String roomName) {
        Program_Room program_room = programContainer_room.get(programName);
        program_room.populateRoomName(roomName);
    }

    public void populateSession_room(String programName, String roomName, Session session) {
        Room room = programContainer_room.get(programName).getRoom(roomName);
        room.populateSession(session);
    }

    public void populatePaper_room(String programName, String roomName, String sessionName, Paper paper) {
        Session session = programContainer_room.get(programName).getRoom(roomName).getSession(sessionName);
        session.populatePaper(paper);
    }


//    public static LinkedHashMap<String, Program> getProgramContainer() {
//        return programContainer;
//    }
//


    public Set<Entry<String, Program>> getProgramEntrySet() {
        return programContainer.entrySet();
    }

    public Set<Entry<String, Program_Room>> getProgram_RoomEntrySet() {
        return programContainer_room.entrySet();
    }


}

