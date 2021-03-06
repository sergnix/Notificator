package com.bic.notificator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    public static final String pdu_type = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        StringBuilder strMessage = new StringBuilder();
        String originatingAddress = "";
        String format = bundle != null ? bundle.getString("format") : null;
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) (bundle != null ? bundle.get(pdu_type) : null);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                strMessage.append(msgs[i].getOriginatingAddress());
                strMessage.append(msgs[i].getMessageBody());
            }
            Intent intent_tabs_renew = new Intent(Tab1Last.BROADCAST_ACTION);
            String sms_from = msgs[0].getDisplayOriginatingAddress();
            if (Settings.checkNumber(sms_from, context)) {
                intent_tabs_renew.putExtra("isTrulySMS", true);
            }
            context.sendBroadcast(intent_tabs_renew);
        }
    }
}

