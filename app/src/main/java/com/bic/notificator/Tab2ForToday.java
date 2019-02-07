package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class Tab2ForToday extends Fragment {

    ListView messageList;
    ArrayAdapter<SMSData> adapter;

    public ArrayList<SMSData> listsms;

    BroadcastReceiver br;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onResume();
            }
        };

        IntentFilter intFilt = new IntentFilter(Tab1Last.BROADCAST_ACTION);
        Objects.requireNonNull(getContext()).registerReceiver(br, intFilt);

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

        return renderFragment(inflater.inflate(R.layout.tab2fortoday, container, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        renderFragment(Objects.requireNonNull(getView()));
    }

//    @Override
//    public void onStop()
//    {
//        Objects.requireNonNull(getContext()).unregisterReceiver(br);
//        super.onStop();
//    }

    public View renderFragment(View rootView) {
        messageList = (ListView) rootView.findViewById(R.id.listsms);
        Utils util = new Utils();
        listsms = util.getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);

        return rootView;
    }
}
