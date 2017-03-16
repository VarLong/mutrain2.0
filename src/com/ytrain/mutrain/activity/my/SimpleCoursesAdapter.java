package com.ytrain.mutrain.activity.my;

import java.util.List;
import com.ytrain.mutrain.entity.train.SimpleTrainCourse;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SimpleCoursesAdapter extends BaseAdapter {
	private List<SimpleTrainCourse> items;
	private Context context;

	public SimpleCoursesAdapter(Context context, List<SimpleTrainCourse> items) {
		// TODO Auto-generated constructor stub
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		CoursesView viewholder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.simple_train,
					parent, false);
			viewholder = new CoursesView();
			viewholder.nameTV = (TextView) view.findViewById(R.id.nameTV);
			viewholder.backstudytimeTV = (TextView) view.findViewById(R.id.backstudytimeTV);
			viewholder.studyscheduleTV = (TextView) view.findViewById(R.id.studyscheduleTV);
			viewholder.attributeTV = (TextView) view.findViewById(R.id.attributeTV);
			view.setTag(viewholder);
		}else {
			viewholder = (CoursesView) view.getTag();
		}
		viewholder.backstudytimeTV.setText(items.get(position).getLast_study_time());
		viewholder.nameTV.setText(items.get(position).getCourseName());
		viewholder.studyscheduleTV.setText(items.get(position).getStudyProgress()+"");
		viewholder.attributeTV.setText(items.get(position).getAttribute()+"");
		return view;
	}

	class CoursesView {
		TextView nameTV;
		TextView backstudytimeTV;
		TextView studyscheduleTV;
		TextView attributeTV;
	}

}
