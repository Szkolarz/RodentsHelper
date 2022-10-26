package com.example.rodentshelper.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rodentshelper.VetsModelClass;

import java.util.ArrayList;
import java.util.List;

public class DBHelperVet extends SQLiteOpenHelper {

    private static final String DB_NAME = "rodents";

    private static final int DB_VERSION = 20221019;

    private static final String TABLE_NAME = "vet";

    private static final String ID_COL = "id";
    private static final String ID_ANIMAL_COL = "id_animal";
    private static final String NAME_COL = "name";
    private static final String ADDRESS_COL = "address";
    private static final String PHONE_NUMBER_COL = "phone_number";
    private static final String NOTES_COL = "notes";

    private SQLiteDatabase sqLiteDatabase;


    public DBHelperVet(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public int getVersion() {
        Integer version = DBHelperVet.DB_VERSION;
        return version;
    }

    private static final String CREATE_TABLE_Vet = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_ANIMAL_COL + " INTEGER,"
            + NAME_COL + " TEXT,"
            + ADDRESS_COL + " TEXT,"
            + PHONE_NUMBER_COL + " TEXT,"
            + NOTES_COL + " TEXT)");

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Vet);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Vet);
    }

    public void addNewVet(VetsModelClass vetsModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_ANIMAL_COL, vetsModelClass.getId_animal());
        contentValues.put(NAME_COL, vetsModelClass.getName());
        contentValues.put(ADDRESS_COL, vetsModelClass.getAddress());
        contentValues.put(PHONE_NUMBER_COL, vetsModelClass.getPhone_number());
        contentValues.put(NOTES_COL, vetsModelClass.getNotes());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        sqLiteDatabase.close();
    }

    public List<VetsModelClass> getVetList() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<VetsModelClass> storeVet = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(2);
                String address = cursor.getString(3);
                String phone_number = cursor.getString(4);
                String notes = cursor.getString(5);
                storeVet.add(new VetsModelClass(id,1, name, address, phone_number, notes));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeVet;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateVet (VetsModelClass vetModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperVet.ID_ANIMAL_COL, vetModelClass.getId_animal());
        contentValues.put(DBHelperVet.NAME_COL, vetModelClass.getName());
        contentValues.put(DBHelperVet.ADDRESS_COL, vetModelClass.getAddress());
        contentValues.put(DBHelperVet.PHONE_NUMBER_COL, vetModelClass.getPhone_number());
        contentValues.put(DBHelperVet.NOTES_COL, vetModelClass.getNotes());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_COL + " = ?", new String[]
                {String.valueOf(vetModelClass.getId())});
    }

    public void deleteVet (int id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID_COL + " = ?", new String[]
                {String.valueOf(id)});
    }


}
