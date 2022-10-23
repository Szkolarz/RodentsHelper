package com.example.rodentshelper.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rodentshelper.RodentsModelClass;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class SQLiteHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "rodents";

    private static final int DB_VERSION = 20221019;

    private static final String TABLE_NAME = "rodents_list";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String NOTES_COL = "notes";

    private SQLiteDatabase sqLiteDatabase;


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public int getVersion() {
        Integer version = SQLiteHelper.DB_VERSION;
        return version;
    }

    private static final String CREATE_TABLE_Rodents = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + NOTES_COL + " TEXT)");

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Rodents);
    }

    public void addNewRodent(String name, String notes) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(NOTES_COL, notes);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void addNeRodent(RodentsModelClass rodentsModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, rodentsModelClass.getName());
        contentValues.put(NOTES_COL, rodentsModelClass.getNotes());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        sqLiteDatabase.close();
    }

    public List<RodentsModelClass> getRodentsLis() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<RodentsModelClass> storeRodent = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String notes = cursor.getString(2);
                storeRodent.add(new RodentsModelClass(id, name, notes));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeRodent;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateRodent (RodentsModelClass rodentsModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAME_COL, rodentsModelClass.getName());
        contentValues.put(SQLiteHelper.NOTES_COL, rodentsModelClass.getNotes());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_COL + " = ?", new String[]
                {String.valueOf(rodentsModelClass.getId())});
    }

    public void deleteRodent (int id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID_COL + " = ?", new String[]
                {String.valueOf(id)});
    }


}
