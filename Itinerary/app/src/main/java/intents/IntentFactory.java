package intents;

import android.content.Context;


import icws.itinerary.*;

/**
 * Created by zmhbh on 6/4/15.
 */
public class IntentFactory {
    public static ClickInterface goToNext(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){

        ClickInterface onClick=null;

        if(cl==null){

        }
        else if (cl.equals(Program.class)){
            onClick = new GoToProgram(packageContext,cl,objectOne, objectTwo);
        }
        else if (cl.equals(Itinerary.class)){
            onClick = new GoToItinerary(packageContext,cl,objectOne, objectTwo);
        }
        else if (cl.equals(ICWSInfo.class)){
            onClick = new GoToICWSInfo(packageContext,cl,objectOne,objectTwo);
        }
        else if (cl.equals(VenueMap.class)){
            onClick = new GoToVenueMap(packageContext,cl,objectOne,objectTwo);
        }
        else if (cl.equals(CityMap.class)){
            onClick = new GoToCityMap(packageContext,cl,objectOne,objectTwo);
        }
        else if (cl.equals(PostEvent.class)){
            onClick = new GoToPostEvent(packageContext,cl,objectOne,objectTwo);
        }

        else {
            //
        }

        return onClick;
    }
}