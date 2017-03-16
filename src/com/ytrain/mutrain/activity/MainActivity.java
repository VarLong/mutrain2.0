package com.ytrain.mutrain.activity;

import com.lecloud.common.cde.LeCloud;
import com.ytrain.mutrain.activity.open.OpenFragment;
import com.ytrain.mutrain.activity.open.TopOpenFragment.onChangedListener;
import com.ytrain.mutrain.activity.train.TrainFragment;
import com.ytrain.mutrain.fragement.TabFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements onChangedListener {
	private FragmentManager manager;
	OpenFragment openFragment;
	TrainFragment trainFragment;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置取消严苛模式
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// 乐视云服务
		LeCloud.init(getApplicationContext());
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();
		
		initTab();
	}

	private void initTab() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = manager.beginTransaction();
		TabFragment docWaitFrag = new TabFragment();
		transaction.add(R.id.tablinearlayout, docWaitFrag, "tabfrag");
		transaction.commit();

	}

	// 按返回键退出
	long previous = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == 4)
			if (System.currentTimeMillis() - previous > 2000) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				previous = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
		return true;
	}

	@Override
	public void onChanged() {
		// 找到的id是fragment父类的id
		openFragment = (OpenFragment) manager
				.findFragmentById(R.id.mainlinearlayout);
		// trainFragment = (TrainFragment) manager
		// .findFragmentById(R.id.mainlinearlayout);
		openFragment.openDrawLayout();
		// trainFragment.openDrawLayout();
	}

}
