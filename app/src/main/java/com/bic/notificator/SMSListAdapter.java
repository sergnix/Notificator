package com.bic.notificator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SMSListAdapter extends ArrayAdapter<SMSData> {

    private LayoutInflater inflater;
    private int resource;
    private ArrayList<SMSData> data;
    CheckBox checkBox;

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

        TextView tpar = (TextView) view.findViewById(R.id.tpar);
        TextView lac = (TextView) view.findViewById(R.id.lac);
        TextView cid = (TextView) view.findViewById(R.id.cid);
        TextView mcc = (TextView) view.findViewById(R.id.mcc);
        TextView mns = (TextView) view.findViewById(R.id.mns);
        checkBox = view.findViewById(R.id.checkbox);

        SMSData msg = this.data.get(position);

        tpar.setText("T-" + msg.getTpar());
        lac.setText("LAC=" + msg.getLac());
        cid.setText("CID=" + msg.getCid());
        mcc.setText("MCC=" + msg.getMcc());
        mns.setText("MNS=" + msg.getMns());

        if (msg.isCoord) {
            checkBox.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
