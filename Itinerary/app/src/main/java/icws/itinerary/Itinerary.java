package icws.itinerary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import database.ExternalDbOpenHelper;
import model.Program;
import model.Session;
import model.TimeSlot;
import utility.ContentFiller;


public class Itinerary extends Activity {

    private LinearLayout firstLevelLinearLayout;
    //Radio
    private RadioGroup radioViewGroup;

    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;

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

        for (Map.Entry<String, Program> programEntry : contentFiller.getProgramEntrySet()) {
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

            for (Map.Entry<String, TimeSlot> timeSlotEntry : programEntry.getValue().getTimeSlotEntrySet()) {
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

                for (Map.Entry<String, Session> sessionEntry : timeSlotEntry.getValue().getSessionEntrySet()) {
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

                    linearThird.setTag(session);

                    linearThird.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Session session=(Session)v.getTag();
                            Intent intent = new Intent(v.getContext(), SessionDetail.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("session",session);
                            intent.putExtras(bundle);
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

}
