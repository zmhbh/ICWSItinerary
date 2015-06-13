package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by zmhbh on 6/12/15.
 */
public class ExternalDbOpenHelper extends SQLiteOpenHelper {

    //database schema details
    //table name
    public static final String PROGRAM_TABLE = "program";
    public static final String TIMESLOT_TABLE = "timeslot";
    public static final String SESSION_TABLE = "session";
    public static final String PAPER_TABLE = "paper";
    // column name
    public static final String ID_COLUMN = "_id";
    public static final String VALUE_COLUMN = "value";
    public static final String TIMESLOT_PROGRAM_ID = "program_id";

    public static final String SESSION_TIMESLOT_ID="timeslot_id";
    public static final String SESSION_ROOM="room";
    public static final String SESSION_SELECTED="selected";
    public static final String SESSION_TITLE="session_title";
    public static final String SESSION_CHAIR="chair";
    public static final String SESSION_PAPERS="papers";
    public static final String PAPER_UNIQUE_ID="unique_id";
    public static final String PAPER_TITLE="title";
    public static final String PAPER_AUTHOR="author";
    public static final String PAPER_AFFILIATION="affiliation";
    public static final String PAPER_AUTHOR_WITH_AFFILIATION="authorwithaffiliation";
    public static final String PAPER_ABSTRACT="abstract";

    // for view by room
    public static final String ROOM_TABLE="room";
    public static final String ROOMSESSION_TABLE="room_session";
    public static final String VIEW_TABLE="view";
    public static final String ROOM_PROGRAM_ID = "program_id";


    public static final String ROOMSESSION_ROOM_ID="room_id";
    public static final String ROOMSESSION_TIMESLOT="timeslot";
    public static final String ROOMSESSION_SELECTED="selected";
    public static final String ROOMSESSION_SESSION_TITLE="session_title";
    public static final String ROOMSESSION_SESSION_CHAIR="chair";
    public static final String ROOMSESSION_SESSION_PAPERS="papers";



    //Database name
    private static final String DB_NAME = "cmuicws.sqlite3";

    //Path to the device folder with databases
    public static String DB_PATH;

    //Database file name
    public SQLiteDatabase database;
    public final Context context;

    public SQLiteDatabase getDb() {
        return database;
    }


    public ExternalDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        //Write a full path to the databases of your application
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
        openDataBase();
    }

    //This piece of code will create a database if it’s not yet created
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    //Performing a database existence check
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Android doesn’t like resource leaks, everything should
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }


    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

        //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

        //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
