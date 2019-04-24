package com.bic.notificator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    public static SharedPreferences sPref;
    static String numberForParse;
    Button btnSave;
    RadioGroup radioGroup;
    RadioButton showCurrentDate;
    RadioButton showAllSMS;
    EditText ed;
    String text;

    public static boolean checkNumber(String string, Context context) {
        sPref = context.getSharedPreferences("numberparse", MODE_PRIVATE);
        numberForParse = sPref.getString("numberparse", "");
        return Objects.requireNonNull(numberForParse).contains(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor shEditor = sPref.edit();

        btnSave = (Button) findViewById(R.id.save_setting_number);
        ed = (EditText) findViewById(R.id.numberForParseSMS);
        showCurrentDate = (RadioButton) findViewById(R.id.radioButtonCurrentDate);
        showAllSMS = (RadioButton) findViewById(R.id.radioButtonAllSMS);

        showCurrentDate.setChecked(sPref.getBoolean("showCurrentDate",true));
        showAllSMS.setChecked(sPref.getBoolean("showAllSMS",false));

        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonCurrentDate:
                        Toast.makeText(getApplicationContext(), "Выбрано показать текущие смс",
                                Toast.LENGTH_SHORT).show();
                        shEditor.putBoolean("showCurrentDate", showCurrentDate.isChecked());
                        shEditor.putBoolean("showAllSMS", showAllSMS.isChecked());
                        shEditor.apply();
                        break;
                    case R.id.radioButtonAllSMS:
                        Toast.makeText(getApplicationContext(), "Выбрано показать все смс",
                                Toast.LENGTH_SHORT).show();
                        shEditor.putBoolean("showCurrentDate", showCurrentDate.isChecked());
                        shEditor.putBoolean("showAllSMS", showAllSMS.isChecked());
                        shEditor.apply();
                        break;
                    default:
                        break;
                }
            }
        });

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
