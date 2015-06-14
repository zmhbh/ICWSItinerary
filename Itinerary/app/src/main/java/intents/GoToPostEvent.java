package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.EventPost;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToPostEvent implements ClickInterface{
    public GoToPostEvent(Context packageContext,Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, EventPost.class);
        packageContext.startActivity(intent);
    }
}
