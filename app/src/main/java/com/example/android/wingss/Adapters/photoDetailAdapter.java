package com.example.android.wingss.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.wingss.Activities.JavaClasses.Photo_fb;
import com.example.android.wingss.R;

import java.util.List;

/**
 * Created by Ninaad on 5/30/2018.
 */

public class photoDetailAdapter extends ArrayAdapter {
    private final Activity context;
    List<Photo_fb> photos;

    public photoDetailAdapter(Activity context, List<Photo_fb> photos) {
        super(context, R.layout.list_photo, photos);
        this.context = context;
        // this.name = name;
        this.photos = photos;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_photo, null, true);
        TextView txtdate = (TextView) rowView.findViewById(R.id.date_view);
        TextView txtname = (TextView) rowView.findViewById(R.id.name_view);
        TextView txtid = (TextView) rowView.findViewById(R.id.id_view);

        txtdate.setText(photos.get(position).getCreated_time());
        txtname.setText(photos.get(position).getName());
        txtid.setText(photos.get(position).getId());
        return rowView;
    }
}
