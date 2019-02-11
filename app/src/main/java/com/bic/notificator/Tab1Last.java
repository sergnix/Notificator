package com.bic.notificator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Tab1Last extends Fragment {

    public static final String BROADCAST_ACTION = "tabs_renew";
    public ArrayList<SMSData> listsms;
    TextView phoneNumber;
    TextView lac;
    TextView cid;
    TextView mcc;
    TextView mns;
    TextView address;
    MapView mapview;
    List<String> SMSBodyItem;
    Button btn;
    BroadcastReceiver br;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey("5dd517ed-ca71-4d05-b644-58b979f0d724");

        br = new SMSReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                sendNotification("Получены новые данные о БС", "");
                mapview.onStop();
                MapKitFactory.getInstance().onStop();
                onResume();
            }
        };
        IntentFilter intFilt = new IntentFilter(Tab1Last.BROADCAST_ACTION);
        Objects.requireNonNull(getContext()).registerReceiver(br, intFilt);

        return inflater.inflate(R.layout.tab1last, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        renderFragment(Objects.requireNonNull(getView()));
    }

    public void renderFragment(final View rootView) {
        phoneNumber = (TextView) rootView.findViewById(R.id.phone);
        lac = (TextView) rootView.findViewById(R.id.lac);
        cid = (TextView) rootView.findViewById(R.id.cid);
        mcc = (TextView) rootView.findViewById(R.id.mcc);
        mns = (TextView) rootView.findViewById(R.id.mns);
        address = (TextView) rootView.findViewById(R.id.address);
        btn = (Button) rootView.findViewById(R.id.showmap);
        mapview = (MapView) rootView.findViewById(R.id.mapview);

        Utils util = new Utils();
        listsms = util.getAllSms(rootView.getContext());

        if (listsms.isEmpty()) {
            lac.setText("");
            cid.setText("");
            mcc.setText("");
            mns.setText("");
            address.setText("");
            return;
        } else {
            SMSBodyItem = Arrays.asList(listsms.get(0).getBody().split("\\$"));

            lac.setText((String) SMSBodyItem.get(5));
            cid.setText((String) SMSBodyItem.get(6));
            mcc.setText((String) SMSBodyItem.get(3));
            mns.setText((String) SMSBodyItem.get(4));
            address.setText((String) SMSBodyItem.get(8));

            final Map map = new Map(String.valueOf(SMSBodyItem.get(10)));

            final Point pointOnMap = new Point(map.getLon(), map.getLatt());

            mapview.getMap().move(
                    new CameraPosition(pointOnMap, 11.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
            mapview.getMap().getMapObjects().addPlacemark(pointOnMap);
            MapKitFactory.initialize(this.requireContext());
            MapKitFactory.getInstance().onStart();
            mapview.onStart();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(rootView.getContext(), MapViewActivity.class);
                intention.putExtra("raw", String.valueOf(SMSBodyItem.get(10)));
                startActivity(intention);
            }
        });

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