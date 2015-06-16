package icws.itinerary;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;

import database.ExternalDbOpenHelper;
import database.ProfileDbAccess;
import intents.ClickInterface;
import intents.IntentFactory;
import model.event.ProcessJSONInterface;
import webService.JSONRequest;
import webService.NetworkStatus;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SubmitProfile extends Activity implements ProcessJSONInterface {

    //backend
    private final String process_response_filter = "action.submitProfile";
    private BroadcastReceiver receiver;
    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;
    //EditText
    private EditText editTextFullName;
    private EditText editTextTitle;
    private EditText editTextCollege;
    private EditText editTextEmail;
    //
    private String fullname;
    private String title;
    private String college;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_profile);
        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();


        // EditText
        editTextFullName = (EditText) findViewById(R.id.editText_fullName);
        editTextTitle = (EditText) findViewById(R.id.editText_title);
        editTextCollege = (EditText) findViewById(R.id.editText_college);
        editTextEmail = (EditText) findViewById(R.id.editText_eventContent);


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
                if (responseType.trim().equalsIgnoreCase("submitProfile")) {
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
        externalDbOpenHelper.close();
        unregisterReceiver(receiver);
    }

    public void goSubmit(View v) {
        fullname = editTextFullName.getText().toString().trim();
        title = editTextTitle.getText().toString().trim();
        college = editTextCollege.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();

        askToSubmitProfile();


    }


    //sending...
    //ask to send JSON request
    private void askToSubmitProfile() {
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if (internet) {
            if (fullname.isEmpty()) {
                editTextFullName.setError("Full name cannot be empty!");
            }
            if (title.isEmpty()) {
                editTextTitle.setError("Title cannot be empty!");
            }
            if (college.isEmpty()) {
                editTextCollege.setError("College cannot be empty!");
            }
            if (email.isEmpty()) {
                editTextEmail.setError("Email cannot be empty!");
            }
            if (fullname.isEmpty() || title.isEmpty() || college.isEmpty() || email.isEmpty()) {
                Toast toast = Toast.makeText(this, "Please enter the fullname, title, college and email!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            } else {
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent = new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG, "submitProfile");
                msgIntent.putExtra("fullname", fullname);
                msgIntent.putExtra("title", title);
                msgIntent.putExtra("college", college);
                msgIntent.putExtra("email", email);
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
                Toast toast = Toast.makeText(this, "Creating profile is successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();

                // for future remote database
        /*
        if email is not repeated, store it into local database
         */
                // local database
                ProfileDbAccess profileDbAccess = new ProfileDbAccess(database);
                profileDbAccess.insertProfile(fullname, title, college, email);

                Intent intent = new Intent(this, EventPost.class);
                startActivity(intent);
                finish();

            } else {
                Toast toast = Toast.makeText(this, "Creating profile failure, maybe email does exist, Please try again!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_submit_profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
