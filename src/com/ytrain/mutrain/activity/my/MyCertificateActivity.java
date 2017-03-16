package com.ytrain.mutrain.activity.my;

import com.ytrain.mutrain.utils.UserSharedPreferences;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCertificateActivity extends Activity {
	
	
	/**
	 * 我的证书界面
	 */
	private String stuId;
	private ILoadingLayout endLabels;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_train);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("我的证书");
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		stuId = (String) UserSharedPreferences.getParam(
				MyCertificateActivity.this, "stuId", "");
		goBack.setOnClickListener(new GoBackListener());
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
		gridView = mPullRefreshGridView.getRefreshableView();
		// 设置上拉显示字幕
		initIndicator();
		mPullRefreshGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			

			}
		});
		
		
	}

	private void initIndicator() {
		endLabels = mPullRefreshGridView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel(getString(R.string.pull_to_up_pull));// 刚上拉时，显示的提示
		endLabels.setRefreshingLabel(getString(R.string.pull_to_up_refreshing));// 刷新时
		endLabels.setReleaseLabel(getString(R.string.pull_to_up_release));// 下来达到一定距离时，显示的提示
	}
	private class GoBackListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	}

}
