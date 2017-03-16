package com.ytrain.mutrain.activity.train;

import java.util.List;

import com.ytrain.mutrain.entity.train.Outline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OutLineAdapter extends BaseAdapter{
  private List<Outline> items;
  private Context context ;
  public  OutLineAdapter(List<Outline> items,Context context ){
	  this.items = items ;
	  this.context = context ;
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
		final Outlineview holder;
		  View view = convertView;
	    if(view == null){
	    holder = new Outlineview() ; 
	    view =	LayoutInflater.from(context).inflate(R.layout.outline_item, parent, false);
	    holder.userIdTV =  (TextView) view.findViewById(R.id.user_id);
	    holder.courseTV = (TextView) view.findViewById(R.id.courseTV);
	    holder.teacherNameTV =  (TextView) view.findViewById(R.id.teacherName);
		holder.studyformTV = (TextView) view.findViewById(R.id.studyform);
		view.setTag(holder);
		  }else {
		 holder = (Outlineview) view.getTag();
		}
	    if(items.size() !=0 && items != null ){
	    	holder.userIdTV.setText(""+position+1);
	    	holder.courseTV.setText(items.get(position).getName());
	    	holder.teacherNameTV.setText(items.get(position).getTeacher_name());
	    	if(items.get(position).getAttribute() == 1){
	    		holder.studyformTV.setText("线上");
	    	}else{
	    		holder.studyformTV.setText("线下");
	    	}   	
		} else {
			Toast.makeText(context, "该系列班没有课程!", Toast.LENGTH_SHORT).show();
		}
		return view;
	}
    private class Outlineview {
    	TextView courseTV ;
    	TextView teacherNameTV ;
    	TextView studyformTV ;
    	TextView userIdTV ;
    }
}
