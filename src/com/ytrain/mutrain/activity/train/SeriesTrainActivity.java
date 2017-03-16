package com.ytrain.mutrain.activity.train;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.entity.train.SeriesCourses;
import com.ytrain.mutrain.indicator.TabPageIndicator;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SeriesTrainActivity extends FragmentActivity {
	/**
	 * 团建Viewpager内容
	 */
	private static final String[] TITLE = new String[] { "大纲", "介绍", "评论" };
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private String train_id;
	private String name;
	private SeriesCourses seriesCourses;
	private String JsonString;
	private ImageView bigImage;
	private TextView source;
	private ViewPager pager;
	private TabPageIndicator indicator;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			initView();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_train);
		bigImage = (ImageView) findViewById(R.id.bigImage);
		source = (TextView) findViewById(R.id.source);
		train_id = getIntent().getExtras().getString("id");
		name = getIntent().getExtras().getString("name");
		
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		goBack.setOnClickListener(new GoBackListener());

		titleTV.setText(name);
		if (Utils.isExistNetwork(this)) {
			getPublicCourseJson();
		} else {
			Toast.makeText(this, "网络错误，请检查你的网络！", Toast.LENGTH_SHORT).show();
		}

	}

	// 获取培训班的单个系列课详情
	public void getPublicCourseJson() {

		HttpUtil.getFullUrl(
				Constants.GET_SIMPLE_SERIES + "?id="
						+ train_id, null,
				new AsyncHttpCilentHandler<SeriesCourses>(
						SeriesTrainActivity.this, SeriesCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}
					@Override
					public void onSuccess(String paramString) {
						JsonString=paramString;
						if (JsonString != null && JsonString.length() != 0) {
							seriesCourses = JSON.parseObject(JsonString,
									SeriesCourses.class);
						}
						handler.sendEmptyMessage(101);
					}
					@Override
					public void process(SeriesCourses paramT) {
					}
				}, SeriesTrainActivity.this);
		
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

	private void initView() {
		OutlineFragment tab01 = OutlineFragment.newInstance(JsonString);
		IntroductionFragment tab02 = IntroductionFragment
				.newInstance(JsonString);
		CommentFragment tab03 = CommentFragment.newInstance(seriesCourses
				.getId());
		mFragments.add(tab01);
		mFragments.add(tab02);
		mFragments.add(tab03);
		source.setText(seriesCourses.getName());
		String image_url = Constants.DOMAIN + "/file/load?image="
				+ seriesCourses.getBig_img_path();
		Glide.with(this).load(image_url).centerCrop().into(bigImage);

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

	private class GoBackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}
}
