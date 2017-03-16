package com.ytrain.mutrain.activity.my;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.entity.train.MySimpleCourse;
import com.ytrain.mutrain.entity.train.SimpleTrainCourse;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MySimpleTrainActivity extends FragmentActivity {
	/**
	 * 我的培训班界面 我的
	 */

	private String enrollId;
	private ListView coursesLV;
	private TextView nameTV;
	private TextView starttimeTV;
	private TextView remaintimeTV;
	private ImageView back;
	private MySimpleCourse mycourses;
	private List<SimpleTrainCourse> courses;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			nameTV.setText(mycourses.getCourseGroupName());
			starttimeTV.setText(mycourses.getEnrollTime());
			remaintimeTV.setText(mycourses.getRemainStudyDays());
			SimpleCoursesAdapter adapter = new SimpleCoursesAdapter(
					MySimpleTrainActivity.this, courses);
			coursesLV.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_simple_train);
		enrollId = getIntent().getExtras().getString("id");
		initView();
		GetSimpleTrain();

	}

	private void initView() {
		nameTV = (TextView) findViewById(R.id.nameTV);
		starttimeTV = (TextView) findViewById(R.id.starttimeTV);
		remaintimeTV = (TextView) findViewById(R.id.remaintimeTV);
		coursesLV = (ListView) findViewById(R.id.coursesLV);
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(new GoBackListener());
	}

	private void GetSimpleTrain() {

		HttpUtil.getFullUrl(Constants.GET_SIMPLE_CLASS
				+ "?enrollId=" + enrollId, null,
				new AsyncHttpCilentHandler<MySimpleCourse>(
						MySimpleTrainActivity.this, MySimpleCourse.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						mycourses = JSON.parseObject(paramString,MySimpleCourse.class);						 
						courses = mycourses.getCourses();
						handler.sendEmptyMessage(110);
					}

					@Override
					public void process(MySimpleCourse paramT) {
					}
				}, MySimpleTrainActivity.this);

	}

	private class GoBackListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			finish();
		}

	}
}
