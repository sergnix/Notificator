package com.bic.notificator;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tab2ForToday extends Fragment {

    ListView messageList;
    ArrayAdapter<SMSData> adapter;

    public ArrayList<SMSData> listsms;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2fortoday, container, false);

        messageList = (ListView) rootView.findViewById(R.id.listsms);
        listsms = getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);

        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "No message to show!", Toast.LENGTH_SHORT).show();
                Intent intention = new Intent(getContext(), MapViewActivity.class);
                startActivity(intention);
                return true;
            }
        });

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
                    if (settings.checkNumber(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), context)) {
                        smsList.add(new SMSData(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY)), c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))));
                    }
                    c.moveToNext();
                }
                c.close();
            }
        } else {
            Toast.makeText(context, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return smsList;
    }
}
