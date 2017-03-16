package com.ytrain.mutrain.activity.my;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MySuggestion extends Activity implements OnClickListener{
	
	private EditText edit_suggestion,edit_phone;
	private Button commit;
	private TextView textView;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);	
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_traintop);
		TextView titleTV = (TextView) actionBar.getCustomView().findViewById(
				R.id.titleTv);
		titleTV.setText("意见反馈");
		ImageView goBack = (ImageView) actionBar.getCustomView()
				.findViewById(R.id.topbutton);
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		goBack.setOnClickListener(this);
		initview();
	}

	private void initview() {
		edit_suggestion=(EditText) findViewById(R.id.edit_suggestion);
		edit_phone=(EditText) findViewById(R.id.edit_phone);
		commit=(Button) findViewById(R.id.button1);
		textView=(TextView) findViewById(R.id.textViewNum);
		//设置意见的动态监听
		edit_suggestion.addTextChangedListener(mTextWatcher);
		commit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.topbutton:
			finish();
			break;
		case R.id.button1:
			
			break;

		default:
			break;
		}
		
	}
	
	TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                int arg3) {
            temp = s;
        }
       
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                int arg3) {
        	textView.setText(String.valueOf(s.length()));
        }
       
        @Override
        public void afterTextChanged(Editable s) {
            editStart = textView.getSelectionStart();
            editEnd = textView.getSelectionEnd();
            if (temp.length() > 250) {
                Toast.makeText(MySuggestion.this,
                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
                        .show();
            }
        }
	
    };

}
