package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MobileSafeSetUp3Activity extends Activity implements
		OnClickListener {
	private SharedPreferences sp;
	private Button btn_choose_contact, btn_pre, btn_next;
	private EditText etContactNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("设置向导(3/4)");
		setContentView(R.layout.ll_setguid3);
		etContactNum=(EditText) findViewById(R.id.et_setguide3_contact);
		System.out.println(findViewById(R.id.et_setguide3_contact)==null);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		String safeNumber=sp.getString("safe_number", null);
		if (safeNumber!=null&&!"".equals(safeNumber)) {
			etContactNum.setText(safeNumber.trim());
		}
		
		btn_choose_contact = (Button) findViewById(R.id.btn_select_contact);
		btn_next = (Button) findViewById(R.id.btn_guide3_next);
		btn_pre = (Button) findViewById(R.id.btn_guide3_pre);
		btn_choose_contact.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		btn_pre.setOnClickListener(this);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent = data;
		if (data != null) {
			String telNumber = data.getStringExtra("tel");
			System.out.println(telNumber);
			if (telNumber!= null) {
				System.out.println(etContactNum==null);
				etContactNum.setText(telNumber);
			}
		}
	}

	private void statNewActivity(Class<?> clz) {
		Intent in = new Intent();
		in.setClass(this, clz);
		startActivity(in);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select_contact:
			Intent intent = new Intent();
			intent.setClass(this, ViewContactsActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.btn_guide3_pre:
			statNewActivity(MobileSafeSetUp2Activity.class);
			break;
		case R.id.btn_guide3_next:
			String number=etContactNum.getText().toString();
			Editor editor=sp.edit();
			
			if (number==null||"".equals(number)) {
				//System.out.println("111111111111111111111");
				Toast.makeText(this, "安全号码不能为空!", 0).show();
			return;
			}else {
				editor.putString("safe_number", number.trim());
				editor.commit();
				statNewActivity(MobileSafeSetUp4Activity.class);
			}
			
			break;

		default:
			break;
		}
	}
}
