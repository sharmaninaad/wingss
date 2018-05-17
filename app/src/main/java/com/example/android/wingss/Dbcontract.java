package com.example.android.wingss;

import android.provider.BaseColumns;

/**
 * Created by Ninaad on 5/16/2018.
 */

public final class Dbcontract {

        private Dbcontract() {
        }

        public static class Dbentry implements BaseColumns {
            public static final String _ID = "id";

            public static final String TABLE_NAME = "user";
            public static final String COLUMN_NAME = "username";
            public static final String COLUMN_PWD = "password";
            public static final String COLUMN_MAIL = "mail";

            public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PWD + " TEXT, " +
                    COLUMN_MAIL + " TEXT" + ")";
        }

}
