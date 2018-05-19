package com.example.android.wingss;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class list_database extends AppCompatActivity {
    ListView lv;
    public static ArrayList<String> mails = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> pwds= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_database);
        Cursor cursorl=  readFromDB();
        cursorl.moveToFirst();
      do
        {
            mails.add(cursorl.getString(0));
            pwds.add(cursorl.getString(1));
            names.add(cursorl.getString(2));
        } while (cursorl.moveToNext());
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

        String selection =
                "" ;
        String[] selectionArgs = { };
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
