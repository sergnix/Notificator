package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Tab2ForToday extends ListFragment {

    public ArrayList<SMSData> listsms;
    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    BroadcastReceiver br;
    int backgroundColor;
    CheckBox checkBox;

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SMSData selectedSMS = ((SMSData) l.getItemAtPosition(position));
        StringBuilder prompt = new StringBuilder("Вы выбрали " + selectedSMS.getTpar() + "\n");
        prompt.append("Выбранные элементы: \n");
        int count = getListView().getCount();
        SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();
        for (int i = 0; i < count; i++) {
            if (sparseBooleanArray.get(i)) {
                prompt.append(((SMSData) l.getItemAtPosition(i)).getTpar()).append("\n");
            }
        }
        Toast.makeText(getActivity(), prompt.toString(), Toast.LENGTH_LONG).show();
    }



    public View renderFragment(View rootView) {
        messageList = (ListView) rootView.findViewById(android.R.id.list);
        messageList.setBackgroundColor(Color.WHITE);

        Utils util = new Utils();

//        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                SparseBooleanArray sp = messageList.getCheckedItemPositions();
////
////                StringBuilder selectedItems = new StringBuilder();
////                for (int i = 0; i < listsms.size(); i++) {
////                    if (sp.get(i))
////                        selectedItems.append(1);
////                }
////                Log.d("DEBUG1", String.valueOf(selectedItems.length()));
////                fab.show();
////            }
////        });


        listsms = util.getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        listsms.sort(new BodyComparator());
        messageList.setAdapter(adapter);

        return rootView;
    }
    static class BodyComparator implements Comparator<SMSData> {
        @Override
        public int compare(SMSData o1, SMSData o2) {
            return getNumber(o1.getTpar()).compareTo(getNumber(o2.getTpar()));
        }
        private Integer getNumber(String tpar) {
            return Integer.parseInt(tpar.substring(2));
        }
    }
}
