package com.ytrain.mutrain.activity.train;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.entity.train.SeriesComment;
import com.ytrain.mutrain.utils.Constants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SeriesCommentAdapter extends BaseAdapter {
	private Context context;
	private List<SeriesComment> items;
	private String user_type;

	public SeriesCommentAdapter(Context context, List<SeriesComment> items) {

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
			cv.TouristTV = (TextView) convertView
					.findViewById(R.id.tourist_name);
			cv.DateTV = (TextView) convertView.findViewById(R.id.comment_time);
			cv.ContentTV = (TextView) convertView
					.findViewById(R.id.comment_content);
			cv.User_Photo = (ImageView) convertView
					.findViewById(R.id.user_photo);
			convertView.setTag(cv);
		} else {
			cv = (CommentView) convertView.getTag();
		}
		switch (items.get(position).getUser_type()) {
		case 1:
			user_type = "游客";
			break;
		case 2:
			user_type = "学员";
			break;
		case 3:
			user_type = "讲师";
		default:
			break;
		}
		cv.DateTV.setText("" + items.get(position).getCreate_time());
		cv.ContentTV.setText(items.get(position).getContent());
		cv.TouristTV.setText(user_type + items.get(position).getStu_nickname());
		String image_url = Constants.DOMAIN + "/file/load?image="
				+ items.get(position).getStu_head_image();
		Glide.with(context)
		.load(image_url).centerCrop()
		.into(cv.User_Photo);
		
		return convertView;
	}

	class CommentView {
		ImageView User_Photo;
		TextView TouristTV;
		TextView DateTV;
		TextView ContentTV;

	}
}
