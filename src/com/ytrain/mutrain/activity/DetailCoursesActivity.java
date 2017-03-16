package com.ytrain.mutrain.activity;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.lecloud.download.control.DownloadCenter;
import com.lecloud.skin.PlayerStateCallback;
import com.lecloud.skin.vod.VODPlayCenter;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.home.LoadMoreCommentActivity;
import com.ytrain.mutrain.adapter.CommentAdapter;
import com.ytrain.mutrain.adapter.RecommAdapter;
import com.ytrain.mutrain.entity.CommentPage;
import com.ytrain.mutrain.entity.PublicComments;
import com.ytrain.mutrain.entity.courses.CollectCourses;
import com.ytrain.mutrain.entity.courses.DetailCourses;
import com.ytrain.mutrain.entity.courses.RecommCourList;
import com.ytrain.mutrain.entity.courses.RecommendCourses;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.utils.asynchttp.RequestParams;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import com.ytrain.mutrain.view.CustomLoginDialog;
import com.ytrain.mutrain.view.HorizontalListView;
import com.ytrain.mutrain.view.MyListview;
import com.ytrain.mutrain.view.TeacherDetailDialog;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DetailCoursesActivity extends Activity {
	private ScrollView sv;
	private String id, image_urlshare;
	private ImageButton collectbtn;
	private ImageButton sharebtn;
	private TextView sourcetv;
	private TextView introductiontv;
	private TextView teachertv;
	private DetailCourses courses = null;
	private HorizontalListView hListView;
	private TextView logintocomment;
	private TextView loadmore;
	private CommentPage commentPage;
	private CommentAdapter commentAdapter = null;
	private MyListview myListview;
	private List<PublicComments> commentList = null;
	private CustomLoginDialog dialog;
	private RequestParams params;
	int currentpage = 1;
	String CoursesStringRe;
	private List<RecommendCourses> recommcourlist;
	private SelectPicPopupWindow menuWindow;
	private RelativeLayout mPlayerLayoutView;
	private VODPlayCenter mPlayerView = null;
	Button button1;
	Button button2;
	InputMethodManager imm;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (courses.isFavorites()) {
					collectbtn.setImageResource(R.drawable.details_collected);
					collectbtn.setOnClickListener(new CancleCollect());
				} else {
					collectbtn.setImageResource(R.drawable.details_collect);
					collectbtn.setOnClickListener(new CollectListener());
				}
				String description = courses.getDescription();
				sourcetv.setText(courses.getSource_name());
				introductiontv.setText("课程简介: " + FormatString(description));
				teachertv.setText(courses.getTeacher_name());

				// uu-用户标识,vu-视频唯一标识.UserKey-用户申请获得的KEY,CheckCode-可选,PayerName-视频名称
				if (DetailCoursesActivity.this.mPlayerView != null) {
					DetailCoursesActivity.this.mPlayerView.playVideo(Utils.UUID, courses.getUnique(), Utils.UserKey, "",
							"测试节目", true);

				}

				GetRecommendCourses();
				break;
			case 2:
				if (CoursesStringRe != null && CoursesStringRe.length() != 0) {
					if (CoursesStringRe.startsWith("{") && CoursesStringRe.endsWith("}")) {
						CoursesStringRe = "[" + CoursesStringRe + "]";
					}
					CoursesStringRe = "{recommedlist:" + CoursesStringRe + "}";
					RecommCourList recommedlist = JSON.parseObject(CoursesStringRe, RecommCourList.class);
					recommcourlist = recommedlist.getRecommedlist();
					RecommAdapter recommAdapter = new RecommAdapter(recommcourlist, DetailCoursesActivity.this);
					hListView.setAdapter(recommAdapter);
					recommAdapter.notifyDataSetChanged();
				}
				break;
			case 110:
				if (menuWindow.isShowing()) {
					// 显示或者隐藏输入法
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(menuWindow.contentET.getWindowToken(), 0);
					}
					menuWindow.dismiss();
				}
				Toast.makeText(DetailCoursesActivity.this, "发表成功！", Toast.LENGTH_SHORT).show();
				getPublicComments();
				break;
			case 115:
				if (commentList != null && commentList.size() != 0) {
					commentAdapter = new CommentAdapter(DetailCoursesActivity.this, commentList);
					myListview.setAdapter(commentAdapter);
					commentAdapter.notifyDataSetChanged();
					// 将滚动条放到头部
					sv.smoothScrollTo(0, 0);
				}
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 广播接收注册完成功发过来的信息，以便更新界面更新
	 */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("GETNAME")) {
				// 登录成功后更改播放界面，以便直接可以进行评论。
				logintocomment.setText("我也来说两句");
				logintocomment.setOnClickListener(new commentListener());
			}

		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 视频播放需要销毁的,
		this.mPlayerView.destroyVideo();
		this.mPlayerLayoutView.removeAllViews();
		this.mPlayerView = null;
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onPause() {

		super.onPause();
		if (this.mPlayerView != null) {
			mPlayerView.pauseVideo();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (this.mPlayerView != null) {
			this.mPlayerView.resumeVideo();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_open);
		// 动态注册广播
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("GETNAME");
		this.registerReceiver(broadcastReceiver, intentFilter);

		id = getIntent().getExtras().getString("id");
		image_urlshare = getIntent().getExtras().getString("image_urlshare");
		sv = (ScrollView) findViewById(R.id.scrollView);
		mPlayerLayoutView = (RelativeLayout) findViewById(R.id.videoView);
		DetailCoursesActivity.this.mPlayerView = new VODPlayCenter(this, true);
		this.mPlayerLayoutView.addView(DetailCoursesActivity.this.mPlayerView.getPlayerView());

		/**
		 * 播放状态Callback接口 PLAYER_VIDEO_PLAY ：视频开始播放事件 PLAYER_VIDEO_PAUSE
		 * ：视频暂停播放事件 PLAYER_VIDEO_RESUME：视频恢复播放事件 PLAYER_VIDEO_STOP：视频停止播放事件
		 * PLAYER_SEEK_FINISH：视频拖拽完成事件 PLAYER_VIDEO_COMPLETE：视频播放结束事件
		 * PLAYER_ERROR：视频播放出错
		 */
		DetailCoursesActivity.this.mPlayerView.setPlayerStateCallback(new PlayerStateCallback() {
			@Override
			public void onStateChange(int state, Object... extra) {
				if (state == PlayerStateCallback.PLAYER_VIDEO_PAUSE) {
					Log.e("PLAYER_VIDEO_PAUSE", "视频暂停播放事件");
				} else if (state == PlayerStateCallback.PLAYER_VIDEO_PLAY) {
					Log.e("PLAYER_VIDEO_PLAY", "视频开始播放事件 ");
				} else if (state == PlayerStateCallback.PLAYER_VIDEO_RESUME) {
					Log.e("PLAYER_VIDEO_RESUME", "视频恢复播放事件");

				} else if (state == PlayerStateCallback.PLAYER_STOP) {
					Log.e("PLAYER_STOP", "视频停止播放事件");

				} else if (state == PlayerStateCallback.PLAYER_ERROR) {
					Log.e("PLAYER_ERROR", "视频播放出错");

				} else if (state == PlayerStateCallback.PLAYER_SEEK_FINISH) {
					Log.e("PLAYER_SEEK_FINISH", "视频拖拽完成事件");

				} else if (state == PlayerStateCallback.PLAYER_VIDEO_COMPLETE) {
					Log.e("PLAYER_VIDEO_COMPLETE", "视频播放结束事件");

				}
			}
		});
		mPlayerView.bindDownload(DownloadCenter.getInstances(this));
		DownloadCenter.getInstances(this).allowShowMsg(false);

		params = new RequestParams();
		params.put("courseId", id);
		params.put("stuId", (String) UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", ""));
		initview();
		if (Utils.isExistNetwork(this)) {
			getDataThread();
		} else {
			Toast.makeText(this, "网络错误，请检查你的网络！", Toast.LENGTH_SHORT).show();
		}

	}

	// 初始化
	private void initview() {
		collectbtn = (ImageButton) findViewById(R.id.collect);
		sharebtn = (ImageButton) findViewById(R.id.share);
		sourcetv = (TextView) findViewById(R.id.soure);
		introductiontv = (TextView) findViewById(R.id.introduction);
		teachertv = (TextView) findViewById(R.id.teacher);
		hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
		logintocomment = (TextView) findViewById(R.id.logintocomment);
		loadmore = (TextView) findViewById(R.id.loadmore);
		myListview = (MyListview) findViewById(R.id.commentlist);

		if (UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", "").equals("")) {
			logintocomment.setText("登陆后可发表评论");
			logintocomment.setOnClickListener(new LoginListener());
		} else {
			logintocomment.setText("我也来说两句");
			logintocomment.setOnClickListener(new commentListener());
		}

		getPublicComments();

		myListview.setDividerHeight(0);
		// 加载更多
		loadmore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DetailCoursesActivity.this.mPlayerView.pauseVideo();
				Intent intent = new Intent(DetailCoursesActivity.this, LoadMoreCommentActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);

			}
		});
		sharebtn.setOnClickListener(new ShareListener());
		hListView.setOnItemClickListener(new OnHorItemListener());
		teachertv.setOnClickListener(new TeacherClickListener());
	}

	/**
	 * 获取公开课评论列表,评论框不能写在线程中.
	 */
	private void getPublicComments() {
		int currentpage = 1;
		HttpUtil.getFullUrl(
				Constants.PUBLIC_COMMENTS + "?courseId=" + id + "&pageNo=" + currentpage + "&pageSize="
						+ Constants.PAGE_SIZE,
				null, new AsyncHttpCilentHandler<CommentPage>(DetailCoursesActivity.this, CommentPage.class) {
					int currentpage = 1;

					@Override
					public void onFailure(Throwable paramThrowable, String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							commentPage = JSON.parseObject(paramString, CommentPage.class);
							if (currentpage <= commentPage.getPageCount()) {
								commentList = commentPage.getRows();
							} else {
							}
						}
						handler.sendEmptyMessage(115);
					}

					@Override
					public void process(CommentPage paramT) {
					}
				}, DetailCoursesActivity.this);
	}

	/**
	 * 获取单个公开课课程的详细信息
	 */
	private void getDataThread() {
		HttpUtil.getFullUrl(
				Constants.GET_PUCLIC_COURSE + "?id=" + id + "&stuId="
						+ (String) UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", ""),
				null, new AsyncHttpCilentHandler<DetailCourses>(DetailCoursesActivity.this, DetailCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable, String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							courses = JSON.parseObject(paramString, DetailCourses.class);
						}
						Message msg = handler.obtainMessage();
						msg.what = 1;
						handler.sendMessage(msg);
					}

					@Override
					public void process(DetailCourses paramT) {
					}
				}, DetailCoursesActivity.this);

	}

	public static void reSetListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 获得推荐课程
	 * 
	 */
	private void GetRecommendCourses() {
		String minTypeId = courses.getMin_type_id();
		HttpUtil.getFullUrl(Constants.GET_PUBLIC_RECOMMEND_COURSES + "?minTypeId=" + minTypeId, null,
				new AsyncHttpCilentHandler<RecommCourList>(DetailCoursesActivity.this, RecommCourList.class) {
					@Override
					public void onFailure(Throwable paramThrowable, String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						CoursesStringRe = paramString;
						Message msg = handler.obtainMessage();
						msg.what = 2;
						handler.sendMessage(msg);
					}

					@Override
					public void process(RecommCourList paramT) {
					}
				}, DetailCoursesActivity.this);

	}

	/**
	 * 处理text换行杂乱问题
	 * 
	 * @param StringData
	 * @return
	 */
	private String FormatString(String StringData) {

		if (StringData != null) {
			StringData = StringData.replaceAll("\r|\n", "");
		}
		return StringData;
	}

	/**
	 * 收藏点击
	 * 
	 * @author Administrator
	 * 
	 */
	private class CollectListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", "").equals("")) {
				dialog = new CustomLoginDialog(DetailCoursesActivity.this, R.style.logindialog);
				dialog.show();
			} else {// 点击收藏
				HttpUtil.postFullUrl(Constants.SAVE_PUBLIC_FAVORITE, params,
						new AsyncHttpCilentHandler<CollectCourses>(DetailCoursesActivity.this, CollectCourses.class) {
							@Override
							public void process(CollectCourses paramT) {
								Log.e("process", "process");

								if (paramT.isSuccess()) {
									collectbtn.setImageResource(R.drawable.details_collected);
									Toast.makeText(DetailCoursesActivity.this, paramT.getMessage(), Toast.LENGTH_SHORT)
											.show();
									collectbtn.setOnClickListener(new CancleCollect());
								}
							}
						}, DetailCoursesActivity.this);
			}

		}

	}

	/**
	 * 取消收藏
	 * 
	 * @author Administrator
	 * 
	 */
	private class CancleCollect implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", "").equals("")) {
				dialog = new CustomLoginDialog(DetailCoursesActivity.this, R.style.logindialog);
				dialog.show();
			} else {
				HttpUtil.postFullUrl(
						Constants.DELETE_PUBLIC_FAVORITE + "?courseId=" + id + "&stuId="
								+ (String) UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", ""),
						params,
						new AsyncHttpCilentHandler<CollectCourses>(DetailCoursesActivity.this, CollectCourses.class) {
							@Override
							public void process(CollectCourses paramT) {

								if (paramT.isSuccess()) {
									Toast.makeText(DetailCoursesActivity.this, paramT.getMessage(), Toast.LENGTH_SHORT)
											.show();
									collectbtn.setImageResource(R.drawable.details_collect);
									collectbtn.setOnClickListener(new CollectListener());
								}
							}
						}, DetailCoursesActivity.this);
			}
		}

	}

	private class ShareListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			showShare();
		}

	}

	private class LoginListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			dialog = new CustomLoginDialog(DetailCoursesActivity.this, R.style.logindialog);
			dialog.show();

		}
	}

	private class OnHorItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			Intent intent = new Intent(DetailCoursesActivity.this, DetailCoursesActivity.class);
			intent.putExtra("id", recommcourlist.get(position).getCourse_id());
			startActivity(intent);
			finish();

		}
	}

	/**
	 * 教师信息弹出框
	 * 
	 * @author zhuhj
	 */
	private class TeacherClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (courses != null) {
				TeacherDetailDialog dialog = new TeacherDetailDialog(DetailCoursesActivity.this, R.style.teacherdialog,
						courses.getTeacher_name(), courses.getTeacher_description(), courses.getTeacher_img_path());
				dialog.show();
			}

		}

	}

	/**
	 * 评论点击
	 * 
	 * @author Administrator
	 * 
	 */
	private class commentListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			menuWindow = new SelectPicPopupWindow(DetailCoursesActivity.this);
			// 显示窗口
			menuWindow.showAtLocation(DetailCoursesActivity.this.findViewById(R.id.courses),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
		}
	}

	/**
	 * 评论弹出框
	 * 
	 * @author Administrator
	 * 
	 */
	public class SelectPicPopupWindow extends PopupWindow {
		private Context context;
		private View mMenuView;
		private EditText contentET;
		private ImageView commentSend;

		public SelectPicPopupWindow(Activity context) {
			this.context = context;
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			mMenuView = inflater.inflate(R.layout.alert_dialog, null);
			contentET = (EditText) mMenuView.findViewById(R.id.content);
			commentSend = (ImageView) mMenuView.findViewById(R.id.commentsend);
			commentSend.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// 隐藏输入法

					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							sendComment(contentET.getText().toString());
						}
					});
					thread.start();
				}
			});
			// 设置SelectPicPopupWindow的View
			this.setContentView(mMenuView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.MATCH_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			this.setFocusable(true);
			// 设置SelectPicPopupWindow弹出窗体动画效果
			this.setAnimationStyle(R.style.commentstyle);
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(R.color.white);
			// 设置SelectPicPopupWindow弹出窗体的背景
			this.setBackgroundDrawable(dw);
			// 设置有输入法时，输入框会被顶上去
			this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		}

	}

	/**
	 * 保存评论信息
	 */
	private void sendComment(String string) {
		RequestParams params;
		params = new RequestParams();
		params.put("courseId", id);
		params.put("content", string);
		params.put("touristName", (String) UserSharedPreferences.getParam(DetailCoursesActivity.this, "name", ""));
		params.put("userId", (String) UserSharedPreferences.getParam(DetailCoursesActivity.this, "stuId", ""));
		params.put("userType", String.valueOf(2));

		// 发送评论
		HttpUtil.postFullUrl(Constants.SAVE_PUBLIC_COMMENT, params,
				new AsyncHttpCilentHandler<CollectCourses>(DetailCoursesActivity.this, CollectCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable, String paramString) {

					}

					@Override
					public void onSuccess(String paramString) {
						handler.sendEmptyMessage(110);
					}

					@Override
					public void process(CollectCourses paramT) {

					}
				}, DetailCoursesActivity.this);

	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("U培训");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(Utils.getVideoUrl(id));
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用，点击图片的网址
		oks.setSiteUrl(Utils.getVideoUrl(id));
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(Utils.getVideoUrl(id));
		
		// text是分享文本，所有平台都需要这个字段
		oks.setText("U培训是打造全球最领先的企业培训交流平台和资讯中心，让中国企业共享全球优秀学习资源，从学习走向合作，从优秀走向卓越。");
		// String path = "file:///android_asset/ic_launcher.png";


		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");

		// 显示编辑页面
		oks.setSilent(true);
		oks.setCallback(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// 分享错误
				Toast.makeText(DetailCoursesActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
				
				
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// 取消分享

			}
		});

		// 参考代码配置章节，设置分享参数
		// 通过OneKeyShareCallback来修改不同平台分享的内容
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。 本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
	 */
	public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {
		public void onShare(Platform platform, ShareParams paramsToShare) {
			// 改写twitter分享内容中的text字段，否则会超长，
			// 因为twitter会将图片地址当作文本的一部分去计算长度
			if (QQ.NAME.equals(platform.getName())) {
				String text = platform.getContext().getString(R.string.app_name);
				paramsToShare.setText(text);

			}
			if (TencentWeibo.NAME.equals(platform.getName())) {
//				imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段
//				oks.setImageUrl(image_urlshare);
			}
			if (QQ.NAME.equals(platform.getName())) {

			}
			if (QZone.NAME.equals(platform.getName())) {
//				imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段
//				oks.setImageUrl(image_urlshare);
			}
			if (Wechat.NAME.equals(platform.getName())) {
				
			}
			if (WechatMoments.NAME.equals(platform.getName())) {
				
				
				

			}
			if (Email.NAME.equals(platform.getName())) {

			}
		}

	}
}