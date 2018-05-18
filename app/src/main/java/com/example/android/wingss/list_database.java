package com.example.android.wingss;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class list_database extends AppCompatActivity {
    ListView lv;
    ArrayList<String> mails;
    ArrayList<String> names;
    ArrayList<String> pwds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_database);
        Cursor cursorl=  readFromDB();
        cursorl.moveToFirst();
        while (cursorl.moveToNext()) {
            mails.add(cursorl.getString(0));
            pwds.add(cursorl.getString(1));
            names.add(cursorl.getString(2));
        }
        cursorl.close();
        lv=(ListView)findViewById(R.id.data);
        Dbadapter dbadapter=new Dbadapter(this,names,mails,pwds);
        lv.setAdapter(dbadapter);

    }
    private Cursor readFromDB() {



        SQLiteDatabase database = new Dbhelper(this).getReadableDatabase();

        String[] projection = {
                Dbcontract.Dbentry.COLUMN_MAIL,
                Dbcontract.Dbentry.COLUMN_PWD,
                Dbcontract.Dbentry.COLUMN_NAME

        };


        Cursor cursor;
        cursor = database.query(
                Dbcontract.Dbentry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );



//        cursor.close();
        return cursor;



    }
}