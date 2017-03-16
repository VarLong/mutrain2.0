package com.ytrain.mutrain.activity.my;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.entity.courses.Interact;
import com.ytrain.mutrain.entity.courses.MyInteract;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InteractFragment extends Fragment {
	/**
	 * 我的互动的ViewPager界面
	 */
	private String jsonString;
	private MyInteract myInteract;
	List<Interact> interact;
	private ListView listview;

	public static InteractFragment newInstance(String jsonString, int flag) {
		InteractFragment fragment = new InteractFragment();
		Bundle b = new Bundle();
		b.putInt("flag", flag);
		b.putString("jsonString", jsonString);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		jsonString = bundle.getString("jsonString");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contextView = inflater.inflate(R.layout.fragment_outline, container, false);

		if (jsonString != null && jsonString.length() != 0) {
			myInteract = JSON.parseObject(jsonString, MyInteract.class);
			interact = myInteract.getRows();
		}
		listview = (ListView) contextView.findViewById(R.id.outlineLV);
		InteractAdapter adapter = new InteractAdapter(interact, getActivity());
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		return contextView;
	}

	class InteractAdapter extends BaseAdapter {
		private Context context;
		private List<Interact> interactList;

		public InteractAdapter(List<Interact> interact, Context context) {
			this.context = context;
			this.interactList = interact;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return interactList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return interactList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Itemview holder;
			if (convertView == null) {
				holder = new Itemview();
				convertView = LayoutInflater.from(context).inflate(R.layout.interact_item, parent, false);
				holder.title = (TextView) convertView.findViewById(R.id.textView1);
				holder.data = (TextView) convertView.findViewById(R.id.textView3);
				holder.star = (TextView) convertView.findViewById(R.id.textView4);
				holder.content = (TextView) convertView.findViewById(R.id.textView5);
				convertView.setTag(holder);

			} else {
				holder = (Itemview) convertView.getTag();
			}
			if (interactList.size() != 0 && interactList != null) {
				holder.title.setText(interactList.get(position).getCourseName());
				holder.data.setText(interactList.get(position).getCreate_time());
				holder.content.setText(interactList.get(position).getContent());
				if (interactList.get(position).getStar_score() != null) {
					holder.star.setText(interactList.get(position).getStar_score());
				} else {
					holder.star.setVisibility(View.GONE);
				}
			} else {

			}

			return convertView;
		}
	};

	private class Itemview {
		TextView title;
		TextView data;
		TextView star;
		TextView content;

	}

}
