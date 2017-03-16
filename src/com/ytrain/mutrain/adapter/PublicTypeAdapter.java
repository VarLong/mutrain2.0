package com.ytrain.mutrain.adapter;

import java.util.List;
import com.ytrain.mutrain.entity.PublicMaxType;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PublicTypeAdapter extends BaseAdapter {
	private List<PublicMaxType> maxTypes;
	private Context context ;
	private View view;
	private TextView typeView;
	private String Typename;
	public PublicTypeAdapter( List<PublicMaxType> maxTypes ,Context context) {
	this.maxTypes =	maxTypes;
	this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return maxTypes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return maxTypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "ViewHolder", "ResourceAsColor" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
		typeView = (TextView) view.findViewById(R.id.textView1);		
		Typename = maxTypes.get(position).getName() ;
		typeView.setText(Typename);
		return view;
	}

}
