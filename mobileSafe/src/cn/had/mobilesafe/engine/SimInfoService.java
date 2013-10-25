package cn.had.mobilesafe.engine;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SimInfoService {
	private Context context;
	private TelephonyManager tManager;

	public SimInfoService(Context context) {
		this.context = context;
		tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	public String getSimSerial() {
		return tManager.getSimSerialNumber();

	}

}
