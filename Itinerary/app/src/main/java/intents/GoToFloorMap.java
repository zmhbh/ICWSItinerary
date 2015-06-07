package intents;
import android.content.*;

import icws.itinerary.FloorMap;

/**
 * Created by zmhbh on 6/5/15.
 */
public class GoToFloorMap implements ClickInterface {

    public GoToFloorMap(Context packageContext, Class <?> cl, Object objectOne, Object objectTwo){
        int index = (int) objectOne;
        Intent intent = new Intent();
        intent.setClass(packageContext, FloorMap.class);
        intent.putExtra("floorIndex",index);
        packageContext.startActivity(intent);
    }

}
