package com.bic.notificator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

public class Settings extends AppCompatActivity {

    Button btnSave;
    public SharedPreferences sPref;
    EditText ed;
    String numberForParse;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSave = (Button) findViewById(R.id.save_setting_number);
        ed = (EditText) findViewById(R.id.numberForParseSMS);

        sPref = getSharedPreferences("numberparse", MODE_PRIVATE);

        if (sPref.contains("numberparse")) {
            numberForParse = sPref.getString("numberparse", "");
            ed.setText(numberForParse);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("numberparse", ed.getText().toString());
                editor.apply();
                Intent intention = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intention);
            }
        });
    }

    public boolean checkNumber(String string, Context context) {
        sPref = context.getSharedPreferences("numberparse", MODE_PRIVATE);
        numberForParse = sPref.getString("numberparse", "");
        System.out.println("debugg " + String.valueOf(numberForParse));
        return numberForParse.contains(string);
    }
}
