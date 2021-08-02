package nithra.word.game.onepiconeword;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import nithra.word.game.onepiconeword.extra.SharedPreference;

import static nithra.word.game.onepiconeword.Utils.android_id;

public class referrer extends BroadcastReceiver {

	Context context1;
	String source = "";
	String medium = "";
	String comp = "";
	@Override
	public void onReceive(final Context context, Intent intent) {


		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Bundle extras = intent.getExtras();
		if (extras != null) {

			String referrerString = extras.getString("referrer");
			if(referrerString!=null){
				if(referrerString.length()>0){
					String[] referrerList = referrerString.split("&");

					for (int i = 0; i < referrerList.length; i++) {
						if (i == 0) {
							source = referrerList[i].substring(referrerList[i].indexOf("=") + 1);
						} else if (i == 1) {
							medium = referrerList[i].substring(referrerList[i].indexOf("=") + 1);
						} else if (i == 2) {
							comp = referrerList[i].substring(referrerList[i].indexOf("=") + 1);
						}
					}


					if (isNetworkAvailable(context)) {
						new AsyncTask<String,String,String>() {
							@Override
							protected String doInBackground(String... params) {
								send(context, source, medium, comp);
								return null;
							}
						}.execute();

					}
				}
			}
		}

	}

	public void send(Context context, String utm, String utm1, String utm2) {

		SharedPreference sp = new SharedPreference();

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://nithra.mobi/apps/referrer.php");
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
			String email = android_id(context);

			nameValuePairs.add(new BasicNameValuePair("app", "newopow"));

			nameValuePairs.add(new BasicNameValuePair("ref", utm));
			nameValuePairs.add(new BasicNameValuePair("mm", utm1));
			nameValuePairs.add(new BasicNameValuePair("cn", utm2));
			nameValuePairs.add(new BasicNameValuePair("email", email));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				Log.e("HttpResponse", line);
			}

		} catch (IOException e) {

		}

	}

	private boolean isNetworkAvailable(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}