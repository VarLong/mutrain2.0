package com.ytrain.mutrain.activity.open;

import com.ytrain.mutrain.activity.search.CourserSearch;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class TopOpenFragment extends Fragment implements OnClickListener {

	private ImageButton searchBtn;
	private TextView typename;
	private String title;
	onChangedListener listener = null;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			listener = (onChangedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			title = bundle.getString("name");			
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_topopen, container,
				false);
		typename = (TextView) view.findViewById(R.id.typename);
		searchBtn = (ImageButton) view.findViewById(R.id.searchbtn);
		searchBtn.setOnClickListener(this);
		typename.setOnClickListener(this);
		settitle(title);
		return view;
	}

	public void settitle(String title) {
		typename.setText(title);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.searchbtn:
			Intent intent = new Intent(getActivity(), CourserSearch.class);
			intent.putExtra("flag", 1);
			startActivity(intent);
			break;
		case R.id.typename:
			listener.onChanged();
			break;
		default:
			break;
		}
		
	}
	/**
	 * 接口
	 * @author Administrator
	 *
	 */
		public interface onChangedListener {
			public void onChanged();
		}
}
