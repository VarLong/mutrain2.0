package com.ytrain.mutrain.activity.home;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.activity.open.OpenAdapter;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.entity.courses.PageCourses;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
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

@SuppressLint("NewApi")
public class MaxCoursesActivity extends Activity {
	/**
	 * 由主界面进入的所有课程界面
	 */
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private ILoadingLayout endLabels;
	private String CoursesString;
	private int pageIndex = 1;
	private boolean hasMore = true;
	private OpenAdapter myadapter;
	private PageCourses Courseslist;
	private String typeid;
	/**
	 * 根据获取的数据大小判断是否具有上拉加载的功能
	 */
	private boolean IfHasDown = true;

	private List<BaseCourses> CoursesDatas;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 101:
				mPullRefreshGridView.onRefreshComplete();
				if (CoursesDatas != null && CoursesDatas.size() != 0) {
					if (!IfHasDown) {
						mPullRefreshGridView.setMode(Mode.PULL_FROM_START);
					} else {
						mPullRefreshGridView.setMode(Mode.BOTH);
					}
					gridView.setAdapter(myadapter);
					myadapter.notifyDataSetChanged();
				}
				break;
			case 102:
				mPullRefreshGridView.onRefreshComplete();
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
		typeid = getIntent().getExtras().getString("id");

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText(getIntent().getExtras().getString("name"));
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		// 得到控件
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		gridView = mPullRefreshGridView.getRefreshableView();
		// 设置上拉显示字幕d
		initIndicator();
		// 调用setRefreshing来完成自动刷新
		mPullRefreshGridView.setRefreshing();

		// 对 grid item设置监听
		mPullRefreshGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BaseCourses positionDatas = CoursesDatas.get(position);
				String image_urlshare = Constants.DOMAIN + "/file/load?image="
						+ CoursesDatas.get(position).getImg_path();
				Intent intent = new Intent(MaxCoursesActivity.this,
						DetailCoursesActivity.class);
				intent.putExtra("id", positionDatas.getId());
				intent.putExtra("image_urlshare", image_urlshare);
				startActivity(intent);
			}
		});
		goBack.setOnClickListener(new GoBackListener());
		if (Utils.isExistNetwork(this)) {
			initGridView(typeid);
		} else {

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

			finish();
		}
	}

	// 初始化界面数据
	private void initGridView(final String typeId) {
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// 有更多的数据
						hasMore = true;
						// 设定当前页为1
						pageIndex = 1;
						getCourses(pageIndex, typeId);
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

	/**
	 * 获取公开课
	 * 
	 * @param currentpage
	 * @param maxTypeId
	 */
	private void getCourses(int currentpage, String maxTypeId) {
		HttpUtil.getFullUrl(Constants.GET_PUBLIC_COURSES + "?maxTypeId="
				+ maxTypeId + "&pageNo=" + currentpage + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<PageCourses>(
						MaxCoursesActivity.this, PageCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						CoursesString = paramString;
						if (CoursesString != null
								&& CoursesString.length() != 0) {
							Courseslist = JSON.parseObject(CoursesString,
									PageCourses.class);
							CoursesDatas = Courseslist.getRows();
							myadapter = new OpenAdapter(
									MaxCoursesActivity.this, CoursesDatas);
							if (Constants.PAGE_SIZE > CoursesDatas.size()) {
								IfHasDown=false;
								hasMore = false;
								handler.sendEmptyMessage(101);
							} else {
								IfHasDown = true;
								pageIndex = pageIndex + 1;
								handler.sendEmptyMessage(101);
							}
						}
					}

					@Override
					public void process(PageCourses paramT) {
					}
				}, MaxCoursesActivity.this);

	}

	/**
	 * 加载更多
	 * 
	 * @param typeId
	 */
	private void loadMoreCourses(String typeId) {
		if (hasMore) {
			HttpUtil.getFullUrl(Constants.GET_PUBLIC_COURSES + "?maxTypeId="
					+ typeId + "&pageNo=" + pageIndex + "&pageSize="
					+ Constants.PAGE_SIZE, null,
					new AsyncHttpCilentHandler<PageCourses>(
							MaxCoursesActivity.this, PageCourses.class) {
						@Override
						public void onFailure(Throwable paramThrowable,
								String paramString) {
							handler.sendEmptyMessage(102);
						}

						@Override
						public void onSuccess(String paramString) {
							if (paramString != null
									&& paramString.length() != 0) {
								Courseslist = JSON.parseObject(paramString,
										PageCourses.class);
								if (Courseslist != null) {
									List<BaseCourses> RowsCourses = Courseslist
											.getRows();
									if (RowsCourses != null
											&& RowsCourses.size() != 0) {
										if (Constants.PAGE_SIZE == RowsCourses
												.size()) {
											// 将数据添加在 courseDatas中
											CoursesDatas.addAll(RowsCourses);
											pageIndex++;
											handler.sendEmptyMessage(102);
										} else {
											CoursesDatas.addAll(RowsCourses);
											hasMore = false;
											handler.sendEmptyMessage(108);
										}

									}else {
										handler.sendEmptyMessage(108);
									}

								}
							}
						}

						@Override
						public void process(PageCourses paramT) {
						}
					}, MaxCoursesActivity.this);
		} else {
			handler.sendEmptyMessage(102);
		}

	}

}
