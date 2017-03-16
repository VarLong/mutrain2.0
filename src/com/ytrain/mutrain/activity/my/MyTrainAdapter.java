package com.ytrain.mutrain.activity.my;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.entity.train.TrainClass;
import com.ytrain.mutrain.utils.Constants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTrainAdapter extends BaseAdapter {

	private List<TrainClass> items;
	private Context context;

	public MyTrainAdapter(Context context, List<TrainClass> items) {
		this.context = context;
		this.items = items;

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TrainHolder holder;

		View view = convertView;

		if (view == null) {

			view = LayoutInflater.from(context).inflate(R.layout.mytrain_item,
					parent, false);
			holder = new TrainHolder();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.image);
			holder.coursesname = (TextView) view.findViewById(R.id.coursesname);
			holder.studytime = (TextView) view.findViewById(R.id.studytimeTV);
			holder.remaintime = (TextView) view.findViewById(R.id.remaintimeTV);
			holder.Systemcourseschedule = (TextView) view
					.findViewById(R.id.SystemcoursescheduleTV);
			view.setTag(holder);
		} else {
			holder = (TrainHolder) view.getTag();
		}
		String image_url = Constants.DOMAIN + "/file/load?image="
				+ items.get(position).getResourceSmallImgPath();
		// 初始化
		Glide.with(context).load(image_url).centerCrop().into(holder.imageView);

		holder.coursesname.setText(items.get(position).getCourseGroupName());
		holder.studytime.setText(items.get(position).getValidStudyDays() + "");
		holder.remaintime.setText(items.get(position).getRemainStudyDays());
		holder.Systemcourseschedule.setText(items.get(position)
				.getStudyProgress() + "");
		return view;
	}

}

class TrainHolder {
	ImageView imageView;
	TextView studytime;
	TextView remaintime;
	TextView Systemcourseschedule;
	TextView coursesname;
}
