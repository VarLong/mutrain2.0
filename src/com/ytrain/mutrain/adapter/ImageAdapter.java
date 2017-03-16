package com.ytrain.mutrain.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.activity.DetailCoursesActivity;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.utils.Constants;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {

	private Context context;
	private List<BaseCourses> items;

	private ImageView PagerImage;

	public ImageAdapter(Context context, List<BaseCourses> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final String id;
		final String image_url;
		id = items.get(position).getId();
		image_url = Constants.DOMAIN + "/file/load?image="
				+ items.get(position).getImg_path();
		View imageview = LayoutInflater.from(context).inflate(
				R.layout.item_pager, container, false);
		PagerImage = (ImageView) imageview.findViewById(R.id.pagerimage);
		
		Glide.with(context).load(image_url).centerCrop().into(PagerImage);
		imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(context, DetailCoursesActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("image_urlshare", image_url);
				context.startActivity(intent);
			}

		});
		container.addView(imageview, 0);
		return imageview;
	}

	@Override
	public int getCount() {
		return items.size();
		// return items.size() > 1 ? items.size() + 2 : items.size();
	}

	/**
	 * 判断出去的view是否等于
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
