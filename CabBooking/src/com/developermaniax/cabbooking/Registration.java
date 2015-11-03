package com.developermaniax.cabbooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.developermaniax.cabbooking.models.RegistrationModel;

public class Registration extends Activity {

	EditText edtFirstName;
	EditText edtLastName;
	EditText edtEmailid;
	EditText edtMobileNo;
	EditText edtPassword;
	Button btnRegister;
	ProgressDialog progress;
	RegistrationModel model;
	boolean isSuccess;

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
		progress.setMessage(getString(R.string.alertmsg_AuthenticationInProgressMessage));
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isEmpty()) {
					model = new RegistrationModel();
					model.setFName(edtFirstName.getText().toString());
					model.setLName(edtLastName.getText().toString());
					model.setEmailId(edtEmailid.getText().toString());
					model.setMobileNo(edtMobileNo.getText().toString());
					model.setPassword(edtPassword.getText().toString());
					model.setCity("Rajkot");
					new ConnnectInternet()
							.execute(new String[] { com.developermaniax.cabbooking.utils.Constants.RegURL });
				}
			}
		});
	}

	private boolean isEmpty() {
		if (edtFirstName.getText().toString().isEmpty()
				|| edtLastName.getText().toString().isEmpty()
				|| edtEmailid.getText().toString().isEmpty()
				|| edtMobileNo.getText().toString().isEmpty()
				|| edtPassword.getText().toString().isEmpty()) {
			if (edtFirstName.getText().toString().isEmpty()) {
				edtFirstName.setError("Please enter first name!");
				return false;
			}
			if (edtLastName.getText().toString().isEmpty()) {
				edtLastName.setError("Please enter last name!");
				return false;
			}
			if (edtEmailid.getText().toString().isEmpty()) {
				edtEmailid.setError("Please enter email id!");
				return false;
			}
			if (edtMobileNo.getText().toString().isEmpty()) {
				edtMobileNo.setError("Please enter mobile no!");
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

	String status = "";
	String Message = "";

	private String getURL(String URL) {
		// http://bus.srooh.com/index.php?action=register&accesskey=ANDBUSAPK30014PK&f_name=ketan&l_name=makwana&email_id=ketan@gmail.com&password=124&phone_no=9033684948&city_id=rajkot
		URL += "&f_name=" + model.getFName() + "&l_name=" + model.getLName()
				+ "&email_id=" + model.getEmailId() + "&password="
				+ model.getPassword() + "&phone_no=" + model.getMobileNo()
				+ "&city_id=" + model.getCity();
		return URL;
	}

	private void getJSONData(String URL) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet post = new HttpGet(getURL(URL));
			// RegistrationModel model = new RegistrationModel();
			// List<NameValuePair> nameValuePair = new
			// ArrayList<NameValuePair>();
			// nameValuePair
			// .add(new BasicNameValuePair("f_name", model.getFName()));
			// nameValuePair
			// .add(new BasicNameValuePair("l_name", model.getLName()));
			// nameValuePair.add(new BasicNameValuePair("email_id", model
			// .getEmailId()));
			// nameValuePair.add(new BasicNameValuePair("password", model
			// .getPassword()));
			// nameValuePair.add(new BasicNameValuePair("phone_no", model
			// .getMobileNo()));
			// nameValuePair.add(new BasicNameValuePair("city_id", model
			// .getCity()));
			// post.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = client.execute(post);
			InputStream atomInputStream = response.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					atomInputStream));
			String line;
			String str = "";
			while ((line = in.readLine()) != null) {
				str += line;
			}
			JSONObject json = new JSONObject(str);
			for (int i = 0; i < json.length(); i++) {
				status = json.getString("status");
				Message = json.getString("msg");
				Log.e("Message", "" + status + " : " + Message);
			}
			if (status.equals("success")) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		} catch (IOException e) {
			Message = getString(R.string.alertmsg_ApplicationFailedMessage);
			Log.e("IOException", e.toString());
		} catch (JSONException e) {
			Log.e("IOException", e.toString());
			Message = getString(R.string.alertmsg_ApplicationFailedMessage);
		}

	}

	class ConnnectInternet extends AsyncTask<String, Void, Void> {

		public ConnnectInternet() {
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
			if (isSuccess == true) {
				Intent intent = new Intent(Registration.this, Login.class);
				startActivity(intent);
				finish();
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						Registration.this);
				alert.setTitle("Error!");
				alert.setMessage(Message);
				alert.setCancelable(false);
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
		}
	}

}
