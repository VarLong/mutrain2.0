package com.ssy.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ytrain.mutrain.utils.Constants;


public class Utils {
	public final static String UUID="91f64a67e8";
	public final static String UserKey="09a9a170e19134f5fdc8215e2065d981";
	
	public static String getVideoUrl(String vuid){
		return Constants.GET_VIDEOURL+vuid;		
	}
	/**
	 * 验证电话号码是否合法
	 * 
	 * @param phoneNumber
	 * @return
	 */
	
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";
		String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;

	}

	/**
	 * 获取文件类型
	 * 
	 * @param f
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		if (end.equals("apk")) {
		} else {
			type += "/*";
		}
		return type;
	}

	/**
	 * 得到网络类型
	 * 
	 * @param act
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static int getNetworkType(Activity act) {
		Context context = act.getApplicationContext();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (null != ni) {
				int type = ni.getType();
				// 手机网络
				if (ConnectivityManager.TYPE_MOBILE == type) {
					return 2;
				}
				// wifi网络
				if (ConnectivityManager.TYPE_WIFI == type) {
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * 判断是否有网络
	 * @param con
	 * @return
	 */
	public static boolean isExistNetwork(Context con) {
		Context context = con.getApplicationContext();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (null != ni) {
			return ni.isAvailable();
		}
		return false;
	}



	public static void displayToast(Activity act, String msg) {
		Toast.makeText(act, msg, 600).show();
	}

	/**
	 * 获取apk配置文件里面的版本号
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getVersionName(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					ctx.getPackageName(), 0);
			String version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "ftwapp2.0.0";
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	/**
	 * 内容摘要：判断属性是否为null 流程说明：
	 * 
	 * 
	 * 1、 如果Object对象为空，返回true . 2、 如果不为空 . 3、 字类可以调用该方法判断一个对象
	 * 
	 * @param object
	 *            操作对象
	 * @return 布尔值（true OR false）
	 */
	public static boolean isNullOfProperty(Object object) {
		try {
			if (object == null) {
				return true;
			}
			if (object instanceof String) {
				String target = (String) object;
				if (target.trim().length() == 0)
					return true;
			}
		} catch (Exception ex) {
			return true;
		}
		return false;
	}

	public static Object nullOfProperty(Object object) {
		if (isNullOfProperty(object)) {
			if (object instanceof String) {
				String target = (String) object;
				if (target.trim().length() == 0)
					return "";
			} else if (object instanceof Double) {
				return 0.0;
			} else if (object instanceof Integer) {
				return 0;
			} else {
				return "0";
			}
		}

		return object;
	}

	/**
	 * 密度转换像素
	 * */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 像素转换密度
	 * */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
