package cn.had.mobilesafe.engine;

import java.lang.annotation.Retention;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class GPSInfoProvider {
	private static Context context;
	private static GPSInfoProvider gpsProvider;
	private static LocationManager locationManager;
	private static LocationListener listener;

	private GPSInfoProvider() {

	}

	public LocationProvider getLocationProvider() {
		String providerName = getProviderName();
		LocationProvider locationProvider = locationManager
				.getProvider(providerName);
		return locationProvider;
	}

	/*
	 * 获取locationprovider
	 */
	private String getProviderName() {
		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
		criteria.setAltitudeRequired(false);
		criteria.setCostAllowed(true);
		criteria.setSpeedRequired(true);

		return locationManager.getBestProvider(criteria, true);
	}

	/*
	 * 停止gps监听
	 */
	public void stopGpsListener() {
		locationManager.removeUpdates(getLocationListener());
	}

	/*
	 * 获取位置信息
	 */
	public String getLocationInfo() {
		System.out.println("getlocationInfo");
		locationManager.requestLocationUpdates(getProviderName(), 60000, 50,
				getLocationListener());
		System.out.println("gpsprovider..............56");
		String locationString = context.getSharedPreferences("config",
		Context.MODE_PRIVATE).getString("location", null);
		/*	if (locationString == null) {
			getLocationListener().onLocationChanged(
					new Location(getProviderName()));
		}*/
		return context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getString("location", null);
	}

	/*
	 * 单例locationListener
	 */
	public synchronized LocationListener getLocationListener() {
		if (listener == null)
			listener = new MyLocationListener();
		return listener;
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			System.out.println("onlocationChanged...........");
			double latitude = location.getLatitude();// 纬度
			double longitude = location.getLongitude();// 经度
			Editor editor = context.getSharedPreferences("config",
					Context.MODE_PRIVATE).edit();
			editor.putString("location", latitude + "," + longitude);
			editor.commit();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	public synchronized static GPSInfoProvider getInstance(Context con) {
		context = con;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (gpsProvider != null) {
			return gpsProvider;
		} else {
			return new GPSInfoProvider();
		}
	}
}
