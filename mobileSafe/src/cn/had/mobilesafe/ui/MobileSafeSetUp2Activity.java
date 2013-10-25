package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.engine.SimInfoService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MobileSafeSetUp2Activity extends Activity implements
		OnClickListener {
	private CheckBox cb_bind_sim;
	private Button btn_bind_sim, btn_pre, btn_next;
	private Intent intent;
	private SharedPreferences sp;
	private SimInfoService simService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("设置向导(2/4)");
		setContentView(R.layout.ll_setguid2);
		simService = new SimInfoService(this);
		
		intent = new Intent();
		cb_bind_sim = (CheckBox) findViewById(R.id.cb_sim_bind_state);
		//cb_bind_sim.setEnabled(false);
		btn_bind_sim = (Button) findViewById(R.id.btn_bind_sim);
		btn_pre = (Button) findViewById(R.id.btn_guide2_pre);
		btn_next = (Button) findViewById(R.id.btn_guide2_next);
		btn_bind_sim.setOnClickListener(this);
		btn_pre.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bind_sim:
			sp = getSharedPreferences("config", Context.MODE_PRIVATE);
			String simSrial = sp.getString("simSrial", null);
			if (simSrial == null) {
				bindSimSrial();
			} else {
				if (simSrial.equals(simService.getSimSerial().trim())) {
					unBindSimSrial();
					
					Toast.makeText(getApplicationContext(),
							"sim卡绑定 成功,点击下一步继续", 0).show();
				}else {
					Toast.makeText(this, "输入密码", 0).show();
				}

			}
			break;
		case R.id.btn_guide2_pre:
			statNewActivity(MobileSafeSetUpActivity.class);

			break;
		case R.id.btn_guide2_next:
			statNewActivity(MobileSafeSetUp3Activity.class);
			break;
		default:
			break;
		}

	}
	
	private void unBindSimSrial() {
		// TODO Auto-generated method stub
		String simSerial =simService.getSimSerial().trim();
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("simSrial", null);
		editor.commit();
		if (cb_bind_sim.isChecked()){
			cb_bind_sim.setChecked(false);
		}else {
			cb_bind_sim.setChecked(true);
		}
		
		isBindStateChange();
		
		//btn_next.setEnabled(true);
	}

	private void bindSimSrial() {
		// TODO Auto-generated method stub
		String simSerial =simService.getSimSerial().trim();
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("simSrial", simSerial);
		editor.commit();
		if (cb_bind_sim.isChecked()){
			cb_bind_sim.setChecked(false);
		}else {
			cb_bind_sim.setChecked(true);
		}
		
		isBindStateChange();
		
		//btn_next.setEnabled(true);
	}
	private void statNewActivity(Class<?> clz){
		Intent in=new Intent();
		in.setClass(this, clz);
		startActivity(in);
		finish();
	}
	
	private void isBindStateChange(){
		if (cb_bind_sim.isChecked()) {
			//cb_bind_sim.setBackgroundColor(Color.parseColor("#ff00f700"));
			cb_bind_sim.setTextColor(Color.parseColor("#ff00f700"));
			cb_bind_sim.setText("已绑定");
			btn_bind_sim.setText("解除绑定");
		}else {
			//cb_bind_sim.setBackgroundColor(Color.parseColor("#ffff0000"));
			cb_bind_sim.setTextColor(Color.parseColor("#ffff0000"));
			cb_bind_sim.setText("未绑定");
			btn_bind_sim.setText("绑定");
		}
		
	}
	
	private void init(){
		
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		String simSrial = sp.getString("simSrial", null);
		if (simSrial == null) {
			cb_bind_sim.setChecked(false);
			isBindStateChange();
		} else {
			if (simSrial.equals(simService.getSimSerial().trim())) {
				cb_bind_sim.setChecked(true);
				isBindStateChange();
				Toast.makeText(getApplicationContext(),
						"sim卡绑定 成功,点击下一步继续", 0).show();
			}else {
				Toast.makeText(this, "sim卡已经更换", 0).show();
			}

		}
	}
}
