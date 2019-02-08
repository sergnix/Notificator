package com.bic.notificator;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class Utils {
    ArrayList<SMSData> getAllSms(Context context) {
        ArrayList<SMSData> smsList;
        smsList = new ArrayList<SMSData>();
        ContentResolver cr = context.getContentResolver();
        int totalSMS = 0;
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        Settings settings = new Settings();
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    if (settings.checkNumber(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), context) && isDateBetween(Long.parseLong(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))))) {
                        smsList.add(new SMSData(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY)), c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)), c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))));
                    }
                    c.moveToNext();
                }
                c.close();
            }
        } else {
            Toast.makeText(context, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return smsList;
    }

    private boolean checkDateSMS(Long date) {
        Date currentTime = Calendar.getInstance().getTime();
        Date datesms = new Date();
        datesms.setTime(date);

        return !(currentTime.before(datesms) || currentTime.after(datesms));
    }

    private static boolean isDateBetween(Long date) {
        Calendar calInitial = setZeroHour(Calendar.getInstance().getTime());
        Calendar calFinal = setLastHout(Calendar.getInstance().getTime());
        Calendar calToday = Calendar.getInstance();
        Date datesms = new Date(date);
        calToday.setTime(datesms);

        return (calToday.getTime().getTime() >= calInitial.getTime().getTime()) &&
                (calToday.getTime().getTime() <= calFinal.getTime().getTime());

    }

    private static Calendar setZeroHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return calendar;
    }

    private static Calendar setLastHout(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        return calendar;
    }
}
