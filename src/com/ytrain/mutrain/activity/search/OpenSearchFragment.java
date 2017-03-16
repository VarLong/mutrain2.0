package com.ytrain.mutrain.activity.search;

import java.util.List;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ssy.utils.Utils;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.activity.open.OpenSearchAdapter;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.entity.courses.PageCourses;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;

public class OpenSearchFragment extends Fragment {
	private String keyword;
	TextView textview;
	private ListView searchlist;
	private OpenSearchAdapter adapter;
	private int GETMESS = 100;
	private List<BaseCourses> SearchDatas;
	int currentpage = 1;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			searchlist.setAdapter(adapter);
		}

	};

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		keyword = bundle.getString("keyword");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_layout, container, false);
		searchlist = (ListView) view.findViewById(R.id.searchlist);
		searchlist.setDividerHeight(0);
		searchlist.setOnItemClickListener(new OnSearchItemClickListener());
		if (Utils.isExistNetwork(getActivity())) {
			GetSearchDatas(textview);
		}
		return view;
	}

	private void GetSearchDatas(TextView textView) {

		HttpUtil.getFullUrl(Constants.GET_PUBLIC_COURSES + "?keyword="
				+ keyword + "&pageNo=" + currentpage + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<PageCourses>(getActivity(),
						PageCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
						
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							PageCourses Courseslist = JSON.parseObject(
									paramString, PageCourses.class);
							List<BaseCourses> SearchCourses = Courseslist
									.getRows();
							if (SearchCourses.size() < Constants.PAGE_SIZE) {
								SearchDatas = SearchCourses;
								if (SearchDatas != null) {
									adapter = new OpenSearchAdapter(
											getActivity(), SearchDatas);
								}
							}
							if (SearchCourses.size() == Constants.PAGE_SIZE) {
								SearchDatas = SearchCourses;
								currentpage = currentpage + 1;
								SearchDatas.addAll(SearchCourses);
								GetSearchNextDatas(textview);
							}
						}
						handler.sendEmptyMessage(GETMESS);
					}

					@Override
					public void process(PageCourses paramT) {
					}
				}, getActivity());

	}

	/**
	 * 加载跟多搜索数据。
	 * 
	 * @param textview
	 */
	private void GetSearchNextDatas(TextView textview) {

		HttpUtil.getFullUrl(Constants.GET_PUBLIC_COURSES + "?keyword="
				+ keyword + "&pageNo=" + currentpage + "&pageSize="
				+ Constants.PAGE_SIZE, null,
				new AsyncHttpCilentHandler<PageCourses>(getActivity(),
						PageCourses.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							PageCourses Courseslist = JSON.parseObject(
									paramString, PageCourses.class);
							List<BaseCourses> SearchCourses = Courseslist
									.getRows();
							currentpage = currentpage + 1;
							SearchDatas.addAll(SearchCourses);
							if (SearchDatas != null) {
								adapter = new OpenSearchAdapter(getActivity(),
										SearchDatas);
							}
						}
						handler.sendEmptyMessage(GETMESS);
					}

					@Override
					public void process(PageCourses paramT) {
					}
				}, getActivity());

	}

	private class OnSearchItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(getActivity(),
					DetailCoursesActivity.class);
			String image_urlshare = Constants.DOMAIN+"/file/load?image="+SearchDatas.get(position).getImg_path();
			intent.putExtra("id", SearchDatas.get(position).getId());
			intent.putExtra("image_urlshare", image_urlshare);
			startActivity(intent);

		}
	}
}
