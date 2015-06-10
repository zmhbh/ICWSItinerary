package icws.itinerary;

import android.app.ListActivity;
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


public class SessionDetail extends ListActivity implements AdapterView.OnItemClickListener {

    private MyListAdapter myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);
        myListAdapter = new MyListAdapter(this);
        setListAdapter(myListAdapter);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);

        Paper paper = new Paper("This guy is so cool!", "Jingyu Huang", "CMU");
        myListAdapter.addItem(0,paper);
        myListAdapter.addItem(1,paper);
        this.setSelection(0);
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
        ClickInterface click = IntentFactory.goToNext(this, PaperDetail.class, null, null);
    }
}
