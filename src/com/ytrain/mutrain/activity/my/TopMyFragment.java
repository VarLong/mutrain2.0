package com.ytrain.mutrain.activity.my;

import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.view.CustomLoginDialog;
import Decoder.BASE64Encoder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopMyFragment extends Fragment {
	private LinearLayout loginLinear;
	private LinearLayout userLinear;
	private CustomLoginDialog dialog;
	private TextView userName;
	private ImageButton loginOut;
	private ImageView userImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		{
			if (UserSharedPreferences.getParam(getActivity(), "stuId", "")
					.equals("")) {

			} else {
				String username = (String) UserSharedPreferences.getParam(
						getActivity(), "name", "");
				userName.setText(username);
				loginLinear.setVisibility(View.GONE);
				userLinear.setVisibility(View.VISIBLE);
				loginOut.setVisibility(View.VISIBLE);
			}

		}
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("GETNAME")) {
				String getName = intent.getStringExtra("name");
				loginLinear.setVisibility(View.GONE);
				userLinear.setVisibility(View.VISIBLE);
				loginOut.setVisibility(View.VISIBLE);
				userName.setText(getName);
			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_topmy, container, false);
		loginLinear = (LinearLayout) view.findViewById(R.id.loginlinear);
		userLinear = (LinearLayout) view.findViewById(R.id.userlinear);
		userImage = (ImageView) view.findViewById(R.id.userimage);
		userName = (TextView) view.findViewById(R.id.username);
		loginOut = (ImageButton) view.findViewById(R.id.logout);
		loginOut.setOnClickListener(new LoginOutListener());
		// 动态注册广播
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("GETNAME");
		getActivity().registerReceiver(broadcastReceiver, intentFilter);

		loginLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				dialog = new CustomLoginDialog(getActivity(),
						R.style.logindialog);
				dialog.show();
			}
		});
		return view;
	}

	private class LoginOutListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UserSharedPreferences.onDestroy(getActivity());
			loginLinear.setVisibility(View.VISIBLE);
			userLinear.setVisibility(View.GONE);
			loginOut.setVisibility(View.INVISIBLE);
		}

	}

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new BASE64Encoder()).encode(s.getBytes());
	}

}
