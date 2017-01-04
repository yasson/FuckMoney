package com.ys.fuckmoney.service;

import android.view.accessibility.AccessibilityNodeInfo;

import com.ys.fuckmoney.db.FMDbManager;
import com.ys.fuckmoney.model.FMMoneyNodeInfo;
import com.ys.fuckmoney.model.Signature;
import com.ys.fuckmoney.utils.L;

import java.util.List;

/**
 * Created by nufeng on 1/4/17.
 */

public class FMREHelper {

    /**
     * 判断这个红包节点是否有效是通过{@link Signature 进行判断}
     *
     * @param nodeInfo 当前节点
     *
     * true  节点有效，可以打开;
     * false 无效节点,打开过了
     *
     */
    public static boolean checkValidate(AccessibilityNodeInfo nodeInfo) {
        FMMoneyNodeInfo node = new FMMoneyNodeInfo(nodeInfo);

        List<FMMoneyNodeInfo> opened = FMDbManager.getInstance().getOneDayREInfoByNode(node);

        if (opened.size() == 0) {
            FMDbManager.getInstance().insertOneRENode(node);
            return true;
        }
        L.e("红包抢过了"+node.signature.toString());


        for( FMMoneyNodeInfo info:opened){

        }

        for (int i = 0,j=opened.size();i<j;i++){

        }
        return false;

    }
}
