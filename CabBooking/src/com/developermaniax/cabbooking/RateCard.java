package com.developermaniax.cabbooking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RateCard extends Fragment {

	// Tab titles
	// customer.care@icicibank.com
	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_rate_card, container,
				false);

		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup(this.getActivity(), getChildFragmentManager(),
				R.id.realtabcontent);

		mTabHost.addTab(
				mTabHost.newTabSpec("NightRate").setIndicator(
						"Night Rate",
						getActivity().getResources().getDrawable(
								R.drawable.home)), NightRate.class, null);

		mTabHost.addTab(
				mTabHost.newTabSpec("DayRate").setIndicator(
						"Day Rate",
						getActivity().getResources().getDrawable(
								R.drawable.book)), DayRate.class, null);
		
		return view;
	}
}
