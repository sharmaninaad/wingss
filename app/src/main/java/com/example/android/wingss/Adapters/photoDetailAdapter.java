package com.example.android.wingss.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.wingss.Activities.JavaClasses.Photo_fb;
import com.example.android.wingss.R;

import java.util.List;


/**
 *
 * Created by Ninaad on 5/30/2018.
 */

@SuppressWarnings("unchecked")
public class photoDetailAdapter extends ArrayAdapter {
    private final Activity context;
    private List<Photo_fb> photos;

    public photoDetailAdapter(Activity context, List<Photo_fb> photos) {
        super(context, R.layout.list_photo, photos);
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.list_photo, parent, true);
        TextView txtdate = (TextView) rowView.findViewById(R.id.date_view);
        TextView txtname = (TextView) rowView.findViewById(R.id.name_view);
        TextView txtid = (TextView) rowView.findViewById(R.id.id_view);

        txtdate.setText(photos.get(position).getCreated_time());
        txtname.setText(photos.get(position).getName());
        txtid.setText(photos.get(position).getId());
        return rowView;
    }
}
