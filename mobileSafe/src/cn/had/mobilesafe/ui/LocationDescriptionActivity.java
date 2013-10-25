package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.engine.GPSInfoProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LocationDescriptionActivity extends Activity {
	private LocationManager lm;
	private String locationStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_location_detail);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		String providerName = lm.getBestProvider(criteria, true);
		if (providerName!=null) {
			 Location location = lm.getLastKnownLocation(providerName);
	               
	             //获取维度信息
	            double latitude = location.getLatitude();
	            //获取经度信息
	            double longitude = location.getLongitude();
	             locationStr=latitude+","+longitude;
		}
	}

	public void expir(View v) {
		final Dialog dialog = new Dialog(this, R.style.set_safePass_dialog);
		View view = getLayoutInflater().inflate(R.layout.ll_experience_dialog,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tv_show_url);
		Button btn = (Button) view.findViewById(R.id.btn_experience_cancel);

		//String locate = provider.getLocationInfo();

		String url = "http://ditu.google.cn?q=" + locationStr;
		tv.setText(Html.fromHtml("<a href=" + url + ">" + url + "</a>"));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}

		});

		dialog.setContentView(view);
		dialog.show();
		//provider.stopGpsListener();
	}

	public void returnBack(View v) {
		finish();
	}

}
