package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.VenueMap;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToVenueMap implements ClickInterface {
    public GoToVenueMap(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, VenueMap.class);
        packageContext.startActivity(intent);
    }
}
