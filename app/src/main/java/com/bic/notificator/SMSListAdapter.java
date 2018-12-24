package com.bic.notificator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SMSListAdapter extends ArrayAdapter<SMSData> {

    private LayoutInflater inflater;
    private int resource;
    private ArrayList<SMSData> data;

    SMSListAdapter(Context context, int resource, ArrayList<SMSData> data) {
        super(context, resource, data);
        this.resource = resource;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(this.resource, parent, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView phoneNumber = (TextView) view.findViewById(R.id.phone);
//        TextView lont = (TextView) view.findViewById(R.id.lont);
//        TextView lat = (TextView) view.findViewById(R.id.lat);

        SMSData msg = this.data.get(position);

        title.setText(msg.getTitle());
        phoneNumber.setText(msg.getPhone());
//        lont.setText(String.valueOf(msg.getLont()));
//        lat.setText(String.valueOf(msg.getLat()));

        return view;
    }
}
