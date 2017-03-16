package com.ytrain.mutrain.activity.my;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.entity.courses.MyOpenCourses;
import com.ytrain.mutrain.entity.courses.OpenCourses;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MyOpenActivity extends Activity {
	/**
	 * 我的公开课
	 */
	private PullToRefreshGridView mPullRefreshGridView;
	private String stuId;
	private List<OpenCourses> myCourses;
	private ILoadingLayout endLabels;
	private GridView gridView;
	private int pageIndex;
	private String JsonString;
	private MyOpenCourses openCourses;
	private MyOpenAdapter myadapter;

	private boolean hasMore;
	/**
	 * 根据获取的数据大小判断是否具有上拉加载的功能
	 */
	private boolean IfHasDown = true;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 102:
				
				myadapter.notifyDataSetChanged();
				mPullRefreshGridView.onRefreshComplete();
				break;
			case 101:
				mPullRefreshGridView.onRefreshComplete();
				if (!IfHasDown) {
					mPullRefreshGridView.setMode(Mode.DISABLED);
				} else {
					mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
				}
				gridView.setAdapter(myadapter);
				myadapter.notifyDataSetChanged();
				break;
			case 108:
				myadapter.notifyDataSetChanged();
				mPullRefreshGridView.onRefreshComplete();
				mPullRefreshGridView.setMode(Mode.DISABLED);
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_train);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("我的公开课");
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(new GoBackListener());
		stuId = (String) UserSharedPreferences.getParam(MyOpenActivity.this,
				"stuId", "");
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		gridView = mPullRefreshGridView.getRefreshableView();
		// 设置上拉显示字幕
		initIndicator();
		mPullRefreshGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OpenCourses positionDatas = myCourses.get(position);
				String image_urlshare = Constants.DOMAIN + "/file/load?image="
						+ myCourses.get(position).getCourseSmallImgPath();
				Intent intent = new Intent(MyOpenActivity.this,
						DetailCoursesActivity.class);
				intent.putExtra("id", positionDatas.getCourseId());
				intent.putExtra("image_urlshare", image_urlshare);
				startActivity(intent);
			}
		});

		if (Utils.isExistNetwork(this)) {
			initGridView(stuId);
		} else {
			Toast.makeText(this, "网络错误，请检查你的网络！", Toast.LENGTH_SHORT).show();
		}

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

	private void GetOpenCoursesData() {
		pageIndex = 1;
		// 如果没有ID是不能发送消息过去的
		if (null != stuId && stuId.length() != 0) {
			HttpUtil.getFullUrl(Constants.GET_OPEN_COURSES + "?stuId=" + stuId
					+ "&pageNo=" + pageIndex + "&pageSize="
					+ Constants.PAGE_SIZE, null,
					new AsyncHttpCilentHandler<MyOpenCourses>(
							MyOpenActivity.this, MyOpenCourses.class) {
						@Override
						public void onFailure(Throwable paramThrowable,
								String paramString) {
						}

						@Override
						public void onSuccess(String paramString) {
							JsonString = paramString;
							if (JsonString != null && JsonString.length() != 0) {
								openCourses = JSON.parseObject(JsonString,
										MyOpenCourses.class);
								if (openCourses != null) {
									myCourses = openCourses.getRows();
									myadapter = new MyOpenAdapter(
											MyOpenActivity.this, myCourses);
									if (myCourses.size() == Constants.PAGE_SIZE) {
										handler.sendEmptyMessage(101);
										pageIndex++;
									} else {
										hasMore = false;
										IfHasDown = false;
										handler.sendEmptyMessage(101);
									}
								}
							}

						}

						@Override
						public void process(MyOpenCourses paramT) {
						}
					}, MyOpenActivity.this);
		}
	}

	private void initGridView(final String typeId) {
		GetOpenCoursesData();
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {

						// 上拉加载更多的课
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								loadMoreCourses(typeId);
							}
						});
						thread.start();
					}

				});
	}

	private void loadMoreCourses(String typeId) {
		// TODO Auto-generated method stub
		if (hasMore) {
				// 如果没有ID是不能发送消息过去的
				if (null != stuId && stuId.length() != 0) {
					HttpUtil.getFullUrl(Constants.GET_OPEN_COURSES + "?stuId="
							+ stuId + "&pageNo=" + pageIndex + "&pageSize="
							+ Constants.PAGE_SIZE, null,
							new AsyncHttpCilentHandler<MyOpenCourses>(
									MyOpenActivity.this, MyOpenCourses.class) {
								@Override
								public void onFailure(Throwable paramThrowable,
										String paramString) {
								}

								@Override
								public void onSuccess(String paramString) {
									if (paramString != null
											&& paramString.length() != 0) {
										openCourses = JSON.parseObject(
												paramString,
												MyOpenCourses.class);

										if (openCourses != null) {
											List<OpenCourses> Courses = openCourses
													.getRows();
											if (Courses.size() == Constants.PAGE_SIZE) {
												myCourses.addAll(Courses);
												pageIndex++;
												handler.sendEmptyMessage(102);
											} else {
												myCourses.addAll(Courses);
												hasMore = false;
												handler.sendEmptyMessage(108);
											}
										} else {
											handler.sendEmptyMessage(108);
										}
									}

								}

								@Override
								public void process(MyOpenCourses paramT) {
								}
							}, MyOpenActivity.this);
				}
			
		}
	}
}
