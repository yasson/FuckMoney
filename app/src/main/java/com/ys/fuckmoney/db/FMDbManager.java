package com.ys.fuckmoney.db;

import com.ys.fuckmoney.core.AppInstance;
import com.ys.fuckmoney.db.table.TbOpenedRE;
import com.ys.fuckmoney.model.FMMoneyNodeInfo;

import java.util.List;

/**
 * 红包数据库管理
 * Created by senyang on 2017/1/3.
 */

public class FMDbManager {

    private static FMDbManager sInstance;
    private TbOpenedRE mTbOpened;

    private FMDbManager() {
        FMDbHelper dbHelper = new FMDbHelper(AppInstance.context());
        mTbOpened = new TbOpenedRE(dbHelper.getReadableDatabase());
    }

    public static FMDbManager getInstance() {
        if (sInstance == null) {
            synchronized (FMDbManager.class) {
                if (sInstance == null) {
                    sInstance = new FMDbManager();
                }
            }
        }
        return sInstance;
    }

    public void insertOneRENode(FMMoneyNodeInfo nodeInfo) {
        mTbOpened.insert(nodeInfo);
    }

    public List<FMMoneyNodeInfo> getOneDayREInfoByNode(FMMoneyNodeInfo nodeInfo){
        return mTbOpened.getNodesToday(nodeInfo);
    }
}
