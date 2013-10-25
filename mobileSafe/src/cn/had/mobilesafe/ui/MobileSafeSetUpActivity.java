package cn.had.mobilesafe.ui;

import cn.had.mobilesafe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MobileSafeSetUpActivity extends Activity{
private Button btn_setguid_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_setguid);
		setTitle("…Ë÷√œÚµº(1/4)");
		btn_setguid_next=(Button) findViewById(R.id.btn_setguide_next);
		btn_setguid_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
			Intent intent=new Intent();
			intent.setClass(MobileSafeSetUpActivity.this, MobileSafeSetUp2Activity.class);
			startActivity(intent);
			MobileSafeSetUpActivity.this.finish();
			}
		});
		
	}
	
}
