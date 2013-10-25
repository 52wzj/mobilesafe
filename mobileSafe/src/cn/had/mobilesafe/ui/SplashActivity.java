package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;

import cn.had.mobilesafe.engine.UpdateInfoService;

import android.os.Bundle;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView versionTxt;
	private LinearLayout ll_splash_main;

	/*
	 * private Handler handler=new Handler(){ public void
	 * handleMessage(android.os.Message msg) { switch (msg.what) { case
	 * SHOW_UPDATE_DIALOG: updateInfo=(UpdateInfo) msg.obj;
	 * showUpdateRemenber(); break; case ConstantUtil.NOT_NEED_UPDATE: //
	 * startMainActivity(); break; default: break; }
	 * 
	 * }
	 * 
	 * 
	 * };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		versionTxt = (TextView) findViewById(R.id.tv_splash_version);
		ll_splash_main = (LinearLayout) findViewById(R.id.ll_splash_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		String versionStr = versionTxt.getText().toString().trim();
		// new Thread(new UpdateInfoService(this,
		// R.string.updateurl,getVerion(),handler)).start();
		// 开启升级服务区
		startVersionUpdateService();
		versionStr += getVerion();
		versionTxt.setText(versionStr);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		ll_splash_main.setAnimation(aa);
		// boolean isNeedUpdate=isNeedUpdate(this);
		// 设置开始界面停留时长
		new Thread(
				new Runnable() {
					
					@Override
					public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						startMainActivity();
						SplashActivity.this.finish();
					}
				}
				).start();
				
	}

	private void startVersionUpdateService() {
		Intent intent = new Intent();
		intent.putExtra("urlString", R.string.updateurl);
		intent.putExtra("currentVersion", getVerion());
		intent.setClass(this, UpdateInfoService.class);
		startService(intent);

	}

	private void startMainActivity() {
		Intent intent = new Intent();
		intent.setClass(SplashActivity.this, MainActivity.class);

		SplashActivity.this.startActivity(intent);
		// SplashActivity.this.finish();
	}

	/*
	 * private void showUpdateRemenber() { //startMainActivity();
	 * AlertDialog.Builder dialog=new Builder(this);
	 * dialog.setIcon(R.drawable.ic_launcher); dialog.setTitle("升级提醒");
	 * dialog.setMessage(updateInfo.getUpdateDescription());
	 * dialog.setCancelable(false); dialog.setPositiveButton("现在升级", new
	 * OnClickListener() {
	 * 
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //new
	 * FileDownloader(updateInfo.getApkUrl(), getApplicationContext(),
	 * handler).start(); Intent intent=new Intent();
	 * intent.setClass(SplashActivity.this, UpdateVersionService.class);
	 * intent.putExtra("url", updateInfo.getApkUrl()); startService(intent);
	 * //startMainActivity();
	 * 
	 * } }); dialog.setNegativeButton("日后再说", new OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * startMainActivity(); } }); dialog.create().show(); }
	 */
	/*
	 * 获取当前版本号
	 */
	private String getVerion() {
		PackageManager pManager = getPackageManager();
		try {
			PackageInfo pInfo = pManager.getPackageInfo(getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "未知";
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
