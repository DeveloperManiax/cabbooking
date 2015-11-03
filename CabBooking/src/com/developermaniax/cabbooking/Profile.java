package com.developermaniax.cabbooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developermaniax.cabbooking.models.LoginData;

public class Profile extends Fragment {

	TextView txtUserName;
	TextView txtContactNo;
	TextView txtCityName;
	TextView txtEmailId;
	TextView txtAddress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_profile, container,
				false);
		txtUserName = (TextView) view.findViewById(R.id.txtUserName);
		txtContactNo = (TextView) view.findViewById(R.id.txtMobileNo);
		txtCityName = (TextView) view.findViewById(R.id.txtCityName);
		txtEmailId = (TextView) view.findViewById(R.id.txtEmailId);
		txtAddress = (TextView) view.findViewById(R.id.txtAddress);

		try {

			txtUserName.setText(LoginData.getfName() + " "
					+ LoginData.getlName());
			txtContactNo.setText(LoginData.getMobileNo());
			txtCityName.setText(LoginData.getCityId());
			txtEmailId.setText(LoginData.getEmailId());
			txtAddress.setText(LoginData.getAddress());

		} catch (NullPointerException e) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					getActivity());
			alertDialog.setMessage("No Data Found");
			alertDialog.setCancelable(false);
			alertDialog.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			alertDialog.show();
		}

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

}
