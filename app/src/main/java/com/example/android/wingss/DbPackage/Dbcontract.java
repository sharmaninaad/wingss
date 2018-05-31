package com.example.android.wingss.DbPackage;

import android.provider.BaseColumns;

/**
 * Created by Ninaad on 5/16/2018.
 */

public final class Dbcontract {

        private Dbcontract() {
        }

        public static class Dbentry implements BaseColumns {

            public static final String TABLE_NAME = "user";
            public static final String COLUMN_MAIL = "mail";
            public static final String COLUMN_PWD = "password";
            public static final String COLUMN_NAME="name";

            public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    COLUMN_MAIL + " TEXT, " +
                    COLUMN_PWD + " TEXT," +
                    COLUMN_NAME + " TEXT" + ")";
        }

}
