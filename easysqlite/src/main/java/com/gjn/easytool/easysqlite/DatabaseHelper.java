package com.gjn.easytool.easysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author gjn
 * @time 2019/6/18 18:13
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
