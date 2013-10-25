package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MobileSafeSetUp4Activity extends Activity implements
		OnClickListener {
	private Button btn_start, btnHold;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("������(4/4)");
		setContentView(R.layout.ll_setguid4);
		btn_start = (Button) findViewById(R.id.btn_start_protect);
		btnHold = (Button) findViewById(R.id.btn_hold_protect);
		btn_start.setOnClickListener(this);
		btnHold.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isguided", true);//���������

		switch (v.getId()) {
		case R.id.btn_start_protect:
			editor.putBoolean("isProtected", true);//��������
			break;
		case R.id.btn_hold_protect:
			editor.putBoolean("isProtected", false);//�ݲ�����
			break;
		default:
			break;
		}
		editor.commit();
		finish();
	}
}
