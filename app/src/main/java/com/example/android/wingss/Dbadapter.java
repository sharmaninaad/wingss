package com.example.android.wingss;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.android.wingss.R.id.imageView;
import static com.example.android.wingss.R.id.txt;

/**
 * Created by Ninaad on 5/18/2018.
 */

public class Dbadapter extends ArrayAdapter {


    private final Activity context;
    ArrayList<String> nams;
    ArrayList<String> malls;
    ArrayList<String> pdds;
    public Dbadapter(Activity context, ArrayList<String> nams, ArrayList<String> malls, ArrayList<String> pdds) {
        super(context, R.layout.dblist, malls);
        this.context = context;
        this.nams=nams;
        this.malls=malls;
        this.pdds=pdds;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.dblist, null, true);
        TextView txtmail = (TextView) rowView.findViewById(R.id.mail_list);
        TextView txtname = (TextView) rowView.findViewById(R.id.name_list);
        TextView txtpwd = (TextView) rowView.findViewById(R.id.pwd_list);

        txtmail.setText(malls.get(position));
        txtname.setText(nams.get(position));
        txtpwd.setText(pdds.get(position));
        return rowView;
    }
}
