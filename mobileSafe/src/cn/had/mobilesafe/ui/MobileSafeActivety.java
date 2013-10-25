package cn.had.mobilesafe.ui;

import java.util.List;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.R.string;
import cn.had.mobilesafe.adapter.LoseProFunctionAdapter;
import cn.had.mobilesafe.domian.LoseProtectFunction;
import cn.had.mobilesafe.util.MD5Encoding;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MobileSafeActivety extends Activity implements OnClickListener {
	private SharedPreferences sp;
	private LayoutInflater inflater;
	private Button btnConfirm, btnCancel, btnInputConfirm, btnInputCancel,
			btnLosePro, btnLoseOrder, btnLoseSetting;
	private EditText etSetPass, etRePass, inputPass;
	private View setPassView, inputPassView;
	private Dialog dialog;
	private static int count;
	private ListView lv;
	private LoseProFunctionAdapter adapter;
	private LinearLayout lLayout;
	private TextView txtSwitch;
	private ImageView imgv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_mobilesafe_ui);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		lLayout = (LinearLayout) findViewById(R.id.ll_lose_protect_switch);
		imgv = (ImageView) findViewById(R.id.iv_lose_pro_state);
		btnLosePro = (Button) findViewById(R.id.btn_lose_protect_switch);
		btnLoseOrder = (Button) findViewById(R.id.btn_lose_protect_order);
		txtSwitch = (TextView) findViewById(R.id.tv_lose_protect_state);
		btnLoseSetting = (Button) findViewById(R.id.btn_lose_protect_setting);
		Boolean protectState = sp.getBoolean("isProtected", false);
		if (protectState) {
			initProtectState();
		}

		btnLoseOrder.setOnClickListener(this);
		btnLosePro.setOnClickListener(this);
		btnLoseSetting.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv_lose_protect_function);
		adapter = new LoseProFunctionAdapter(this);
		lv.setAdapter(adapter);

		inflater = getLayoutInflater();

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				LoseProtectFunction function = adapter
						.getLoseProtectFunctions().get(arg2);
				if (function.getFuncName()==999) {
					return;
				}
				startNewActivity(function.getClz());
			}
		});
		/*
		 *
		 */
	}

	/*
	 * 弹出密码输入框
	 */
	private void showInputSafePassDialog() {
		dialog = new Dialog(this, R.style.input_safePass_dialog);
		inputPassView = inflater.inflate(R.layout.ll_pass_input, null);
		inputPass = (EditText) inputPassView.findViewById(R.id.etxt_pass_input);
		btnInputConfirm = (Button) inputPassView
				.findViewById(R.id.btn_pass_pos);
		btnInputCancel = (Button) inputPassView
				.findViewById(R.id.btn_pass_concel);
		btnInputConfirm.setOnClickListener(this);
		btnInputCancel.setOnClickListener(this);
		dialog.setContentView(inputPassView);
		dialog.setCancelable(false);
		dialog.show();

	}

	/*
	 * 弹出设置安全密码对话框
	 */
	private void showSetSafePassDialog() {
		dialog = new Dialog(this, R.style.set_safePass_dialog);
		setPassView = inflater.inflate(R.layout.ll_setpassword_dialog, null);
		btnConfirm = (Button) setPassView
				.findViewById(R.id.btn_setPass_confirm);
		btnCancel = (Button) setPassView.findViewById(R.id.btn_setPass_cancel);
		etRePass = (EditText) setPassView.findViewById(R.id.etxt_repass);
		etSetPass = (EditText) setPassView.findViewById(R.id.etxt_setpass);
		btnCancel.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		dialog.setContentView(setPassView);
		dialog.setCancelable(false);
		dialog.show();

	}

	/*
	 * 是否初次使用
	 */
	private boolean isFirstUse() {
		String pass = sp.getString("safe_pass", null);
		if ("".equals(pass) || pass == null) {
			return true;
		}
		return false;
	}

	/*
	 * activity 显式意图 无参跳转
	 */
	private void startNewActivity(Class<? extends Context> clz) {
		Intent intent = new Intent();
		intent.setClass(this, clz);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// System.out.println("*******************************************");
		switch (v.getId()) {// 设置密码
		case R.id.btn_setPass_confirm:
			setPassword();
			break;
		case R.id.btn_setPass_cancel:// 取消设置
			finish();
			break;
		case R.id.btn_pass_pos:// 输入密码进行验证
			inputPassword();
			break;
		case R.id.btn_pass_concel:// 取消密码输入
			dialog.cancel();
			MobileSafeActivety.this.finish();
			break;

		case R.id.btn_lose_protect_order:// 查看远程操控命令

			break;

		case R.id.btn_lose_protect_setting:// 设置防盗功能
startNewActivity(LoseProtectSetting.class);
			break;

		case R.id.btn_lose_protect_switch:// 开启防盗保护
			if (isFirstUse()) {
				showSetSafePassDialog();
			}
			Editor ed = sp.edit();
			ed.putBoolean("isProtected", true);
			ed.commit();
			initProtectState();
			break;
		default:
			break;
		}

	}

	private void initProtectState() {
		imgv.setImageResource(R.drawable.kn_protection_lock_open);
		txtSwitch.setText(getResources().getString(R.string.lose_pro_started));
		// txtSwitch.setTextColor(Color.parseColor(getResources().getString(R.color.green111)));
		txtSwitch.setTextColor(getResources().getColor(R.color.green111));
		lLayout.removeAllViews();
		TextView txtView = new TextView(this);
		txtView.setText(R.string.lose_pro_state);
		txtView.setTextColor(getResources().getColor(R.color.green111));
		txtView.setTextSize(15);
		txtView.setGravity(Gravity.CENTER_HORIZONTAL);

		lLayout.addView(txtView);
	}

	/*
	 * 验证密码
	 */
	private void inputPassword() {
		String inPass = inputPass.getText().toString().trim();
		String password = sp.getString("safe_pass", null);
		if (inPass != null) {
			if (password.equals(MD5Encoding.ecode(inPass))) {
				if (!isSetted()) {
					Intent in = new Intent();
					in.setClass(this, MobileSafeSetUpActivity.class);
					startActivity(in);
				} else {
					setContentView(R.layout.ll_mobilesafe_ui);
				}
				dialog.cancel();
			} else {
				Toast.makeText(getApplicationContext(), "密码错误!", 0).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "请输入密码!", 0).show();
		}
	}

	/*
	 * 检查密码是否合格 将所设密码保存到sharedPrefrence中
	 */
	private void setPassword() {
		String pass = etSetPass.getText().toString().trim();
		String rePass = etRePass.getText().toString().trim();
		if (!("".endsWith(pass) || pass == null)) {
			if (pass.equals(rePass)) {
				Editor editor = sp.edit();
				editor.putString("safe_pass", MD5Encoding.ecode(pass));
				editor.commit();
				if (!isSetted()) {
					Intent in = new Intent();
					in.setClass(this, MobileSafeSetUpActivity.class);
					startActivity(in);
				}
				dialog.cancel();
			} else {
				Toast.makeText(this, "两次输入的密码不一致!", 0).show();
			}
		} else {
			Toast.makeText(this, "密码不能为空!", 0).show();
		}
	}

	/*
	 * 查看是否已经完成过设置向导
	 */
	private boolean isSetted() {
		boolean flag = sp.getBoolean("isguided", false);
		return flag;
	}

	public void startProtect() {
		Editor ed = sp.edit();
		ed.putBoolean("isProtectec", true);
		ed.commit();
	}

	public void stopProtect() {
		Editor ed = sp.edit();
		ed.putBoolean("isProtectec", false);
		ed.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Boolean protectState = sp.getBoolean("isProtected", false);
		if (protectState) {
			initProtectState();
		}
	}

}
