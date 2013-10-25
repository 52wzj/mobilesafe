package cn.had.mobilesafe.ui;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.domian.ContactInfo;
import cn.had.mobilesafe.engine.ContactInfoService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewContactsActivity extends Activity implements OnItemClickListener{
	private ListView lvContact;
	private List<ContactInfo> contactInfos;
	private  ContactInfo contactInfo;
	private static TextView tvName, tvPhoneNum;
	private static View viewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ll_contacts);
		lvContact = (ListView) findViewById(R.id.lv_contacts);
		contactInfos = new ContactInfoService(this).getContactInfos();
		System.out.println(contactInfos.size()+"---------------------size");
		lvContact.setAdapter(new MyAdapter());
		lvContact.setOnItemClickListener(this);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
		
			return contactInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			contactInfo = contactInfos.get(position);
		//	System.out.println(contactInfo.getPhoneNumber()+"------------position------------"+position+"==========="+contactInfo.getDisplayName());
			
			viewItem = getLayoutInflater().from(ViewContactsActivity.this)
					.inflate(R.layout.lv_item_contacts, null);
			tvName = (TextView) viewItem.findViewById(R.id.tv_contact_name);
			tvName.setText(contactInfo.getDisplayName());
			tvPhoneNum = (TextView) viewItem.findViewById(R.id.tv_tel_number);
			tvPhoneNum.setText(contactInfo.getPhoneNumber());
			return viewItem;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ContactInfo contactInfo=contactInfos.get(arg2);
		Intent intent=new Intent();
		intent.putExtra("tel", contactInfo.getPhoneNumber());
		setResult(0, intent);
		finish();
		
	}
}
