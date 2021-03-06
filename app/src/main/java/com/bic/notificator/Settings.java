package com.bic.notificator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    public static SharedPreferences sPref;
    static String numberForParse;
    Button btnSave;
    EditText ed;
    String text;

    public static boolean checkNumber(String string, Context context) {
        sPref = context.getSharedPreferences("numberparse", MODE_PRIVATE);
        numberForParse = sPref.getString("numberparse", "");
        return numberForParse.contains(string);
    }

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
                finish();
            }
        });

    }
}
