package com.ytrain.mutrain.view;

import com.bumptech.glide.Glide;
import com.ytrain.mutrain.utils.Constants;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherDetailDialog extends AlertDialog {
	/**
	 * 教师信息弹出框
	 */	
	private Context context;
	private ImageButton closeBtn;
	private ImageView teacherImg;
	private TextView teacherTV;
	private TextView descriptionTV;
    private String teacher_name;
    private String description;
    private String teacher_img_path;
	
	public TeacherDetailDialog(Context context, int theme, String teacher_name,
			String teacher_description, String teacher_img_path) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.description = teacher_description;
		this.teacher_img_path = teacher_img_path ;
		this.teacher_name = teacher_name ;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_dialog);

		teacherImg = (ImageView) findViewById(R.id.teacherimg);
		teacherTV = (TextView) findViewById(R.id.teacher);
		descriptionTV = (TextView)findViewById(R.id.teadescription);		
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams params = dialogWindow.getAttributes();
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		params.width = (int) (dm.widthPixels * 0.9); // 宽度设置为屏幕的0.6
		params.height = (int) (dm.heightPixels*0.8);
		dialogWindow.setAttributes(params);
		init();	
	}
     private void init(){
    	 String image_url = Constants.DOMAIN+"/file/load?image="+ teacher_img_path;
    	 Glide.with(context)
 		.load(image_url).centerCrop()
 		.into(teacherImg);
		
		teacherTV.setText(teacher_name);
		descriptionTV.setText("讲师简介:"+FormatString(description));
     }
     private String FormatString(String StringData ){
 		
 		//处理简介中的换行问题
 		if(StringData != null){
 			StringData = StringData.replaceAll("\r|\n", "");
 		}
 		return StringData ;
 	}
}
