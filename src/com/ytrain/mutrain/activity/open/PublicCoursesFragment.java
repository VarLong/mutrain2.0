package com.ytrain.mutrain.activity.open;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.entity.courses.PageCourses;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PublicCoursesFragment extends Fragment {
	/**
	 * 显示的主界面 公开课
	 */
	private View view;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private ILoadingLayout endLabels;
	private String CoursesString;
	private int pageIndex;
	private boolean hasMore;
	private OpenAdapter myadapter;
	private PageCourses Courseslist;
	private String typeid;
	private List<BaseCourses> CoursesDatas = null;
	/**
	 * 根据获取的数据大小判断是否具有上拉加载的功能
	 */
	private boolean IfHasDown = true;
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
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle b = getArguments();
		typeid = b.getString("id");
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_my_train, container, false);
		// 得到控件
		mPullRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.pull_refresh_grid);
		gridView = mPullRefreshGridView.getRefreshableView();
		mPullRefreshGridView
				.setOnLastItemVisibleListener(new LastItemListener());
		// 设置上拉显示字幕
		initIndicator();

		// 对 grid item设置监听
		mPullRefreshGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BaseCourses positionDatas = CoursesDatas.get(position);
				String image_urlshare = Constants.DOMAIN + "/file/load?image="
						+ CoursesDatas.get(position).getImg_path();
				Intent intent = new Intent(getActivity(),
						DetailCoursesActivity.class);
				intent.putExtra("image_urlshare", image_urlshare);
				intent.putExtra("id", positionDatas.getId());
				startActivity(intent);
			}
		});
		if (Utils.isExistNetwork(getActivity())) {
			// 调用setRefreshing来完成自动刷新
			mPullRefreshGridView.setRefreshing();
			initGridView(typeid);
		} else {

			Toast.makeText(getActivity(), "网络错误，请检查你的网络!", Toast.LENGTH_SHORT)
					.show();
		}
		return view;
	}

	private void initIndicator() {
		endLabels = mPullRefreshGridView.getLoadingLayoutProxy(false, true);
		endLabels.setPullLabel(getString(R.string.pull_to_up_pull));// 刚上拉时，显示的提示
		endLabels.setRefreshingLabel(getString(R.string.pull_to_up_refreshing));// 刷新时
		endLabels.setReleaseLabel(getString(R.string.pull_to_up_release));// 下来达到一定距离时，显示的提示
	}

	private class LastItemListener implements OnLastItemVisibleListener {

		@Override
		public void onLastItemVisible() {

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
				new AsyncHttpCilentHandler<PageCourses>(getActivity(),
						PageCourses.class) {
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
							if (Courseslist != null) {
								CoursesDatas = Courseslist.getRows();								
								myadapter = new OpenAdapter(getActivity(),
										CoursesDatas);
								if (Constants.PAGE_SIZE > CoursesDatas.size()) {
									IfHasDown = false;
									hasMore = false;
									handler.sendEmptyMessage(101);
								} else {
									IfHasDown = true;
									pageIndex = pageIndex + 1;
									handler.sendEmptyMessage(101);
								}
							}
						}
					}

					@Override
					public void process(PageCourses paramT) {
					}
				}, getActivity());

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
					new AsyncHttpCilentHandler<PageCourses>(getActivity(),
							PageCourses.class) {
						@Override
						public void onFailure(Throwable paramThrowable,
								String paramString) {
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
										CoursesDatas.addAll(RowsCourses);
										if (Constants.PAGE_SIZE == RowsCourses
												.size()) {
											// 将数据添加在 courseDatas中

											pageIndex++;
											handler.sendEmptyMessage(102);
										} else {
											hasMore = false;
											handler.sendEmptyMessage(108);
										}
									} else {
										handler.sendEmptyMessage(108);
									}

								}
							}
						}

						@Override
						public void process(PageCourses paramT) {
						}
					}, getActivity());
		}
	}

}
