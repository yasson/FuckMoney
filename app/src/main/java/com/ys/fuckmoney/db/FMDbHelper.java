package com.ys.fuckmoney.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 红包出数据创建与升级
 * Created by senyang on 2017/1/3.
 */

public class FMDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fuck_money_db";
    private static final int VERSION = 1;



    public FMDbHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    public FMDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConst.TBOpenedConst.create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
