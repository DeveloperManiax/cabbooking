package com.developermaniax.cabbooking.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developermaniax.cabbooking.R;
import com.developermaniax.cabbooking.models.EmergencyContact;

public class EmergencyContactAdapter extends ArrayAdapter<EmergencyContact> {

	ArrayList<EmergencyContact> objects;
	Context mContext;
	int resource;

	public EmergencyContactAdapter(Context context, int textViewResourceId,
			ArrayList<EmergencyContact> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.resource = textViewResourceId;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		view = (RelativeLayout) inflater.inflate(resource, parent, false);
		TextView txtName = (TextView) view.findViewById(R.id.txtEmergencyName);
		TextView txtContactNo = (TextView) view
				.findViewById(R.id.txtEmergencyNO);
		txtName.setText(objects.get(position).getName());
		txtContactNo.setText(objects.get(position).getContactNo());

		return view;
	}

}
