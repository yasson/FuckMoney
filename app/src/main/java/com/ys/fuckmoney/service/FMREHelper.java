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
        if (opened.size() > 0) {
            String up = node.signature.up;
            String down = node.signature.down;

            //1.up down 都存在
            //确定true 数据库中数据存在up和down有一个不同或者都不同
            //确定false 数据库中数据都存在down和up都相同
            //不确定 false 数据库中数据up和down有一个相同，领一个不存在
            if (!TextUtils.isEmpty(up) && !TextUtils.isEmpty(down)) {
                for (FMMoneyNodeInfo info : opened) {
                    if (!TextUtils.isEmpty(info.signature.up)) {
                        if (up.equals(info.signature.up)) {
                            if (!TextUtils.isEmpty(info.signature.down)) {
                                if (down.equals(info.signature.down)) {
                                    return false;
                                }
                            } else {
                                //up或者down有一个相同，另一个不存在，默认直接返回false
                                return false;
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(info.signature.down)) {
                        if (up.equals(info.signature.down)) {
                            if (!TextUtils.isEmpty(info.signature.up)) {
                                if (down.equals(info.signature.up)) {
                                    return false;
                                }
                            } else {
                                //up或者down有一个相同，另一个不存在，默认直接返回false
                                return false;
                            }
                        }
                    }
                }
            } else if (!TextUtils.isEmpty(up)) {
                //2.up 存在
                for (FMMoneyNodeInfo info : opened) {
                    if (TextUtils.isEmpty(info.signature.up) || up.equals(info.signature.up)) {
                        return false;
                    }
                }
            } else if (!TextUtils.isEmpty(down)) {
                //3 down 存在
                for (FMMoneyNodeInfo info : opened) {
                    if (TextUtils.isEmpty(info.signature.down) || up.equals(info.signature.down)) {
                        return false;
                    }
                }
            } else if (TextUtils.isEmpty(node.signature.up) && TextUtils.isEmpty(node.signature.down)) {
                //4都不存在
                for (FMMoneyNodeInfo info : opened) {
                    if (TextUtils.isEmpty(info.signature.up) && TextUtils.isEmpty(info.signature.down)) {
                        return false;
                    }
                }
            }
        }
        return true;

    }
}
