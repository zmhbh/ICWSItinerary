package icws.itinerary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import intents.ClickInterface;
import intents.IntentFactory;

public class VenueMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venue_map, menu);
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
    // show third floor map
    public void goThirdFloor(View v){
        ClickInterface click = IntentFactory.goToNext(this, FloorMap.class, 3, null);
    }

    // show fourth floor map
    public void goFourthFloor(View v){
        ClickInterface click = IntentFactory.goToNext(this, FloorMap.class,4,null);
    }

    // show Seventh floor map
    public void goSeventhFloor(View v){
        ClickInterface click = IntentFactory.goToNext(this, FloorMap.class,7,null);
    }

}
