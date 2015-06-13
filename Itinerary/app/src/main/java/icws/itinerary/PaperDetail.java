package icws.itinerary;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import model.Session;
import model.Paper;
import android.widget.TextView;

public class PaperDetail extends Activity {

    private Paper paper;
    private TextView textView5_paperTitle;
    private TextView textView5_AuthorAffl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_detail);
        paper=(Paper) getIntent().getSerializableExtra("paper");
        textView5_paperTitle=(TextView) findViewById(R.id.textView5_paperTitle);
        textView5_AuthorAffl=(TextView) findViewById(R.id.textView5_AuthorAffl);
        //set content

        textView5_paperTitle.setText(paper.getTitle());
        //textView5_AuthorAffl.setText(paper.getAuthorWithAffiliation());
        textView5_AuthorAffl.setText(paper.getPaperAbstract());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paper_detail, menu);
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
