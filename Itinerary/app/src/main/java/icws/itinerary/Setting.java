package icws.itinerary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import database.ExternalDbOpenHelper;
import database.SessionDbAccess;
import intents.ClickInterface;
import intents.IntentFactory;


public class Setting extends Activity {
    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();

    }

   public void goClearItinerary(View v){
       SessionDbAccess sessionDbAccess = new SessionDbAccess(database);
       sessionDbAccess.resetSession();
       Toast toast = Toast.makeText(this, "Your Itinerary has been reset!", Toast.LENGTH_SHORT);
       toast.setGravity(Gravity.END, 105, 50);
       toast.show();
       ClickInterface click = IntentFactory.goToNext(this,Index.class,null,null);
       finish();
   }
}
