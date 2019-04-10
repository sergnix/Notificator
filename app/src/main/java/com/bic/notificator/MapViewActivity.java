package com.bic.notificator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This is a basic example that displays a map and sets camera focus on the target location.
 * Note: When working on your projects, remember to request the required permissions.
 */
public class MapViewActivity extends Activity {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final String MAPKIT_API_KEY = "5dd517ed-ca71-4d05-b644-58b979f0d724";

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Set the api key before calling initialize on MapKitFactory.
         * It is recommended to set api key in the Application.onCreate method,
         * but here we do it in each activity to make examples isolated.
         */
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        /**
         * Initialize the library to load required native libraries.
         * It is recommended to initialize the MapKit library in the Activity.onCreate method
         * Initializing in the Application.onCreate method may lead to extra calls and increased battery use.
         */
        MapKitFactory.initialize(this);
        // Now MapView can be created.
        setContentView(R.layout.activity_map);
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.mapview);

        Intent intention = getIntent();

        Bundle raw = intention.getExtras();

        for (int i = 0; i < Objects.requireNonNull(raw).size(); i++) {
            Map map = new Map(Objects.requireNonNull(raw.getString("sms_checked_item_coord" + i)));
            Point point = new Point(map.getLon(), map.getLatt());
//            mapView.getMap().move(
//               new CameraPosition(point, 14.0f, 0.0f, 0.0f),
//               new Animation(Animation.Type.SMOOTH, 0), null);
            mapView.getMap().getMapObjects().addPlacemark(point);

        }
        // And to show what can be done with it, we move the camera to the center of Saint Petersburg.
//        mapView.getMap().move(
//                new CameraPosition(point, 14.0f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 0), null);


    }
    @Override
    protected void onStop() {
        // Activity onStop call must be passed to both MapView and MapKit instance.
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}