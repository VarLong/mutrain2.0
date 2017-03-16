package com.ytrain.mutrain.utils;

import com.ytrain.mutrain.utils.asynchttp.AsyncHttpClient;
import com.ytrain.mutrain.utils.asynchttp.AsyncHttpResponseHandler;
import com.ytrain.mutrain.utils.asynchttp.RequestParams;

import android.content.Context;
import android.util.Log;

public class HttpUtil {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static String getApiUrl(String relativeUrl) {
		return Constants.DOMAIN + relativeUrl;
	}

	public static void init(Context context) {
		client.setTimeout(30 * 1000);
	}

	public static void getImage(String url,
			AsyncHttpResponseHandler responseHandler) {
		client.get(url, null, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler, Context context) {
		if (params == null) {
			params = new RequestParams();
		}

		Log.i("api post", "url:" + getApiUrl(url) + "?" + params);
		client.post(getApiUrl(url), params, responseHandler);
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler, Context context) {
		if (params == null) {
			params = new RequestParams();
		}

		Log.i("api post", "url:" + getApiUrl(url) + "?" + params);
		client.get(getApiUrl(url), params, responseHandler);
	}

	// ===============================================
	/*
	 * 全路径访问
	 */
	public static void postFullUrl(String fullUrl, RequestParams params,
			AsyncHttpResponseHandler responseHandler, Context context) {
		if (params == null) {
			params = new RequestParams();
		}
		
		client.post(fullUrl, params, responseHandler);
	}

	public static void getFullUrl(String fullUrl, RequestParams params,
			AsyncHttpResponseHandler responseHandler, Context context) {
		
		client.get(fullUrl, params, responseHandler);
	}

}
