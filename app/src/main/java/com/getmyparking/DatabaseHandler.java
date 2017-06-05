package com.getmyparking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.StaleDataException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "getmyparking";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "getmyparking.db";

    private static DatabaseHandler databaseHandler;


    public interface Columns {
        String ID = "_id";
        String SERIAL_NUMBER = "sNo";
        String AMOUNT_PLEGED = "amount_pleged";
        String BLURB = "blurb";
        String BY = "by";
        String COUNTRY = "country";
        String CURRENCY = "currency";
        String END_TIME = "end_time";
        String LOCATION = "location";
        String PERCENTAGE_FUNDED = "percentage_funded";
        String NUM_BACKERS = "num_backers";
        String STATE = "state";
        String TITLE = "title";
        String TYPE = "type";
        String URL = "url";
    }

    String CREATE_PROJECT_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + Columns.ID + " INTEGER AUTO INCREMENT, " +
            Columns.SERIAL_NUMBER + " INTEGER UNIQUE, " +
            Columns.AMOUNT_PLEGED + " INTEGER, " +
            Columns.BLURB + " TEXT, " +
            Columns.BY + " TEXT, " +
            Columns.COUNTRY + " TEXT, " +
            Columns.CURRENCY + " TEXT, " +
            Columns.END_TIME + " TEXT, " +
            Columns.LOCATION + " TEXT, " +
            Columns.PERCENTAGE_FUNDED + " INTEGER, " +
            Columns.NUM_BACKERS + " TEXT, " +
            Columns.STATE + " TEXT, " +
            Columns.TITLE + " TEXT, " +
            Columns.TYPE + " TEXT, " +
            Columns.URL + " TEXT) ";


    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public void saveProject(List<ProjectModel> modelList){

        SQLiteDatabase db = getWritableDatabase();
        for(ProjectModel item : modelList){
            ContentValues values = new ContentValues();
            values.put(Columns.SERIAL_NUMBER , item.getSNo());
            values.put(Columns.AMOUNT_PLEGED, item.getAmtPledged());
            values.put(Columns.BLURB,item.getBlurb());
            values.put(Columns.BY, item.getBy());
            values.put(Columns.COUNTRY, item.getCountry());
            values.put(Columns.CURRENCY,item.getCurrency());
            values.put(Columns.END_TIME,item.getEndTime());
            values.put(Columns.LOCATION, item.getLocation());
            values.put(Columns.NUM_BACKERS, item.getNumBackers());
            values.put(Columns.PERCENTAGE_FUNDED, item.getPercentageFunded());
            values.put(Columns.STATE, item.getState());
            values.put(Columns.TITLE, item.getTitle());
            values.put(Columns.TYPE, item.getType());
            values.put(Columns.URL, item.getUrl());
            db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();

    }

    public Cursor getAllProjectList(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
            return cursor;
        return null;
    }


}
