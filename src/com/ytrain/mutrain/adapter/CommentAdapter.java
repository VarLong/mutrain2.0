package com.ytrain.mutrain.adapter;

import java.util.List;

import com.ytrain.mutrain.entity.PublicComments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	/**
	 * 用户评论适配器
	 */

	private List<PublicComments> items;
	private Context context;

	public CommentAdapter(Context context, List<PublicComments> items) {
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
		CommentView cv;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comment_item, parent, false);
			cv = new CommentView();
			cv.User_Photo = (ImageView) convertView
					.findViewById(R.id.user_photo);
			cv.TouristTV = (TextView) convertView
					.findViewById(R.id.tourist_name);
			cv.DateTV = (TextView) convertView.findViewById(R.id.comment_time);
			cv.ContentTV = (TextView) convertView
					.findViewById(R.id.comment_content);
			convertView.setTag(cv);
		} else {
			cv = (CommentView) convertView.getTag();
		}
		cv.TouristTV.setText(items.get(position).getTourist_name());
		cv.DateTV.setText("" + items.get(position).getCreate_time());
		cv.ContentTV.setText(items.get(position).getContent());

		return convertView;
	}

	class CommentView {
		ImageView User_Photo;
		TextView TouristTV;
		TextView DateTV;
		TextView ContentTV;

	}

}
