package intents;

import android.content.Context;
import android.content.Intent;

import icws.itinerary.Setting;

/**
 * Created by zmhbh on 6/16/15.
 */
public class GoToSetting implements ClickInterface{
    public GoToSetting(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, Setting.class);
        packageContext.startActivity(intent);
    }
}
