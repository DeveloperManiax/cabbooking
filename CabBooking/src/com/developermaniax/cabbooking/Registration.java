package com.developermaniax.cabbooking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends Activity {

	EditText edtFirstName;
	EditText edtLastName;
	EditText edtEmailid;
	EditText edtMobileNo;
	EditText edtPassword;
	Button btnRegister;
	ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		edtFirstName = (EditText) findViewById(R.id.edtFirstName);
		edtLastName = (EditText) findViewById(R.id.edtLastName);
		edtEmailid = (EditText) findViewById(R.id.edtEmailId);
		edtMobileNo = (EditText) findViewById(R.id.edtMobileNo);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		progress = new ProgressDialog(Registration.this);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Registration.this, Login.class);
				startActivity(intent);

			}
		});
	}

	private void ConnectInternet() {
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(
					com.developermaniax.cabbooking.utils.Constants.RegURL);
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("username", "test_user"));
			nameValuePair.add(new BasicNameValuePair("password", "123456789"));
			post.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = client.execute(post);
			
			InputStream atomInputStream = response.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					atomInputStream));
			String line;
			String str = "";
			while ((line = in.readLine()) != null) {
				str += line;
			}

			// parse json data and store into tax and currency variables
			JSONObject json = new JSONObject(str);
			JSONArray data = json.getJSONArray("status");
			

		} catch (Exception e) {

		}

	}

	private void getJSONData(String URL) {
		JSONArray jArray;
		JSONObject jObj;

	}

	class ConnnectInternet extends AsyncTask<Void, Void, Void> {

		public ConnnectInternet() {
			// TODO Auto-generated constructor stub
			progress.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			progress.dismiss();
			return null;
		}

	}

}
