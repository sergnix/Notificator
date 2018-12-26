package com.bic.notificator;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tab2ForToday extends Fragment {

    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    ArrayList<SMSData> listsms;
    SharedPreferences sPref;
    String numberForParse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2fortoday, container, false);

        messageList = (ListView) rootView.findViewById(R.id.listsms);
        listsms = getAllSms(getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);

        return rootView;
    }

    public ArrayList<SMSData> getAllSms(Context context) {
        ArrayList<SMSData> smsList;
        smsList = new ArrayList<SMSData>();
        ContentResolver cr = context.getContentResolver();
        int totalSMS = 0;
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        Settings settings = new Settings();
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    if (settings.checkNumber(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), getContext())) {
                        smsList.add(new SMSData(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))));
                    }
                    c.moveToNext();
                }
                c.close();
            }

        } else {
            Toast.makeText(this.getContext(), "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return smsList;
    }

}
