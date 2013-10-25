package cn.had.mobilesafe.net.http;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpConnection;
import org.apache.http.client.entity.UrlEncodedFormEntity;


public class HttpHelper  {

	public InputStream  getInputStream(String urlString) throws Exception {
		URL url=new URL(urlString);
		HttpsURLConnection huc=(HttpsURLConnection) url.openConnection();
		huc.setConnectTimeout(5000);
		huc.setRequestMethod("GET");
		InputStream is=huc.getInputStream();
		return is;
	}



}
