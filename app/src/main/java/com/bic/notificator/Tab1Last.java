package com.bic.notificator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.yandex.mapkit.MapKitFactory;

import java.util.ArrayList;
import java.util.Objects;

public class Tab1Last extends ListFragment {

    public static final String BROADCAST_ACTION = "tabs_renew";
    public ArrayList<SMSData> listsms;
    public int col_selected;
    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    BroadcastReceiver br;
    FloatingActionButton fab;
    CheckBox checkBox;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        br = new SMSReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isTrulySMS = intent.getBooleanExtra("isTrulySMS", false);
                if (isTrulySMS) {
                    sendNotification("Получены новые данные о БС", "");
//                    MapKitFactory.getInstance().onStop();
                    onResume();
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(Tab1Last.BROADCAST_ACTION);
        Objects.requireNonNull(getContext()).registerReceiver(br, intFilt);
        return renderFragment(inflater.inflate(R.layout.tab1last, container, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.hide();
        renderFragment(Objects.requireNonNull(getView()));
    }

    public View renderFragment(final View rootView) {
        fab = (FloatingActionButton) Objects.requireNonNull(getActivity()).findViewById(R.id.fab);

        messageList = (ListView) rootView.findViewById(android.R.id.list);
        messageList.setBackgroundColor(Color.WHITE);

        Utils util = new Utils();

        messageList.setOnItemClickListener((parent, view, position, id) -> {

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
                        if (!((SMSData) parent.getItemAtPosition(i)).getCoord().isEmpty()) {
                            intention.putExtra("sms_checked_item_coord" + col_selected, ((SMSData) parent.getItemAtPosition(i)).getCoord());
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
        });


        listsms = util.getAllSms(rootView.getContext());
        adapter = new SMSListAdapter(this.getContext(), R.layout.sms_list_item, listsms);
        messageList.setAdapter(adapter);

        return rootView;

    }

    private void sendNotification(String title, String body) {
        createNotificationChannel();
        Intent i = new Intent(getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(getContext(), 1 /* Request code */, i, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Objects.requireNonNull(getContext()), "1")
                .setSmallIcon(R.drawable.signal)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(2)
                .setContentIntent(pi);
        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SMS";
            String description = "BS";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = Objects.requireNonNull(getContext()).getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}