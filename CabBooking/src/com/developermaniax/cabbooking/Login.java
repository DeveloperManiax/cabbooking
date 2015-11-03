package com.developermaniax.cabbooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developermaniax.cabbooking.models.LoginData;
import com.developermaniax.cabbooking.models.LoginModel;

public class Login extends Activity {

	Button btnLogin;
	EditText edtUserName;
	EditText edtPassword;
	ProgressDialog progress;
	LoginModel model;
	boolean isLoggedIn;
	String Message;
	TextView txtSignUp;
	LoginData loginData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		edtUserName = (EditText) findViewById(R.id.edtUserName);
		edtPassword = (EditText) findViewById(R.id.edtLPassword);
		txtSignUp = (TextView) findViewById(R.id.txtSignUP);

		progress = new ProgressDialog(Login.this);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);
		progress.setMessage("Login in progress...");
		txtSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, Registration.class);
				startActivity(intent);
			}
		});
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isEmpty()) {
					model = new LoginModel();
					model.setUserName(edtUserName.getText().toString());
					model.setPassword(edtPassword.getText().toString());
					new GetData()
							.execute(new String[] { com.developermaniax.cabbooking.utils.Constants.LoginURL });
				}
			}
		});

	}

	private boolean isEmpty() {
		if (edtUserName.getText().toString().isEmpty()
				|| edtPassword.getText().toString().isEmpty()) {

			if (edtUserName.getText().toString().isEmpty()) {
				edtUserName.setError("Please enter username!");
				return false;
			}
			if (edtPassword.getText().toString().isEmpty()) {
				edtPassword.setError("Please enter password!");
				return false;
			}
			return false;
		} else {
			return true;
		}
	}

	private String getURL(String URL) {
		URL += "&email=" + model.getUserName() + "&pass=" + model.getPassword();
		return URL;
	}

	private void getJSONData(String URL) {
		try {
			Log.e("URL", getURL(URL));
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(getURL(URL));
			HttpResponse response = client.execute(get);
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String str;
			String line = "";
			while ((str = reader.readLine()) != null) {
				line += str;
			}
			JSONObject JSonObj = new JSONObject(line);
			JSONArray jArray;
			JSONObject inJObject;
			loginData = new LoginData();
			String status = "";

			for (int i = 0; i < JSonObj.length(); i++) {
				status = JSonObj.getString("status");
				jArray = JSonObj.getJSONArray("user_data");
				for (int j = 0; j < jArray.length(); j++) {
					inJObject = jArray.getJSONObject(j);
					loginData.setUserId(inJObject.getString("uid"));
					loginData.setfName(inJObject.getString("f_name"));
					loginData.setlName(inJObject.getString("l_name"));
					loginData.setEmailId(inJObject.getString("email_id"));
					loginData.setMobileNo(inJObject.getString("phone_no"));
					loginData.setAddress(inJObject.getString("address"));
					loginData.setCityId(inJObject.getString("city_id"));
					loginData.setPassword(inJObject.getString("password"));
					loginData.setDate(inJObject.getString("created_date"));
				}
				Message = JSonObj.getString("msg");
			}
			if (status.equals("success")) {
				isLoggedIn = true;
			} else {
				isLoggedIn = false;
			}

		} catch (JSONException e) {
			Message = getString(R.string.alertmsg_ApplicationFailedMessage);
			Log.e("JSONExce", e.toString());
		} catch (IOException e) {
			Message = getString(R.string.alertmsg_ApplicationFailedMessage);
			Log.e("IOExcep", e.toString());
		}
		// saveLoginDetails();

	}

	private void saveLoginDetails() {
		Context context = getApplicationContext();

		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.pref_key), 0);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.pref_user), edtUserName.getText()
				.toString());
		editor.putString(getString(R.string.pref_pass), edtPassword.getText()
				.toString());
		editor.commit();
	}

	private class GetData extends AsyncTask<String, Void, Void> {

		public GetData() {
			// TODO Auto-generated constructor stub
			progress.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			getJSONData(params[0]);
			progress.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (isLoggedIn == true) {
				Intent intent = new Intent(Login.this, Home.class);
				startActivity(intent);
				finish();
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
				alert.setTitle("Error!");
				alert.setMessage(Message);
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				alert.show();
			}
			super.onPostExecute(result);
		}
	}
}
