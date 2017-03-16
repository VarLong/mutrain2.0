package com.ytrain.mutrain.activity.train;

import java.util.List;

import com.ytrain.mutrain.adapter.PublicTypeAdapter;
import com.ytrain.mutrain.entity.MaxTypelist;
import com.ytrain.mutrain.entity.PublicMaxType;
import com.ytrain.mutrain.utils.Constants;
import com.ytrain.mutrain.utils.HttpUtil;
import com.ytrain.mutrain.utils.asynchttp.handler.AsyncHttpCilentHandler;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TrainFragment extends Fragment {
	/**
	 * 加载团建内容左侧显示
	 */

	private View view;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private List<PublicMaxType> list;
	private PublicTypeAdapter typeAdapter;
	private String TypeString;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		// TODO Auto-generated method stub
		// manager = getActivity().getSupportFragmentManager();
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = null;
		view = inflater.inflate(R.layout.openfragment, container, false);
		mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) view.findViewById(R.id.left_drawer);

		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		DrawerLayout.LayoutParams draLayoutParams = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList
				.getLayoutParams();
		draLayoutParams.width = dm.widthPixels / 2;
		mDrawerList.setLayoutParams(draLayoutParams);

		mDrawerList.setDividerHeight(0);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		selectitem("d22a77f1d6e94b769f881851c7e445fc", 1, "团干培训");
		getPublicMaxType();
		return view;
	}

	/**
	 * 获取数据
	 */
	private void getPublicMaxType() {
		HttpUtil.getFullUrl(Constants.GET_SERIES_TYPE, null,
				new AsyncHttpCilentHandler<MaxTypelist>(getActivity(),
						MaxTypelist.class) {
					@Override
					public void onFailure(Throwable paramThrowable,
							String paramString) {
						
					}

					@Override
					public void onSuccess(String paramString) {
						if (paramString != null && paramString.length() != 0) {
							if (paramString.startsWith("{")
									&& paramString.endsWith("}")) {
								paramString = "[" + paramString + "]";
							}
							paramString = "{typelist:" + paramString + "}";
						}
						super.onSuccess(paramString);
					}

					@Override
					public void process(MaxTypelist paramT) {
						// TODO Auto-generated method stub
						list = paramT.getTypelist();
						typeAdapter = new PublicTypeAdapter(list, getActivity());
						mDrawerList.setAdapter(typeAdapter);
						typeAdapter.notifyDataSetChanged();
					}
				}, getActivity());
	}

	private class DrawerItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			String typeid = list.get(position).getId();
			String typename = list.get(position).getName();
			selectitem(typeid, position, typename);
		}
	}

	public void selectitem(String id, int position, String name) {
		Fragment fragment = new TrainClassFragment();
		Fragment toptrain = new TopTrainFragment();
		Bundle args = new Bundle();
		args.putString("id", id);
		args.putString("name", name);
		fragment.setArguments(args);
		toptrain.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.framecontent, fragment)
				.commit();
		fragmentManager.beginTransaction()
				.replace(R.id.toplinealayout, toptrain).commit();
		fragmentManager.beginTransaction().commitAllowingStateLoss();
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/**
	 * 根据当前的状态判断侧边栏开启还是关闭
	 */
	public void openDrawLayout() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			mDrawerLayout.openDrawer(mDrawerList);
		}

	}

}
