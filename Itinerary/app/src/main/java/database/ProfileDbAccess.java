package database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.event.Profile;

/**
 * Created by zmhbh on 6/14/15.
 */
public class ProfileDbAccess {
    private SQLiteDatabase database;

    public ProfileDbAccess(SQLiteDatabase database) {
        this.database = database;
    }

    public Profile getProfile() {
        Cursor cursor;
        cursor = database.query(ExternalDbOpenHelper.PROFILE_TABLE,
                new String[]{ExternalDbOpenHelper.PROFILE_FULLNAME, ExternalDbOpenHelper.PROFILE_TITLE, ExternalDbOpenHelper.PROFILE_COLLEGE, ExternalDbOpenHelper.PROFILE_EMAIL},
                "_id =?",
                new String[]{"1"},
                null, null, null);

        Profile profile=null;
        while (cursor.moveToNext()) {
            profile = new Profile(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));


        }
        return profile;
    }

    public void insertProfile(String fullname, String title, String college, String email) {
        ContentValues values = new ContentValues();
        values.put(ExternalDbOpenHelper.PROFILE_FULLNAME, fullname);
        values.put(ExternalDbOpenHelper.PROFILE_TITLE, title);
        values.put(ExternalDbOpenHelper.PROFILE_COLLEGE, college);
        values.put(ExternalDbOpenHelper.PROFILE_EMAIL, email);
        database.insert(ExternalDbOpenHelper.PROFILE_TABLE, null, values);
    }
}
