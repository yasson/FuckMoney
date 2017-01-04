package com.ys.fuckmoney.model;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;

/**
 * 红包节点信息
 * 用于判断红包是否有效
 * Created by nufeng on 1/3/17.
 */

public class FMMoneyNodeInfo {

    private AccessibilityNodeInfo mParentNode;
    private AccessibilityNodeInfo mParentListNode;
    private AccessibilityNodeInfo mNode;

    private int mIndexInParent;

    public long time;
    public Signature signature=new Signature();//用于做次红包唯一性判断

    public FMMoneyNodeInfo() {

    }
    public FMMoneyNodeInfo(AccessibilityNodeInfo node) {
        mNode = node;
        time = System.currentTimeMillis();
        getListParent(node);
        makeSignature();
    }

    private void makeSignature() {
        signature.self = getChildPart(mParentNode);
        signature.up = getUpParentPart();
        signature.down = getDownParent();
    }


    private int getIndex(){
        for (int i = 0, j = mParentListNode.getChildCount(); i<j; i++){
            if (mParentListNode.getChild(i) ==null)
                continue;
            Rect rect1 = new Rect();
            Rect rect2 = new Rect();
            mParentListNode.getChild(i).getBoundsInScreen(rect1);
            mParentNode.getBoundsInScreen(rect2);
            if (rect1.equals(rect2) ){
                return i;
            }
        }
        return -1;
    }

    private String getUpParentPart(){
        if (mIndexInParent -1>=0){
            AccessibilityNodeInfo up = mParentListNode.getChild(mIndexInParent -1);
            return getChildPart(up);
        }
        return null;
    }
    private String getDownParent(){
        if (mParentListNode.getChildCount()> mIndexInParent +1){
            AccessibilityNodeInfo down = mParentListNode.getChild(mIndexInParent +1);
            return getChildPart(down);
        }
        return null;
    }

    private void getListParent(AccessibilityNodeInfo node) {
        if (node.getParent()!=null){
            if (node.getParent().getClassName().equals(ListView.class.getName())) {
                mParentNode = node;
                mParentListNode = node.getParent();
                mIndexInParent = getIndex();
            } else {
                getListParent(node.getParent());
            }
        }

    }

    private String getChildPart(AccessibilityNodeInfo info) {
        String s = "";
        if (info.getChildCount() == 0 && !TextUtils.isEmpty(info.getText())) {
            s = info.getText().toString();
        } else {
            for (int i = 0, j = info.getChildCount(); i < j; i++) {
                AccessibilityNodeInfo c = info.getChild(i);
                s += getChildPart(c);
            }
        }
        return s;
    }

    public boolean equals(FMMoneyNodeInfo info) {

        return false;
    }
}
