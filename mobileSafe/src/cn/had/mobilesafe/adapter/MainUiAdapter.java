package cn.had.mobilesafe.adapter;

import java.util.HashMap;
import java.util.Map;

import cn.had.mobilesafe.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainUiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private static View view;
	private static ImageView imgv;
	private static TextView txtv;
	private SharedPreferences sp;

	private static String[] functionNames = { "手机防盗", "通讯卫士", "软件管理", "任务管理",
			"流量管理", "手机杀毒", "系统优化", "高级工具", "系统设置" };
	private static int[] functionIcons = { R.drawable.safe,
			R.drawable.callmsgsafe, R.drawable.app, R.drawable.taskmanager,
			R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
			R.drawable.atools, R.drawable.settings };

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return functionNames.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 view=inflater.inflate(R.layout.main_gv_item, null);
		 imgv=(ImageView) view.findViewById(R.id.imgv_main_gv);
		 txtv=(TextView) view.findViewById(R.id.tv_main_funName);
		imgv.setImageResource(functionIcons[position]);
		txtv.setText(functionNames[position]);
		if (position==0) {
		String nickName=sp.getString("nickName", null);
		if ("".equals(nickName)||nickName!=null) {
			txtv.setText(nickName);
		}
		}
		return view;
	}

	public MainUiAdapter(Context context) {

		this.context = context;
		inflater = LayoutInflater.from(context);
		sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

}
