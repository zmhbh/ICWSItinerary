package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zmhbh on 6/13/15.
 */
public class Program_Room {
    private String programName;
    private LinkedHashMap<String, Room> programContent;


    public Program_Room(String programName) {
        this.programName = programName;
        programContent = new LinkedHashMap<String, Room>();
    }

    public void populateRoomName(String roomName) {
        programContent.put(roomName, new Room(roomName));
    }


    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Room getRoom(String roomName) {
        return programContent.get(roomName);
    }


    public Set<Map.Entry<String, Room>> getRoomEntrySet(){
        return programContent.entrySet();
    }

}
