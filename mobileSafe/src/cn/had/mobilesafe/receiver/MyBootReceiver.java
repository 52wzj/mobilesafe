package cn.had.mobilesafe.receiver;

import cn.had.mobilesafe.engine.SMSService;
import cn.had.mobilesafe.engine.SimInfoService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class MyBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("mybootReceiver........................");
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		boolean isProtected = sp.getBoolean("isProtected", false);
		if (isProtected) {
			String simSrial = sp.getString("simSrial", null);
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String newSimSrial = tm.getSimSerialNumber();
			if (newSimSrial != null) {
				if (!newSimSrial.equals(simSrial)) {
					String safeNumber = sp.getString("safe_number", null);
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(safeNumber, null,
							"sim卡发生改变,您的手机可能已经被盗", null, null);
					System.out.println("sim卡发生改变,您的手机可能已经被盗");
				}
			}
		}
	}

}
