package intents;
import android.content.Context;
import android.content.Intent;

import icws.itinerary.ProgramActivity;

/**
 * Created by zmhbh on 6/4/15.
 */
public class GoToProgram implements ClickInterface{
    public GoToProgram(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, ProgramActivity.class);
        packageContext.startActivity(intent);
    }
}
