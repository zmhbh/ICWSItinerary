package intents;

import android.content.*;

import icws.itinerary.SessionDetail;
import icws.itinerary.VenueMap;

/**
 * Created by zmhbh on 6/9/15.
 */
public class GoToSessionDetail implements ClickInterface{
    public GoToSessionDetail(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo) {
        Intent intent = new Intent();
        intent.setClass(packageContext, SessionDetail.class);
        packageContext.startActivity(intent);
    }

}
