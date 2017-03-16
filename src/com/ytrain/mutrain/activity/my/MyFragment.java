package com.ytrain.mutrain.activity.my;

import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.view.CustomLoginDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MyFragment extends Fragment {

	/**
	 * “我的”主界面界面
	 * 
	 */
	private CustomLoginDialog dialog;
	private TextView myopen;
	private TextView mytrain;
	private TextView mytest;
	private TextView myorder;
	private TextView mycollect;
	private TextView myinteract;
	private TextView mycertificate;
	private TextView myset;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.myfragment, container, false);
		myopen = (TextView) view.findViewById(R.id.myopen);
		mytrain = (TextView) view.findViewById(R.id.mytrain);
		mytest = (TextView) view.findViewById(R.id.mytest);
		myorder = (TextView) view.findViewById(R.id.myorder);
		mycollect = (TextView) view.findViewById(R.id.mycollect);
		myinteract = (TextView) view.findViewById(R.id.myinteract);
		mycertificate = (TextView) view.findViewById(R.id.mycertificate);
		myset = (TextView) view.findViewById(R.id.myset);

		myopen.setOnClickListener(itemsOnClick);
		mytrain.setOnClickListener(itemsOnClick);
		mytest.setOnClickListener(itemsOnClick);
		myorder.setOnClickListener(itemsOnClick);
		mycollect.setOnClickListener(itemsOnClick);
		myinteract.setOnClickListener(itemsOnClick);
		mycertificate.setOnClickListener(itemsOnClick);
		myset.setOnClickListener(itemsOnClick);

		return view;
	}

	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myopen:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent = new Intent(getActivity(),
							MyOpenActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.mytrain:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent1 = new Intent(getActivity(),
							MyTrainActivity.class);
					startActivity(intent1);
				}
				break;
			case R.id.mytest:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent2 = new Intent(getActivity(),
							MyTestActivity.class);
					startActivity(intent2);
				}
				break;
			case R.id.myorder:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent3 = new Intent(getActivity(),
							MyOrderActivity.class);
					startActivity(intent3);
				}
				break;
			case R.id.mycollect:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent4 = new Intent(getActivity(),
							MyCollectActivity.class);
					startActivity(intent4);
				}
				break;
			case R.id.myinteract:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent5 = new Intent(getActivity(),
							MyInteractActivity.class);
					startActivity(intent5);
				}
				break;
			case R.id.mycertificate:
				if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
						.equals("")) {
					dialog = new CustomLoginDialog(getActivity(),
							R.style.logindialog);
					dialog.show();
				} else {
					Intent intent6 = new Intent(getActivity(),
							MyCertificateActivity.class);
					startActivity(intent6);
				}
				break;
			case R.id.myset:
				Intent intent7 = new Intent(getActivity(), MySet.class);
				startActivity(intent7);
				break;
			default:
				break;
			}
		}

	};
}
