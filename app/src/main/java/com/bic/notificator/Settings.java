package com.bic.notificator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    Button btnSave;
    SharedPreferences sPref;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ed = (EditText) findViewById(R.id.numberForParseSMS);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();

//                sPref.putString();
            }
        });

    }
}
