package database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zmhbh on 6/14/15.
 */
public class ProfileDbAccess {
    private SQLiteDatabase database;

    public ProfileDbAccess(SQLiteDatabase database){
        this.database=database;
    }

    public String getProfileFullname(){
        Cursor cursor;
        cursor = database.query(ExternalDbOpenHelper.PROFILE_TABLE,
                new String[]{ExternalDbOpenHelper.PROFILE_FULLNAME},
                "_id =?",
                new String[]{"1"},
                null,null,null);

        String fullname=null;
        while(cursor.moveToNext()){
            fullname= cursor.getString(0);
        }
        return fullname;
    }

    public void insertProfile(String fullname, String title, String college, String email){
        ContentValues values = new ContentValues();
        values.put(ExternalDbOpenHelper.PROFILE_FULLNAME,fullname);
        values.put(ExternalDbOpenHelper.PROFILE_TITLE,title);
        values.put(ExternalDbOpenHelper.PROFILE_COLLEGE,college);
        values.put(ExternalDbOpenHelper.PROFILE_EMAIL,email);
        database.insert(ExternalDbOpenHelper.PROFILE_TABLE,null,values);
    }
}
