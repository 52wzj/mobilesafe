package cn.had.mobilesafe.net.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.util.ConstantUtil;
import cn.had.mobilesafe.util.FileUtil;

public class FileDownloader extends Thread {
	private String urlString;
	private Context context;
	private NotificationManager nManager;
	private NotificationCompat.Builder builder;
	private String fileName;
	private Handler handler;
	
	private void sendErroMessage(){
		Message msg=new Message();
		msg.what=ConstantUtil.NOT_NEED_UPDATE;
		handler.sendMessage(msg);
		
	}
	
@Override
public void run() {
	System.out.println("run download ......");
	 fileName=FileUtil.getFileName(urlString);
	 System.out.println("FileDownloader 33 ..........."+fileName);
	File file=new File(FileUtil.getStorePath()+ fileName);
	if (!FileUtil.isExist(fileName)) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendErroMessage();
		}
	}
	try {
		writeFile(file);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		sendErroMessage();
	}
}

private void writeFile(File file)throws Exception{
	System.out.println("writeFile.....53"+urlString);
	URL url=new URL(urlString);
	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	conn.setConnectTimeout(5000);
	conn.setRequestMethod("GET");
	int code=conn.getResponseCode();
	if (code==200) {
		System.out.println("downloader responsecode.........");
		InputStream is=conn.getInputStream();
		
		int fileSize=conn.getContentLength();
		nManager = (NotificationManager)context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentText("文件正在下载...");
		builder.setContentTitle(fileName);
		builder.setProgress(fileSize,0, false);
		nManager.notify(0, builder.build());
	FileOutputStream fos=new FileOutputStream(file);
	byte[]buffer=new byte[4096];
	int temp=0;
	int count=0;
	while ((temp=is.read(buffer))!=-1) {
		fos.write(buffer, 0,temp);
		count+=temp;
		builder.setProgress(fileSize, count, false);
		nManager.notify(0, builder.build());
	}
	if (count>=fileSize) {
		builder.setContentText("下载完成!");
		Message msg=new Message();
		msg.what=ConstantUtil.INSTALL_NEW_VERSION;
		msg.obj=file;
		handler.sendMessage(msg);
	}
	fos.flush();
	is.close();
	}
	
	
}

public FileDownloader(String urlString, Context context,Handler handler) {

	this.urlString = urlString;
	this.context = context;
	this.handler=handler;
}

}
