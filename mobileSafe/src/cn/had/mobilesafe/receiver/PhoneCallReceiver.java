package cn.had.mobilesafe.receiver;

import cn.had.mobilesafe.ui.MobileSafeActivety;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	String data=getResultData();
	if ("#5555#".equals(data.trim())) {
		intent.setClass(context, MobileSafeActivety.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		setResultData(null);
	}
		
	}

}
