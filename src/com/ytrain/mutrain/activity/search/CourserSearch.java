package com.ytrain.mutrain.activity.search;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.adapter.HotAdapter;
import com.ytrain.mutrain.entity.courses.HotCourses;
import com.ytrain.mutrain.entity.courses.HotCourseslist;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("NewApi")
public class CourserSearch extends FragmentActivity {
	/**
	 * 公开课搜索 界面
	 * 
	 */
	private List<HotCourses> list;
	private String hotString;
	private HotAdapter adapter;
	private ListView listrank;
	private EditText search;
	private TextView textview;
	private ImageView imageview;
	private FragmentManager manager;
	private int DONE = 1101;
	private int flag;	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			adapter = new HotAdapter(list, getApplicationContext());
			adapter.notifyDataSetChanged();
			listrank.setAdapter(adapter);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courser_search);
		flag = getIntent().getExtras().getInt("flag");
		textview = (TextView) findViewById(R.id.textView1);
		imageview = (ImageView) findViewById(R.id.HotRank);
		listrank = (ListView) findViewById(R.id.listRank);
		textview.setVisibility(View.VISIBLE);
		imageview.setVisibility(View.VISIBLE);
		listrank.setVisibility(View.VISIBLE);
		listrank.setDividerHeight(0);
		manager = getSupportFragmentManager();
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_search);
		search = (EditText) actionBar.getCustomView().findViewById(
				R.id.EditSearch);
		actionBar.setTitle("");
		actionBar.setHomeButtonEnabled(true);//
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(
				R.color.green));
		actionBar.setDisplayShowHomeEnabled(false);// 不显示应用图标
		listrank.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String keyword = list.get(position).getCourse_name();			
				search.setText(keyword);
				searchlistener(keyword);
			}
		});
		search.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String keyword = search.getText().toString();
					if (!keyword.isEmpty()) {
						searchlistener(keyword);
					}else {
						
					}
					
				}
				return false;
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if (flag == 1) {
					GetHotSearch();
				}
			}
		});
		thread.start();
	}

	private void GetHotSearch() {

		HttpUtil.getFullUrl(
				Constants.GET_HOT_SEARCH, null,
				new AsyncHttpCilentHandler<HotCourseslist>(
						CourserSearch.this, HotCourseslist.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
					}

					@Override
					public void onSuccess(String paramString) {
						hotString=paramString;
						if (hotString != null && hotString.length() != 0) {
							if (hotString.startsWith("{") && hotString.endsWith("}")) {
								hotString = "[" + hotString + "]";
							}
							hotString = "{HotList:" + hotString + "}";
							HotCourseslist hotlist = JSON.parseObject(hotString,
									HotCourseslist.class);
							list = hotlist.getHotList();
						}
						handler.sendEmptyMessage(DONE);
					}

					@Override
					public void process(HotCourseslist paramT) {
					}
				}, CourserSearch.this);
	}

	private void searchlistener(String keyword) {
		OpenSearchFragment fragment = new OpenSearchFragment();
		Bundle args = new Bundle();
		args.putString("keyword", keyword);
		textview.setVisibility(View.GONE);
		imageview.setVisibility(View.GONE);
		listrank.setVisibility(View.GONE);
		fragment.setArguments(args);
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.searchedlist, fragment).commit();
		// 输入完成后隐藏键盘
		((InputMethodManager) search.getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.courser_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.search) {
			String keyword = search.getText().toString();
			searchlistener(keyword);
		}
		return super.onOptionsItemSelected(item);
	}
}
