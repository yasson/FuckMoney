package com.ys.fuckmoney.db.table;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ys.fuckmoney.model.FMMoneyNodeInfo;
import com.ys.fuckmoney.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ys.fuckmoney.db.DbConst.TBOpenedConst.*;

/**
 * Created by senyang on 2017/1/3.
 */

public class TbOpenedRE {


    private SQLiteDatabase mDb;

    public TbOpenedRE(SQLiteDatabase db) {
        mDb = db;
    }

    public void insert(FMMoneyNodeInfo nodeInfo) {
        String sql = "insert into " + TB_NAME + "("
                + C_TIME + "," + C_SELF + "," + C_UP + "," + C_DOWN + ") " +
                "values ("
                + nodeInfo.time + ",\'" + nodeInfo.signature.self + "\',\'" + nodeInfo.signature.up + "\',\'" + nodeInfo.signature.down+"\')";
        mDb.execSQL(sql);
    }

    /**
     * 根据 self 秘钥去查找数据库中类似红包，之查找一天内打开过的
     */
    public List<FMMoneyNodeInfo> getNodesToday(FMMoneyNodeInfo nodeInfo) {
        String sql = "select * from " + TB_NAME + " where " + C_SELF + "=\'" + nodeInfo.signature.self + "\' and " + C_TIME + ">" + TimeUtils
                .getOneDayBeforeLong();
        Cursor cursor = null;
        List<FMMoneyNodeInfo> nodes = new ArrayList<>();
        try {
            cursor = mDb.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FMMoneyNodeInfo info = new FMMoneyNodeInfo();
                    info.time = cursor.getLong(cursor.getColumnIndex(C_TIME));
                    info.signature.self = cursor.getString(cursor.getColumnIndex(C_SELF));
                    info.signature.up = cursor.getString(cursor.getColumnIndex(C_UP));
                    info.signature.down = cursor.getString(cursor.getColumnIndex(C_DOWN));
                    nodes.add(info);
                }while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nodes;
    }
}
