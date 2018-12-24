package com.bic.notificator;

import android.content.ContentResolver;
import android.content.Context;
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

public class Tab2ForToday extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2fortoday, container, false);
        return rootView;
    }

}
