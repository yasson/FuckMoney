package com.ys.fuckmoney.service;

import android.text.TextUtils;

import com.ys.fuckmoney.db.FMDbManager;
import com.ys.fuckmoney.model.FMMoneyNodeInfo;
import com.ys.fuckmoney.model.Signature;

import java.util.List;

/**
 * Created by nufeng on 1/4/17.
 */

public class FMREHelper {

    /**
     * 判断这个红包节点是否有效是通过{@link Signature 进行判断}
     *
     * @param node 当前节点
     *
     * true  节点有效，可以打开;
     * false 无效节点,打开过了
     *
     */
    public static boolean checkValidate(FMMoneyNodeInfo node) {

        List<FMMoneyNodeInfo> opened = FMDbManager.getInstance().getOneDayREInfoByNode(node);
//        if (opened.size()>0)
//            return false;
        if (opened.size() > 0) {
            String up = node.signature.up;
            String down = node.signature.down;

            //1.up down 都存在
            //确定true 数据库中数据存在up和down有一个不同或者都不同
            //确定false 数据库中数据都存在down和up都相同
            //不确定 false 数据库中数据up和down有一个相同，领一个不存在
            if (node.signature.hasUp() && node.signature.hasDown()) {
                for (FMMoneyNodeInfo old : opened) {
                    if (old.signature.hasUp()) {
                        if (up.equals(old.signature.up)) {
                            if (old.signature.hasDown()) {
                                if (down.equals(old.signature.down)) {
                                    return false;
                                }
                            } else {
                                //up或者down有一个相同，另一个不存在，默认直接返回false
                                return false;
                            }
                        }
                    }
                    if (old.signature.hasDown()) {
                        if (up.equals(old.signature.down)) {
                            if (old.signature.hasUp()) {
                                if (down.equals(old.signature.up)) {
                                    return false;
                                }
                            } else {
                                //up或者down有一个相同，另一个不存在，默认直接返回false
                                return false;
                            }
                        }
                    }
                }
            } else if (node.signature.hasUp()) {
                //2.up 存在
                for (FMMoneyNodeInfo old : opened) {
                    if (!old.signature.hasUp() || up.equals(old.signature.up)) {
                        // TODO: 1/5/17 这里存在bug,先save一次,防止已经被标记忽略的红包再次打开
//                        FMDbManager.getInstance().insertOneRENode(node);
                        return false;
                    }
                }
            } else if (node.signature.hasDown()) {
                //3 down 存在
                for (FMMoneyNodeInfo old : opened) {
                    if (!old.signature.hasDown() || down.equals(old.signature.down)) {
                        return false;
                    }
                }
            } else if (!node.signature.hasUp() && !node.signature.hasDown()) {
                //4都不存在
                for (FMMoneyNodeInfo old : opened) {
                    if (!old.signature.hasUp() && !old.signature.hasDown()) {
                        return false;
                    }
                }
            }
        }
        return true;

    }
}
