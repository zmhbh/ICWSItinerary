package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.Itinerary;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToItinerary implements ClickInterface {
    public GoToItinerary(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, Itinerary.class);
        packageContext.startActivity(intent);
    }
}
