package com.red.hot.mobile.peppers.rockpapersissors;

import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 
import android.util.Log;

public class DBAdapter {

	//in Android, many database-related methods rely on
	//_id being the primary key of the table, so we use
	//this standard as well
	
	//constant Strings identifying the columns of our database
	public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_AGE = "age"; 
    public static final String KEY_WINS = "wins";
    public static final String KEY_LOSSES = "losses";
    
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "RockPaperScissors";
    private static final String DATABASE_TABLE = "users";
    private static final int DATABASE_VERSION = 1;

    //SQL code that, when passed, will create our database
    private static final String DATABASE_CREATE =
        "create table users (_id integer primary key autoincrement, "
        + "username text not null, gender text not null, " 
        + "age integer not null, wins integer not null, "
        + "losses integer not null);";
        
    private final Context context;
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    
    // Method: open()
    // throws an SQLException if database cannot be opened
    // returns this instance of DBAdapter
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // Method: close()
    // closes the database saved in data member DBHelper
    public void close() 
    {
        DBHelper.close();
    }
    
    // Method: insert(String, String, int) returns long
    // inserts a new user into the database and returns its rowId
    public long insertTitle(String pUsername, String pGender, int pAge) 
    {
        ContentValues initialValues = new ContentValues();
        //_id not needed as it is auto-incremented
        initialValues.put(KEY_USERNAME, pUsername);
        initialValues.put(KEY_GENDER, pGender);
        initialValues.put(KEY_AGE, pAge);
        //wins and losses are 0 by default
        initialValues.put(KEY_WINS, 0);
        initialValues.put(KEY_LOSSES, 0);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Method: deleteUser(long)
    // Although not implemented in our app, this method provides the
    // ability to delete a particular user, provided the rowId
    /*public boolean deleteUser(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }*/

    // Method: getAllUsers()
    // Although not implemented in our app, this method provides the
    // ability to get a list (Cursor) of all users in our database
    /*public Cursor getAllUsers() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_ISBN,
        		KEY_TITLE,
                KEY_PUBLISHER}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }*/

    // Method: getUser(String)
    // throws a SQLException if the connection to database was not made
    // Given a username, this method returns the user information from
    // our database
    public Cursor getUser(String pUsername) throws SQLException 
    {
        Cursor mCursor =
                db.query(DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_USERNAME, 
                		KEY_GENDER,
                		KEY_AGE,
                		KEY_WINS,
                		KEY_LOSSES,
                		}, 
                		KEY_USERNAME + "=" + pUsername,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Method: updateUser(CurrentSession)
    // Updates wins and losses for given user if found
    public boolean updateUser(CurrentSession pCSesh) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USERNAME, pCSesh.getCurrentUser());
        args.put(KEY_GENDER, pCSesh.getGender());
        args.put(KEY_AGE, pCSesh.getAge());
        args.put(KEY_WINS, pCSesh.getWins());
        args.put(KEY_LOSSES, pCSesh.getLosses());
        return db.update(DATABASE_TABLE, args, 
                         KEY_USERNAME + "=" + pCSesh.getCurrentUser(), null) > 0;
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //method of the SQLiteOpenHelper class that must be overridden
        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

      //method of the SQLiteOpenHelper class that must be overridden
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                              int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    } 
    
}
