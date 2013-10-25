package cn.had.mobilesafe.engine;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.domian.UpdateInfo;
import cn.had.mobilesafe.net.http.FileDownloader;
import cn.had.mobilesafe.ui.SplashActivity;
import cn.had.mobilesafe.util.ConstantUtil;
import cn.had.mobilesafe.util.UpdateInfoParse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Path;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;

public class UpdateInfoService extends Service {
	// private Context context;
	private String currentVersion;
	private int urlId;
	private UpdateInfo updateInfo;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ConstantUtil.SHOW_UPDATE_DIALOG:
				showUpdateRemenber();
				break;
			case ConstantUtil.INSTALL_NEW_VERSION:
				System.out.println("install apk...............");
				File file = (File) msg.obj;
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 打开另一应用的activity需要设置
				// intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				startActivity(intent);
				onDestroy();
				break;
			case ConstantUtil.NET_CONNECT_ERROR:
				Toast.makeText(getApplicationContext(), "网络连接失败",  0).show();
				break;
			default:
				break;
			}

		}

	};

	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("update version infomation service...........51");
		urlId = intent.getIntExtra("urlString", 0);
		currentVersion = intent.getStringExtra("currentVersion");
		final String path = getApplicationContext().getResources().getString(
				urlId);
		// System.out.println(path);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					InputStream is = null;
					URL url = new URL(path);
					HttpURLConnection huc = (HttpURLConnection) url
							.openConnection();
					huc.setConnectTimeout(5000);
					huc.setRequestMethod("GET");

					is = huc.getInputStream();

					updateInfo = UpdateInfoParse.getUpdateInfo(is);
					System.out.println(updateInfo.getApkUrl()
							+ updateInfo.getUpdateDescription()
							+ updateInfo.getVersion());
					if (!(currentVersion.equals(updateInfo.getVersion()))) {
						// System.out.println("updateinfoservice............68");
						// showUpdateRemenber();
						Message msg = new Message();
						msg.what = ConstantUtil.SHOW_UPDATE_DIALOG;
						handler.sendMessage(msg);
					} else {
						onDestroy();
					}

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					// 
					Message msg = new Message();
					msg.what = ConstantUtil.NET_CONNECT_ERROR;
					handler.sendMessage(msg);
				}

			}
		}).start();

		return 0;
	};

	/*
	 * 弹出升级提示对话框
	 */
	private void showUpdateRemenber() {
		// startMainActivity();
		AlertDialog.Builder dialog = new Builder(getApplicationContext());
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("升级提醒");
		dialog.setMessage(updateInfo.getUpdateDescription());
		dialog.setCancelable(false);
		dialog.setPositiveButton("现在升级", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// new FileDownloader(updateInfo.getApkUrl(),
				// getApplicationContext(), handler).start();
				new FileDownloader(updateInfo.getApkUrl(),
						getApplicationContext(), handler).start();

			}
		});
		dialog.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog aDialog = dialog.create();
		aDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		// aDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
		aDialog.show();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
