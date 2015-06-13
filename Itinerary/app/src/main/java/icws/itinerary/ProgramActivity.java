package icws.itinerary;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.content.*;

import database.ExternalDbOpenHelper;
import intents.ClickInterface;
import intents.IntentFactory;
import model.*;
import utility.ContentContainer;
import utility.ContentFiller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.view.*;
import android.widget.*;

import java.util.Map.Entry;

public class ProgramActivity extends Activity {

    private ArrayList<Program> programArrayList;
    private ArrayList<TimeSlot> timeSlot1ArrayList;
    private ArrayList<TimeSlot> timeSlot2ArrayList;
    private LinearLayout firstLevelLinearLayout;
    //Radio
    private RadioGroup radioViewGroup;

    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;
    // private RadioButton radioViewButton;

/*
    private static LinkedHashMap<String, Program> itinerary = new LinkedHashMap<String, Program>();

    private String[] category_program = {"Saturday, June 27, 2015", "Sunday, June 28, 2015", "Monday, June 29, 2015",
            "Tuesday, June 30, 2015", "Wednesday, July 1, 2015", "Thursday, July 2, 2015"};
    //private String[] category_timeslot=
    private String[] category_timeslot_saturday = {"9:00-10:00", "10:00-10:15", "10:15-11:15", "11:15-11:20", "11:20-12:20"};
    private String[] category_timeslot_sunday = {"8:15-9:15", "9:15-9:40", "9:40-11:30", "11:30-12:30", "12:30-13:30", "13:30-14:40"};
*/

    @Override
    protected void onStop() {
        super.onStop();
        externalDbOpenHelper.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        //Radio Button
        addListenerOnButton();

        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();

        // for test

        ContentFiller contentFiller = new ContentFiller(database);
        // test initialization
        contentFiller.populateContainer();

        firstLevelLinearLayout = (LinearLayout) findViewById(R.id.linear_listview);

        for (Entry<String, Program> programEntry : contentFiller.getProgramEntrySet()) {
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View firstLevelView = inflater.inflate(R.layout.listview_firstlevel_program, null);

            final TextView textVew1Program = (TextView) firstLevelView.findViewById(R.id.textView1_program);
            final RelativeLayout linearFirst = (RelativeLayout) firstLevelView.findViewById(R.id.linearFirst);
            final ImageView firstArrow = (ImageView) firstLevelView.findViewById(R.id.imageFirstArrow);
            final LinearLayout linear_second = (LinearLayout) firstLevelView.findViewById(R.id.linear_second);

            //initialization
            linear_second.setVisibility(View.GONE);
            firstArrow.setBackgroundResource(R.drawable.arw_lt);

            // on click event
            linearFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (linear_second.getVisibility() == View.GONE) {
                        firstArrow.setBackgroundResource(R.drawable.arw_down);
                        linear_second.setVisibility(View.VISIBLE);
                    } else {
                        firstArrow.setBackgroundResource(R.drawable.arw_lt);
                        linear_second.setVisibility(View.GONE);
                    }
                }
            });



            String programName = programEntry.getKey();
            textVew1Program.setText(programName);

            // second level

            for (Entry<String, TimeSlot> timeSlotEntry : programEntry.getValue().getTimeSlotEntrySet()) {
                LayoutInflater inflater2 = null;
                inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View secondView = inflater2.inflate(R.layout.listview_secondlevel_timeslot, null);

                TextView textView2TimeRoom = (TextView) secondView.findViewById(R.id.textView2_viewTimeRoom);
                final RelativeLayout linearSecond = (RelativeLayout) secondView.findViewById(R.id.linearSecond);
                final ImageView secondArrow = (ImageView) secondView.findViewById(R.id.imageSecondArrow);
                final LinearLayout linear_third = (LinearLayout) secondView.findViewById(R.id.linear_third);
                //initialization
                linear_third.setVisibility(View.GONE);
                secondArrow.setBackgroundResource(R.drawable.arw_lt);

                // click event
                linearSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (linear_third.getVisibility() == View.GONE) {
                            secondArrow.setBackgroundResource(R.drawable.arw_down);
                            linear_third.setVisibility(View.VISIBLE);
                        } else {
                            secondArrow.setBackgroundResource(R.drawable.arw_lt);
                            linear_third.setVisibility(View.GONE);
                        }
                    }
                });



                final String timeSlotName = timeSlotEntry.getKey();
                textView2TimeRoom.setText(timeSlotName);

                // third level

                for (Entry<String, Session> sessionEntry : timeSlotEntry.getValue().getSessionEntrySet()) {
                    LayoutInflater inflater3 = null;
                    inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View thirdView = inflater3.inflate(R.layout.listview_thirdlevel_session, null);

                    // get the view and box to set
                    TextView text_sessionType = (TextView) thirdView.findViewById(R.id.textView3_sessionType);
                    TextView text_sesstionRoomTime = (TextView) thirdView.findViewById(R.id.textView3_sessionRoomTime);
                    CheckBox checkBox = (CheckBox) thirdView.findViewById(R.id.checkBox);
                    //session object
                    String sessionType = sessionEntry.getKey();
                    Session session = sessionEntry.getValue();
                    // set the view and check box
                    text_sessionType.setText(sessionType);
                    text_sesstionRoomTime.setText(session.getSessionRoomTime());
                    checkBox.setChecked(session.getSessionSelected().equals("0"));
                    // set session id (database) as tag
                    checkBox.setTag(session);
                    // click event
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            CheckBox checkBox = (CheckBox)buttonView;
                            Session session = (Session)buttonView.getTag();
                            Toast.makeText(getApplicationContext(),
                                    "Session ID in database: " + session.get_id(), Toast.LENGTH_SHORT).show();
                            if(isChecked){
                                session.setSessionSelected("1");
                                //for database update
                            }else{
                                session.setSessionSelected("0");
                                //for database update
                            }
                        }
                    });

                    final RelativeLayout linearThird = (RelativeLayout) thirdView.findViewById(R.id.linearThird);



                    linearThird.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(v.getContext(), SessionDetail.class);
                            startActivity(intent);

                        }
                    });


                    linear_third.addView(thirdView);
                }
                linear_second.addView(secondView);
            }
            firstLevelLinearLayout.addView(firstLevelView);
        }

    }


    //Radio Button onClick

    public void addListenerOnButton() {
        radioViewGroup = (RadioGroup) findViewById(R.id.radioView);
        radioViewGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == 2131492972) // View by time
                    Toast.makeText(getApplicationContext(),
                            "View by Time checkedId: " + checkedId, Toast.LENGTH_SHORT).show();
                else  //View by room
                    Toast.makeText(getApplicationContext(),
                            "View by Room checkedId: " + checkedId, Toast.LENGTH_SHORT).show();
            }
        });
    }




/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_program, menu);
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
    }*/
}
