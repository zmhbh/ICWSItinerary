package icws.itinerary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NotificationDetail extends Activity {

    private String notice;
    private String time;
    private String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        notice=getIntent().getStringExtra("notice");
        time=getIntent().getStringExtra("time");
        detail = getIntent().getStringExtra("detail");

        TextView textView_detailNotice = (TextView) findViewById(R.id.textView_detailNotice);
        TextView textView_detailTime = (TextView) findViewById(R.id.textView_detailTime);
        TextView textView_detail=(TextView) findViewById(R.id.textView_detail);
        textView_detailNotice.setText(notice);
        textView_detailTime.setText(time);
        textView_detail.setText(detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_detail, menu);
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
}
