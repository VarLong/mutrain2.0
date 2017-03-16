package com.ytrain.mutrain.activity.train;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.entity.train.SeriesComment;
import com.ytrain.mutrain.entity.train.SeriesCommentPages;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommentFragment extends Fragment {
	/**
	 * 团建内容界面viewpager第三页
	 */
	private List<SeriesComment> commentDatas;
	private SeriesCommentAdapter adapter;
	private String id;
	private ListView commentLV;

	public static CommentFragment newInstance(String id) {
		CommentFragment fragment = new CommentFragment();
		Bundle b = new Bundle();
		b.putString("id", id);
		fragment.setArguments(b);
		return fragment;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adapter = new SeriesCommentAdapter(getActivity(), commentDatas);
			commentLV.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		id = bundle.getString("id");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View contextView = inflater.inflate(R.layout.fragment_comment,
				container, false);
		commentLV = (ListView) contextView.findViewById(R.id.commentLV);
		if (Utils.isExistNetwork(getActivity())) {
			GetSeriesComment();
		}

		return contextView;
	}

	private void GetSeriesComment() {
		boolean hasMore = true;
		int currentpage = 1;
		if (hasMore) {
			HttpUtil.getFullUrl(Constants.GET_SERIES_COMMENT
					+ "?seriesCourseId=" + id + "&pageNo=" + currentpage
					+ "&pageSize=" + Constants.PAGE_SIZE, null,
					new AsyncHttpCilentHandler<SeriesCommentPages>(
							getActivity(), SeriesCommentPages.class) {
						@Override
						public void onFailure(Throwable paramThrowable,
								String paramString) {
						}

						@Override
						public void onSuccess(String paramString) {
							SeriesCommentPages commentPages = JSON
									.parseObject(paramString,
											SeriesCommentPages.class);
							commentDatas = commentPages.getRows();
							if (commentDatas!=null) {
								handler.sendEmptyMessage(101);
							}
							
						}

						@Override
						public void process(SeriesCommentPages paramT) {
						}
					}, getActivity());
		}
	}
}
