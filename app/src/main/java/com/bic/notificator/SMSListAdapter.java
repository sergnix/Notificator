package com.bic.notificator;

import android.content.Context;
import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(this.resource, parent, false);

        TextView phoneNumber = (TextView) view.findViewById(R.id.phone);
        TextView number = (TextView) view.findViewById(R.id.number);
        TextView lac = (TextView) view.findViewById(R.id.lac);
        TextView cid = (TextView) view.findViewById(R.id.cid);
        TextView mcc = (TextView) view.findViewById(R.id.mcc);
        TextView mns = (TextView) view.findViewById(R.id.mns);

        SMSData msg = this.data.get(position);

        phoneNumber.setText("Телефонный номер: " + msg.getPhone());
        number.setText(String.valueOf(position + 1));
        lac.setText(msg.getLac());
        cid.setText(msg.getCid());
        mcc.setText(msg.getMcc());
        mns.setText(msg.getMns());

        return view;
    }
}
