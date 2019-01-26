package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
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
import java.util.Objects;

public class Tab2ForToday extends Fragment {

    ListView messageList;
    ArrayAdapter<SMSData> adapter;

    public ArrayList<SMSData> listsms;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

//        if (listsms.isEmpty()) {
//            Intent intention = new Intent(this.getContext(), Settings.class);
//            startActivity(intention);
//        }

//        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "No message to show!", Toast.LENGTH_SHORT).show();
//                Intent intention = new Intent(getContext(), MapViewActivity.class);
//                startActivity(intention);
//                return true;
//            }
//        });

        return renderActivity(inflater.inflate(R.layout.tab2fortoday, container, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        renderActivity(Objects.requireNonNull(getView()));
    }

    public View renderActivity(View rootView) {
        messageList = (ListView) rootView.findViewById(R.id.listsms);
        Utils util = new Utils();
        listsms = util.getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);

        return rootView;
    }


}
