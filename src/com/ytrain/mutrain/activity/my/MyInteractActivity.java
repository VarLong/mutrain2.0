package com.ytrain.mutrain.activity.my;

import java.util.ArrayList;
import java.util.List;

import com.ssy.utils.Utils;
import com.ytrain.mutrain.entity.courses.MyInteract;
import com.ytrain.mutrain.indicator.TabPageIndicator;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyInteractActivity extends FragmentActivity {
	/**
	 * 我的互动
	 */
	
	private static final String[] TITLE = new String[] { "公开课", "系列课", "系列单课" };
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private TabPageIndicator indicator;
	private ViewPager pager;
	private String stuId;
	private String JsonparamStringOne;
	private String JsonparamStringTwo;
	private String JsonparamStringThree;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 101:
				GetOpenCoursesTwo();
				break;
			case 102:
				GetOpenCoursesThree();
				break;
			case 103:
				initView();
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_interact);
		stuId = (String) UserSharedPreferences.getParam(
				MyInteractActivity.this, "stuId", "");
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("我的互动");
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(new GoBackListener());
		if (Utils.isExistNetwork(this)) {
			GetOpenCourses();
		} else {
			Toast.makeText(this, "网络错误，请检查你的网络！", Toast.LENGTH_SHORT).show();
		}

	}

	private void GetOpenCourses() {
		int pageIndex = 1;
		HttpUtil.getFullUrl(Constants.GET_MY_INTERACT + "?stuId=" + stuId
				+ "&flag=" + 1 + "&pageNo=" + pageIndex + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<MyInteract>(MyInteractActivity.this,
						MyInteract.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						JsonparamStringOne = paramString;
						handler.sendEmptyMessage(101);
					}

					@Override
					public void process(MyInteract paramT) {
					}
				}, MyInteractActivity.this);
	}

	private void GetOpenCoursesTwo() {
		int pageIndex = 1;
		HttpUtil.getFullUrl(Constants.GET_MY_INTERACT + "?stuId=" + stuId
				+ "&flag=" + 2 + "&pageNo=" + pageIndex + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<MyInteract>(MyInteractActivity.this,
						MyInteract.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						JsonparamStringTwo = paramString;
						handler.sendEmptyMessage(102);
					}

					@Override
					public void process(MyInteract paramT) {
					}
				}, MyInteractActivity.this);
	}

	private void GetOpenCoursesThree() {
		int pageIndex = 1;
		HttpUtil.getFullUrl(Constants.GET_MY_INTERACT + "?stuId=" + stuId
				+ "&flag=" + 3 + "&pageNo=" + pageIndex + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<MyInteract>(MyInteractActivity.this,
						MyInteract.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						JsonparamStringThree = paramString;
						handler.sendEmptyMessage(103);
					}

					@Override
					public void process(MyInteract paramT) {
					}
				}, MyInteractActivity.this);
	}

	private void initView() {
		InteractFragment tab01 = InteractFragment.newInstance(
				JsonparamStringOne, 1);
		InteractFragment tab02 = InteractFragment.newInstance(
				JsonparamStringTwo, 2);
		InteractFragment tab03 = InteractFragment.newInstance(
				JsonparamStringThree, 3);
		mFragments.add(tab01);
		mFragments.add(tab02);
		mFragments.add(tab03);

		// ViewPager的adapter
		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(
				getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		// 实例化TabPageIndicator然后设置ViewPager与之关联
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		// 数据从网络获取，利用handler，需要在XML中将指示器设为不可见，当有数据的时候再设置可见。不然报错
		indicator.setVisibility(View.VISIBLE);
		indicator.setViewPager(pager);
		// 如果我们要对ViewPager设置监听，用indicator设置就行了
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	class TabPageIndicatorAdapter extends FragmentPagerAdapter {

		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position % TITLE.length];
		}

		@Override
		public int getCount() {
			return TITLE.length;
		}
	}

	private class GoBackListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	}

}
