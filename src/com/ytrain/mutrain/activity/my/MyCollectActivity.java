package com.ytrain.mutrain.activity.my;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.entity.courses.CollectCourses;
import com.ytrain.mutrain.entity.courses.MyCollectCourses;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class MyCollectActivity extends Activity {
	/**
	 * 我的收藏界面
	 */
	private String stuId;
	private PullToRefreshGridView mPullRefreshGridView;
	private List<CollectCourses> myCourses;
	private ILoadingLayout endLabels;
	private GridView gridView;
	private int pageIndex;
	private String JsonString;
	private MyCollectCourses openCourses;
	private MyCollectAdapter myadapter;
	private boolean hasMore = true;
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
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(R.id.titleTv);
		titleTV.setText("我的收藏");
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(new GoBackListener());

		goBack.setOnClickListener(new GoBackListener());
		stuId = (String) UserSharedPreferences.getParam(MyCollectActivity.this, "stuId", "");
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		gridView = mPullRefreshGridView.getRefreshableView();
		// 设置上拉显示字幕
		initIndicator();
		mPullRefreshGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				CollectCourses positionDatas = myCourses.get(position);
				String image_urlshare = Constants.DOMAIN + "/file/load?image="
						+ myCourses.get(position).getImg_path();
				Intent intent = new Intent(MyCollectActivity.this, DetailCoursesActivity.class);
				intent.putExtra("id", positionDatas.getCourse_id());
				intent.putExtra("image_urlshare", image_urlshare);
				startActivity(intent);
			}
		});
		if (Utils.isExistNetwork(this)) {
			initGridView(stuId);
		} else {
			Toast.makeText(MyCollectActivity.this, "网络错误，请检查你的网络！", Toast.LENGTH_SHORT).show();
		}

	}

	private class GoBackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	private void initIndicator() {
		endLabels = mPullRefreshGridView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel(getString(R.string.pull_to_up_pull));// 刚上拉时，显示的提示
		endLabels.setRefreshingLabel(getString(R.string.pull_to_up_refreshing));// 刷新时
		endLabels.setReleaseLabel(getString(R.string.pull_to_up_release));// 下来达到一定距离时，显示的提示
	}

	private void GetOpenCourses() {
		if (null != stuId &&stuId.length() != 0 ) {
			pageIndex = 1;
			HttpUtil.getFullUrl(
					Constants.GET_MY_COLLECT + "?stuId=" + stuId + "&pageNo=" + pageIndex + "&pageSize="
							+ Constants.PAGE_SIZE,
					null, new AsyncHttpCilentHandler<MyCollectCourses>(MyCollectActivity.this, MyCollectCourses.class) {
						@Override
						public void onFailure(Throwable paramThrowable, String paramString) {
						}

						@Override
						public void onSuccess(String param) {
							JsonString = param;
							if (JsonString != null && JsonString.length() != 0) {
								openCourses = JSON.parseObject(JsonString, MyCollectCourses.class);
								myCourses = openCourses.getRows();
								if (null != myCourses&&myCourses.size() != 0 ) {
									myadapter = new MyCollectAdapter(MyCollectActivity.this, myCourses);
									if (myCourses.size() == Constants.PAGE_SIZE) {
										handler.sendEmptyMessage(101);
										pageIndex++;
									} else {
										IfHasDown = false;
										hasMore = false;
										handler.sendEmptyMessage(101);
									}

								}

							}

						}

						@Override
						public void process(MyCollectCourses paramT) {
						}
					}, MyCollectActivity.this);
		}
	}

	private void initGridView(final String typeId) {
		GetOpenCourses();
		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			Handler handler = new Handler();
			private MyCollectCourses openCourses;
			private int lastPage;

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			}

			/**
			 * @param currentpage
			 * @param maxTypeId
			 */

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				// 上拉加载更多的课

				loadMoreCourses(typeId);

			}

			@SuppressLint("ShowToast")
			private void loadMoreCourses(String typeId) {
				// TODO Auto-generated method stub
				if (hasMore) {
					HttpUtil.getFullUrl(
							Constants.GET_OPEN_COURSES + "?stuId=" + stuId + "&pageNo=" + pageIndex + "&pageSize="
									+ Constants.PAGE_SIZE,
							null, new AsyncHttpCilentHandler<MyCollectCourses>(MyCollectActivity.this,
									MyCollectCourses.class) {
						@Override
						public void onFailure(Throwable paramThrowable, String paramString) {
						}

						@Override
						public void onSuccess(String paramString) {
							if (paramString != null && paramString.length() != 0) {
								openCourses = JSON.parseObject(JsonString, MyCollectCourses.class);
								if (openCourses != null) {
									List<CollectCourses> Courses = openCourses.getRows();
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
						public void process(MyCollectCourses paramT) {
						}
					}, MyCollectActivity.this);

				}
			}

		});
	}
}
