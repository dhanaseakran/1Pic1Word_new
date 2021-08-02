package nithra.word.game.onepiconeword;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nithra.word.game.onepiconeword.daily_test.Dailytest;
import nithra.word.game.onepiconeword.extra.SharedPreference;
import nithra.word.game.onepiconeword.demo.my_play_page;

import static nithra.word.game.onepiconeword.Utils.isNetworkAvailable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    SQLiteDatabase mydb_copy, mydb1;

    Cursor cursor = null;

    TextView noti_count, DT_noti_count, versition_name;
    ImageView play_btn_img, privacy, feed;
    LinearLayout noti_lay, DT_lay;

    RelativeLayout leader_board_lay, achivement_lay;

    static SharedPreference sp = new SharedPreference();

    static int addloded1 = 0;

    public static AdView adView3 = null;
    public static AdRequest adRequestrec;
    public static InterstitialAd interstitialAd123, interstitialAd1, interstitialAd_word;
    static LinearLayout add;
    private static final int RC_LEADERBOARD_UI = 9004;

    private GoogleApiClient mGoogleApiClient;

    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

    private static final String TAG = "TrivialQuest";

    int my_score = 0;

    private class gcmpost_update1 extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strr) {
            ServerUtilities.gcmupdate(MainActivity.this, Utils.versionname_get(MainActivity.this),
                    Utils.versioncode_get(MainActivity.this), strr[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String onlineVersions) {
            super.onPostExecute(onlineVersions);
            SharedPreference sharedPreference = new SharedPreference();
            sharedPreference.putInt(MainActivity.this, "fcm_update", Utils.versioncode_get(MainActivity.this));
        }
    }

    private class gcmpost_update2 extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strr) {
            ServerUtilities.gcmpost(strr[0], Utils.android_id(MainActivity.this), Utils
                            .versionname_get(MainActivity.this),
                    Utils.versioncode_get(MainActivity.this), MainActivity.this);
            return "";
        }

        @Override
        protected void onPostExecute(String onlineVersions) {
            super.onPostExecute(onlineVersions);
            SharedPreference sharedPreference = new SharedPreference();
            sharedPreference.putInt(MainActivity.this, "fcm_update", Utils.versioncode_get(MainActivity.this));
        }
    }

    public void smallestWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        float smallestWidth = Math.min(widthDp, heightDp);

        System.out.println("Width Pixels : " + widthPixels);
        System.out.println("Height Pixels : " + heightPixels);
        System.out.println("Dots per inch : " + metrics.densityDpi);
        System.out.println("Scale Factor : " + scaleFactor);
        System.out.println("Smallest Width : " + smallestWidth);

        sp.putString(MainActivity.this, "smallestWidth", smallestWidth + "");
        sp.putString(MainActivity.this, "widthPixels", widthPixels + "");
        sp.putString(MainActivity.this, "heightPixels", heightPixels + "");
        sp.putString(MainActivity.this, "density", metrics.densityDpi + "");

        /*Utilities.sharedPrefAdd("smallestWidth", smallestWidth + "", Utilities.mPreferences);
        Utilities.sharedPrefAdd("widthPixels", widthPixels + "", Utilities.mPreferences);
        Utilities.sharedPrefAdd("heightPixels", heightPixels + "", Utilities.mPreferences);
        Utilities.sharedPrefAdd("density", metrics.densityDpi + "", Utilities.mPreferences);*/


    }

    BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // mydb_copy = this.openOrCreateDatabase("findtheword_copy.db", MODE_PRIVATE, null);
        mydb_copy = this.openOrCreateDatabase("findtheword", MODE_PRIVATE, null);

        mydb1 = openOrCreateDatabase("myDB", 0, null);
        String tablenew = "noti_cal";
        mydb1.execSQL("CREATE TABLE IF NOT EXISTS "
                + tablenew
                + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR," +
                "bm VARCHAR,ntype VARCHAR,url VARCHAR);");

        mydb1.execSQL("CREATE TABLE IF NOT EXISTS notify_mark (uid integer NOT NULL PRIMARY KEY AUTOINCREMENT,id integer);");


        //  mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1'");

        if (sp.getString(MainActivity.this, "dailytest").equals("")) {
            sp.putString(MainActivity.this, "dailytest", "yes");
            Dailytest alarm2 = new Dailytest();
            Context context2 = this.getApplicationContext();
            if (alarm2 != null) {

                try {
                    alarm2.SetAlarm1(context2, Dailytest.armTodayOrTomo1("8", "0", MainActivity.this));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        smallestWidth();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                }
            }
        };

        if (sp.getInt(MainActivity.this, "first_time_privacy") == 0) {
            first_time_privacy();
        }

        if (sp.getInt(MainActivity.this, "isvalid") == 0) {
            if (sp.getString(this, "token").length() > 0) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                new gcmpost_update2().execute(refreshedToken);
            }

        } else if (sp.getInt(MainActivity.this, "fcm_update") < Utils.versioncode_get(MainActivity.this)) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            new gcmpost_update1().execute(refreshedToken);
        }

        System.out.println("android_id=====" + Utils.android_id(MainActivity.this));

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        // ...

        cursor = mydb_copy.rawQuery("SELECT * FROM onepiconewords", null);

        add = (LinearLayout) findViewById(R.id.add);
        noti_count = (TextView) findViewById(R.id.noti_count);
        DT_noti_count = (TextView) findViewById(R.id.DT_noti_count);
        versition_name = (TextView) findViewById(R.id.versition_name);
        versition_name.setText("V.name - " + sp.getString(MainActivity.this, "versionName"));

        play_btn_img = (ImageView) findViewById(R.id.play_btn_img);
        privacy = (ImageView) findViewById(R.id.privacy);
        feed = (ImageView) findViewById(R.id.feed);

        noti_lay = (LinearLayout) findViewById(R.id.noti_lay);
        DT_lay = (LinearLayout) findViewById(R.id.DT_lay);
        leader_board_lay = (RelativeLayout) findViewById(R.id.leader_board_lay);
        achivement_lay = (RelativeLayout) findViewById(R.id.achivement_lay);


        achivement_lay.setOnClickListener(this);
        leader_board_lay.setOnClickListener(this);
        play_btn_img.setOnClickListener(this);
        noti_lay.setOnClickListener(this);
        privacy.setOnClickListener(this);
        feed.setOnClickListener(this);
        DT_lay.setOnClickListener(this);

        play_btn_img.startAnimation(zoomAnim());

        if (sp.getString(MainActivity.this, "review_time").equals("")) {
            Calendar current_date = Calendar.getInstance();
            long currentdate_mills = current_date.getTimeInMillis();
            sp.putString(MainActivity.this, "review_time", "" + currentdate_mills);
        }
        app_update_manager();
    }

    AppUpdateManager appUpdateManager;

    public void app_update_manager() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

// Returns an intent object that you use to check for an update.
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE//){
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                //Toast.makeText(Main_open.this, "Update Available", Toast.LENGTH_SHORT).show();

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            MainActivity.this,
                            // Include a request code to later monitor this update request.
                            200);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                //inapp_review_dialog();
                if (sp.getString(MainActivity.this, "review_complete").equals("")) {
                    if (!sp.getString(MainActivity.this, "review_time").equals("")) {
                        long before_date = Long.parseLong(sp.getString(MainActivity.this, "review_time"));

                        Calendar current_date = Calendar.getInstance();

                        long currentdate_mills = current_date.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                        String string_current_date = sdf.format(currentdate_mills);
                        String string_before_date = sdf.format(before_date);


                        //System.out.println("new Version " + date);
                        long timediff = 0;

                        try {
                            timediff = TimeUnit.DAYS.convert(sdf.parse(string_current_date).getTime() - sdf.parse(string_before_date).getTime(), TimeUnit.MILLISECONDS);

                            System.out.println("new Version " + timediff);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        if ((int) timediff >= 10) {
                            System.out.println("new Version 1 " + timediff);
                            if (isNetworkAvailable(MainActivity.this)) {
                                inapp_review_dialog();
                            }
                        }
                    }

                }
            }
        });
    }

    public void inapp_review_dialog() {
//        ReviewManager manager =new FakeReviewManager(Main_open.this);
        ReviewManager manager = ReviewManagerFactory.create(MainActivity.this);
        com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                com.google.android.play.core.tasks.Task<Void> flow = manager.launchReviewFlow(MainActivity.this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {

                    sp.putString(MainActivity.this, "review_complete", "review_completed");
                    //Toast.makeText(Main_open.this, "review completed ", Toast.LENGTH_SHORT).show();
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                //Toast.makeText(Main_open.this, "There was a problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Animation zoomAnim() {

        ScaleAnimation animation = new ScaleAnimation((float) 0.9,
                (float) 0.83, (float) 0.9, (float) 0.83,
                Animation.RELATIVE_TO_SELF, (float) 0.5,
                Animation.RELATIVE_TO_SELF, (float) 0.5);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_btn_img: {
                Intent intent = new Intent(MainActivity.this, Game_Play_Activity.class);
                intent.putExtra("Dailytest_ok","");
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(intent);
            }
            break;
            case R.id.noti_lay: {
                Intent i = new Intent(MainActivity.this, Noti_Mark.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(i);
            }
            break;
            case R.id.DT_lay: {
                Intent intent = new Intent(MainActivity.this, my_play_page.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(intent);

            }
            break;
            case R.id.achivement_lay: {
                if (isNetworkAvailable(MainActivity.this)) {
                    if (mGoogleApiClient.isConnected()) {
                        Log.d(TAG, "Win button clicked");
                        startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 1002);
                        sp.putString(getApplicationContext(), "lead_achieve", "yes");

                    } else {
                        Log.d(TAG, "Sign-in button clicked");
                        mSignInClicked = true;

                        // Games.Leaderboards.submitScore(mGoogleApiClient, getString(nithra.logoquiz.R.string.leaderboard_high_score), 100);
                        // startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(nithra.logoquiz.R.string.leaderboard_high_score)), 1001);

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                Log.d(TAG, "Sign-in button clicked");
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                System.out.println("leader board");
                                mSignInClicked = true;
                                mGoogleApiClient.connect();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                if (mGoogleApiClient.isConnected()) {
                                    System.out.print("play u:");
                                    startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 1002);


                                }
                            }
                        }.execute();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                }
                // showLeaderboard();
            }
            break;
            case R.id.leader_board_lay: {
                my_score = sp.getInt(MainActivity.this, "My_leader_point");
                if (isNetworkAvailable(MainActivity.this)) {
                    if (mGoogleApiClient.isConnected()) {
                        Log.d(TAG, "Win button clicked");

                        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_high_score), my_score);
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_high_score)), 1001);
                        sp.putString(getApplicationContext(), "lead_achieve", "yes");

                    } else {
                        Log.d(TAG, "Sign-in button clicked");


                        mSignInClicked = true;

                        //Games.Leaderboards.submitScore(mGoogleApiClient, getString(nithra.logoquiz.R.string.leaderboard_high_score), 100);
                        //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(nithra.logoquiz.R.string.leaderboard_high_score)), 1001);

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();

                                Log.d(TAG, "Sign-in button clicked");
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                System.out.println("leader board");
                                mSignInClicked = true;
                                onConnectionSuspended(0);

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                if (mGoogleApiClient.isConnected()) {
                                    System.out.print("play u:");
                                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_high_score), my_score);
                                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_high_score)), 1001);

                                }
                            }
                        }.execute();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                }

            }
            break;
            case R.id.privacy: {
                if (isNetworkAvailable1(MainActivity.this)) {
                    showPrivacy(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.feed: {
                reportProblem(MainActivity.this);
            }
            break;
        }
    }



    public static boolean isNetworkAvailable1(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static AdView adView;
    public static AdRequest request;

   /* public void load_add1(final LinearLayout addViw,Context context) {
        adView = new AdView(MainActivity.this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-4267540560263635/4459051542");
        request = new AdRequest.Builder().build();

        sp.putInt(getApplicationContext(), "addloded", 0);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                sp.putInt(getApplicationContext(), "addloded", 1);
                load_addFromMain1(MainActivity.this, add);

                super.onAdLoaded();
            }

          *//*  @Override
            public void onAdFailedToLoad(int errorcode) {
                load_add(addViw);
            }*//*
        });

        adView.loadAd(request);
        if (adView != null) {
            ViewGroup parentViewGroup = (ViewGroup) adView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

    public static void load_addFromMain1(Context context, LinearLayout add_banner) {
        add = add_banner;
        try {
            if (adView != null) {
                ViewGroup parentViewGroup = (ViewGroup) adView.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
            if (sp.getInt(context, "addloded") == 1) {
                add_banner.setVisibility(View.VISIBLE);
                add_banner.removeAllViews();
                add_banner.addView(adView);
            }

        } catch (Exception e) {

        }
    }*/

/*    public void adds(final LinearLayout layout) {
        AdView adView = new AdView(MainActivity.this);
        adView.setAdUnitId("ca-app-pub-4267540560263635/4459051542");
        adView.setAdSize(AdSize.SMART_BANNER);

        layout.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                layout.setVisibility(View.VISIBLE);
            }
        });
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
    }*/


    public static void load_add1(final LinearLayout addViw, final Context context) {
        adView3 = new AdView(context);
        adView3.setAdSize(AdSize.SMART_BANNER);
        adView3.setAdUnitId("ca-app-pub-4267540560263635/7924212313");
        adRequestrec = new AdRequest.Builder().build();
        System.out.println("load add rect");

        sp.putInt(context, "addloded1", 0);

        addloded1 = 0;
        // Utilities.sharedPrefAddInt("addloded1", 0, Utilities.mPreferences);
        adView3.setAdListener(new AdListener() {
            public void onAdLoaded() {
                addloded1 = 1;
                System.out.println("load add rect ====onAdLoaded=== ");
                sp.putInt(context, "addloded1", 1);
                MainActivity.load_addFromMain1(addViw);
                super.onAdLoaded();
            }

            public void onAdFailedToLoad(int errorcode) {
                load_add1(addViw, context);
                System.out.println("load add rect onAdFailedToLoad " + errorcode);
            }
        });
        adView3.loadAd(adRequestrec);
        if (adView3 != null) {
            ViewGroup parentViewGroup = (ViewGroup) adView3.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

    public static void load_addFromMain1(LinearLayout add_banner) {
        add = add_banner;
        //Toast.makeText(get, "Load Add rect", Toast.LENGTH_SHORT).show();
        try {
            if (MainActivity.adView3 != null) {
                ViewGroup parentViewGroup = (ViewGroup) MainActivity.adView3.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
            if (addloded1 == 1) {
                add_banner.setVisibility(View.VISIBLE);
                add_banner.removeAllViews();
                add_banner.addView(MainActivity.adView3);
            }
        } catch (Exception e) {

        }

    }

    public static void loadInterstialAdd_exit(final Context context) {


        interstitialAd123 = new InterstitialAd(context);
        interstitialAd123.setAdUnitId("ca-app-pub-4267540560263635/2857069530");
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd123.loadAd(adRequest);

        interstitialAd123.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO Auto-generated method stub
                super.onAdFailedToLoad(errorCode);
                loadInterstialAdd_exit(context);
            }

            @Override
            public void onAdClosed() {
                // TODO Auto-generated method stub
                super.onAdClosed();
            }
        });
    }

    int go_back = 0;

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // rate();
        if (go_back == 0) {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            go_back = 1;
        } else {
            exit_fun();
        }


    }


    public void exit_fun() {
        if (sp.getString(MainActivity.this, "PointsPurchase").equals("")) {
            if (interstitialAd123.isLoaded()) {
                interstitialAd123.show();
                interstitialAd123.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {

                        backpresspop1();
                    }

                });

            } else {
                finish();
            }
        } else {
            finish();
        }

        /*final Dialog exit_game_dia = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        exit_game_dia.setContentView(R.layout.dia_game_exit);
        exit_game_dia.setCancelable(false);
        exit_game_dia.show();

        TextView EXIT_done_letter = (TextView) exit_game_dia.findViewById(R.id.EXIT_done_letter);
        TextView EXIT_cancel_letter = (TextView) exit_game_dia.findViewById(R.id.EXIT_cancel_letter);

        EXIT_done_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sp.getString(MainActivity.this, "PointsPurchase").equals("")) {
                    if (interstitialAd123.isLoaded()) {
                        interstitialAd123.show();
                        interstitialAd123.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {

                                backpresspop1();
                            }

                        });

                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
        EXIT_cancel_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_game_dia.dismiss();
            }
        });*/

    }

    @Override
    protected void onPause() {

        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));

        if (sp.getString(MainActivity.this, "PointsPurchase").equals("")) {
            load_add1(add, MainActivity.this);
            loadInterstialAdd_exit(MainActivity.this);
        } else {
            add.setVisibility(View.GONE);
        }


        Cursor c = mydb1.rawQuery("select * from noti_cal where isclose='0'", null);
        if (c.getCount() == 0) {
            noti_count.setVisibility(View.GONE);
        } else {
            noti_count.setVisibility(View.VISIBLE);

            if (c.getCount() < 10) {
                noti_count.setText("" + c.getCount());
            } else {
                noti_count.setText("9+");
            }
        }
    }

    public void rate() {

        Calendar calendar = Calendar.getInstance();
        String day_cal = (calendar.get(Calendar.DAY_OF_MONTH)) + "";
        String month_cal = (calendar.get(Calendar.MONTH) + 1) + "";
        String year_cal = calendar.get(Calendar.YEAR) + "";
        if (day_cal.length() == 1)
            day_cal = "0" + day_cal;
        if (month_cal.length() == 1)
            month_cal = "0" + month_cal;
        String current_day = day_cal + "/" + month_cal + "/" + year_cal;

        System.out.println("ask_rate_us =====current_day---- " + current_day);


        if (sp.getString(MainActivity.this, "ask_rate_us").equals(current_day)) {
            rateus_feedbak();

            Date currentDate = new Date();
            // convert date to calendar
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, 60);

            String day1 = "" + (c.get(Calendar.DAY_OF_MONTH));
            String month1 = "" + (c.get(Calendar.MONTH) + 1);
            String year1 = "" + c.get(Calendar.YEAR);
            if (day1.length() == 1)
                day1 = "0" + day1;
            if (month1.length() == 1)
                month1 = "0" + month1;

            sp.putString(MainActivity.this, "ask_rate_us", day1 + "/" + month1 + "/" + year1);

            System.out.println("ask_rate_us =====---- " + sp.getString(MainActivity.this, "ask_rate_us"));
        } else {

            System.out.println("ask_rate_us =====---- interstitialAd123.isLoaded() " + interstitialAd123.isLoaded());

            if (sp.getString(MainActivity.this, "PointsPurchase").equals("")) {
                if (interstitialAd123.isLoaded()) {
                    interstitialAd123.show();
                    interstitialAd123.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {

                            backpresspop1();
                        }

                    });

                } else {
                    finish();
                }
            } else {
                finish();
            }
        }

    }




        /*int initial = sp.getInt(MainActivity.this, "Initial");
        if (initial == 0) {

            rateus_feedbak();


            sp.putInt(MainActivity.this, "Rate_val", 1);
            sp.putInt(MainActivity.this, "Initial", 4);


            Calendar c = Calendar.getInstance();
            String day1 = (c.get(Calendar.DAY_OF_MONTH) + 15) + "";
            String month1 = (c.get(Calendar.MONTH) + 1) + "";
            String year1 = c.get(Calendar.YEAR) + "";
            if (day1.length() == 1)
                day1 = "0" + day1;
            if (month1.length() == 1)
                month1 = "0" + month1;

            sp.putString(MainActivity.this, "15Days", day1 + "/" + month1 + "/" + year1);

        } else if (initial == 1) {

            try {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String day = c.get(Calendar.DAY_OF_MONTH) + "";
                String month = (c.get(Calendar.MONTH) + 1) + "";
                String year = c.get(Calendar.YEAR) + "";
                if (day.length() == 1)
                    day = "0" + day;
                if (month.length() == 1)
                    month = "0" + month;


                Date date1 = sdf.parse(day + "/" + month + "/" + year);
                Date date2 = sdf.parse(sp.getString(MainActivity.this, "15Days"));
                System.out.println(sdf.format(date1));
                System.out.println(sdf.format(date2));

                if (date1.compareTo(date2) > 0) {
                    System.out.println("Date1 is after Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 30) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "30Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 2);

                } else if (date1.compareTo(date2) < 0) {
                    System.out.println("Date1 is before Date2");

                } else if (date1.compareTo(date2) == 0) {
                    System.out.println("Date1 is equal to Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 30) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "30Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 2);

                } else {
                    System.out.println("How to get here?");
                }

            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
            sp.putInt(MainActivity.this, "Initial", 4);

        } else if (initial == 2) {


            try {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String day = c.get(Calendar.DAY_OF_MONTH) + "";
                String month = (c.get(Calendar.MONTH) + 1) + "";
                String year = c.get(Calendar.YEAR) + "";
                if (day.length() == 1)
                    day = "0" + day;
                if (month.length() == 1)
                    month = "0" + month;


                Date date1 = sdf.parse(day + "/" + month + "/" + year);
                Date date2 = sdf.parse(sp.getString(MainActivity.this, "30Days"));
                System.out.println(sdf.format(date1));
                System.out.println(sdf.format(date2));

                if (date1.compareTo(date2) > 0) {
                    System.out.println("Date1 is after Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 60) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "60Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 3);
                } else if (date1.compareTo(date2) < 0) {
                    System.out.println("Date1 is before Date2");

                } else if (date1.compareTo(date2) == 0) {
                    System.out.println("Date1 is equal to Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 60) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "60Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 3);

                } else {
                    System.out.println("How to get here?");
                }

            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }

            sp.putInt(MainActivity.this, "Initial", 4);


        } else if (initial == 3) {

            try {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String day = c.get(Calendar.DAY_OF_MONTH) + "";
                String month = (c.get(Calendar.MONTH) + 1) + "";
                String year = c.get(Calendar.YEAR) + "";
                if (day.length() == 1)
                    day = "0" + day;
                if (month.length() == 1)
                    month = "0" + month;


                Date date1 = sdf.parse(day + "/" + month + "/" + year);
                Date date2 = sdf.parse(sp.getString(MainActivity.this, "60Days"));
                System.out.println(sdf.format(date1));
                System.out.println(sdf.format(date2));

                if (date1.compareTo(date2) > 0) {
                    System.out.println("Date1 is after Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 60) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "60Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 3);


                } else if (date1.compareTo(date2) < 0) {
                    System.out.println("Date1 is before Date2");

                } else if (date1.compareTo(date2) == 0) {
                    System.out.println("Date1 is equal to Date2");
                    rateus_feedbak();
                    Calendar c1 = Calendar.getInstance();
                    String day1 = (c1.get(Calendar.DAY_OF_MONTH) + 60) + "";
                    String month1 = (c1.get(Calendar.MONTH) + 1) + "";
                    String year1 = c1.get(Calendar.YEAR) + "";
                    if (day1.length() == 1)
                        day1 = "0" + day1;
                    if (month1.length() == 1)
                        month1 = "0" + month1;
                    sp.putString(MainActivity.this, "60Days", day1 + "/" + month1 + "/" + year1);
                    sp.putInt(MainActivity.this, "Rate_val", 3);
                } else {
                    System.out.println("How to get here?");
                }

            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }

            sp.putInt(MainActivity.this, "Initial", 4);


        } else {

           *//* Intent intent11 = new Intent(context, Bonus.class);
            @SuppressLint("WrongConstant") PendingIntent sender = PendingIntent.getBroadcast(context, 11, intent11, 11);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(sender);
            sender.cancel();


            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, Bonus.class);
            @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getBroadcast(context, 11, intent, 11);
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.MINUTE, 15);
            am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);*//*

            sp.putInt(MainActivity.this, "Initial", sp.getInt(MainActivity.this, "Rate_val"));
            if (interstitialAd123 != null) {
                if (interstitialAd123.isLoaded()) {
                    interstitialAd123.show();
                    interstitialAd123.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {

                            backpresspop1();
                        }

                    });

                } else {

                    finish();
                }
            } else {
                finish();
            }
        }


}*/

    public void backpresspop1() {
        Dialog share_app = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        share_app.setContentView(R.layout.back_press1);
        share_app.setCancelable(false);
        System.out.println("back press");
        Handler mHandler6 = new Handler();
        mHandler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1500);


        share_app.show();


    }


    private void rateus_feedbak() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView no = (TextView) dialog.findViewById(R.id.no);
        TextView ww = (TextView) dialog.findViewById(R.id.txt_exit);
        ImageView ss = (ImageView) dialog.findViewById(R.id.ssss);
        TextView yy = (TextView) dialog.findViewById(R.id.wpro);

        yy.setText("1Pic 1Word");
        ww.setText("Do you like this app?");
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp.putInt(MainActivity.this, "Value", 1);

                final Dialog yes_dialog = new Dialog(MainActivity.this);
                yes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                yes_dialog.setContentView(R.layout.rate);

                TextView yes = (TextView) yes_dialog.findViewById(R.id.yes);
                TextView no = (TextView) yes_dialog.findViewById(R.id.no);
                TextView ww = (TextView) yes_dialog.findViewById(R.id.txt_exit);
                TextView yy = (TextView) yes_dialog.findViewById(R.id.wpro);
                yy.setText("Rate Us");
                no.setText("No");
                yes.setText("Yes");
                ww.setText("If you like our application rate us 5 star in playstore.");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        yes_dialog.dismiss();

                        if (isNetworkAvailable(MainActivity.this)) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=nithra.word.game.onepiconeword"));
                            startActivity(marketIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Hey Buddy Connect to Network", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        yes_dialog.dismiss();
                        reportProblem(MainActivity.this);


                    }
                });

                yes_dialog.show();
                dialog.cancel();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.putInt(MainActivity.this, "Value", 1);
                reportProblem(MainActivity.this);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void reportProblem(final Context context) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.feedback);

        final TextView privacy_txt = (TextView) dialog.findViewById(R.id.privacy_txt);
        privacy_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(privacy_txt.getWindowToken(), 0);
                if (isNetworkAvailable(context)) {
                    showPrivacy(context);
                } else {
                    Toast.makeText(context, "Please check your internet....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final EditText EmaileditText = (EditText) dialog.findViewById(R.id.EmaileditText);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView yy = (TextView) dialog.findViewById(R.id.wpro);


        final EditText txtFeedBack = (EditText) dialog.findViewById(R.id.editText1);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (txtFeedBack.getText().toString().length() == 0) {
                    Toast.makeText(context, "Please type your feedback or suggestion, Thank you", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkAvailable(context)) {

                        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                            public void handleMessage(Message msg) {
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                    }
                                };
                                runOnUiThread(runnable);
                            }
                        };
                        Thread checkUpdate = new Thread() {
                            public void run() {
                                try {
                                    String email = EmaileditText.getText().toString().trim();
                                    send_feedback(context, txtFeedBack.getText().toString(), email);
                                } catch (Exception e) {
                                }
                                handler.sendEmptyMessage(0);
                            }
                        };
                        checkUpdate.start();
                        Toast.makeText(context, "Feedback send successfully ..", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Please check your internet..", Toast.LENGTH_SHORT).show();
                    }
                }


               /* if (isNetworkAvailable()) {
                    if (txtFeedBack.getText().toString().length() == 0) {
                        Toast.makeText(stage.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
                    } else {
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost("https://www.nithra.mobi/apps/appfeedback.php");
                        try {
                            // i=i+5;
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                            // Get the deviceID
                            PackageInfo pInfo = null;
                            try {
                                pInfo = stage.this.getPackageManager().getPackageInfo(stage.this.getPackageName(), 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            nameValuePairs.add(new BasicNameValuePair("type", "OPOW"));
                            String finalString = URLEncoder.encode(String.valueOf(txtFeedBack.getText().toString()), "UTF-8");
                            nameValuePairs.add(new BasicNameValuePair("feedback", finalString));
                            nameValuePairs.add(new BasicNameValuePair("email", Utilities.android_id(stage.this)));
                            nameValuePairs.add(new BasicNameValuePair("model", android.os.Build.MODEL));
                            nameValuePairs.add(new BasicNameValuePair("vcode", "" + pInfo.versionCode));

                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = client.execute(post);
                            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                            String line = "";
                            while ((line = rd.readLine()) != null) {
                                Log.e("HttpResponse", line);
                            }

                        } catch (IOException e) {

                        }

                        Toast.makeText(stage.this, "Feedback sent, Thank you", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(stage.this, "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                }*/


            }
        });

        InputMethodManager imm1 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        dialog.show();
    }

    public static void send_feedback(Context context, String feedback, String email) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://www.nithra.mobi/apps/appfeedback.php");
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

            PackageInfo pInfo = null;
            try {
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            nameValuePairs.add(new BasicNameValuePair("type", "opow_new"));
            String finalString = URLEncoder.encode(feedback, "UTF-8");
            System.out.println("Message-----/" + feedback);
            nameValuePairs.add(new BasicNameValuePair("feedback", finalString));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("model", android.os.Build.MODEL));
            nameValuePairs.add(new BasicNameValuePair("vcode", "" + pInfo.versionCode));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                Log.e("HttpResponse", line);
            }

        } catch (IOException e) {

        }

    }

    public static void showPrivacy(Context context) {
        String url = "https://www.nithra.mobi/privacy.php";

        Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.privacy);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
        final WebView webView = (WebView) dialog.findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.loadUrl(url);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        dialog.show();
    }

    int first_privacy = 0;

    public void first_time_privacy() {
        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.first_time_privacy);
        dialog.show();
        dialog.setCancelable(false);
        TextView privacy_txt = (TextView) dialog.findViewById(R.id.privacy_txt);
        TextView agree_txt = (TextView) dialog.findViewById(R.id.agree_txt);

        privacy_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(MainActivity.this)) {
                    showPrivacy(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                }

            }
        });


        agree_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_privacy = 1;
                dialog.dismiss();

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (first_privacy == 1) {
                    sp.putInt(MainActivity.this, "first_time_privacy", 1);
                   /* if (sp.getString(MainActivity.this, "Log_in").equals("")) {
                        if (isNetworkAvailable(MainActivity.this)) {
                            sp.putString(MainActivity.this, "Log_in", "Log_in");
                            mGoogleApiClient.connect();
                        }
                    }*/
                }
            }
        });


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow

        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, String.valueOf(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }
    }

    // Call when the sign-in button is clicked
  /*  private void signInClicked() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    // Call when the sign-out button is clicked
    private void signOutclicked() {
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
    }*/


    @Override
    protected void onStart() {
        super.onStart();
      /*  if (sp.getString(MainActivity.this, "Log_in").equals("")) {
            if (isNetworkAvailable(MainActivity.this)) {
                sp.putString(MainActivity.this, "Log_in", "Log_in");
                mGoogleApiClient.connect();
            }
        }*/


    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    //***************  Backup funtion start  *******************
    public void backup_DB() {

        Toast.makeText(MainActivity.this, "backup", Toast.LENGTH_SHORT).show();

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Logo Quiz", "Creating Backup Please wait....", true);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                dialog.dismiss();

            }
        };
        Thread checkUpdate = new Thread() {
            public void run() {

                try {

                    File dbFile = new File("/data/data/" + getPackageName() + "/databases/findtheword_copy.db");
                    // File dbFile = new File(Environment.getDataDirectory() + "/data/nithra.newlogoquiz/databases/My_LQ");
                    //File dbFile = new File(Environment.getDataDirectory() + "/data/logoquiz.nithra.logoquiz_check/databases/My_LQ");
                    FileInputStream in = new FileInputStream(dbFile);

                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdCard.getAbsolutePath() + "/Nithra/new_OPOW/");
                    dir.mkdirs();
                    File file = new File(dir, "new_OPOW_selection.db");
                    //File file = new File(dir, "Main_logoquiz.db");
                    FileOutputStream f = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                    }
                    f.close();

                } catch (Exception e) {
                    Log.d("CopyFileFromAssetsToSD", e.getMessage());
                }
                handler.sendEmptyMessage(0);
            }
        };
        checkUpdate.start();
    }

    //***************  Backup funtion end  *******************
}
