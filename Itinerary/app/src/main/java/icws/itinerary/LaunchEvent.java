package icws.itinerary;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import database.ProfileDbAccess;
import model.event.ProcessJSONInterface;
import webService.JSONRequest;
import webService.NetworkStatus;

public class LaunchEvent extends Activity implements ProcessJSONInterface{
    //backend
    private final String process_response_filter = "action.launchEvent";
    private BroadcastReceiver receiver;
    // email as a tag
    private String email;

    //EditText
    private EditText editTextEventTheme;
    private EditText editTextTime;
    private EditText editTextPlace;
    private EditText editTextContent;

    private String eventTheme;
    private String eventTime;
    private String eventPlace;
    private String eventContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_event);
        email=getIntent().getStringExtra("email");

        //EditText
        editTextEventTheme = (EditText) findViewById(R.id.editText_eventTheme);
        editTextTime= (EditText) findViewById(R.id.editText_eventTime);
        editTextPlace = (EditText) findViewById(R.id.editText_eventPlace);
        editTextContent = (EditText) findViewById(R.id.editText_eventContent);

        // Register receiver so that this Activity can be notified
        // when the JSON response came back
        //set the receiver filter
        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response = null;
                String responseType = intent.getStringExtra(JSONRequest.IN_MSG);
                if (responseType.trim().equalsIgnoreCase("launchEvent")) {
                    response = intent.getStringExtra(JSONRequest.OUT_MSG);
                    //
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }


    public void goSubmit(View v){

        eventTheme=editTextEventTheme.getText().toString().trim();
        eventTime=editTextTime.getText().toString().trim();
        eventPlace=editTextPlace.getText().toString().trim();
        eventContent=editTextContent.getText().toString().trim();

        askToLaunchEvent();


    }

    //sending...
    //ask to send JSON request
    private void askToLaunchEvent() {
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if (internet) {
            if (eventTheme.isEmpty()) {
                editTextEventTheme.setError("Full name cannot be empty!");
            }
            if (eventTime.isEmpty()) {
                editTextTime.setError("Title cannot be empty!");
            }
            if (eventPlace.isEmpty()) {
                editTextPlace.setError("College cannot be empty!");
            }
            if (eventContent.isEmpty()) {
                editTextContent.setError("Email cannot be empty!");
            }
            if (eventTheme.isEmpty() || eventTime.isEmpty() || eventPlace.isEmpty() || eventContent.isEmpty()) {
                Toast toast = Toast.makeText(this, "Please enter the fullname, title, college and email!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            } else {
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent = new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG, "launchEvent");
                msgIntent.putExtra("eventTheme", eventTheme);
                msgIntent.putExtra("eventTime", eventTime);
                msgIntent.putExtra("eventPlace", eventPlace);
                msgIntent.putExtra("eventContent", eventContent);
                msgIntent.putExtra("process_response_filter", process_response_filter);
                startService(msgIntent);
            }


        }
    }

    @Override
    public void processJsonResponse(String response) {
        JSONObject responseObj = null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success = responseObj.getBoolean("success");
            if (success) {
                Toast toast = Toast.makeText(this, "launching event is successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();


                Intent intent = new Intent(this, MyEvent.class);
                startActivity(intent);
                finish();

            } else {
                Toast toast = Toast.makeText(this, "Launching event failure, maybe internet connection problem, Please try again!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
