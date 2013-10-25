package cn.had.mobilesafe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Environment;

public class FileUtil {
	
	public static int  getFileSize(String fileName) {
		File file=new File(Environment.getExternalStorageDirectory(), fileName);
		if (file.exists()){
			try {
				FileInputStream fis=new FileInputStream(file);
			try {
				int size=fis.available();
				System.out.println(size);
				return size;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	/*
	 * ��ȡ�ⲿ�洢·��
	 */
public  static  String getStorePath() {
	if (Environment.getExternalStorageState()!=Environment.MEDIA_BAD_REMOVAL) 
return	Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	else return "�ڴ濨�𻵻���ж��";
}

/*
 * ��ȡ�ļ���
 */
public static String getFileName(String url) {
	String fileName=url.substring(url.lastIndexOf("/")+1);
	//System.out.println("filename----------------------------------"+fileName);
	return fileName;
}
/*
 * ��ȡ�ļ�ȫ·��
 * 
 */
public static String  getAbsoluteStoreLocate(String url) {
	return getStorePath()+getFileName(url);
}
/*
 * �鿴�ڴ濨���Ƿ��Ѿ����� 
 */
public static boolean isExist(String fileName) {
	File file=new File(Environment.getExternalStorageDirectory(), fileName);
	return file.exists();
}
}
