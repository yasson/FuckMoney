package com.ys.fuckmoney.db.table;


import android.database.sqlite.SQLiteDatabase;

import com.ys.fuckmoney.model.FMMoneyNodeInfo;

/**
 * Created by senyang on 2017/1/3.
 */

public class TbOpenedRE {

    public static final String TB_NAME = "opened_red_envelopes";
    public static final String K_ID = "id";
    public static final String K_TIME = "time";
    public static final String K_SELF = "first";
    public static final String K_UP = "second";
    public static final String K_DOWN = "third";

    private SQLiteDatabase mDb;

    public TbOpenedRE(SQLiteDatabase db) {
        mDb = db;
    }

    public void insert(FMMoneyNodeInfo nodeInfo) {
        String sql = "inert into " + TB_NAME + "("
                + K_TIME + "," + K_SELF + "," + K_UP + "," + K_DOWN + ") " +
                "values ("
                + nodeInfo.time + "," + nodeInfo.signature.sChild + "," + nodeInfo.signature.sParentUp + "," + nodeInfo.signature.sParentDown;
        mDb.execSQL(sql);
    }
}
