package com.ssy.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

public class UpdateManager {

	private Context mContext;

	private Dialog noticeDialog;

	private String apkUrl = "";

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo(String url) {
		apkUrl = url;
		showNoticeDialog();
	}

	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件升级");
		builder.setMessage("发现新版本,建议您立即更新!");
		builder.setPositiveButton("现在更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(apkUrl));
				mContext.startActivity(intent);
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

}
