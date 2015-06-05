package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.CityMap;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToCityMap implements ClickInterface {
    public GoToCityMap(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, CityMap.class);
        packageContext.startActivity(intent);
    }
}
