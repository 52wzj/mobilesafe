package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.engine.GPSInfoProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PlayAlarmFunctionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_alarm_detail);
	}

	public void expir(View v) {
		MediaPlayer player=MediaPlayer.create(this, R.raw.alarm);
		player.setVolume(1.0f, 1.0f);
		player.start();
	}
	public void returnBack(View v) {
		finish();
	}
	
}
