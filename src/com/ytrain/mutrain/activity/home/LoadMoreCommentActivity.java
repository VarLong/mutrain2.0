package com.ytrain.mutrain.activity.home;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.adapter.CommentAdapter;
import com.ytrain.mutrain.entity.CommentPage;
import com.ytrain.mutrain.entity.PublicComments;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoadMoreCommentActivity extends Activity {
	/**
	 * 加载更多评论
	 */
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private ILoadingLayout endLabels;
	private CommentPage commentPage;
	private List<PublicComments> commentList;
	private String id;
	private int currentpage = 1;
	private CommentAdapter commentAdapter;
	private boolean hasMore;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 101:

				gridView.setAdapter(commentAdapter);
				commentAdapter.notifyDataSetChanged();
				break;
			case 102:
				commentAdapter.notifyDataSetChanged();
				mPullRefreshGridView.onRefreshComplete();
				break;
			case 108:
				commentAdapter.notifyDataSetChanged();
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
		setContentView(R.layout.activity_morecomment);
		hasMore = true;
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("评论列表");
		ImageView goBack = (ImageView) actionBar.getCustomView().findViewById(
				R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(new GoBackListener());
		id = getIntent().getExtras().getString("id");

		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.comment);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
		gridView = mPullRefreshGridView.getRefreshableView();
		// 设置上拉显示字幕
		initIndicator();

		if (Utils.isExistNetwork(this)) {
			initGridView();
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
			finish();
		}

	}

	private void GetOpenCoursesData() {
		HttpUtil.getFullUrl(
				Constants.PUBLIC_COMMENTS + "?courseId=" + id + "&pageNo="
						+ currentpage + "&pageSize=" + Constants.PAGE_SIZE,
				null, new AsyncHttpCilentHandler<CommentPage>(
						LoadMoreCommentActivity.this, CommentPage.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {

						if (paramString != null && paramString.length() != 0) {
							commentPage = JSON.parseObject(paramString,
									CommentPage.class);
							commentList = commentPage.getRows();
							commentAdapter = new CommentAdapter(
									LoadMoreCommentActivity.this, commentList);
							if (commentList.size() == Constants.PAGE_SIZE) {
								currentpage = currentpage + 1;
								handler.sendEmptyMessage(101);
							} else {
								handler.sendEmptyMessage(101);
								hasMore = false;
							}
						}
					}

					@Override
					public void process(CommentPage paramT) {
					}
				}, LoadMoreCommentActivity.this);
	}

	private void initGridView() {
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
						loadMore();
					}
				});
	}

	protected void loadMore() {
		if (hasMore) {
			HttpUtil.getFullUrl(Constants.PUBLIC_COMMENTS + "?courseId=" + id
					+ "&pageNo=" + currentpage + "&pageSize="
					+ Constants.PAGE_SIZE, null,
					new AsyncHttpCilentHandler<CommentPage>(
							LoadMoreCommentActivity.this, CommentPage.class) {
						@Override
						public void onFailure(Throwable paramThrowable,
								String paramString) {
							handler.sendEmptyMessage(102);
						}

						@Override
						public void onSuccess(String paramString) {
							if (paramString != null
									&& paramString.length() != 0) {
								commentPage = JSON.parseObject(paramString,
										CommentPage.class);
								List<PublicComments> commentListMore = commentPage
										.getRows();
								if (commentListMore!=null) {
									if (commentListMore.size() == Constants.PAGE_SIZE) {
										commentList.addAll(commentListMore);
										currentpage = currentpage + 1;
										handler.sendEmptyMessage(102);
									} else {
										hasMore = false;
										commentList.addAll(commentListMore);
										handler.sendEmptyMessage(108);
									}
								}else {
									handler.sendEmptyMessage(108);
								}
							
							}
						}

						@Override
						public void process(CommentPage paramT) {
						}
					}, LoadMoreCommentActivity.this);

		} else {

		}

	}

}
