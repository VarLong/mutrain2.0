package com.ssy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

	private static final String act = "android.provider.Telephony.SMS_RECEIVED";

	OnClickListener confirm;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(act)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				StringBuilder sb = new StringBuilder();
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] msg = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage currMsg : msg) {
					String prefix = "10001";
					String form = currMsg.getDisplayOriginatingAddress();
					if (form.equals(prefix)) {
						sb.append("您收到一条新消息:");
						sb.append(currMsg.getDisplayMessageBody());
					}
				}
				if (sb.toString().length() > 0) {
					Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG)
							.show();
				}
			}
		}

	}

}
