package cn.had.mobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoding {
public static String ecode(String str) {
	StringBuilder sb=new StringBuilder();
	try {
		
		MessageDigest digest=MessageDigest.getInstance("MD5");
		byte [] strs=digest.digest(str.getBytes());
		for (int i = 0; i < strs.length; i++) {
		String temp=	Integer.toHexString(0xff&strs[i]);
		if (temp.length()==1) {
			temp=0+temp;
		}
		sb.append(temp);
		}
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return sb.toString();
}
}
