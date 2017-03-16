package com.ytrain.mutrain.activity.my;

import com.ytrain.mutrain.utils.Constants;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	private WebView wv;
    private String url;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("注册");
		url = Constants.REGISTER;
		ImageView goBack = (ImageView) actionBar.getCustomView()
				.findViewById(R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(new GoBackListener());
		wv = (WebView) findViewById(R.id.register);
		WebSettings webSettings = wv.getSettings();
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setSupportMultipleWindows(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true); 
		 wv.loadUrl(url);
	}

	private class GoBackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
}
