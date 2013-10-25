package cn.had.mobilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.had.mobilesafe.R;
import cn.had.mobilesafe.domian.LoseProtectFunction;
import cn.had.mobilesafe.ui.ChangeSimDescActivity;
import cn.had.mobilesafe.ui.DeleteAllDataActivity;
import cn.had.mobilesafe.ui.LocationDescriptionActivity;
import cn.had.mobilesafe.ui.LockScreenIntroduceActivity;
import cn.had.mobilesafe.ui.PlayAlarmFunctionActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LoseProFunctionAdapter extends BaseAdapter {
	private List<LoseProtectFunction> loseProtectFunctions;
	private static LoseProtectFunction protectFunction;
	private Context context;
	private LayoutInflater inflater;
	private static View view;
	private static ImageView imgv;
	private static TextView txtv;

	private void init() {
		loseProtectFunctions = new ArrayList<LoseProtectFunction>();
		LoseProtectFunction fun1, fun2, fun3, fun4, fun5, fun6;
		fun1 = new LoseProtectFunction();
		fun1.setFuncIcon(R.drawable.kn_protection_huanka);
		fun1.setFuncName(R.string.lose_pro_sendSms);
		fun1.setClz(ChangeSimDescActivity.class);

		fun2 = new LoseProtectFunction();
		fun2.setFuncIcon(R.drawable.kn_protection_dingwei);
		fun2.setFuncName(R.string.lose_pro_located);
		fun2.setClz(LocationDescriptionActivity.class);
		fun3 = new LoseProtectFunction();
		fun3.setFuncIcon(R.drawable.kn_protection_suoding);
		fun3.setFuncName(R.string.lose_pro_lockCamara);
		fun3.setClz(LockScreenIntroduceActivity.class);
		fun4 = new LoseProtectFunction();
		fun4.setFuncIcon(R.drawable.kn_protection_xiaohui);
		fun4.setFuncName(R.string.lose_pro_destroy);
		fun4.setClz(DeleteAllDataActivity.class);
		fun5 = new LoseProtectFunction();
		fun5.setFuncIcon(R.drawable.kn_protection_baojing);
		fun5.setFuncName(R.string.lose_pro_warnRing);
		fun5.setClz(PlayAlarmFunctionActivity.class);
		fun6 = new LoseProtectFunction();
		fun6.setFuncName(999);
		loseProtectFunctions.add(fun1);
		loseProtectFunctions.add(fun2);
		loseProtectFunctions.add(fun3);
		loseProtectFunctions.add(fun4);
		loseProtectFunctions.add(fun5);
		loseProtectFunctions.add(fun6);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return loseProtectFunctions.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LoseProtectFunction function = loseProtectFunctions.get(position);
		view = inflater.inflate(R.layout.ll_item_lose_protect_function, null);
		txtv = (TextView) view.findViewById(R.id.tv_lose_pro_fun_name);

		imgv = (ImageView) view.findViewById(R.id.iv_lose_pro_fun);
		if (function.getFuncName() == 999) {
			return view;
		}
		txtv.setText(context.getResources().getString(function.getFuncName()));
		System.out.println("*****************************************878");
		imgv.setImageResource(function.getFuncIcon());
		return view;
	}

	public LoseProFunctionAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		init();
	}

	public List<LoseProtectFunction> getLoseProtectFunctions() {
		return loseProtectFunctions;
	}

}
