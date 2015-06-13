package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zmhbh on 6/8/15.
 */

public class Room{
    private String roomName;
    private LinkedHashMap<String, Session> roomContent;

    public Room(String roomName) {
        this.roomName = roomName;
        roomContent = new LinkedHashMap<String, Session>();
    }



    public void populateSession(Session session){
        roomContent.put(session.getSessionName(),session);
    }

    public Session getSession(String sessionName) {
        return roomContent.get(sessionName);
    }

    public Set<Map.Entry<String, Session>> getSessionEntrySet(){
        return roomContent.entrySet();
    }
}
