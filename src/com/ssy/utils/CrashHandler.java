package com.ssy.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;

public class CrashHandler implements UncaughtExceptionHandler {
	/**
	 * 捕捉全局异常
	 */
	private static CrashHandler INSTANCE = new CrashHandler();
	private Context mContext;
	
	@SuppressWarnings("unused")
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				new AlertDialog.Builder(mContext)
						.setTitle("温馨提示")
						.setCancelable(false)
						.setMessage("程序出了点小意外, 要关闭了哦...")
						.setNeutralButton("我知道了",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										System.exit(0);
									}
								}).create().show();
				Looper.loop();
			}
		}.start();
	}
}