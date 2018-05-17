package com.example.android.wingss;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.android.wingss.Dbcontract.Dbentry.COLUMN_NAME;

public class Login extends AppCompatActivity {
    TextView name_edit;
    TextView pwd_edit;
    //TextView mail_edit;
    //TextView phone_edit;
    Button ver_btn;
    //Button opt_btn;
   //int flag_login=1;
   // int flag_signup=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        name_edit=(TextView)findViewById(R.id.mail1);
        pwd_edit=(TextView) findViewById(R.id.pwd1);
       // mail_edit=(EditText)findViewById(R.id.mail);
       // phone_edit=(EditText)findViewById(R.id.phone);

       // mail_edit.setVisibility(View.INVISIBLE);
       // phone_edit.setVisibility(View.INVISIBLE);

        ver_btn=(Button)findViewById(R.id.versatile);
       // opt_btn=(Button)findViewById(R.id.option);

        /*opt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_login==1)
                {
                    ver_btn.setText("signup");
                    opt_btn.setText("or login");
                    flag_signup=1;
                    flag_login=0;
                    mail_edit.setVisibility(View.VISIBLE);
                    phone_edit.setVisibility(View.VISIBLE);
                }
                else if(flag_signup==1)
                {
                    ver_btn.setText("login");
                    opt_btn.setText("or signup");
                    flag_login=1;
                    flag_signup=0;
                    mail_edit.setVisibility(View.INVISIBLE);
                    phone_edit.setVisibility(View.INVISIBLE);
                }
            }
        });*/
        ver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      if(flag_login==1)
            //    {
                    String passdb=pwd_edit.getText().toString();
                    if(passdb.equals(readFromDB()))
                    {
                        Toast.makeText(Login.this, "Login succesful", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(Login.this,MainActivity.class));

                    }
                    else
                    {
                        pwd_edit.setText("");
                        Toast.makeText(Login.this, "Please enter the right password", Toast.LENGTH_SHORT).show();
                    }
                    
                }
              // if(flag_login==0)
               //{
                 //   saveToDB();
               //}
        //    }
        });


    }
  /*  private void saveToDB() {
        SQLiteDatabase database = new Dbhelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name_edit.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_PWD, pwd_edit.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_MAIL, mail_edit.getText().toString());



        long newRowId = database.insert(Dbcontract.Dbentry.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }*/
    private String readFromDB() {
        String name = name_edit.getText().toString();



        SQLiteDatabase database = new Dbhelper(this).getReadableDatabase();

        String[] projection = {
                COLUMN_NAME,
                Dbcontract.Dbentry.COLUMN_PWD,
                Dbcontract.Dbentry.COLUMN_MAIL
        };

        String selection =
                Dbcontract.Dbentry.COLUMN_NAME + " like ? " ;
        String[] selectionArgs = { name};

        Cursor cursor = database.query(
                Dbcontract.Dbentry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor==null)
        {
            Toast.makeText(this, "No user with the above name exists", Toast.LENGTH_SHORT).show();
        return null;}
        else {
            cursor.moveToFirst();
            String pwddb = cursor.getString(1);
            return pwddb;}

    }
    //To do: make a read from db function and then call them on buttons beong clicked
}
