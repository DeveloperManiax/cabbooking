package com.developermaniax.cabbooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BookingSelection extends Fragment {

	AlertDialog levelDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_booking_selection,
				container, false);
		final CharSequence[] items = { " Intracity Ride ",
				" City Sight Seeing ", " Car Hire On Disposal " };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select Ride Type");
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {

						switch (item) {
						case 0:
							// Your code when first option seletced
							android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
							Fragment fragment = new Order();
							fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
							break;
						case 1:
							// Your code when 2nd option seletced
							break;
						case 2:
							// Your code when 3rd option seletced
							break;
						}
						levelDialog.dismiss();
					}
				});
		levelDialog = builder.create();
		levelDialog.show();

		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
