package com.ytrain.mutrain.activity.home;

import java.lang.ref.WeakReference;
import java.util.List;

import com.ytrain.mutrain.adapter.BodyCoursesAdapter;
import com.ytrain.mutrain.adapter.ImageAdapter;
import com.ytrain.mutrain.entity.BannerList;
import com.ytrain.mutrain.entity.body.BodyCourses;
import com.ytrain.mutrain.entity.body.BodyCourseslist;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.indicator.CirclePageIndicator;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint({ "ShowToast", "InflateParams" })
public class HomeFragment extends Fragment {

	private View view;
	View headerView;
	private ViewPager pager;
	private List<BodyCourses> list;
	private ListView bodylv;
	private List<BaseCourses> banlist;
	private CirclePageIndicator circlePageIndicator;
	private static final String LOG_TAG = "ViewPagerIndicator";
	private ImageHandler handler;
	private static boolean left = false;
	private static boolean right = false;
	private int lastValue = -1;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.homefragment, container, false);
		headerView = LayoutInflater.from(getActivity()).inflate(
				R.layout.circlefragment, null);
		bodylv = (ListView) view.findViewById(R.id.bodylist);
		circlePageIndicator = (CirclePageIndicator) headerView
				.findViewById(R.id.id_indicator);
		pager = (ViewPager) headerView.findViewById(R.id.pager);
		bodylv.setDividerHeight(0);
		bodylv.addHeaderView(headerView, null, false);
		
		bodylv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						MaxCoursesActivity.class);
				intent.putExtra("id", list.get(0).getMax_type_id());
				startActivity(intent);
			}
		});
		getBodyCourses();
		getBanner();
		return view;
	}

	/**
	 * 初始化circlepager
	 */
	private void pagerLogin() {
		pager.setAdapter(new ImageAdapter(getActivity(), banlist));
		circlePageIndicator.setViewPager(pager);
		handler = new ImageHandler(new WeakReference<HomeFragment>(this),
				banlist.size());
		circlePageIndicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					// 配合Adapter的currentItem字段进行设置。
					@Override
					public void onPageSelected(int arg0) {
						handler.sendMessage(Message.obtain(handler,
								ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

						if (lastValue > arg2) {
							// 递减，向右侧滑动
							right = true;
							left = false;
						} else if (lastValue < arg2) {
							// 递减，向右侧滑动
							right = false;
							left = true;
						} else if (lastValue == arg2) {
							right = left = false;
						}

						lastValue = arg2;
					}

					// 覆写该方法实现轮播效果的暂停和恢复
					@Override
					public void onPageScrollStateChanged(int arg0) {
						switch (arg0) {
						case ViewPager.SCROLL_STATE_DRAGGING:
							handler.sendEmptyMessageDelayed(
									ImageHandler.MSG_KEEP_SILENT, 200);
							break;
						case ViewPager.SCROLL_STATE_IDLE:
							handler.sendEmptyMessageDelayed(
									ImageHandler.MSG_UPDATE_IMAGE,
									ImageHandler.MSG_DELAY);
							break;
						default:
							break;
						}

					}
				});
		pager.setCurrentItem(0);
		// 开始轮播效果
		handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
				ImageHandler.MSG_DELAY);
	}

	/**
	 * 得到是否向右侧滑动
	 * 
	 * @return true 为右滑动
	 */
	public static boolean getMoveRight() {
		return right;
	}

	/**
	 * 得到是否向左侧滑动
	 * 
	 * @return true 为左做滑动
	 */
	public static boolean getMoveLeft() {
		return left;
	}

	/**
	 * 获取banner课程
	 */
	private void getBanner() {
		HttpUtil.getFullUrl(Constants.GET_BANNER_COURSES, null,
				new AsyncHttpCilentHandler<BannerList>(getActivity(),
						BannerList.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {						
					}
					@Override
					public void onSuccess(String paramString) {

						if (paramString != null && paramString.length() != 0) {
							if (paramString.startsWith("{")
									&& paramString.endsWith("}")) {
								paramString = "[" + paramString + "]";
							}
							paramString = "{bannerList:" + paramString + "}";
						}
						super.onSuccess(paramString);
					}

					@Override
					public void process(BannerList paramT) {

						banlist = paramT.getBannerList();
						pagerLogin();
					}
				}, getActivity());
	}

	/**
	 * 获取body课程
	 */
	private void getBodyCourses() {
		HttpUtil.getFullUrl(Constants.GET_BODY_COURSES, null,
				new AsyncHttpCilentHandler<BodyCourseslist>(getActivity(),
						BodyCourseslist.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
						Toast.makeText(context, "网络错误，请检查你的网络！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							if (paramString.startsWith("{")
									&& paramString.endsWith("}")) {
								paramString = "[" + paramString + "]";
							}
							paramString = "{bodyCourseList:" + paramString
									+ "}";
						}
						super.onSuccess(paramString);
					}

					@Override
					public void process(BodyCourseslist paramT) {

						list = paramT.getBodyCourseList();
						BodyCoursesAdapter adapter = new BodyCoursesAdapter(
								getActivity(), list);
						bodylv.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				}, getActivity());
	}

	private static class ImageHandler extends Handler {
		/**
		 * 请求更新显示的View。
		 */
		protected static final int MSG_UPDATE_IMAGE = 1;
		/**
		 * 请求暂停轮播。
		 */
		protected static final int MSG_KEEP_SILENT = 2;
		/**
		 * 请求恢复轮播。
		 */
		protected static final int MSG_BREAK_SILENT = 3;
		/**
		 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
		 * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
		 * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
		 */
		protected static final int MSG_PAGE_CHANGED = 4;

		// 轮播间隔时间
		protected static final long MSG_DELAY = 3000;
		// 使用弱引用避免Handler泄露.
		private WeakReference<HomeFragment> weakReference;
		private int currentItem = 0;
		private int size;

		protected ImageHandler(WeakReference<HomeFragment> wk, int size) {
			weakReference = wk;
			this.size = size;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			HomeFragment fragment = weakReference.get();
			if (fragment == null) {
				// Activity已经回收，无需再处理UI了
				return;
			}
			// 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)) {
				fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem = (1 + currentItem % size) % size;
				fragment.pager.setCurrentItem(currentItem);
				// 准备下次播放
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
						MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				// 实现滑动轮播的效果，如果不需要滑动轮播 注释掉即可
				currentItem = (currentItem % size) % size;
				if (currentItem == 0 && getMoveLeft() == false
						&& getMoveRight() == false) {
					fragment.pager.setCurrentItem(size - 1);
				}
				if (currentItem == size - 1 && getMoveLeft() == false
						&& getMoveRight() == false) {
					fragment.pager.setCurrentItem(0);
				}
				// 只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
						MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				break;
			default:
				break;
			}
		}
	}
}
