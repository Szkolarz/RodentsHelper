package com.example.rodentshelper.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rodentshelper.ClassModels.RodentsModelClass;

import java.util.ArrayList;
import java.util.List;

public class DBHelperAnimal extends SQLiteOpenHelper {


    private static final String DB_NAME = "rodents";

    private static final int DB_VERSION = 20221019;

    private static final String TABLE_NAME = "rodents_list";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String GENDER_COL = "gender";
    private static final String BIRTH_COL = "birth";
    private static final String FUR_COL = "fur";
    private static final String NOTES_COL = "notes";

    private SQLiteDatabase sqLiteDatabase;


    public DBHelperAnimal(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public int getVersion() {
        Integer version = DBHelperAnimal.DB_VERSION;
        return version;
    }

    private static final String CREATE_TABLE_Rodents = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + GENDER_COL + " TEXT,"
            + BIRTH_COL + " DATE,"
            + FUR_COL + " TEXT,"
            + NOTES_COL + " TEXT)");

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_Rodents);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Rodents);
    }


    public void addNeRodent(RodentsModelClass rodentsModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, rodentsModelClass.getName());
        contentValues.put(GENDER_COL, rodentsModelClass.getGender());
        contentValues.put(BIRTH_COL, (rodentsModelClass.getBirth()).toString());
        contentValues.put(FUR_COL, rodentsModelClass.getFur());
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
                String gender = cursor.getString(2);
                String birth = cursor.getString(3);
                String fur = cursor.getString(4);
                String notes = cursor.getString(5);
                storeRodent.add(new RodentsModelClass(id, name, gender, birth, fur, notes));
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
        System.out.println(rodentsModelClass.getName());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperAnimal.NAME_COL, rodentsModelClass.getName());
        contentValues.put(DBHelperAnimal.GENDER_COL, rodentsModelClass.getGender());
        contentValues.put(DBHelperAnimal.BIRTH_COL, (rodentsModelClass.getBirth()).toString());
        contentValues.put(DBHelperAnimal.FUR_COL, rodentsModelClass.getFur());
        contentValues.put(DBHelperAnimal.NOTES_COL, rodentsModelClass.getNotes());
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
