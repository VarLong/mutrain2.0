package com.ytrain.mutrain.adapter;

import java.util.List;

import com.ytrain.mutrain.entity.courses.HotCourses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class HotAdapter extends BaseAdapter {
	private List<HotCourses> hotlist;
	private Context context;	

	public HotAdapter(List<HotCourses> hotlist, Context context ) {
		this.hotlist = hotlist;
		this.context = context;		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hotlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holderview holder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.layout_hot_item, parent, false);
			holder = new Holderview();
			assert view != null;
			holder.id = (TextView) view.findViewById(R.id.hot_id);
			holder.name = (TextView) view.findViewById(R.id.hot_name);
			view.setTag(holder);
		} else {
			holder = (Holderview) view.getTag();
		}		
		holder.id.setText(position + 1 + "");
		holder.name.setText(hotlist.get(position).getCourse_name());
		return view;
	}

	static class Holderview {
		TextView id;
		TextView name;
	}

}
