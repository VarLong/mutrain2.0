package com.ytrain.mutrain.utils;

import android.os.Environment;

public class Constants {

	public final static String SDCARD = Environment
			.getExternalStorageDirectory().toString();
	// 客户端文件夹包名
	public final static String DIR_PATH = SDCARD + "/mutrain";
	// 客户端图片缓存文件夹包名
	public final static String IMG_PATH = DIR_PATH + "/images";
	// 偏好设置文件名
	public final static String PREFER_FILE_NAME = "mutrain";
	public final static Integer PAGE_SIZE = 20;
	// 测试
//	public final static String DOMAIN = "http://120.24.79.127:8081";
	// 正式http://120.24.79.127:8081
	 public final static String DOMAIN="http://edu.youths.org.cn";
	// 登陆
	public final static String LOGIN = DOMAIN + "/ws/app/auth/stu/login";
	// 注册
	public final static String REGISTER = "http://sso.youths.org.cn/auth/register?mode=complex&appid=25&time=1438227004&secret=b865358bf203e880fa6bcd7821feebd8&backurl=http%3A%2F%2Fwww.youths.org.cn%2Fhome%2Fpublic%2Fcallback%3Fcallback%3Dregister%26code%3D%7Bcode%7D%26type%3D%7Btype%7D%26userid%3D%7Buserid%7D%26time%3D%7Btime%7D%26secret%3D%7Bsecret%7D";
	// 获取公开课推荐的banner课程
	public final static String GET_BANNER_COURSES = DOMAIN
			+ "/ws/app/public/reco/getBannerCourses";
	// 获取公开课推荐的body课程
	public final static String GET_BODY_COURSES = DOMAIN
			+ "/ws/app/public/reco/getBodyCourses";
	// 获取单个公开课课程的详细信息
	public final static String GET_PUCLIC_COURSE = DOMAIN
			+ "/ws/app/public/getCourse";
	// 获取公开课相关推荐课程（共10个）
	public final static String GET_PUBLIC_RECOMMEND_COURSES = DOMAIN
			+ "/ws/app/public/getRecommendCourses";
	// 获取公开课热门搜索课程（共10个）
	public final static String GET_HOT_SEARCH = DOMAIN
			+ "/ws/app/public/getHotSearch";
	// 获取公开课课程列表
	public final static String GET_PUBLIC_COURSES = DOMAIN
			+ "/ws/app/public/getCourses";
	// 获取公开课的课程类型（只包含大类）
	public final static String GET_PUBLIC_MAX_TYPE = DOMAIN
			+ "/ws/app/public/getMaxType";
	// 获取公开课课程评论列表
	public final static String PUBLIC_COMMENTS = DOMAIN
			+ "/ws/app/public/comment/list";
	// 保存公开课课程评论
	public final static String SAVE_PUBLIC_COMMENT = DOMAIN
			+ "/ws/app/public/comment/save";
	// 公开课收藏操作
	public final static String SAVE_PUBLIC_FAVORITE = DOMAIN
			+ "/ws/app/public/favorites/save";
	// 公开课取消收藏操作
	public final static String DELETE_PUBLIC_FAVORITE = DOMAIN
			+ "/ws/app/public/favorites/delete";
	// 获取培训班的课程类型
	public final static String GET_SERIES_TYPE = DOMAIN
			+ "/ws/app/series/getType";
	// 获取培训班的系列课列表
	public final static String GET_SERIES_COURSES = DOMAIN
			+ "/ws/app/series/getSeriesCourses";
	// 获取培训班的单个系列课详情
	public final static String GET_SIMPLE_SERIES = DOMAIN
			+ "/ws/app/series/getSeriesCourse";
	// 获取培训班系列课评论信息
	public final static String GET_SERIES_COMMENT = DOMAIN
			+ "/ws/app/series/comment/listSeriesCourse";
	// 获取培训班系列课单课评论信息
	public final static String GET_TRAIN_COMMENT = DOMAIN
			+ "/ws/app/series/comment/listCourse";
	// 获取培训班系列课单个课的详细信息
	public final static String GET_SIMPLE_COURSE = DOMAIN
			+ "/ws/app/series/getCourse";
	// 获取我的公开课列表
	public final static String GET_OPEN_COURSES = DOMAIN
			+ "/ws/app/personal/stu/public/list";
	// 获取我的培训班系列列表
	public final static String GET_TRAIN_CLASS = DOMAIN
			+ "/ws/app/personal/stu/series/list";

	// 获取我的培训班系列课详情
	public final static String GET_SIMPLE_CLASS = DOMAIN
			+ "/ws/app/personal/stu/series/detail";

	// 获取我的考试列表
	public final static String GET_MY_EXAME = DOMAIN
			+ " /ws/app/personal/stu/exam/list";
	// 获取我的收藏列表
	public final static String GET_MY_COLLECT = DOMAIN
			+ "/ws/app/personal/stu/favorites/list";
	// 删除我的收藏
	public final static String DELECT_COLLECT = DOMAIN
			+ "/ws/app/personal/stu/favorites/delete";
	// 获取我的互动列表
	public final static String GET_MY_INTERACT = DOMAIN
			+ "/ws/app/personal/stu/interactive/list";	
	// 获取我的证书列表
	public final static String GET_MY_CERTIFICATE = DOMAIN
			+ "/ws/app/personal/stu/certificate/list";
	// 获取版本更新
	public final static String GET_VERSION = DOMAIN
			+ "/ws/app/resource/checkLatestVersionApp";
	//乐视云视频根据uuid和vuid获取网页播放地址
	public final static String GET_VIDEOURL ="http://edu.youths.org.cn/public_class/show?id=";
}
