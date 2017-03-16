package com.ytrain.mutrain.activity.my;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MySet extends Activity implements OnClickListener {
	/**
	 * 设置界面
	 */
	private TextView set_wifi, set_suggestion, set_check, sclean, set_clean,
			set_about;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_set);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("设置");
		ImageView goBack = (ImageView) actionBar.getCustomView()
				.findViewById(R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		goBack.setOnClickListener(this);
		initview();

	}

	private void initview() {
		set_wifi=(TextView) findViewById(R.id.set_wifi);
		set_suggestion=(TextView) findViewById(R.id.set_suggestion);
		set_check=(TextView) findViewById(R.id.set_check);
//		sclean=(TextView) findViewById(R.id.set_version);
		set_clean=(TextView) findViewById(R.id.set_clean);
		set_about=(TextView) findViewById(R.id.set_about);
		set_wifi.setOnClickListener(this);
		set_suggestion.setOnClickListener(this);
		set_check.setOnClickListener(this);
//		sclean.setOnClickListener(this);
		set_clean.setOnClickListener(this);
		set_about.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbutton:
			finish();
			break;
		case R.id.set_wifi:
			
			break;
		case R.id.set_suggestion:
			Intent intent=new Intent(this, MySuggestion.class);
			startActivity(intent);
			break;
		case R.id.set_check:
			
			break;
		case R.id.set_clean:
			showExitAlert();
			break;
		case R.id.set_about:
			Intent intentA=new Intent(this, MyAbout.class);
			startActivity(intentA);
			break;
		default:
			break;
		}

	}
	
	private void showExitAlert() {
		int width = getStatusWidth();
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		dlg.setCanceledOnTouchOutside(false);
		Window window = dlg.getWindow();
		// 动态设置提示框宽度
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = width;
		window.setAttributes(lp);
		// 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.dialog_checkcache);
		// 为确认按钮添加事件,执行退出应用操作
		Button ok = (Button) window.findViewById(R.id.button1);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		// 关闭alert对话框架
		Button cancel = (Button) window.findViewById(R.id.button2);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});
	}
	
	/**
	 * 根据屏幕大小获得弹出框的宽度。大小为十分之九屏幕宽度
	 * 
	 * @return
	 */
	public int getStatusWidth() {
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		return width / 10 * 9;
	}

}
