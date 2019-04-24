package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Tab2ForToday extends ListFragment {

    public ArrayList<SMSData> listsms;
    public static SharedPreferences sPref;
    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    BroadcastReceiver br;
    FloatingActionButton fab;
    Integer col_selected;
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
        fab.hide();
        renderFragment(Objects.requireNonNull(getView()));
    }

    public View renderFragment(View rootView) {

        fab = (FloatingActionButton) Objects.requireNonNull(getActivity()).findViewById(R.id.fab);

        messageList = (ListView) rootView.findViewById(android.R.id.list);
        messageList.setBackgroundColor(Color.WHITE);
        sPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        Utils util = new Utils();

        if (sPref.getBoolean("showCurrentDate", true)) {
            listsms = util.getSmsCurrentDate(rootView.getContext());
        }

        if (sPref.getBoolean("showAllSMS", false)) {
            listsms = util.getAllSms(rootView.getContext());
        }

        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        Collections.sort(listsms, new BodyComparator());
        messageList.setAdapter(adapter);

        messageList.setOnItemClickListener((parent, view, position, id) -> {
            if (listsms.get(position).isCoord) {
                Intent intention = new Intent(getContext(), MapViewActivity.class);

                int countAllListItems = getListView().getCount();

                SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();

                int countChecked = getListView().getCheckedItemCount();

                fab.show();

                if (countChecked == 0) {
                    fab.hide();
                }

                col_selected = 0;

                fab.setOnClickListener(view1 -> {

                    for (int i = 0; i < countAllListItems; i++) {
                        if (sparseBooleanArray.get(i)) {
                            if (!(((SMSData) parent.getItemAtPosition(i)).getCoord() == null)) {
                                intention.putExtra("sms_checked_item_coord" + col_selected, ((SMSData) parent.getItemAtPosition(i)).getAzimuth() +"$"+((SMSData) parent.getItemAtPosition(i)).getCoord());
                                col_selected++;
                            }
                        }
                    }
                    startActivity(intention);
                });


                checkBox = view.findViewById(R.id.checkbox);
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        });

        return rootView;
    }

    static class BodyComparator implements Comparator<SMSData> {
        @Override
        public int compare(SMSData o1, SMSData o2) {
            if (!(o1.getTpar() == null) && !(o2.getTpar() == null)) {
                return getNumber(o1.getTpar()).compareTo(getNumber(o2.getTpar()));
            }
            return -1;
        }

        private Integer getNumber(String tpar) {
            return Integer.parseInt(tpar);
        }
    }
}
