package com.red.hot.mobile.peppers.rockpapersissors;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class DBAdapter 
{
	private static final String DATABASE_TABLE = "USER";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_AGE = "age";
	public static final String KEY_WINS = "wins";
	public static final String KEY_LOSSES = "losses";

	
	
	SQLiteDatabase mDb;
	Context mCtx;
	DBHelper mDbHelper;
	
	public DBAdapter(Context context)
	{
		this.mCtx = context;
	}

	public DBAdapter open() throws SQLException
	{
		mDbHelper = new DBHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		mDbHelper.close();
	}
	
	public long register(String user,String gender, String age, int wins, int losses)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, user);
		initialValues.put(KEY_GENDER, gender);
		initialValues.put(KEY_AGE, age);
		initialValues.put(KEY_WINS, wins);
		initialValues.put(KEY_LOSSES, losses);
		
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public boolean Login(String username, String gender, String age) throws SQLException 
    {
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=? AND gender=? AND age=?", new String[]{username,gender,age});
        if (mCursor != null) {           
            if(mCursor.getCount() > 0)
            {
            	return true;
            }
        }
     return false;
    }
	
	public boolean checkUser(String username) throws SQLException 
    {
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{username});
        if (mCursor != null) {           
            if(mCursor.getCount() > 0)
            {
            	return true;
            }
        }
     return false;
    }
	
	
	public long winsUpdate(String user)
	{
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{user});
        mCursor.moveToFirst();
    	
    	int newwins = mCursor.getInt(3)+1;
    	System.out.println("new wins "+newwins);
    	 ContentValues data=new ContentValues();
    	 data.put("wins",newwins);
      	
    	return mDb.update(DATABASE_TABLE, data,KEY_USERNAME +" = ?", new String[] {user});
    	
    	}
	
	public long lossUpdate(String user)
	{
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{user});
        mCursor.moveToFirst();
    	
    	int newloss = mCursor.getInt(4)+1;
    	System.out.println("new loss "+newloss);

    	 ContentValues data=new ContentValues();
    	 data.put("losses",newloss);
      	
    	return mDb.update(DATABASE_TABLE, data,KEY_USERNAME +" = ?", new String[] {user});

    	}
	
	public int getWins(String user)
	{
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{user});
        mCursor.moveToFirst();
    	int wins = mCursor.getInt(3);
   		return wins;
	}
	
	public int getLoss(String user)
	{
    	Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{user});
        mCursor.moveToFirst();
    	int loss = mCursor.getInt(4);
   		return loss;
	}


}