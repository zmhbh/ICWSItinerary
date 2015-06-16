package intents;

import android.content.Context;
import android.content.Intent;

import icws.itinerary.EventPost;
import icws.itinerary.PostNotification;

/**
 * Created by zmhbh on 6/15/15.
 */
public class GoToPostNotification implements ClickInterface {

    public GoToPostNotification(Context packageContext,Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, PostNotification.class);
        packageContext.startActivity(intent);
    }
}
