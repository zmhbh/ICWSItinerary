package icws.itinerary;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.widget.Toast;
import android.widget.TextView;
import model.TouchImageView;

public class FloorMap extends Activity {

    private int floorIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map);

        Intent intent = getIntent();
        floorIndex=intent.getIntExtra("floorIndex",0);
//        Toast.makeText(getApplicationContext(),
//                "get floorIndex:"+floorIndex, Toast.LENGTH_SHORT).show();



        // TouchImageView
        TouchImageView touchImageView = (TouchImageView)findViewById(R.id.floorMap);
        // TextView
        TextView textView = (TextView)findViewById(R.id.textView_floorMap);

        // based on floor index
        String floorName=null;
        int imageResrouce=-1;
        switch (floorIndex){
            case 3:
                floorName="Third Floor";
                imageResrouce=R.drawable.thirdfloor;
                break;

            case 4:
                floorName= "Fourth Floor";
                imageResrouce=R.drawable.fourthfloor;
                break;

            case 7:
                floorName="Seventh Floor";
                imageResrouce=R.drawable.seventhfloor;
                break;
            default:
                break;
        }
        touchImageView.setImageResource(imageResrouce);
        textView.setText(floorName);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_floor_map, menu);
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
}
