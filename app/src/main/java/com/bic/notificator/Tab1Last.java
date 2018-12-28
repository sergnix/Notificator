package com.bic.notificator;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Tab1Last extends Fragment {

    List list;
    TextView phoneNumber;
    TextView lac;
    TextView cid;
    TextView mcc;
    TextView mns;
    MapView mapview;

    Button btn;
    Button btnfind;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1last, container, false);

        phoneNumber = (TextView) rootView.findViewById(R.id.phone);
        lac = (TextView) rootView.findViewById(R.id.lac);
        cid = (TextView) rootView.findViewById(R.id.cid);
        mcc = (TextView) rootView.findViewById(R.id.mcc);
        mns = (TextView) rootView.findViewById(R.id.mns);

        ContentResolver cr = rootView.getContext().getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            list = Arrays.asList(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY)).split("\\$"));
            c.close();
        }

        lac.setText((String) list.get(5));
        cid.setText((String) list.get(6));
        mcc.setText((String) list.get(3));
        mns.setText((String) list.get(4));

        btn = (Button) rootView.findViewById(R.id.showmap);

        final Map map = new Map(String.valueOf(list.get(10)));

        mapview = (MapView) rootView.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(map.getLon(), map.getLatt()), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        btnfind = (Button) rootView.findViewById(R.id.find);

        final Point pointOnMap = new Point(map.getLon(), map.getLatt());

        mapview.getMap().getMapObjects().addPlacemark(pointOnMap);

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapview = (MapView) rootView.findViewById(R.id.mapview);
                mapview.getMap().move(
                        new CameraPosition(pointOnMap, 20.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 3),
                        null);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(rootView.getContext(), MapViewActivity.class);
//                Double coordLon = map.getLon();
//                Double coordLatt = map.getLatt();
//                intention.putExtra("coordLon", coordLon);
//                intention.putExtra("coordLatt", coordLatt);
                startActivity(intention);
            }
        });

        return rootView;
    }


}
