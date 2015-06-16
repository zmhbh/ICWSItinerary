package icws.itinerary;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;

import database.ExternalDbOpenHelper;
import intents.ClickInterface;
import intents.IntentFactory;


public class Index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ExternalDbOpenHelper externalDbOpenHelper = new ExternalDbOpenHelper(this);
        externalDbOpenHelper.createDataBase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //click program button
    public void goProgram(View v){
        ClickInterface click = IntentFactory.goToNext(this, ProgramActivity.class,null,null);
    }
    // click itinerary button
    public void goItinerary(View v){
        ClickInterface click = IntentFactory.goToNext(this, Itinerary.class,null,null);
    }
    // click icws info button
    public void goICWSInfo(View v){
        ClickInterface click = IntentFactory.goToNext(this, ICWSInfo.class,null,null);
    }
    // click venue map button
    public void goVenueMap(View v){
        ClickInterface click = IntentFactory.goToNext(this, VenueMap.class,null,null);
    }
    // click city map button
    public void goCityMap(View v){
        ClickInterface click = IntentFactory.goToNext(this,CityMap.class,null,null);
    }
//    //click post event button
//    public void goPostEvent(View v){
//        ClickInterface click = IntentFactory.goToNext(this, EventPost.class,null,null);
//    }
    public void goPostNotification(View v){
        boolean committeeFlag=true;
        if(committeeFlag){
            ClickInterface click = IntentFactory.goToNext(this,PostNotification.class,null,null);
        }else{
            ClickInterface click = IntentFactory.goToNext(this,NotificationActivity.class,null,null);
        }
    }
    //click setting button
    public void goSetting (View v){

    }
}
