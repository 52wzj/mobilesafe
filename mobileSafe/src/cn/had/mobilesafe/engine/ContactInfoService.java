package cn.had.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import cn.had.mobilesafe.domian.ContactInfo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactInfoService {
	private Context context;
	private ContentResolver resolver;

	public ContactInfoService(Context context) {
		this.context = context;
		resolver = context.getContentResolver();
		
	}
	
	public List<ContactInfo> getContactInfos() {
		List<ContactInfo> contactInfos = null;
		ContactInfo contactInfo;
		Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri=Uri.parse("content://com.android.contacts/data");
		Cursor cursor=resolver.query(uri, null, null, null, null);
		contactInfos=new ArrayList<ContactInfo>();
		while (cursor.moveToNext()) {
			contactInfo=new ContactInfo();
			String id=cursor.getString(cursor.getColumnIndex("_id"));
			String name=cursor.getString(cursor.getColumnIndex("display_name"));
			contactInfo.setDisplayName(name);
			Cursor dataCursor=resolver.query(dataUri, null, "raw_contact_id=?", new String[]{id}, null);
			while (dataCursor.moveToNext()) {
				//System.out.println(dataCursor.getString(dataCursor.getColumnIndex("mimeType")));	
			String type=dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
			if ("vnd.android.cursor.item/phone_v2".equals(type)) {
				String number=dataCursor.getString(dataCursor.getColumnIndex("data1"));
				contactInfo.setPhoneNumber(number);
			}
			}
			//System.out.println(contactInfo.getDisplayName()+"-------------tel:=="+contactInfo.getPhoneNumber());
			contactInfos.add(contactInfo);
		}
		
		return contactInfos;
	}
}
