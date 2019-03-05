package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class Tab2ForToday extends Fragment {

    public ArrayList<SMSData> listsms;
    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    BroadcastReceiver br;
    FloatingActionButton fab;

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
        return renderFragment(inflater.inflate(R.layout.tab2fortoday, container, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        renderFragment(Objects.requireNonNull(getView()));
    }

    public View renderFragment(View rootView) {
        messageList = (ListView) rootView.findViewById(R.id.listsms);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        Utils util = new Utils();

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray sp = messageList.getCheckedItemPositions();

                String selectedItems = "";
                for (int i = 0; i < listsms.size(); i++) {
                    if (sp.get(i))
                        selectedItems += 1;
                }
                Log.d("DEBUG1", selectedItems);
                fab.show();
            }
        });
        listsms = util.getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);


        return rootView;
    }
}
