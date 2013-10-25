package cn.had.mobilesafe.util;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import cn.had.mobilesafe.domian.UpdateInfo;

public class UpdateInfoParse {
public static UpdateInfo  getUpdateInfo(InputStream is) throws Exception {
	//System.out.println("updateInfoParse***********");
	UpdateInfo updateInfo=null;
	XmlPullParser xmlParser=Xml.newPullParser();
	xmlParser.setInput(is, "utf-8");
	int event=xmlParser.getEventType();
	while (event!=XmlPullParser.END_DOCUMENT) {
		switch (event) {
		case XmlPullParser.START_TAG:
			if ("updateInfo".equals(xmlParser.getName())) {
				updateInfo=new UpdateInfo();
				
			}
		else if ("version".equals(xmlParser.getName())) {
				updateInfo.setVersion(xmlParser.nextText());
				//System.out.println(updateInfo.getVersion());
			}else if ("apkurl".equals(xmlParser.getName())) {
				updateInfo.setApkUrl(xmlParser.nextText());
				//System.out.println(updateInfo.getApkUrl());
			}else if ("updateDescription".equals(xmlParser.getName())) {
				updateInfo.setUpdateDescription(xmlParser.nextText());
				//System.out.println(updateInfo.getUpdateDescription());
			}
			
			break;

		default:
			break;
		}
		event=xmlParser.next();
	}
	//System.out.println("updateInfo==null------------------"+updateInfo==null);
	//System.out.println(updateInfo.getApkUrl()+updateInfo.getVersion()+updateInfo.getUpdateDescription());
return updateInfo;	
}
}
