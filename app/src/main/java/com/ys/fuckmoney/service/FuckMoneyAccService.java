package com.ys.fuckmoney.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;

import com.ys.fuckmoney.CoverView;
import com.ys.fuckmoney.core.AppInstance;
import com.ys.fuckmoney.db.FMDbManager;
import com.ys.fuckmoney.model.FMMoneyNodeInfo;
import com.ys.fuckmoney.utils.L;
import com.ys.fuckmoney.utils.SCTools;
import com.ys.fuckmoney.utils.T;

import java.util.List;

/**
 * main service
 * Created by ys on 2016/12/12.
 */

public class FuckMoneyAccService extends AccessibilityService {
    CoverView c;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder b = new Notification.Builder(this);
        b.setContentText("辅助关闭权限管理弹窗,mt提示弹窗等").setContentTitle("ys辅助服务");
        startForeground(110, b.build());
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        L.d(event.toString());
        dealMM(event);

    }

    /**
     * 处理微信问题
     */
    private void dealMM(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> ts = event.getText();
                boolean hasMoney = false;
                for (CharSequence s : ts) {
                    if (TextUtils.isEmpty(s))
                        continue;
                    if (s.toString().contains("微信红包")) {
                        hasMoney = true;
                        break;
                    }
                }
                if (hasMoney) {
                    Notification nf = (Notification) event.getParcelableData();
                    PendingIntent pi = nf.contentIntent;
                    try {
                        pi.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    //开始抢红包
                    openPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    //开始打开红包
//                    getPacket();
                } else if (className.equals("com.tencent.mm.ui.base.p")) {
//                    getPacket();
                }
                break;
        }
    }

    /**
     * 进入抢红包页面
     */
    private void openPacket() {
        final AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                recycle(nodeInfo);
            }
        }).start();

    }

    private void recycle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null)
            return;
//        if (nodeInfo.getClassName().toString().equals(ListView.class.getSimpleName())) {
//
//        }
        final Rect rect = new Rect();
        nodeInfo.getBoundsInScreen(rect);

        if (nodeInfo.getChildCount() == 0) {
            if (!TextUtils.isEmpty(nodeInfo.getText())) {
                if ("领取红包".endsWith(nodeInfo.getText().toString())) {
                    // TODO: 1/3/17 发现红包
                    //处理红包，得到红包的秘钥
                    //与之前24小时红包数据进行比较，看这个红包是否已经打开过
                    //打开过的话就不做任何动作
                    //为打开过打开并且信息写入数据库
                    FMMoneyNodeInfo node = new FMMoneyNodeInfo(nodeInfo);

                    if (FMREHelper.checkValidate(node)){
                        L.e("打开红包"+node.signature);
                        //新红包，存数据库并打开
                        FMDbManager.getInstance().insertOneRENode(node);
//                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        AccessibilityNodeInfo parent = nodeInfo.getParent();
                        while (parent != null) {
//                        if (parent.isCheckable()){
//                        }
//                            T.show("parent click");
                            // TODO: 1/3/17
//                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            parent = parent.getParent();
                        }
                    }else {
                        L.e("红包抢过了"+node.signature);
                    }

                }
            }
        } else {
            for (int i = 0, j = nodeInfo.getChildCount(); i < j; i++) {
                recycle(nodeInfo.getChild(i));

            }
        }
    }

    public void showView(Rect rect) {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.x = rect.left;
        lp.y = rect.top - SCTools.getStatusBarHeight();
        lp.width = rect.width();
        lp.height = rect.height();

        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.format = PixelFormat.TRANSLUCENT;
        if (c == null) {
            c = new CoverView(AppInstance.context());
            c.setRect(rect);
            windowManager.addView(c, lp);
        } else {
            c.setRect(rect);
            windowManager.updateViewLayout(c, lp);
        }
    }

    /**
     * 打开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void getPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByText("微信红包");
        List<AccessibilityNodeInfo> btns = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bdg");
        for (AccessibilityNodeInfo info : btns) {
            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @Override
    protected void onServiceConnected() {
        T.show("抢红包辅助服务开启");
        super.onServiceConnected();
    }

    @Override
    public void onInterrupt() {
        T.show("抢红包辅助服务关闭");
        L.w("onInterrupt");
    }
}
