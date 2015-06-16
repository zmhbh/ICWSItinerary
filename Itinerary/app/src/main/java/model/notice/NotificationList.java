package model.notice;

import java.util.ArrayList;

/**
 * Created by zmhbh on 6/16/15.
 */
public class NotificationList {

    private ArrayList<Notification> notificationArrayList;

    public NotificationList() {
        notificationArrayList = new ArrayList<Notification>();
    }

    public void insertNotification(Notification notification) {
        notificationArrayList.add(notification);
    }

    public int getNotificationNum() {
        return notificationArrayList.size();
    }

    public Notification getNotification(int index) {
        return notificationArrayList.get(index);
    }
}
