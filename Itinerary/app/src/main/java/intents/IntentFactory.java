package intents;

import android.content.Context;
import icws.itinerary.Program;
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

        else {
            //
        }

        return onClick;
    }
}
