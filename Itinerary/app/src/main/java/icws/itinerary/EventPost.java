package icws.itinerary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import org.w3c.dom.Text;

import database.ExternalDbOpenHelper;
import database.ProfileDbAccess;
import android.widget.TextView;

public class EventPost extends Activity {

    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;

    private String profileFullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);
        setTitle("Event Post");
        //TextView
        TextView textViewFullname= (TextView) findViewById(R.id.textView_eventFullname);

        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();


        ProfileDbAccess profileDbAccess = new ProfileDbAccess(database);
        profileFullname=profileDbAccess.getProfileFullname();
        if(profileFullname==null) {
            Intent intent = new Intent(this, SubmitProfile.class);
            startActivity(intent);
        }
        // set the fullname in event post
        textViewFullname.setText(profileFullname);
    }

    @Override
    protected void onStop() {
        super.onStop();
        externalDbOpenHelper.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_event, menu);
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

    // go Launch Event
    public void  goLaunchEvent(View v){

        Intent intent= new Intent(this,LaunchEvent.class);
        startActivity(intent);
    }

    // go My Event
    public void goMyEvent(View v){
        Intent intent = new Intent(this,MyEvent.class);
        startActivity(intent);

    }
}
