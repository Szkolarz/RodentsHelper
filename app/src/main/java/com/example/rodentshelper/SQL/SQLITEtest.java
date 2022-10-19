package com.example.rodentshelper.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLITEtest extends SQLiteOpenHelper {
    private static final String DB_NAME = "rodents";

    private static final int DB_VERSION = 20221019;

    private static final String TABLE_NAME = "test";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String SURNAME_COL = "surname";

    // creating a constructor for our database handler.
    public SQLITEtest(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public int getVersion() {
        Integer version = SQLITEtest.DB_VERSION;
        return version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + SURNAME_COL + " TEXT)";

        // to execute above sql query
        db.execSQL(query);
    }

    public void addNewRodent(String name, String surname) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(SURNAME_COL, surname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
