package com.example.android.wingss.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.wingss.R;

import java.util.ArrayList;

/**
 * Created by Ninaad on 5/30/2018.
 */

public class photo_detail_adapter extends ArrayAdapter {
    private final Activity context;
    ArrayList<String> date_created;
    ArrayList<String> name;
    ArrayList<String> id;

    public photo_detail_adapter(Activity context, ArrayList<String> date_created, ArrayList<String> name, ArrayList<String> id) {
        super(context, R.layout.list_photo, name);
        this.context = context;
        this.name = name;
        this.date_created = date_created;
        this.id = id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_photo, null, true);
        TextView txtdate = (TextView) rowView.findViewById(R.id.date_view);
        TextView txtname = (TextView) rowView.findViewById(R.id.name_view);
        TextView txtid = (TextView) rowView.findViewById(R.id.id_view);

        txtdate.setText(date_created.get(position));
        txtname.setText(name.get(position));
        txtid.setText(id.get(position));
        return rowView;
    }
}
