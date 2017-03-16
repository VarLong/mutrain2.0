package com.ytrain.mutrain.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.entity.courses.RecommendCourses;
import com.ytrain.mutrain.utils.Constants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommAdapter extends BaseAdapter {
	private List<RecommendCourses> recolist;
	private Context context;

	public RecommAdapter(List<RecommendCourses> recolist, Context context) {
		this.recolist = recolist;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recolist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recolist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		Reholderview holder;
		if (view == null) {
			holder = new Reholderview();
			view = LayoutInflater.from(context).inflate(
					R.layout.horizontal_group_item, parent, false);
			holder.recomimage = (ImageView) view.findViewById(R.id.recomimage);
			holder.recomsource = (TextView) view.findViewById(R.id.recomsource);
			view.setTag(holder);
		} else {
			holder = (Reholderview) view.getTag();
		}
		String image_url = Constants.DOMAIN + "/file/load?image="
				+ recolist.get(position).getCourse_img_path();
		Glide.with(context).load(image_url).centerCrop()
				.into(holder.recomimage);

		String recomname = recolist.get(position).getCourse_name();
		/*
		 * if(recomname >10){ recomname.re }
		 */
		holder.recomsource.setText(recomname);
		return view;
	}

	static class Reholderview {
		ImageView recomimage;
		TextView recomsource;
	}
}
