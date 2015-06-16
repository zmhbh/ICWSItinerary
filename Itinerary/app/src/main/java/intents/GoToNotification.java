package intents;

import android.content.Context;
import android.content.Intent;

import icws.itinerary.NotificationActivity;

/**
 * Created by zmhbh on 6/15/15.
 */
public class GoToNotification implements ClickInterface{

    public GoToNotification(Context packageContext,Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, NotificationActivity.class);
        packageContext.startActivity(intent);
    }
}
