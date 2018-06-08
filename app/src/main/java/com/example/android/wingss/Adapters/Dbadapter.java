package com.example.android.wingss.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.wingss.R;

import java.util.ArrayList;

/**
 * Created by Ninaad on 5/18/2018.\
 *
 */

@SuppressWarnings("unchecked")
public class Dbadapter extends ArrayAdapter {


    private final Activity context;
    private ArrayList<String> nams;
    private ArrayList<String> malls;
    private ArrayList<String> pdds;
    public Dbadapter(Activity context, ArrayList<String> nams, ArrayList<String> malls, ArrayList<String> pdds) {
        super(context, R.layout.dblist, malls);
        this.context = context;
        this.nams=nams;
        this.malls=malls;
        this.pdds=pdds;
    }
    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView= inflater.inflate(R.layout.dblist, parent, true);
        TextView txtmail = (TextView) rowView.findViewById(R.id.mail_list);
        TextView txtname = (TextView) rowView.findViewById(R.id.name_list);
        TextView txtpwd = (TextView) rowView.findViewById(R.id.pwd_list);

        txtmail.setText(malls.get(position));
        txtname.setText(nams.get(position));
        txtpwd.setText(pdds.get(position));
        return rowView;
    }
}
