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
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LockScreenIntroduceActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_lockscreen_detail);
	}

	public void expir(View v) {
		DevicePolicyManager dmManager=(DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		//dmManager.resetPassword(getSharedPreferences("config", Context.MODE_PRIVATE).getString("safe_pass", null), 0);
		
		dmManager.lockNow();
	}
	public void returnBack(View v) {
		finish();
	}
	
}
