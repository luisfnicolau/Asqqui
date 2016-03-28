package com.example.luis.asqqui.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luis on 14/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "weather.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DatabaseContract.UserEntry.TABLE_NAME  + " (" +
                DatabaseContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DatabaseContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                DatabaseContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                DatabaseContract.UserEntry.COLUMN_PHOTO + " BLOB NOT NULL" +
                " );";

        final String SQL_CREATE_POLITICIAN_TABLE = "CREATE TABLE " + DatabaseContract.PoliticianEntry.TABLE_NAME + " (" +

                DatabaseContract.PoliticianEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                DatabaseContract.PoliticianEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DatabaseContract.PoliticianEntry.COLUMN_PHOTO + " BLOB NOT NULL, " +
                DatabaseContract.PoliticianEntry.COLUMN_PARTY + " INTEGER NOT NULL, " +
                DatabaseContract.PoliticianEntry.COLUMN_VOTE_NUMBER + " INTEGER NOT NULL," +

                DatabaseContract.PoliticianEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                DatabaseContract.PoliticianEntry.COLUMN_PROPOSALS + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_PARTY_TABLE = "CREATE TABLE " + DatabaseContract.PartyEntry.TABLE_NAME  + " (" +
                DatabaseContract.PartyEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.PartyEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                DatabaseContract.PartyEntry.COLUMN_PHOTO + " BLOB NOT NULL, " +
                DatabaseContract.PartyEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_COMMENTS_TABLE = "CREATE TABLE " + DatabaseContract.CommentsEntry.TABLE_NAME  + " (" +
                DatabaseContract.CommentsEntry._ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.CommentsEntry.COLUMN_AUTHOR + " INTEGER NOT NULL, " +
                DatabaseContract.CommentsEntry.COLUMN_COMMENT + " TEXT NOT NULL, " +
                DatabaseContract.CommentsEntry.COLUMN_LIKES + " INTEGER NOT NULL, " +
                DatabaseContract.CommentsEntry.COLUMN_DISLIKES + " INTEGER NOT NULL" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POLITICIAN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PARTY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.PoliticianEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.PartyEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.CommentsEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}

