package icws.itinerary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import database.ExternalDbOpenHelper;
import model.*;
import utility.ContentFiller;


public class Itinerary extends Activity {

    private LinearLayout firstLevelLinearLayout;
    //Radio
    private RadioGroup radioViewGroup;

    // TextView
    private TextView textViewIndex;

    //progress dialog
    private ProgressDialog progressDialog;

    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;

    //view tag
    private boolean isViewByTime = true;

    // database, container
    private ContentFiller contentFiller;

    @Override
    protected void onStop() {
        super.onStop();
        externalDbOpenHelper.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        radioViewGroup = (RadioGroup) findViewById(R.id.radioView);

       textViewIndex=(TextView) findViewById(R.id.textView2);
        textViewIndex.setText("This itinerary is generated from the selections in Program, Please tap to expand list for details. ");
        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();

        // for test

        contentFiller = new ContentFiller(database);

        String view = getIntent().getStringExtra("view");
        if (view != null) {
            int checkID;
            if (view.equals("time")) {
                isViewByTime = true;
                checkID = getIntent().getIntExtra("viewid", 0);
                radioViewGroup.check(checkID);
            } else {
                isViewByTime = false;
                checkID = getIntent().getIntExtra("viewid", 0);
                radioViewGroup.check(checkID);
            }
        }
        // get record from database
        else {
            if (contentFiller.getViewSelected().equals("time")) {
                isViewByTime = true;
            } else {
                isViewByTime = false;
                radioViewGroup.check(R.id.radioButtonRoom);
            }
        }
        //Radio Button
        addListenerOnButton();

        new Load().execute();

    }


    private void drawList(){
        firstLevelLinearLayout = (LinearLayout) findViewById(R.id.linear_listview);
        if (isViewByTime) {

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
                int timeSlotRoomIndex=0;
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
                    int sessionIndex=0;
                    for (Map.Entry<String, Session> sessionEntry : timeSlotEntry.getValue().getSessionEntrySet()) {

                        LayoutInflater inflater3 = null;
                        inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        //session object
                        String sessionType = sessionEntry.getKey();
                        Session session = sessionEntry.getValue();
                        if(session.getSessionSelected().equals("1")) {
                            View thirdView = inflater3.inflate(R.layout.listview_thirdlevel_session, null);
                            sessionIndex++;
                            // get the view and box to set
                            TextView text_sessionType = (TextView) thirdView.findViewById(R.id.textView3_sessionType);
                            TextView text_sessionRoomTime = (TextView) thirdView.findViewById(R.id.textView3_sessionRoomTime);
                            CheckBox checkBox = (CheckBox) thirdView.findViewById(R.id.checkBox);
                            checkBox.setVisibility(View.INVISIBLE);
                            // set the view and check box
                            text_sessionType.setText(sessionType);
                            text_sessionRoomTime.setText(session.getSessionRoomTime());


                            final RelativeLayout linearThird = (RelativeLayout) thirdView.findViewById(R.id.linearThird);

                            linearThird.setTag(session);

                            linearThird.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Session session = (Session) v.getTag();
                                    Intent intent = new Intent(v.getContext(), SessionDetail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("session", session);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                }
                            });


                            linear_third.addView(thirdView);
                        }
                    }
                    if(sessionIndex!=0) {
                        linear_second.addView(secondView);
                        timeSlotRoomIndex++;
                    }
                    else
                        secondView.setVisibility(View.INVISIBLE);
                }
                if(timeSlotRoomIndex!=0)
                    firstLevelLinearLayout.addView(firstLevelView);
                else
                    firstLevelView.setVisibility(View.INVISIBLE);
            }
        } else {
            for (Map.Entry<String, Program_Room> program_roomEntry : contentFiller.getProgram_RoomEntrySet()) {
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


                String programName = program_roomEntry.getKey();
                textVew1Program.setText(programName);

                // second level
                int timeSlotRoomIndex=0;
                for (Map.Entry<String, Room> roomEntry : program_roomEntry.getValue().getRoomEntrySet()) {
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


                    final String roomName = roomEntry.getKey();
                    textView2TimeRoom.setText(roomName);

                    // third level

                    int sessionIndex=0;
                    for (Map.Entry<String, Session> sessionEntry : roomEntry.getValue().getSessionEntrySet()) {
                        LayoutInflater inflater3 = null;
                        inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        //session object
                        String sessionType = sessionEntry.getKey();
                        Session session = sessionEntry.getValue();
                        if (session.getSessionSelected().equals("1")) {
                            sessionIndex++;
                            View thirdView = inflater3.inflate(R.layout.listview_thirdlevel_session, null);
                            // set invisible of checkBox
                            CheckBox checkBox = (CheckBox) thirdView.findViewById(R.id.checkBox);
                            checkBox.setVisibility(View.INVISIBLE);
                            // get the view and box to set
                            TextView text_sessionType = (TextView) thirdView.findViewById(R.id.textView3_sessionType);
                            TextView text_sessionRoomTime = (TextView) thirdView.findViewById(R.id.textView3_sessionRoomTime);


                            // set the view and check box
                            text_sessionType.setText(sessionType);
                            text_sessionRoomTime.setText(session.getSessionRoomTime());
                            checkBox.setChecked(session.getSessionSelected().equals("1"));
                            // set session id (database) as tag
                            checkBox.setTag(session);


                            final RelativeLayout linearThird = (RelativeLayout) thirdView.findViewById(R.id.linearThird);

                            linearThird.setTag(session);

                            linearThird.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Session session = (Session) v.getTag();
                                    Intent intent = new Intent(v.getContext(), SessionDetail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("session", session);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                }
                            });
                            linear_third.addView(thirdView);
                        }
                    }
                    if(sessionIndex!=0) {
                        linear_second.addView(secondView);
                        timeSlotRoomIndex++;
                    }
                    else
                        secondView.setVisibility(View.INVISIBLE);
                }
                if(timeSlotRoomIndex!=0)
                    firstLevelLinearLayout.addView(firstLevelView);
                else
                    firstLevelView.setVisibility(View.INVISIBLE);
            }
        }
    }
    //Radio Button onClick

    public void addListenerOnButton() {


        radioViewGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                //alert dialog

                if (radioButton.getText().equals("View By Time")) {
                    Toast.makeText(getApplicationContext(),
                            "Regenerate View By Time! ", Toast.LENGTH_SHORT).show();
                    contentFiller.updateViewSelected("time");
                    Intent intent = new Intent(group.getContext(), Itinerary.class);
                    intent.putExtra("view", "time");
                    intent.putExtra("viewid", checkedId);

                    startActivity(intent);
                    finish();
                } else {
                    contentFiller.updateViewSelected("room");
                    String test = contentFiller.getViewSelected();
                    Toast.makeText(getApplicationContext(),
                            "Regenerate View By Room! "+test, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(group.getContext(), Itinerary.class);
                    intent.putExtra("view", "room");
                    intent.putExtra("viewid", checkedId);

                    startActivity(intent);
                    finish();

                }

//                    Toast.makeText(getApplicationContext(),
//                            "CHECKED BUTTON " + radioButton.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    class Load extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(Itinerary.this);
            progressDialog.setMessage("loading Program");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // test initialization
            contentFiller.populateContainer();
            contentFiller.populateContainer_room();
            return null;

        }
        @Override
        protected void onPostExecute(String file_url){
            progressDialog.dismiss();
            drawList();
        }
    }

}
