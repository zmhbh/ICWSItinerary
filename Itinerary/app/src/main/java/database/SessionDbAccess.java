package database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zmhbh on 6/16/15.
 */
public class SessionDbAccess {

    private SQLiteDatabase database;

    public SessionDbAccess(SQLiteDatabase database) {
        this.database = database;
    }

    public void resetSession(){

        ContentValues values=new ContentValues();
        values.put(ExternalDbOpenHelper.SESSION_SELECTED,"0");
        long result = database.update(ExternalDbOpenHelper.SESSION_TABLE,values,null,null);
        result = database.update(ExternalDbOpenHelper.ROOMSESSION_TABLE,values,null,null);
    }
}
