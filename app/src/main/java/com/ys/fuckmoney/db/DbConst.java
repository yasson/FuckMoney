package com.ys.fuckmoney.db;

/**
 * Created by nufeng on 1/4/17.
 */

public class DbConst {

    public static class TBOpenedConst {

        public static final String TB_NAME = "opened_red_envelopes";

        public static final String C_ID = "id";
        public static final String C_TIME = "time";
        public static final String C_SELF = "self";
        public static final String C_UP = "up";
        public static final String C_DOWN = "down";

        public static final String create = "create table if not exists " + TB_NAME + " (" + C_TIME + " long," + C_SELF + " text," + C_UP + " text," + C_DOWN + ")";
    }
}
