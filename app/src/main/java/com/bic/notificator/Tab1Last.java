package com.bic.notificator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Tab1Last extends ListFragment {

    public static final String BROADCAST_ACTION = "tabs_renew";
    public ArrayList<SMSData> listsms;
    ListView messageList;
    ArrayAdapter<SMSData> adapter;
    BroadcastReceiver br;
    FloatingActionButton fab;
    Integer col_selected;
    CheckBox checkBox;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey("5dd517ed-ca71-4d05-b644-58b979f0d724");

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
        renderFragment(Objects.requireNonNull(getView()));
    }

    public View renderFragment(final View rootView) {
        messageList = (ListView) rootView.findViewById(android.R.id.list);
        messageList.setBackgroundColor(Color.WHITE);

        Utils util = new Utils();

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SMSData selectedSMS = ((SMSData) parent.getItemAtPosition(position));
                Intent intention = new Intent(getContext(), MapViewActivity.class);

//                StringBuilder prompt = new StringBuilder("Вы выбрали " + selectedSMS.getTpar() + "\n");
//                prompt.append("Выбранные элементы: \n");
                int count = getListView().getCount();
                SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();
                col_selected = 0;
                for (int i = 0; i < count; i++) {
                    if (sparseBooleanArray.get(i)) {
//                prompt.append(((SMSData) l.getItemAtPosition(i)).getTpar()).append("\n");
                        if (!((SMSData) parent.getItemAtPosition(i)).getCoord().isEmpty()) {
                            intention.putExtra("sms_checked_item_coord" + col_selected , ((SMSData) parent.getItemAtPosition(i)).getCoord());
                            col_selected =+ 1;
                        }
                    }
                }
//                Toast.makeText(getActivity(), prompt.toString(), Toast.LENGTH_LONG).show();
                fab = (FloatingActionButton) Objects.requireNonNull(getActivity()).findViewById(R.id.fab);
                fab.show();

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                intention.putExtra("raw", String.valueOf(SMSBodyItem.get(10)));
//                intention.putExtra("sms_checked", bundle);
                        startActivity(intention);
                    }
                });


                checkBox = view.findViewById(R.id.checkbox);
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
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