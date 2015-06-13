package icws.itinerary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import intents.ClickInterface;
import intents.IntentFactory;
import model.Paper;
import utility.MyListAdapter;
import android.app.Activity;
import android.widget.Toast;

import model.Session;
import utility.PaperListViewTag;

import android.widget.TextView;
import java.util.Map.Entry;

public class SessionDetail extends ListActivity implements AdapterView.OnItemClickListener {

    private MyListAdapter myListAdapter;
    private Session session;
    private TextView textView_sessionName;
    private TextView textView_sessionTitle;
    private TextView textView_sessionTimeRoom;
    private TextView textView_sessionChair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);
        myListAdapter = new MyListAdapter(this);
        setListAdapter(myListAdapter);

        textView_sessionName = (TextView)findViewById(R.id.textView_sessionName);
        textView_sessionTitle=(TextView) findViewById(R.id.textView_sessionTitle);
        textView_sessionTimeRoom=(TextView) findViewById(R.id.textView_sessionTimeRoom);
        textView_sessionChair=(TextView) findViewById(R.id.textView_sessionChair);

        session=(Session) getIntent().getSerializableExtra("session");

        textView_sessionName.setText(session.getSessionName());
        textView_sessionTitle.setText(session.getSessionTitle());
        textView_sessionTimeRoom.setText(session.getSessionRoomTime());
        textView_sessionChair.setText(session.getSessionChair());


        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);

        int pos=0;
        for(Entry<String, Paper> entrySet:session.getPaperEntrySet()){
            myListAdapter.addItem(pos, entrySet.getValue());
        }

//        myListAdapter.addItem(0,paper);
//        myListAdapter.addItem(1,paper);
        this.setSelection(0);
        session=(Session) getIntent().getSerializableExtra("session");
        Toast.makeText(getApplicationContext(),
                "Session name: " + session.getSessionName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session_detail, menu);
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

    /*Click different picture and jump to different item page*/
    /*We can directly send Item object into next page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        PaperListViewTag paperTag=(PaperListViewTag)v.getTag();
        Paper paper =paperTag.getPaperTag();
        Intent intent = new Intent(v.getContext(), PaperDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("paper",paper);
        intent.putExtras(bundle);
        startActivity(intent);
       // ClickInterface click = IntentFactory.goToNext(this, PaperDetail.class, null, null);
    }
}
