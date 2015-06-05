package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.ICWSInfo;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToICWSInfo implements ClickInterface{
    public GoToICWSInfo(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo ){
        Intent intent=new Intent();
        intent.setClass(packageContext, ICWSInfo.class);
        packageContext.startActivity(intent);
    }
}
