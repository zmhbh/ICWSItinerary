package icws.itinerary;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import intents.ClickInterface;
import intents.IntentFactory;
import model.event.ProcessJSONInterface;
import model.notice.Notification;
import model.notice.NotificationList;
import utility.NoticeListAdapter;
import webService.JSONRequest;
import webService.NetworkStatus;
import android.widget.ListView;

public class NotificationActivity extends ListActivity implements ProcessJSONInterface, AdapterView.OnItemClickListener {

    //backend
    private final String process_response_filter = "action.getNotification";
    private BroadcastReceiver receiver;

    private NotificationList notificationArrayList;
    //progress dialog
    private ProgressDialog progressDialog;
    private NoticeListAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        myAdapter=new NoticeListAdapter(this);
        setListAdapter(myAdapter);

        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);

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
                if (responseType.trim().equalsIgnoreCase("getNotification")) {
                    response = intent.getStringExtra(JSONRequest.OUT_MSG);
                    //
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);
    }

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, NotificationDetail.class, notificationArrayList.getNotification(position), null);

    }

    @Override
    protected void onResume(){
        super.onResume();

        myAdapter.removeAllNotification();
        askToGetNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    //sending...
    //ask to send JSON request
    private void askToGetNotification() {
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if (internet) {

            //pass the request to web service so that it can
            //run outside the scope of the main UI thread
            Intent msgIntent = new Intent(this, JSONRequest.class);
            msgIntent.putExtra(JSONRequest.IN_MSG, "getNotification");
            msgIntent.putExtra("process_response_filter", process_response_filter);
            startService(msgIntent);

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

                Gson gson = new Gson();
                // get the information from servlet;
                String allNotifications= responseObj.getString("allNotifications");
                notificationArrayList=gson.fromJson(allNotifications,NotificationList.class);

                int sum=notificationArrayList.getNotificationNum();
                for(int i=0; i<sum;i++){
                    Notification notification = notificationArrayList.getNotification(i);
                    myAdapter.addNotification(i,notification);
                    this.setSelection(i);
                }

            } else {
                Toast toast = Toast.makeText(this, "Error in receiving notifications!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
