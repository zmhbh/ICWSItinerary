package icws.itinerary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import intents.ClickInterface;
import intents.IntentFactory;
import webService.JSONRequest;
import webService.NetworkStatus;
import model.event.ProcessJSONInterface;

public class PostNotification extends Activity implements ProcessJSONInterface {

    //backend
    private final String process_response_filter = "action.postNotification";
    private BroadcastReceiver receiver;

    private EditText editTextNotice;
    private EditText editTextPostTime;
    private EditText editTextDetail;
    private String notice;
    private String postTime;
    private String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_notification);
        editTextNotice=(EditText)findViewById(R.id.editText_notice);
        editTextPostTime=(EditText)findViewById(R.id.editText_postTime);
        editTextDetail=(EditText)findViewById(R.id.editText_detail);

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
                if (responseType.trim().equalsIgnoreCase("postNotification")) {
                    response = intent.getStringExtra(JSONRequest.OUT_MSG);
                    //
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void goSubmit(View v){
        notice = editTextNotice.getText().toString().trim();
        postTime=editTextPostTime.getText().toString().trim();
        detail=editTextDetail.getText().toString().trim();
        askToPostNotification();

    }

    //sending...
    //ask to send JSON request
    private void askToPostNotification() {
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if (internet) {
            if (notice.isEmpty()) {
                editTextNotice.setError("Full name cannot be empty!");
            }
            if (postTime.isEmpty()) {
                editTextPostTime.setError("Title cannot be empty!");
            }
            if (detail.isEmpty()) {
                editTextDetail.setError("College cannot be empty!");
            }
            if (notice.isEmpty() || postTime.isEmpty() || detail.isEmpty()) {
                Toast toast = Toast.makeText(this, "Please enter the notice, postTime and detail!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            } else {
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent = new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG, "postNotification");
                msgIntent.putExtra("notice", notice);
                msgIntent.putExtra("postTime", postTime);
                msgIntent.putExtra("detail", detail);
                msgIntent.putExtra("process_response_filter", process_response_filter);
                startService(msgIntent);
            }


        }
    }

    @Override
    public void processJsonResponse(String response) {
        JSONObject responseObj = null;
        Log.e("get posting response: ","response");
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success = responseObj.getBoolean("success");
            if (success) {
                Toast toast = Toast.makeText(this, "Posting notification is successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();

                ClickInterface click = IntentFactory.goToNext(this,NotificationActivity.class,null,null);
                finish();

            } else {
                Toast toast = Toast.makeText(this, "Posting notification failure, Please try again!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
