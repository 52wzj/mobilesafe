package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.domian.LoseProtectFunction;
import cn.had.mobilesafe.engine.GPSInfoProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoseProtectSetting extends Activity implements OnItemClickListener {
	private SharedPreferences sp;
	private ListView lv;
	private static View view;
	private static TextView tvName, tvState;
	private static RelativeLayout layout;
	private static CheckBox cb;
	private static ImageView img;
	// private LoseProtectFunction []lFunctions;
	private static String[] funName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_lose_protect_setting);
		funName = new String[] { "防盗保护", "防盗密码", "安全手机号" };

		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		lv = (ListView) findViewById(R.id.lv_lose_protect_setting);
		lv.setAdapter(new MyAdapter());
		lv.setOnItemClickListener(this);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return funName.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String funStr = funName[position];
			view = LoseProtectSetting.this.getLayoutInflater()
					.from(LoseProtectSetting.this)
					.inflate(R.layout.ll_item_losepro_setting, null);
			tvName = (TextView) view.findViewById(R.id.fun_name);
			tvState = (TextView) view.findViewById(R.id.fun_state);
			layout = (RelativeLayout) view.findViewById(R.id.ll_cb_img);
			img = (ImageView) view.findViewById(R.id.iv_icon);
			cb = (CheckBox) view.findViewById(R.id.cb_state);

			if ("防盗保护".equals(funStr)) {
				protectStateChange(funStr);
			} else if ("防盗密码".equals(funStr)) {
				tvName.setText(funStr);
				tvState.setText("点击修改");
				layout.removeView(cb);
			} else if ("安全手机号".equals(funStr)) {
				tvName.setText(funStr);
				tvState.setText(sp.getString("safe_number", null));
				layout.removeView(cb);
				layout.setGravity(Gravity.CENTER_VERTICAL);
			}
			return view;
		}

	

	}

	private final void protectStateChange(String funStr) {
		tvName.setText(funStr);
		if (sp.getBoolean("isProtected", false)) {

			tvState.setText("已开启");
			cb.setChecked(true);
		} else {
			// layout.removeView(img);
			tvState.setText("未开启");
			cb.setChecked(false);
		}
		layout.removeView(img);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			System.out.println("*************8");
			if (sp.getBoolean("isProtected", false)) {
				final AlertDialog.Builder builder=new Builder(this);
				builder.setTitle("温馨提示");
				builder.setMessage("关闭防盗保护后,一旦手机失可能无法找回.\n确定关闭吗?");
				builder.setPositiveButton("", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					Editor editor=sp.edit();
					editor.putBoolean("isProtected", false);
					editor.commit();
				cb=(CheckBox) arg1.findViewById(R.id.cb_state);
				tvState=(TextView) arg1.findViewById(R.id.fun_state);
				tvState.setText("未开启");
				cb.setChecked(false);
					}
				});
				builder.setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					dialog.cancel();
					}
				});
				
				builder.create().show();
			}else {
				Editor editor=sp.edit();
				editor.putBoolean("isProtected", true);
				editor.commit();
				cb=(CheckBox) arg1.findViewById(R.id.cb_state);
				cb.setChecked(true);
			}
			
			
			break;
		case 1:

			break;

		case 2:

			break;

		default:
			break;
		}
	}

	 

}
