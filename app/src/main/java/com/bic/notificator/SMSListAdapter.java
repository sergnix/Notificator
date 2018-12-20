package com.bic.notificator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SMSListAdapter extends ArrayAdapter<SMSData> {
    private final Context context;
    private final List<SMSData> smsList;

    public SMSListAdapter(Context context, List<SMSData> smsList) {
        super(context, R.layout.activity_main, smsList);
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_main, parent, false);

        TextView senderNumber = (TextView) rowView.findViewById(R.id.textsms);
        senderNumber.setText(smsList.get(position).getNumber());

        return rowView;
    }
}
