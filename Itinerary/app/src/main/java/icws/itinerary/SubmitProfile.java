package icws.itinerary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import database.ExternalDbOpenHelper;
import database.ProfileDbAccess;
import android.widget.EditText;

public class SubmitProfile extends Activity {
    //database
    private SQLiteDatabase database;
    private ExternalDbOpenHelper externalDbOpenHelper;
    //EditText
    EditText editTextFullName;
    EditText editTextTitle;
    EditText editTextCollege;
    EditText editTextEmail;
    //
    private String fullname;
    private String title;
    private String college;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_profile);
        // database
        externalDbOpenHelper = new ExternalDbOpenHelper(this);
        database = externalDbOpenHelper.openDataBase();


        // EditText
        editTextFullName = (EditText) findViewById(R.id.editText_fullName);
        editTextTitle = (EditText) findViewById(R.id.editText_title);
        editTextCollege = (EditText)findViewById(R.id.editText_college);
        editTextEmail=(EditText) findViewById(R.id.editText_eventContent);



    }

    @Override
    protected void onStop() {
        super.onStop();
        externalDbOpenHelper.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_profile, menu);
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

    public void goSubmit(View v){
       fullname= editTextFullName.getText().toString().trim();
        title = editTextTitle.getText().toString().trim();
        college = editTextCollege.getText().toString().trim();
        email= editTextEmail.getText().toString().trim();
        // for future remote database
        /*
        if email is not repeated, store it into local database
         */
        // local database
        ProfileDbAccess profileDbAccess = new ProfileDbAccess(database);
        profileDbAccess.insertProfile(fullname,title,college,email);

        Intent intent =new Intent(this,EventPost.class);
        startActivity(intent);
    }
}
