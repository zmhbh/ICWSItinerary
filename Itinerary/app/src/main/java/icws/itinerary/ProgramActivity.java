package icws.itinerary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.content.*;
import intents.ClickInterface;
import intents.IntentFactory;
import model.*;
import java.util.ArrayList;
import android.view.*;
import android.widget.*;

public class ProgramActivity extends Activity {

    private ArrayList<Program> programArrayList;
    private ArrayList<TimeSlot> timeSlot1ArrayList;
    private ArrayList<TimeSlot> timeSlot2ArrayList;
    private LinearLayout firstLevelLinearLayout;

    //Radio
    private RadioGroup radioViewGroup;
    private RadioButton radioViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        //Radio Button
        addListenerOnButton();

        //MultiLevel List View
        firstLevelLinearLayout = (LinearLayout) findViewById(R.id.linear_listview);
        ArrayList<Session> session1ArrayList = new ArrayList<Session>();
        session1ArrayList.add(new Session("Global Faculty Club Initiative","Room 7.03"));
        session1ArrayList.add(new Session("ICWS Short Paper 1", "Room 7.01"));
        session1ArrayList.add(new Session("CLOUD Short Paper 1", "Room 7.04"));
        session1ArrayList.add(new Session("Summer School Tutorial 1: IBM", "Room 4.11"));

        ArrayList<Session> session2ArrayList = new ArrayList<Session>();
        session2ArrayList.add(new Session("Global Faculty Club Initiative 2","Room 7.03"));
        session2ArrayList.add(new Session("ICWS Short Paper 2","Room 7.01"));
        session2ArrayList.add(new Session("CLOUD Short Paper 2", "Room 7.04"));
        session2ArrayList.add(new Session("Summer School Tutorial 2: IBM", "Room 4.11"));

        timeSlot1ArrayList = new ArrayList<TimeSlot>();
        timeSlot2ArrayList = new ArrayList<TimeSlot>();
        timeSlot1ArrayList.add(new TimeSlot("9:00-10:00",session1ArrayList));
        timeSlot1ArrayList.add(new TimeSlot("10:00-11:00",session1ArrayList));
        timeSlot2ArrayList.add(new TimeSlot("9:30-10:00",session2ArrayList));
        timeSlot2ArrayList.add(new TimeSlot("10:00-10:15",session2ArrayList));

        programArrayList = new ArrayList<Program>();
        programArrayList.add(new Program("Saturday, June 27, 2015", timeSlot1ArrayList));
        programArrayList.add(new Program("Sunday, june 28, 2015", timeSlot2ArrayList));

        // first Level
        for (int i=0; i<programArrayList.size();i++){
            LayoutInflater inflater = null;
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View firstLevelView = inflater.inflate(R.layout.listview_firstlevel_program,null);

            final TextView textVew1Program = (TextView) firstLevelView.findViewById(R.id.textView1_program);
            final RelativeLayout linearFirst = (RelativeLayout) firstLevelView.findViewById(R.id.linearFirst);
            final ImageView firstArrow = (ImageView) firstLevelView.findViewById(R.id.imageFirstArrow);
            final LinearLayout linear_second = (LinearLayout) firstLevelView.findViewById(R.id.linear_second);

//            if(isFirstViewClicked==false){
                linear_second.setVisibility(View.GONE);
                firstArrow.setBackgroundResource(R.drawable.arw_lt);
//            }
//            else {
//                linear_second.setVisibility(View.VISIBLE);
//                firstArrow.setBackgroundResource(R.drawable.arw_down);
//            }

            linearFirst.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(linear_second.getVisibility()==View.GONE){
                      //  isFirstViewClicked=true;
                        firstArrow.setBackgroundResource(R.drawable.arw_down);
                        linear_second.setVisibility(View.VISIBLE);
                    }else{
                      //  isFirstViewClicked=false;
                        firstArrow.setBackgroundResource(R.drawable.arw_lt);
                        linear_second.setVisibility(View.GONE);
                    }

                    return false;
                }
            });

            final String programName = programArrayList.get(i).getProgramDetail();
            textVew1Program.setText(programName);

            // second level
            for(int j=0; j<programArrayList.get(i).getTimeSlotArrayList().size();j++){
                LayoutInflater inflater2 = null;
                inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View secondView = inflater2.inflate(R.layout.listview_secondlevel_timeslot,null);

                TextView textView2TimeRoom = (TextView) secondView.findViewById(R.id.textView2_viewTimeRoom);
                final RelativeLayout linearSecond = (RelativeLayout) secondView.findViewById(R.id.linearSecond);
                final ImageView secondArrow = (ImageView) secondView.findViewById(R.id.imageSecondArrow);
                final LinearLayout linear_third = (LinearLayout) secondView.findViewById(R.id.linear_third);

//                if(isSecondViewClicked==false){
                    linear_third.setVisibility(View.GONE);
                    secondArrow.setBackgroundResource(R.drawable.arw_lt);
//                }else{
//                    linear_third.setBackgroundResource(View.VISIBLE);
//                    secondArrow.setBackgroundResource(R.drawable.arw_down);
//                }

                linearSecond.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (linear_third.getVisibility() == View.GONE) {
                            // isSecondViewClicked=true;
                            secondArrow.setBackgroundResource(R.drawable.arw_down);
                            linear_third.setVisibility(View.VISIBLE);
                        } else {
                            // isSecondViewClicked = false;
                            secondArrow.setBackgroundResource(R.drawable.arw_lt);
                            linear_third.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });

                final String timeSlotName = programArrayList.get(i).getTimeSlotArrayList().get(j).getTimeDetail();
                textView2TimeRoom.setText(timeSlotName);

                // third level
                for(int k=0; k<programArrayList.get(i).getTimeSlotArrayList().get(j).getSessionArrayList().size();k++){

                    LayoutInflater inflater3 = null;
                    inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View thirdView = inflater3.inflate(R.layout.listview_thirdlevel_session,null);

                    TextView text_sessionType = (TextView) thirdView.findViewById(R.id.textView3_sessionType);
                    TextView text_sesstionRoomTime = (TextView) thirdView.findViewById(R.id.textView3_sessionRoomTime);
                    CheckBox checkBox = (CheckBox) thirdView.findViewById(R.id.checkBox);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox checkbox = (CheckBox) v;
                            if(checkbox.isChecked())
                                Toast.makeText(getApplicationContext(),
                                        "Checked box ID: "+v.getId(),Toast.LENGTH_SHORT).show();

                        }
                    });
                    final RelativeLayout linearThird = (RelativeLayout) thirdView.findViewById(R.id.linearThird);
                    final String sessionType = programArrayList.get(i).getTimeSlotArrayList().get(j).getSessionArrayList().get(k).getSessionType();
                    final String sessionRoomTime = programArrayList.get(i).getTimeSlotArrayList().get(j).getSessionArrayList().get(k).getSessionRoomTime();
                    text_sessionType.setText(sessionType);
                    text_sesstionRoomTime.setText(sessionRoomTime);

                    linearThird.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(v.getContext(),SessionDetail.class);
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

    @Override
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
    }

    public void addListenerOnButton(){
        radioViewGroup=(RadioGroup)findViewById(R.id.radioView);
        radioViewGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==2131492972) // View by time
                Toast.makeText(getApplicationContext(),
                        "View by Time checkedId: "+checkedId,Toast.LENGTH_SHORT).show();
                else  //View by room
                   Toast.makeText(getApplicationContext(),
                           "View by Room checkedId: "+checkedId,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
