package com.ytrain.mutrain.activity.train;

import com.alibaba.fastjson.JSON;
import com.ytrain.mutrain.entity.train.SeriesCourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class IntroductionFragment extends Fragment {
	/**
	 * 团建内容界面viewpager第二页
	 */
	private TextView tagTV;
	private TextView peopleTV;
	private TextView certificateTV;
	private TextView sourceTV;
	private TextView hitsTV;
	private TextView coursestimeTV;
	private RatingBar ratingBar;
	private TextView descriptionTV;
	private SeriesCourses seriesCourses;
	private String DescriptionString;

	public static IntroductionFragment newInstance(String JsonString) {
		IntroductionFragment fragment = new IntroductionFragment();
		Bundle b = new Bundle();
		b.putString("JsonString", JsonString);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		String JsonString = bundle.getString("JsonString");
		if (JsonString != null && JsonString.length() != 0) {
			seriesCourses = JSON.parseObject(JsonString, SeriesCourses.class);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View contextView = inflater.inflate(R.layout.fragment_introduction,
				container, false);
		tagTV = (TextView) contextView.findViewById(R.id.tag);
		peopleTV = (TextView) contextView.findViewById(R.id.peopleTV);
		certificateTV = (TextView) contextView.findViewById(R.id.certificateTV);
		sourceTV = (TextView) contextView.findViewById(R.id.sourceTV);
		hitsTV = (TextView) contextView.findViewById(R.id.hitsTV);
		coursestimeTV = (TextView) contextView.findViewById(R.id.coursestimeTV);
		ratingBar = (RatingBar) contextView.findViewById(R.id.ratingBar);
		descriptionTV = (TextView) contextView.findViewById(R.id.descriptionTV);
		String TagString = seriesCourses.getTag();
		if (TagString != null && TagString.length() != 0) {
			TagString = TagString.replaceAll("；", "】【");
			TagString = "【" + TagString + "】";
			tagTV.setText(TagString);
		} else if (TagString == "") {
			tagTV.setVisibility(View.GONE);
		}
		DescriptionString = seriesCourses.getDescription();
		if (DescriptionString != null) {
			DescriptionString = DescriptionString.replaceAll("\r|\n", "");
			descriptionTV.setText(DescriptionString);
		}
		peopleTV.setText(seriesCourses.getApplicable_people());
		certificateTV.setText(seriesCourses.getCertificate());
		sourceTV.setText(seriesCourses.getSource_name());
		hitsTV.setText(seriesCourses.getHits() + "");
		if (seriesCourses.getCredit_hours() == null) {

		} else {
			coursestimeTV.setText(seriesCourses.getCredit_hours() + "");
		}

		ratingBar.setRating(seriesCourses.getAvg_star_score());

		return contextView;

	}
}
