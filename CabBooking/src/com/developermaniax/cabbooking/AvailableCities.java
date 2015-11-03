package com.developermaniax.cabbooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.developermaniax.cabbooking.utils.Constants;

public class AvailableCities extends Fragment {

	Spinner spStatelist;
	ListView lstCities;
	ProgressDialog progressDialog;

	List<String> arrayStateNames;
	ArrayList<String> arrayStateId;

	ArrayAdapter<String> stateListAdapter;
	List<String> arrayCityList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_available_cities,
				container, false);

		spStatelist = (Spinner) view.findViewById(R.id.spState);
		lstCities = (ListView) view.findViewById(R.id.lstAvailableCities);

		progressDialog = new ProgressDialog(getActivity());
		progressDialog
				.setMessage(getString(R.string.alertmsg_ProcessingMessage));
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);

		new ConnectStateJSON(true)
				.execute(new String[] { Constants.AvailableState });
		spStatelist
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						new ConnectCityJSON(false)
								.execute(new String[] { Constants.AvailableCities
										+ arrayStateId.get(position) });
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		return view;
	}

	boolean status = false;

	private void disableConnectionReuseIfNecessary() {
		// Work around pre-Froyo bugs in HTTP connection reuse.
		if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	private void setJSONData(String url, boolean flag) {

		Log.e("URL", url);
		int timeout = 30000;
		HttpURLConnection c = null;
		String JSON = null;
		try {
			disableConnectionReuseIfNecessary();
			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);
			c.connect();

			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(
						c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				JSON = sb.toString();
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (c != null) {
				try {
					c.disconnect();
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}

		if (flag == true) {
			try {
				Log.e("JSON", JSON);
				JSONObject jObject = new JSONObject(JSON);
				String status = jObject.getString("status");
				arrayStateId = new ArrayList<String>();
				arrayStateNames = new ArrayList<String>();
				Log.e("Status", status);
				JSONArray array = jObject.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					JSONObject objec = array.getJSONObject(i);
					arrayStateId.add(objec.getString("sid"));
					arrayStateNames.add(objec.getString("sname"));
				}

				if (status.equalsIgnoreCase("success")) {
					this.status = true;
				} else {
					this.status = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("Error!", e.toString());

			}
		} else {
			try {
				Log.e("JSON", JSON);
				JSONObject jObject = new JSONObject(JSON);
				String status = jObject.getString("status");
				arrayCityList = new ArrayList<String>();
				JSONArray array = jObject.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					JSONObject objec = array.getJSONObject(i);
					arrayCityList.add(objec.getString("cname"));
				}

				if (status.equalsIgnoreCase("success")) {
					this.status = true;
				} else {
					this.status = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("Error!", e.toString());

			}
		}
	}

	private class ConnectStateJSON extends AsyncTask<String, Void, Void> {

		boolean flag;

		public ConnectStateJSON(boolean flag) {
			// TODO Auto-generated constructor stub
			this.flag = flag;
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			setJSONData(params[0], flag);
			progressDialog.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (status == true) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), R.layout.list_row_item, arrayStateNames);
				spStatelist.setAdapter(adapter);
			} else {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						getActivity());
				alertDialog.setTitle("Error!");
				alertDialog
						.setMessage("Error! occurred!\nplease restart the application.");
				alertDialog.setCancelable(false);
				alertDialog.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				alertDialog.show();
			}
		}
	}

	private class ConnectCityJSON extends AsyncTask<String, Void, Void> {

		boolean flag;

		public ConnectCityJSON(boolean flag) {
			this.flag = flag;
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			setJSONData(params[0], flag);
			progressDialog.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (status == true) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), R.layout.list_row_item, arrayCityList);
				lstCities.setAdapter(adapter);
			} else {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						getActivity());
				alertDialog.setTitle("Error!");
				alertDialog
						.setMessage("Error! occurred!\nplease restart the application.");
				alertDialog.setCancelable(false);
				alertDialog.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				alertDialog.show();
			}
		}

	}

	public boolean isConnectingToInternet(Context c) {
		ConnectivityManager connectivity = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
}
