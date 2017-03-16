package com.ytrain.mutrain.adapter;

import java.util.List;

import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.activity.home.MaxCoursesActivity;
import com.ytrain.mutrain.activity.open.OpenAdapter;
import com.ytrain.mutrain.entity.body.BodyCourses;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.utils.Constants;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class BodyCoursesAdapter extends BaseAdapter {
	/**
	 * 首页每一个BODY课程的适配器
	 */

	private Context context;
	private List<BodyCourses> items;
	private List<BaseCourses> Coursesitems;

	public BodyCoursesAdapter(Context context, List<BodyCourses> items) {
		this.context = context;
		this.items = items;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final Bodyview bodyview;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.body_item,
					parent, false);
			bodyview = new Bodyview();
			assert view != null;
			bodyview.TypeText = (TextView) view.findViewById(R.id.typetext);
			bodyview.CoursesGridview = (GridView) view
					.findViewById(R.id.coursesgridview);
			view.setTag(bodyview);
		} else {
			bodyview = (Bodyview) view.getTag();
		}
		bodyview.TypeText.setText(items.get(position).getMax_type_name());
		bodyview.TypeText.setOnClickListener(new textClickListener(position));
		bodyview.TypeText.setOnClickListener(new maxtypeListener(position));
		Coursesitems = items.get(position).getMax_type_courses();
		OpenAdapter adapter = new OpenAdapter(context, Coursesitems);
		bodyview.CoursesGridview.setAdapter(adapter);
		bodyview.CoursesGridview.setOnItemClickListener(new gridClickListener(
				position));
		return view;
	}

	/**
	 * 点击标题产生的事件
	 * 
	 * @author Administrator
	 * 
	 */
	private class textClickListener implements OnClickListener {
		int itemposition;

		public textClickListener(int itemposition) {
			this.itemposition = itemposition;
		}

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(context, MaxCoursesActivity.class);
			intent.putExtra("id", items.get(itemposition).getMax_type_id());
			intent.putExtra("name", items.get(itemposition).getMax_type_name());
			context.startActivity(intent);
		}

	}

	private class gridClickListener implements OnItemClickListener {
		int itemposition;
		public gridClickListener(int itemposition) {
			super();
			this.itemposition = itemposition;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String image_urlshare = Constants.DOMAIN + "/file/load?image="
					+ items.get(itemposition).getMax_type_courses()
					.get(position).getImg_path();
			Intent intent = new Intent(context, DetailCoursesActivity.class);
			intent.putExtra("id", items.get(itemposition).getMax_type_courses()
					.get(position).getId());
			intent.putExtra("image_urlshare", image_urlshare);
			context.startActivity(intent);
		}
	}

	private class maxtypeListener implements OnClickListener {
		private int i;

		public maxtypeListener(int position) {
			// TODO Auto-generated constructor stub
			this.i = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, MaxCoursesActivity.class);
			intent.putExtra("id", items.get(i).getMax_type_id());
			intent.putExtra("name", items.get(i).getMax_type_name());
			context.startActivity(intent);
		}
	}

	class Bodyview {
		TextView TypeText;
		GridView CoursesGridview;
	}

}
