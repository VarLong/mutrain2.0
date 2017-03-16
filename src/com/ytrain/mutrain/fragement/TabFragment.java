package com.ytrain.mutrain.fragement;

import com.ytrain.mutrain.activity.home.HomeFragment;
import com.ytrain.mutrain.activity.home.TopHomeFragment;
import com.ytrain.mutrain.activity.my.MyFragment;
import com.ytrain.mutrain.activity.my.TopMyFragment;
import com.ytrain.mutrain.activity.open.OpenFragment;
import com.ytrain.mutrain.activity.train.TrainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabFragment extends Fragment  {
	private View view;
	private RadioGroup radioGroup;
	private FragmentManager manager;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = getActivity().getSupportFragmentManager();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragmentall, container, false);
		init();
		return view;
	}

	private void init() {
		radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.homepage:
					homePageListener();
					break;
				case R.id.openclass:
					openclassListener();
					break;
				case R.id.classtrain:
					classtrainListener();
					break;
				case R.id.my:
					myListener();
					break;
				default:
					break;
				}
			}
		});	
		homePageListener();
	}

	private void homePageListener() {
		FragmentTransaction transaction = manager.beginTransaction();
		HomeFragment detailFragment = new HomeFragment();
		TopHomeFragment top = new TopHomeFragment() ;		
		transaction.replace(R.id.mainlinearlayout, detailFragment);
		transaction.replace(R.id.toplinealayout, top);
		transaction.commit();
	}

	private void openclassListener() {
		FragmentTransaction transaction = manager.beginTransaction();
		OpenFragment detailFragment = new OpenFragment();		
		transaction.replace(R.id.mainlinearlayout, detailFragment);
		transaction.commit();
	}

	private void classtrainListener() {
		FragmentTransaction transaction = manager.beginTransaction();
		TrainFragment classFragment = new TrainFragment();		
		transaction.replace(R.id.mainlinearlayout, classFragment);
		transaction.commit();
	}

	private void myListener() {
		FragmentTransaction transaction = manager.beginTransaction();
		MyFragment detailFragment = new MyFragment();
		TopMyFragment top = new TopMyFragment() ;			
		transaction.replace(R.id.mainlinearlayout, detailFragment);
		transaction.replace(R.id.toplinealayout, top);
		transaction.commit();			
		}

	
	
}
