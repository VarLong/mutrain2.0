package com.ytrain.mutrain.activity.open;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.entity.courses.BaseCourses;
import com.ytrain.mutrain.utils.Constants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class OpenSearchAdapter extends BaseAdapter {
	
	private List<BaseCourses> items;
	private Context context;

	
	public OpenSearchAdapter(Context context, List<BaseCourses> items) {
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
		final HolderView holder;	
		View view = convertView;
		if (view == null) {		
			view = LayoutInflater.from(context).inflate(R.layout.item_search_layout, parent, false);
			holder = new HolderView();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.image);
			holder.source = (TextView) view.findViewById(R.id.sesource);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			view.setTag(holder);
		} else {
			holder = (HolderView) view.getTag();
		}
		String image_url = Constants.DOMAIN+"/file/load?image="+items.get(position).getImg_path();
		// 初始化 
		Glide.with(context)
		.load(image_url).centerCrop()
		.into(holder.imageView);

		holder.source.setText(""+items.get(position).getSource_name());
	    holder.name.setText(items.get(position).getName());
	    holder.ratingBar.setRating(items.get(position).getAvg_star_score());
         return view ;
	}
	
}

class HolderView {
	ImageView imageView;
	ProgressBar progressBar;
	TextView name ;
	TextView source ;
	RatingBar ratingBar ;
}
