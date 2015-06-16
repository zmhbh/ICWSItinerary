package intents;

import android.content.Context;
import android.content.Intent;

import icws.itinerary.NotificationDetail;
import model.notice.Notification;

/**
 * Created by zmhbh on 6/16/15.
 */
public class GoToNotificationDetail implements ClickInterface {
    public GoToNotificationDetail(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        Notification notification = (Notification) inputOne;
        Intent intent = new Intent();
        intent.setClass(packageContext, NotificationDetail.class);
        intent.putExtra("notice",notification.getNotice());
        intent.putExtra("time",notification.getTime());
        intent.putExtra("detail",notification.getDetail());
        packageContext.startActivity(intent);
    }
}
