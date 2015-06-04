package icws.itinerary;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import intents.ClickInterface;
import intents.IntentFactory;


public class Index extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
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
        ClickInterface click = IntentFactory.goToNext(this, Program.class,null,null);
    }
    // click itinerary button
    public void goItinerary(View v){

    }
    // click icws info button
    public void goICWSInfo(View v){

    }
    // click venue map button
    public void goVenueMap(View v){

    }
    // click city map button
    public void goCityMap(View v){

    }
    //click post event button
    public void goPostEvent(View v){

    }
    //click setting button
    public void goSetting (View v){

    }
}
