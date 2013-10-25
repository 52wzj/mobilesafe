package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.R.layout;
import cn.had.mobilesafe.adapter.MainUiAdapter;
import cn.had.mobilesafe.util.MD5Encoding;
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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,
		android.view.View.OnClickListener {
	private Handler handler;
	private GridView gv_main;
	private SharedPreferences sp;
	private Intent intent;
	private EditText etxt_pass;
	private Dialog inputDialog;

	// private Adapter uiAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gv_main = (GridView) findViewById(R.id.gv_main);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		gv_main.setAdapter(new MainUiAdapter(this));
		gv_main.setOnItemClickListener(this);
		intent = new Intent();
		gv_main.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0,
					final View arg1, int arg2, long arg3) {
				if (arg2 == 0) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setTitle("设置别名");
					final EditText etxt = new EditText(MainActivity.this);
					etxt.setHint("请输入别名...");
					etxt.setText(R.string.app_name);
					builder.setView(etxt);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String nickName = etxt.getText().toString().trim();
							if ("".equals(nickName) || nickName == null) {
								Toast.makeText(getApplicationContext(),
										"别名不能设置为空!", 0).show();
								return;
							}
							TextView tv = (TextView) arg1
									.findViewById(R.id.tv_main_funName);
							tv.setText(nickName);
							Editor editor = sp.edit();
							editor.putString("nickName", nickName);
							editor.commit();
						}
					});
					builder.setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
					builder.create().show();

				}
				return false;
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			if (sp.getBoolean("isProtected", false)) {
				 intent =new Intent();
					intent.setClass(this, MobileSafeActivety.class);
					showInputPassDialog();
			}else {
				intent =new Intent();
				intent.setClass(this, MobileSafeActivety.class);
				startActivity(intent);
			}
				
				//startActivity(intent);
			break;

		default:
			break;
		}
	}

	/*
	 * 
	 * 弹出密码输入框
	 */
	private void showInputPassDialog() {
		View view = getLayoutInflater().inflate(R.layout.ll_pass_input, null);
		Button btnConfirm = (Button) view.findViewById(R.id.btn_pass_pos);
		Button btnCancel = (Button) view.findViewById(R.id.btn_pass_concel);
		etxt_pass=(EditText) view.findViewById(R.id.etxt_pass_input);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		inputDialog=new Dialog(this);
		inputDialog.setContentView(view);
		inputDialog.show();
		

		/*
		 * AlertDialog.Builder builder =new Builder(this);
		 * builder.setTitle(R.string.tv_dialogTittle_pass_txt); final View view
		 * =getLayoutInflater().inflate(R.layout.ll_pass_input, null);
		 * builder.setView(view); builder.setPositiveButton("确定", new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub String
		 * password=sp.getString("safe_password", null); EditText
		 * eText=(EditText) view.findViewById(R.id.etxt_pass_input); String
		 * pass=eText.getText().toString().trim(); if
		 * (password.equals(MD5Encoding.ecode(pass))) {
		 * startSubActivity(MobileSafeActivety.class); }else {
		 * Toast.makeText(getApplicationContext(), "密码错误!", 0).show(); } } });
		 * builder.create().show();
		 */

	}

	/*
	 * 弹出密码设置框
	 */
	private void showSetPasswordDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("请设置密码");
		final View view = getLayoutInflater().inflate(
				R.layout.ll_setpassword_dialog, null);
		builder.setView(view);
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText etxtPass = (EditText) view.findViewById(R.id.etxt_setpass);
				String pass = etxtPass.getText().toString().trim();
				EditText etxtRePass = (EditText) view
						.findViewById(R.id.etxt_repass);
				String repass = etxtRePass.getText().toString().trim();
				if (repass != null || pass != null) {
					if (repass.equals(pass)) {
						Editor editor = sp.edit();
						editor.putString("safe_password",
								MD5Encoding.ecode(pass));
						editor.commit();
						startSubActivity(MobileSafeActivety.class);
					} else {
						Toast.makeText(getApplicationContext(), "两次输入密码不一致!", 0)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "密码不能为空!", 0)
							.show();
				}
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}

	// 启动点击的功能相对应的Activity
	private void startSubActivity(Class<MobileSafeActivety> class1) {
		intent.setClass(this, class1);
		startActivity(intent);

	}

	private boolean isFirstUse() {
		String pass = sp.getString("safe_password", null);
		if (pass == null || "".equals(pass)) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pass_pos:
			String inputStr=etxt_pass.getText().toString().trim();
			if (inputStr!=null) {
				if (MD5Encoding.ecode(inputStr).equals(sp.getString("safe_pass", null))) {
					startActivity(intent);
					inputDialog.cancel();
				}else {
					Toast.makeText(this, "密码错误！", 0).show();
				}
			}else {
				Toast.makeText(this, "请输入密码！", 0).show();	
			}
			//System.out.println("8888888888888888888888");
			
			
			break;

		default:
			break;
		}

	}

}
