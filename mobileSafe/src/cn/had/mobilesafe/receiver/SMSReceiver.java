package cn.had.mobilesafe.receiver;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.engine.GPSInfoProvider;
import cn.had.mobilesafe.util.MD5Encoding;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	private String safeName;

	private SmsManager smsManager;
	private boolean isProtected;

	@Override
	public void onReceive(Context context, Intent intent) {

		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		for (Object pdu : pdus) {
			SmsMessage smsg = SmsMessage.createFromPdu((byte[]) pdu);
			String content = smsg.getMessageBody();
			String order = content.substring(0, content.lastIndexOf("#") + 1);
			String pass = content.substring(content.lastIndexOf("#") + 1,
					content.length());
			String sender = smsg.getOriginatingAddress();
			System.out.println("order-----------|" + order + "|************|"
					+ pass + "|==========|" + sender);
			DevicePolicyManager dManager = (DevicePolicyManager) context
					.getSystemService(Context.DEVICE_POLICY_SERVICE);
			if (sp.getBoolean("isProtected", false)) {
				if (sender.equals(sp.getString("safe_number", null))) {
					abortBroadcast();
					if ("#*chongzhimima*#".equals(order)) {
						Editor editor = sp.edit();
						editor.putString("safe_pass", pass);
						editor.commit();
						
					}
				}
				if (MD5Encoding.ecode(pass).equals(
						sp.getString("safe_pass", null))) {
					if ("#*dingwei*#".equals(order)) {// ¶¨Î»
						abortBroadcast();
						System.out.println("--------------dingwei");
						GPSInfoProvider gpsProvider = GPSInfoProvider
								.getInstance(context);
						String location = gpsProvider.getLocationInfo();
						String url = "http://ditu.google.cn?q=" + location;
						System.out.println(url);
						smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(sender, null, url, null,
								null);
						gpsProvider.stopGpsListener();
						//abortBroadcast();
					} else if ("#*baojing*#".equals(order)) {// ±¨¾¯ÁåÉù
						abortBroadcast();
						System.out.println("----------------------baojing");
						MediaPlayer player = MediaPlayer.create(context,
								R.raw.qjtx);
						player.setVolume(1.0f, 1.0f);
						player.start();
						
					} else if ("#*shuoping*#".equals(order)) {
						System.out.println("---------------shuoping");
						abortBroadcast();
						dManager.resetPassword("123", 0);
						dManager.lockNow();
						
					} else if ("#*xiaohui*#".equals(order)&&sender.equals(sp.getString("safe_number", null))) {
						System.out.println("-------------xiaohui");
						abortBroadcast();
						dManager.wipeData(0);
						
					}

				}

			}

			//abortBroadcast();
		}

	}
}
