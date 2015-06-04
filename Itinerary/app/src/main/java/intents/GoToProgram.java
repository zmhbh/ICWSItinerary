package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.Program;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToProgram implements ClickInterface{
    public GoToProgram(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, Program.class);
        packageContext.startActivity(intent);
    }
}
