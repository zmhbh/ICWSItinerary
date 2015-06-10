package intents;

import android.content.Context;
import android.content.Intent;

import icws.itinerary.Itinerary;
import icws.itinerary.PaperDetail;

/**
 * Created by zmhbh on 6/9/15.
 */
public class GoToPaperDetail implements ClickInterface{
    public GoToPaperDetail(Context packageContext, Class<?> cl, Object objectOne, Object objectTwo){
        Intent intent=new Intent();
        intent.setClass(packageContext, PaperDetail.class);
        packageContext.startActivity(intent);
    }
}
