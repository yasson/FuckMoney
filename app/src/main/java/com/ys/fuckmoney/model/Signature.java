package com.ys.fuckmoney.model;

/**
 * Created by nufeng on 1/4/17.
 *
 * 签名分为3部分，
 * 一部分是子节点以及兄弟节点组成的信息，
 * 一部分是父节点以及父兄弟节点组成的信息
 * 一部分是父兄弟节点包括上节点和下节点
 */

public class Signature {

    public String self;
    public String up;
    public String down;

    @Override
    public String toString() {
        return "Signature{" +
                "self='" + self + '\'' +
                ", up='" + up + '\'' +
                ", down='" + down + '\'' +
                '}';
    }
}
