package com.ytrain.mutrain.view;

import Decoder.BASE64Encoder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.activity.my.RegisterActivity;
import com.ytrain.mutrain.entity.user.LoginResult;
import com.ytrain.mutrain.entity.user.UserInfo;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.UserSharedPreferences;
import com.ytrain.mutrain.utils.asynchttp.RequestParams;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;

public class CustomLoginDialog extends Dialog {
	/**
	 * 登录提示框
	 */
	private TextView MessageTv;
	private Context context;
	private TextView forgetpswTv;
	private Button LoginBtn;
	private TextView RegisterTv;
	private EditText nameEt;
	private EditText pwdEt;
	private String username;
	private String password;

	SharedPreferences Preferences;
	SharedPreferences.Editor editor;

	private LoginResult loginResult;
	private UserInfo userinfo;

	public CustomLoginDialog(Context context, int style) {
		super(context, style);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_dialog);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		nameEt = (EditText) findViewById(R.id.username);
		pwdEt = (EditText) findViewById(R.id.password);
		forgetpswTv = (TextView) findViewById(R.id.forgetpsw);
		LoginBtn = (Button) findViewById(R.id.LoginBtn);
		RegisterTv = (TextView) findViewById(R.id.GoRegister);
		MessageTv = (TextView) findViewById(R.id.message);

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams params = dialogWindow.getAttributes();
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		params.width = (int) (dm.widthPixels * 0.9); // 宽度设置为屏幕的0.6
		params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		dialogWindow.setAttributes(params);

		LoginBtn.setOnClickListener(new LoginOnClickListener());
		forgetpswTv.setOnClickListener(new ForgetPswOnClickListener());
		RegisterTv.setOnClickListener(new RegisterOnClickListener());
	}

	public class LoginOnClickListener implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			MessageTv.setText("正在登陆...");
			username = nameEt.getText().toString();
			password = pwdEt.getText().toString();
			if (!username.equals("")) {
				if (!password.equals("")) {
					RequestParams params = new RequestParams();
					params.put("account", username);
					params.put("password", password);
					HttpUtil.postFullUrl(Constants.LOGIN, params,
							new AsyncHttpCilentHandler<LoginResult>(context,
									LoginResult.class) {
								@Override
								public void onFailure(Throwable paramThrowable,
										String paramString) {
									Log.e("onFailure", "onFailure");
									
																	}

								@Override
								public void onSuccess(String paramString) {
									Log.e("onSuccess", "onSuccess");
									LoginResult loginResult=JSON.parseObject(paramString,LoginResult.class);
									UserInfo userinfo=loginResult.getUserinfo();
									if (loginResult.isSuccess()) {
										UserSharedPreferences.setParam(context,
												"user", getBASE64(username
														+ ":" + password));
										if (userinfo.getId() == null) {
											UserSharedPreferences.setParam(
													context, "stuId", "");
										} else {
											UserSharedPreferences.setParam(
													context, "stuId",
													userinfo.getId());
										}
										if (userinfo.getName() == null) {
											UserSharedPreferences.setParam(
													context, "name", "");
										} else {
											UserSharedPreferences.setParam(
													context, "name",
													userinfo.getName());
										}
										if (userinfo.getHeadImage() == null) {
											UserSharedPreferences.setParam(
													context, "headerimage", "");
										} else {
											UserSharedPreferences.setParam(
													context, "headerimage",
													userinfo.getHeadImage());
										}
										Toast.makeText(context, "登录成功",
												Toast.LENGTH_SHORT).show();
										Intent it = new Intent();
										it.setAction("GETNAME");
										it.putExtra("name", userinfo.getName());
										CustomLoginDialog.this.getContext()
												.sendBroadcast(it);
										dismiss();

									} else {
										MessageTv.setText(loginResult.getMessage());
									}
								}

								@Override
								public void process(LoginResult paramT) {
									
								}
							}, context);
				} else {
					MessageTv.setText("");
					Toast.makeText(context, "请输入密码!", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				MessageTv.setText("");
				Toast.makeText(context, "请输入用户名!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new BASE64Encoder()).encode(s.getBytes());
	}

	public class ForgetPswOnClickListener implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}
	}

	public class RegisterOnClickListener implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, RegisterActivity.class);
			context.startActivity(intent);
		}
	}
}
