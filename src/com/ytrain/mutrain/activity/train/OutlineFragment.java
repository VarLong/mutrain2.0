package com.ytrain.mutrain.activity.train;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.entity.train.Outline;
import com.ytrain.mutrain.entity.train.SeriesCourses;
import com.ytrain.mutrain.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OutlineFragment extends Fragment {
	/**
	 * 团建内容界面viewpager第一页
	 */

	private String JsonString;
	private List<Outline> items;
	private ListView outlineLV;

	public static OutlineFragment newInstance(String JsonString) {
		OutlineFragment fragment = new OutlineFragment();
		Bundle b = new Bundle();
		b.putString("JsonString", JsonString);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		JsonString = bundle.getString("JsonString");
		if (JsonString != null && JsonString.length() != 0) {
			SeriesCourses seriesCourses = JSON.parseObject(JsonString,
					SeriesCourses.class);
			items = seriesCourses.getCourses();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_outline,
				container, false);
		outlineLV = (ListView) contextView.findViewById(R.id.outlineLV);
		OutLineAdapter adapter = new OutLineAdapter(items, getActivity());
		outlineLV.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		outlineLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(getActivity(),
						DetailCoursesActivity.class);
				intent.putExtra("id", items.get(position).getId());
				startActivity(intent);
			}
		});
		return contextView;
	}

}
