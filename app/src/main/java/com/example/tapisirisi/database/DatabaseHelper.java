package com.example.tapisirisi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tapisirisi.logic.model.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tapisIrisi4.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + UserContrat.UserTable.TABLE_NAME + " ( " +
                    UserContrat.UserTable._ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                    UserContrat.UserTable.ID_USER + INT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_NOM + TEXT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_PRENOM + TEXT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_ROLE + TEXT_TYPE + COMMA_SEP +
                    UserContrat.UserTable.COLUMN_NAME_PROFILE_IMAGE + BLOB_TYPE + "  )";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + UserContrat.UserTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("info",user.toString());
        db.execSQL(SQL_DELETE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        ContentValues values = new ContentValues();
        values.put(UserContrat.UserTable.ID_USER, user.getId().toString());
        values.put(UserContrat.UserTable.COLUMN_NAME_NOM, user.getNom());
        values.put(UserContrat.UserTable.COLUMN_NAME_PRENOM, user.getPrenom());
        values.put(UserContrat.UserTable.COLUMN_NAME_LOGIN, user.getLogin());
        values.put(UserContrat.UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(UserContrat.UserTable.COLUMN_NAME_ROLE, user.getRole().toString());
        values.put(UserContrat.UserTable.COLUMN_NAME_PROFILE_IMAGE, user.getProfile_image());

        long newRowId = db.insert(UserContrat.UserTable.TABLE_NAME, null, values);
    }

    public void update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContrat.UserTable.COLUMN_NAME_NOM,user.getNom());
        values.put(UserContrat.UserTable.COLUMN_NAME_PRENOM,user.getPrenom());
        values.put(UserContrat.UserTable.COLUMN_NAME_PASSWORD,user.getPassword());
        db.update(UserContrat.UserTable.TABLE_NAME,values," LOGIN=?",new String[]{user.getLogin()});
    }

    public void delete(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(UserContrat.UserTable.TABLE_NAME, "LOGIN = ?", new String[]{user.getLogin()});
    }

    public User getCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> users = new ArrayList<>();
        String[] projection = {
                UserContrat.UserTable.ID_USER,
                UserContrat.UserTable._ID,
                UserContrat.UserTable.COLUMN_NAME_NOM,
                UserContrat.UserTable.COLUMN_NAME_PRENOM,
                UserContrat.UserTable.COLUMN_NAME_LOGIN,
                UserContrat.UserTable.COLUMN_NAME_PASSWORD,
                UserContrat.UserTable.COLUMN_NAME_PROFILE_IMAGE,
                UserContrat.UserTable.COLUMN_NAME_ROLE,
        };
        Cursor c = db.query(
                UserContrat.UserTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            User user = new User();
            user.setId(c.getLong(c.getColumnIndex(UserContrat.UserTable.ID_USER)));
            user.setLogin(c.getString(c.getColumnIndex(UserContrat.UserTable.COLUMN_NAME_LOGIN)));
            user.setNom(c.getString(c.getColumnIndex(UserContrat.UserTable.COLUMN_NAME_NOM)));
            user.setPassword(c.getString(c.getColumnIndex(UserContrat.UserTable.COLUMN_NAME_PASSWORD)));
            user.setPrenom(c.getString(c.getColumnIndex(UserContrat.UserTable.COLUMN_NAME_PRENOM)));
            user.setProfile_image(c.getBlob(c.getColumnIndex(UserContrat.UserTable.COLUMN_NAME_PROFILE_IMAGE)));
            users.add(user);
            c.moveToNext();
        }
        if(users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }

    }

}
