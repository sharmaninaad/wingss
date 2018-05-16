package com.example.android.wingss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText name_edit;
    EditText pwd_edit;
    EditText mail_edit;
    EditText phone_edit;
    Button ver_btn;
    Button opt_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name_edit=(EditText)findViewById(R.id.user);
        pwd_edit=(EditText)findViewById(R.id.pwd);
        mail_edit=(EditText)findViewById(R.id.mail);
        phone_edit=(EditText)findViewById(R.id.phone);

        mail_edit.setVisibility(View.INVISIBLE);
        phone_edit.setVisibility(View.INVISIBLE);

        ver_btn=(Button)findViewById(R.id.versatile);
        opt_btn=(Button)findViewById(R.id.option);

        opt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt_btn.getText().toString().equals("or singup"))
                {
                    ver_btn.setText("signup");
                    opt_btn.setText("or login");
                    mail_edit.setVisibility(View.VISIBLE);
                    phone_edit.setVisibility(View.VISIBLE);
                }
                else if(opt_btn.getText().toString().equals("or login"))
                {
                    ver_btn.setText("login");
                    opt_btn.setText("or signup");

                    mail_edit.setVisibility(View.INVISIBLE);
                    phone_edit.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
}
