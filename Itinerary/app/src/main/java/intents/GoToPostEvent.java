package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.PostEvent;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToPostEvent implements ClickInterface{
    public GoToPostEvent(Context packageContext,Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, PostEvent.class);
        packageContext.startActivity(intent);
    }
}
