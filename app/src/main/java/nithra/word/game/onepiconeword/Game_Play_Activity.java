package nithra.word.game.onepiconeword;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import nithra.word.game.onepiconeword.extra.SharedPreference;


import static nithra.word.game.onepiconeword.MainActivity.adView3;


public class Game_Play_Activity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener, RewardedVideoAdListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView coins_txt, ans_txt_value;

    ImageView ans_txt1, ans_txt2, ans_txt3, ans_txt4, ans_txt5, ans_txt6, ans_txt7;

    ImageView qus_img1, qus_img2, qus_img3, qus_img4, qus_img5, qus_img6, qus_img7, qus_img8, qus_img9, qus_img10, leader_board_img,
            btnhint, btndeleteletter, game_img, play_coin_img, settings_img, dimen_back, btnfacebook, btnwhatsapp, theme_img;

    RelativeLayout qus_full_lay, main_lay, card_view, top;

    LinearLayout coin_lay, addview;

    Handler ads_handler = new Handler();

    TextView temp_view;

    SQLiteDatabase mydb_copy;
    Cursor cursor = null;
    static SharedPreference sp = new SharedPreference();
    TextToSpeech tts;
    public MediaPlayer coin_collect, coimove, wro, ting, edtt, but, coimove1, coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, coin9, coin10;

    int go_tag_value, ans_douch_pos = 0;

    //String alphabet1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    ArrayList<String> qus_letter_list = new ArrayList<>();

    String Dailytest_ok = "Dailytest_ok";

    Dialog no_internet_dia;
    ArrayList<Integer> ans_tag_id;


    int id1 = 0, last_id = 0, random_question_count = 0;

    String daily_ok = "", need_permission = "", share_conetnt = "", btn_str;

    InterstitialAd interstitialAd_word;

    String APP_PATH_SD_CARD = "/.onepiconeword/";
    String APP_THUMBNAIL_PATH_SD_CARD = "photos/";

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    String missing_id = "";

    private boolean isNetworkAvailable() {
        ConnectivityManager connec = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private AdView mAdView;

    //*********************reward videos process 1***********************

    String reward_video = "";



    /*private static final String AD_UNIT_ID = "ca-app-pub-4267540560263635/6113385970";
    private static final String APP_ID = "ca-app-pub-4267540560263635~5888027170";*/

    private static final String AD_UNIT_ID = "ca-app-pub-4267540560263635/5131565422";
    private static final String APP_ID = "ca-app-pub-4267540560263635~9709843372";

    private static final long COUNTER_TIME = 10;
    private static final int GAME_OVER_REWARD = 1;

    static private int mCoinCount = 0, coinearn = 1;
    private boolean mGameOver;
    private boolean mGamePaused;
    private RewardedVideoAd mRewardedVideoAd;
    private long mTimeRemaining;
    //reward videos process 1***********************

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

    private static final String TAG = "TrivialQuest";


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


 /*   protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
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
    }*/

    public class mytheme_adapter extends BaseAdapter {
        RelativeLayout tool_lay;
        ImageView theme_img;
        TextView theme_count;

        @Override
        public int getCount() {
            return my_theme_list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.dia_theme_design, null);

            tool_lay = (RelativeLayout) view1.findViewById(R.id.tool_lay);
            theme_img = (ImageView) view1.findViewById(R.id.theme_img);
            theme_count = (TextView) view1.findViewById(R.id.theme_count);
            theme_count.setText("" + (position + 1));

         /*   String a=""+Math.sqrt(2);
            Math.cbrt(2);*/

            tool_lay.setVisibility(View.GONE);

            // tool_lay.setBackgroundColor(Color.parseColor(my_theme_tool_list.get(position)));


            theme_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int img_resource_value = getResources().getIdentifier(my_theme_list.get(position).toLowerCase(), "drawable", getPackageName());
                    sp.putInt(Game_Play_Activity.this, "theme_position", position);


                    main_lay.setBackgroundResource(img_resource_value);
                    //top.setBackgroundColor(Color.parseColor(my_theme_tool_list.get(position)));

                    dia_theame_page.dismiss();
                }
            });


            int img_resource_value = getResources().getIdentifier(my_theme_list.get(position).toLowerCase(), "drawable", getPackageName());

            Glide.with(Game_Play_Activity.this).load(img_resource_value).into(theme_img);

            //theme_img.setImageResource(img_resource_value);

            //main_lay.setBackgroundResource(img_resource_value);

            return view1;
        }
    }

    ArrayList<Integer> my_select_list = new ArrayList<>(Arrays.asList(3, 8, 9, 12, 13, 23, 27, 29, 33, 34, 43, 44));
    ArrayList<String> my_theme_list = new ArrayList<>();
    ArrayList<String> my_theme_tool_list = new ArrayList<>();
    ArrayList<String> my_theme_tool_list1 = new ArrayList<>();
    Dialog dia_theame_page;


    public void theme_dia() {
        dia_theame_page = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dia_theame_page.setContentView(R.layout.dia_theame_page);
        dia_theame_page.show();

        //my_theme_list.clear();
        my_theme_tool_list.clear();
        /*for (int i = 0; i < my_select_list.size(); i++) {
            int value = my_select_list.get(i) - 1;
            my_theme_list.add("main_bg" + value);
            my_theme_tool_list.add(my_theme_tool_list1.get(value));
        }*/

        GridView theme_grid = (GridView) dia_theame_page.findViewById(R.id.theme_grid);
        mytheme_adapter mytheme_adapter = new mytheme_adapter();
        theme_grid.setAdapter(mytheme_adapter);


    }

    public void find_id() {

       /* my_theme_list.clear();
        for (int i = 1; i < 45; i++) {
            my_theme_list.add("main_bg" + i);
        }*/

        top = (RelativeLayout) findViewById(R.id.top);

        main_lay = (RelativeLayout) findViewById(R.id.main_lay);
        card_view = (RelativeLayout) findViewById(R.id.card_view);


        qus_full_lay = (RelativeLayout) findViewById(R.id.qus_full_lay);
        coin_lay = (LinearLayout) findViewById(R.id.coin_lay);
        addview = (LinearLayout) findViewById(R.id.addview);

        ans_txt_value = (TextView) findViewById(R.id.ans_txt_value);
        coins_txt = (TextView) findViewById(R.id.coins_txt);
        ans_txt1 = (ImageView) findViewById(R.id.ans_txt1);
        ans_txt2 = (ImageView) findViewById(R.id.ans_txt2);
        ans_txt3 = (ImageView) findViewById(R.id.ans_txt3);
        ans_txt4 = (ImageView) findViewById(R.id.ans_txt4);
        ans_txt5 = (ImageView) findViewById(R.id.ans_txt5);
        ans_txt6 = (ImageView) findViewById(R.id.ans_txt6);
        ans_txt7 = (ImageView) findViewById(R.id.ans_txt7);

        qus_img1 = (ImageView) findViewById(R.id.qus_img1);
        qus_img2 = (ImageView) findViewById(R.id.qus_img2);
        qus_img3 = (ImageView) findViewById(R.id.qus_img3);
        qus_img4 = (ImageView) findViewById(R.id.qus_img4);
        qus_img5 = (ImageView) findViewById(R.id.qus_img5);
        qus_img6 = (ImageView) findViewById(R.id.qus_img6);
        qus_img7 = (ImageView) findViewById(R.id.qus_img7);
        qus_img8 = (ImageView) findViewById(R.id.qus_img8);
        qus_img9 = (ImageView) findViewById(R.id.qus_img9);
        qus_img10 = (ImageView) findViewById(R.id.qus_img10);

        btnfacebook = (ImageView) findViewById(R.id.btnfacebook);
        btnwhatsapp = (ImageView) findViewById(R.id.btnwhatsapp);

        dimen_back = (ImageView) findViewById(R.id.dimen_back);
        theme_img = (ImageView) findViewById(R.id.theme_img);
        settings_img = (ImageView) findViewById(R.id.settings_img);
        btnhint = (ImageView) findViewById(R.id.btnhint);
        btndeleteletter = (ImageView) findViewById(R.id.btndeleteletter);
        game_img = (ImageView) findViewById(R.id.game_img);
        play_coin_img = (ImageView) findViewById(R.id.play_coin_img);
        leader_board_img = (ImageView) findViewById(R.id.leader_board_img);

        theme_img.setOnClickListener(this);
        game_img.setOnClickListener(this);
        btnfacebook.setOnClickListener(this);
        btnwhatsapp.setOnClickListener(this);
        coin_lay.setOnClickListener(this);
        ans_txt1.setOnClickListener(this);
        ans_txt2.setOnClickListener(this);
        ans_txt3.setOnClickListener(this);
        ans_txt4.setOnClickListener(this);
        ans_txt5.setOnClickListener(this);
        ans_txt6.setOnClickListener(this);
        ans_txt7.setOnClickListener(this);

        qus_img1.setOnClickListener(this);
        qus_img2.setOnClickListener(this);
        qus_img3.setOnClickListener(this);
        qus_img4.setOnClickListener(this);
        qus_img5.setOnClickListener(this);
        qus_img6.setOnClickListener(this);
        qus_img7.setOnClickListener(this);
        qus_img8.setOnClickListener(this);
        qus_img9.setOnClickListener(this);
        qus_img10.setOnClickListener(this);
        settings_img.setOnClickListener(this);
        btnhint.setOnClickListener(this);
        btndeleteletter.setOnClickListener(this);
        play_coin_img.setOnClickListener(this);

        leader_board_img.setOnClickListener(this);

        tts = new TextToSpeech(Game_Play_Activity.this, this);


        ting = MediaPlayer.create(this, R.raw.ting);
        edtt = MediaPlayer.create(this, R.raw.edt);
        but = MediaPlayer.create(this, R.raw.popup);
        wro = MediaPlayer.create(this, R.raw.wro);
        coimove = MediaPlayer.create(this, R.raw.totcoisou);
        coin_collect = MediaPlayer.create(this, R.raw.coin_collect);
        coimove1 = MediaPlayer.create(this, R.raw.totcoisou);
        coin1 = MediaPlayer.create(this, R.raw.incoi);
        coin2 = MediaPlayer.create(this, R.raw.incoi);
        coin3 = MediaPlayer.create(this, R.raw.incoi);
        coin4 = MediaPlayer.create(this, R.raw.incoi);
        coin5 = MediaPlayer.create(this, R.raw.incoi);
        coin6 = MediaPlayer.create(this, R.raw.incoi);
        coin7 = MediaPlayer.create(this, R.raw.incoi);
        coin8 = MediaPlayer.create(this, R.raw.incoi);
        coin9 = MediaPlayer.create(this, R.raw.incoi);
        coin10 = MediaPlayer.create(this, R.raw.incoi);

    }

    public void reset() {
        need_permission = "";
        missing_id = "";
        enablebutton();
        editextenable();

        ans_txt4.setVisibility(View.GONE);
        ans_txt5.setVisibility(View.GONE);
        ans_txt6.setVisibility(View.GONE);
        ans_txt7.setVisibility(View.GONE);

        ans_txt1.setTag("");
        ans_txt2.setTag("");
        ans_txt3.setTag("");
        ans_txt4.setTag("");
        ans_txt5.setTag("");
        ans_txt6.setTag("");
        ans_txt7.setTag("");

       /* ans_txt1.setTextColor(Color.BLACK);
        ans_txt2.setTextColor(Color.BLACK);
        ans_txt3.setTextColor(Color.BLACK);
        ans_txt4.setTextColor(Color.BLACK);
        ans_txt5.setTextColor(Color.BLACK);
        ans_txt6.setTextColor(Color.BLACK);
        ans_txt7.setTextColor(Color.BLACK);*/

       /* ans_txt1.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_answer_bg);*/

        ans_txt1.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_qus_bg);

        ans_tag_id.set(0, 0);
        ans_tag_id.set(1, 0);
        ans_tag_id.set(2, 0);
        ans_tag_id.set(3, 0);
        ans_tag_id.set(4, 0);
        ans_tag_id.set(5, 0);
        ans_tag_id.set(6, 0);



       /* ans_txt1.setText("");
        ans_txt2.setText("");
        ans_txt3.setText("");
        ans_txt4.setText("");
        ans_txt5.setText("");
        ans_txt6.setText("");
        ans_txt7.setText("");*/
    }

    String NoData = "";

    private void new_data_insert() {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(Game_Play_Activity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        NoData = "";

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                String result = null;


                InputStream is = null;
                StringBuilder sb = null;

                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("emailid", androidId));
                if (missing_id.equals("")) {
                    nameValuePairs.add(new BasicNameValuePair("latsid", "" + last_id));
                    System.out.println("====----===ggg last_id :" + last_id);
                } else {
                    nameValuePairs.add(new BasicNameValuePair("missing_id", "" + missing_id));
                    System.out.println("====----===ggg missing_id :" + missing_id);
                }

                System.out.println("====----===ggg emailid :" + androidId);

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("https://nithra.mobi/apps/opow/apk/send_image.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in https connection" + e.toString());
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line = "0";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    System.out.println("====//" + result);
                } catch (Exception e) {
                    System.out.println("====// Exception  e" + e);
                }

                try {
                    JSONArray jArray = new JSONArray(result);
                    System.err.println("Update===" + result);
                    System.out.println("===  " + jArray.length());
                    JSONObject json_data = null;
                    //isvalid=""+jArray.length();
                    System.out.println("====----===ggg jArray.length() :" + jArray.length());
                    if (missing_id.equals("")) {
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            NoData = json_data.getString("NoData");
                            String word_name = json_data.getString("word_name");
                            String id = json_data.getString("id");

                            System.out.println("====----===ggg word_name :" + word_name);

                            ContentValues value = new ContentValues();
                            value.put("id", id);
                            value.put("words", word_name);
                            value.put("isshow", "0");
                            value.put("mtype", "" + word_name.length());
                            mydb_copy.insert("onepiconewords", null, value);
                        }
                    }


                   /*
                    if (jArray.length() > 0) {
                        json_data = jArray.getJSONObject(0);

                         Cursor rate_cursor = mydb_copy.rawQuery("select * from market_rate_table where update_date='" + update_date + "'", null);

                            System.out.println("----- hhh rate_cursor.getCount() : " + rate_cursor.getCount());
                            if (rate_cursor.getCount() == 0) {
                                ContentValues value = new ContentValues();
                                value.put("word_name", word_name);
                                value.put("isshow", 0);
                                value.put("mtype", word_name.length());
                                mydb_copy.insert("market_rate_table", null, value);
                            } else {
                                ContentValues value = new ContentValues();
                                value.put("gold_gram_rate", gold_gram_rate);
                                value.put("gold_poun_rate", gold_poun_rate);
                                value.put("silver_gram_rate", silver_gram_rate);
                                value.put("silver_KG_rate", silver_KG_rate);
                                db.update("market_rate_table", value, "update_date='" + update_date + "'", null);


                    } }*/


                } catch (JSONException e1) {
                    System.out.println("====// JSONException e1" + e1);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Utils.mProgress.dismiss();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (NoData.equals("YesData")) {
                    //checkmemory();
                    startDownload();

                    cursor = mydb_copy.rawQuery("select * from onepiconewords", null);

                    System.out.println("====----===ggg cursor.getCount() :" + cursor.getCount());
                } else {
                    final Dialog dialog1 = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                    dialog1.setContentView(R.layout.dia_no_data);
                    dialog1.setCancelable(false);
                    dialog1.show();
                    TextView dia_done_no_data = (TextView) dialog1.findViewById(R.id.dia_done_no_data);
                    dia_done_no_data.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });


                }


                // game_selection();
            }
        }.execute();

        /*if(!isFinishing()) {
            dialog.show();
        }*/
    }


    public static boolean exists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkmemory() {
        String url = "";

        url = "http://192.168.57.27/opow/apk/" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "-filename.zip";

        URL uri = null;
        try {
            uri = new URL(url);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = uri.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int length = connection.getContentLength();

        String hrSize = "";
        DecimalFormat dec = new DecimalFormat("0.00");
        double filesize = length / 1204;


        if (length > 0) {

            hrSize = dec.format(filesize).concat(" KB");
            System.out.println("KB:--" + hrSize);

        }


        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdAvailSize = (double) stat.getAvailableBlocks()
                * (double) stat.getBlockSize();

        double gigaAvailable = sdAvailSize / 1073741824;
        double megaaAvailable = sdAvailSize / (1024 * 1024);
        double kiloAvailable = sdAvailSize / 1024;
        if (sdAvailSize > 0) {

            hrSize = dec.format(kiloAvailable).concat(" KB");
            System.out.println("KB:--" + hrSize);

        }
        if (sdAvailSize > 0) {

            hrSize = dec.format(megaaAvailable).concat(" MB");
            System.out.println("MB:--" + hrSize);

        }

        if (sdAvailSize > 0) {

            hrSize = dec.format(gigaAvailable).concat(" GB");
            System.out.println("GB:--" + hrSize);

        }

        if (filesize <= kiloAvailable) {

            startDownload();
        } else {
            goappmanager();
        }


    }

    public void goappmanager() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Game_Play_Activity.this, android.R.style.Theme_Dialog);

        builder1.setMessage("No free space clean your storage");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(Intent.ACTION_MANAGE_PACKAGE_STORAGE);

                        startActivity(i);

                    }
                });
        builder1.setNegativeButton("Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void startDownload() {

        System.out.print("img down ============");

        String url = "";

        url = "https://nithra.mobi/apps/opow/apk/" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "-filename.zip";

        new DownloadFileAsync().execute(url);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading Please wait...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                // playy();
                return mProgressDialog;

            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;


                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output;

                if (daily_ok.equals("yes")) {
                    output = new FileOutputStream(fullPath + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "-filename.zip");
                } else {
                    output = new FileOutputStream(fullPath + "photo.zip");
                }

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            unpackZip();
        }
    }

    public int unpackZip() {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                InputStream is;
                ZipInputStream zis;
                try {
                    String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
                    if (daily_ok.equals("yes")) {
                        is = new FileInputStream(fullPath + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "-filename.zip");
                    } else {
                        is = new FileInputStream(fullPath + "photo.zip");
                    }
                    zis = new ZipInputStream(new BufferedInputStream(is));
                    ZipEntry ze;

                    while ((ze = zis.getNextEntry()) != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int count;

                        // zapis do souboru
                        String filename = ze.getName();
                        FileOutputStream fout = new FileOutputStream(fullPath + filename);

                        // cteni zipu a zapis
                        while ((count = zis.read(buffer)) != -1) {
                            baos.write(buffer, 0, count);
                            byte[] bytes = baos.toByteArray();
                            fout.write(bytes);
                            baos.reset();
                        }

                        fout.close();
                        zis.closeEntry();
                    }

                    zis.close();
                    File file;
                    if (daily_ok.equals("yes")) {
                        file = new File(fullPath + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "-filename.zip");
                    } else {
                        file = new File(fullPath + "photo.zip");
                    }
                    file.delete();

                    System.out.print("img unzip ============");


                } catch (IOException e) {

                    e.printStackTrace();

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                game_selection();
            }
        }.execute();


        return 1;
    }


    public void call_rating() {
        final Dialog yes_dialog = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        yes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yes_dialog.setContentView(R.layout.rate);
        final int dismiss = 0;

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
                if (isNetworkAvailable()) {
                    //Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nithra.onepiconeword"));
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=nithra.word.game.onepiconeword"));
                    startActivity(marketIntent);
                } else {
                    Toast.makeText(Game_Play_Activity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes_dialog.dismiss();
                MainActivity mainActivity = new MainActivity();
                mainActivity.reportProblem(Game_Play_Activity.this);

            }
        });

        yes_dialog.show();
    }

    String downlode_game = "";


    public void game_selection() {
        downlode_game = "";

        alphabet = "abcdefghijklmnopqrstuvwxyz";

        ans_txt1.setTag("");
        ans_txt2.setTag("");
        ans_txt3.setTag("");
        ans_txt4.setTag("");
        ans_txt5.setTag("");
        ans_txt6.setTag("");
        ans_txt7.setTag("");

        ans_txt1.setImageResource(0);
        ans_txt2.setImageResource(0);
        ans_txt3.setImageResource(0);
        ans_txt4.setImageResource(0);
        ans_txt5.setImageResource(0);
        ans_txt6.setImageResource(0);
        ans_txt7.setImageResource(0);

        settext();




      /*  Utilities.sharedPrefAdd("gp", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("fb", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("tw", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("wa", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("wa1", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("word2", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("word1", "no", Utilities.mPreferences);
        Utilities.sharedPrefAdd("de", "no", Utilities.mPreferences);
        Utilities.sharedPrefAddInt("view_hint", 0, Utilities.mPreferences);
        settings.setClickable(true);
        coco.setClickable(true);
        btnfb.setClickable(true);
        btnwhat.setClickable(true);*/

        sp.putInt(Game_Play_Activity.this, "view_hint", 0);

        if (Dailytest_ok.equals("")) {
            cursor = mydb_copy.rawQuery("select * from onepiconewords where isshow='" + 0 + "' limit 1", null);

        } else {
            cursor = mydb_copy.rawQuery("SELECT * FROM onepiconewords ORDER BY RANDOM() LIMIT 1;", null);
        }

        ans_tag_id = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));

        System.out.println("==========----game cursor.getCount() " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            reset();

            word_of_image = cursor.getString(cursor.getColumnIndex("words"));
            id1 = cursor.getInt(cursor.getColumnIndex("id"));

            System.out.println("-----====jjj word_of_image " + word_of_image);


            ans_txt_value.setText(word_of_image.toUpperCase());

            // game_img.setImageResource(getResources().getIdentifier(word_of_image.toLowerCase(), "drawable", getPackageName()));
            int img_resource_value = 0;

            if (word_of_image.equalsIgnoreCase("CLASS") || word_of_image.equalsIgnoreCase("CATCH")) {
                img_resource_value = getResources().getIdentifier(word_of_image.toLowerCase() + "_1", "drawable", getPackageName());

            } else {
                img_resource_value = getResources().getIdentifier(word_of_image.toLowerCase(), "drawable", getPackageName());

            }


            System.out.println("-----====jjj word_of_image img_resource_value " + img_resource_value);

            if (img_resource_value != 0) {
                game_img.setImageResource(img_resource_value);
            } else {
                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.onepiconeword/photos/" + word_of_image.toLowerCase() + ".webp";
                File file = new File(fullPath);
                if (file.exists()) {
                    Bitmap bitimg1 = BitmapFactory.decodeFile(fullPath);
                    Resources res = getResources();
                    BitmapDrawable bd = new BitmapDrawable(res, bitimg1);
                    game_img.setImageDrawable(bd);
                } else {
                    my_download();
                }
            }

            Random random = new Random();
            String random_value = "", collect_letter = "";
            int remain_need_letter = 10 - word_of_image.length();

            qus_letter_list.clear();

            for (int r = 0; r < word_of_image.length(); r++) {
                String s1 = String.valueOf(word_of_image.charAt(r)).toUpperCase();

                for (int d = 0; d < alphabet.length(); d++) {
                    String s2 = String.valueOf(alphabet.charAt(d));
                    if (s1.equalsIgnoreCase(s2)) {
                        alphabet = alphabet.replace(s2, "");
                    }
                }
            }
            System.out.println("-----====jjj alphabet " + alphabet);
            System.out.println("-----====jjj alphabet.length() " + alphabet.length());

            for (int j = 0; j < remain_need_letter; j++) {
                char s = alphabet.charAt(random.nextInt(alphabet.length()));
                random_value = random_value + String.valueOf(s);
            }

            collect_letter = word_of_image + random_value;
            for (int k = 0; k < collect_letter.length(); k++) {
                qus_letter_list.add(String.valueOf(collect_letter.charAt(k)).toUpperCase());
            }
            Collections.shuffle(qus_letter_list);

            System.out.println("-----====jjj qus_letter_list.size() " + qus_letter_list.size());
            System.out.println("-----====jjj qus_letter_list " + qus_letter_list);

            if (word_of_image.length() == 4) {
                ans_txt4.setVisibility(View.VISIBLE);
            }
            if (word_of_image.length() == 5) {
                ans_txt4.setVisibility(View.VISIBLE);
                ans_txt5.setVisibility(View.VISIBLE);
            }
            if (word_of_image.length() == 6) {
                ans_txt4.setVisibility(View.VISIBLE);
                ans_txt5.setVisibility(View.VISIBLE);
                ans_txt6.setVisibility(View.VISIBLE);
            }
            if (word_of_image.length() == 7) {
                ans_txt4.setVisibility(View.VISIBLE);
                ans_txt5.setVisibility(View.VISIBLE);
                ans_txt6.setVisibility(View.VISIBLE);
                ans_txt7.setVisibility(View.VISIBLE);
            }

            qus_img1.setVisibility(View.INVISIBLE);
            qus_img2.setVisibility(View.INVISIBLE);
            qus_img3.setVisibility(View.INVISIBLE);
            qus_img4.setVisibility(View.INVISIBLE);
            qus_img5.setVisibility(View.INVISIBLE);
            qus_img6.setVisibility(View.INVISIBLE);
            qus_img7.setVisibility(View.INVISIBLE);
            qus_img8.setVisibility(View.INVISIBLE);
            qus_img9.setVisibility(View.INVISIBLE);
            qus_img10.setVisibility(View.INVISIBLE);


            qus_img1.setImageResource(getResources().getIdentifier(qus_letter_list.get(0).toLowerCase(), "drawable", getPackageName()));
            qus_img2.setImageResource(getResources().getIdentifier(qus_letter_list.get(1).toLowerCase(), "drawable", getPackageName()));
            qus_img3.setImageResource(getResources().getIdentifier(qus_letter_list.get(2).toLowerCase(), "drawable", getPackageName()));
            qus_img4.setImageResource(getResources().getIdentifier(qus_letter_list.get(3).toLowerCase(), "drawable", getPackageName()));
            qus_img5.setImageResource(getResources().getIdentifier(qus_letter_list.get(4).toLowerCase(), "drawable", getPackageName()));
            qus_img6.setImageResource(getResources().getIdentifier(qus_letter_list.get(5).toLowerCase(), "drawable", getPackageName()));
            qus_img7.setImageResource(getResources().getIdentifier(qus_letter_list.get(6).toLowerCase(), "drawable", getPackageName()));
            qus_img8.setImageResource(getResources().getIdentifier(qus_letter_list.get(7).toLowerCase(), "drawable", getPackageName()));
            qus_img9.setImageResource(getResources().getIdentifier(qus_letter_list.get(8).toLowerCase(), "drawable", getPackageName()));
            qus_img10.setImageResource(getResources().getIdentifier(qus_letter_list.get(9).toLowerCase(), "drawable", getPackageName()));

            qus_img1.setTag(qus_letter_list.get(0).toUpperCase());
            qus_img2.setTag(qus_letter_list.get(1).toUpperCase());
            qus_img3.setTag(qus_letter_list.get(2).toUpperCase());
            qus_img4.setTag(qus_letter_list.get(3).toUpperCase());
            qus_img5.setTag(qus_letter_list.get(4).toUpperCase());
            qus_img6.setTag(qus_letter_list.get(5).toUpperCase());
            qus_img7.setTag(qus_letter_list.get(6).toUpperCase());
            qus_img8.setTag(qus_letter_list.get(7).toUpperCase());
            qus_img9.setTag(qus_letter_list.get(8).toUpperCase());
            qus_img10.setTag(qus_letter_list.get(9).toUpperCase());


            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (sp.getString(Game_Play_Activity.this, "delete").equals("yes")) {
                        deleteburron();
                    } else {
                        buttonanimation();
                    }


                }
            }, 400);

        } else {

            cursor = mydb_copy.rawQuery("select * from onepiconewords", null);
            cursor.moveToLast();
            last_id = cursor.getInt(cursor.getColumnIndex("id"));
            my_download();


        }
    }

    public void achivement_open() {

        System.out.println("====-----====---== id1 : " + id1);
        System.out.println("====-----====---== mGoogleApiClient.isConnected() : " + mGoogleApiClient.isConnected());

        if (sp.getString(getApplicationContext(), "solve_50").equals("")) {
            if (id1 >= 50) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_50));
                    sp.putString(getApplicationContext(), "solve_50", "solve_50");
                }
            }
        }

        if (sp.getString(getApplicationContext(), "solve_100").equals("")) {
            if (id1 >= 100) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_100));
                    sp.putString(getApplicationContext(), "solve_100", "solve_100");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_200").equals("")) {
            if (id1 >= 200) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_200));
                    sp.putString(getApplicationContext(), "solve_200", "solve_200");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_500").equals("")) {
            if (id1 >= 500) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_500));
                    sp.putString(getApplicationContext(), "solve_500", "solve_500");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_750").equals("")) {
            if (id1 >= 750) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_750));
                    sp.putString(getApplicationContext(), "solve_750", "solve_750");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_1000").equals("")) {
            if (id1 >= 1000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_1000));
                    sp.putString(getApplicationContext(), "solve_1000", "solve_1000");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_2000").equals("")) {
            if (id1 >= 2000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_2000));
                    sp.putString(getApplicationContext(), "solve_2000", "solve_2000");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_3000").equals("")) {
            if (id1 >= 3000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_3000));
                    sp.putString(getApplicationContext(), "solve_3000", "solve_3000");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_4000").equals("")) {
            if (id1 >= 4000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_4000));
                    sp.putString(getApplicationContext(), "solve_4000", "solve_4000");

                }
            }
        }
        if (sp.getString(getApplicationContext(), "solve_5000").equals("")) {
            if (id1 >= 5000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.solve_5000));
                    sp.putString(getApplicationContext(), "solve_5000", "solve_5000");

                }
            }
        }

        int current_point = sp.getInt(Game_Play_Activity.this, "My_leader_point");
        if (sp.getString(getApplicationContext(), "points_1000").equals("")) {
            if (current_point >= 1000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.points_1000));
                    sp.putString(getApplicationContext(), "points_1000", "points_1000");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "points_2000").equals("")) {
            if (current_point >= 2000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.points_2000));
                    sp.putString(getApplicationContext(), "points_2000", "points_2000");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "points_5000").equals("")) {
            if (current_point >= 5000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.points_5000));
                    sp.putString(getApplicationContext(), "points_5000", "points_5000");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "points_10000").equals("")) {
            if (current_point >= 10000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.points_10000));
                    sp.putString(getApplicationContext(), "points_10000", "points_10000");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "points_20000").equals("")) {
            if (current_point >= 20000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.points_20000));
                    sp.putString(getApplicationContext(), "points_20000", "points_20000");

                }
            }
        }

        int total_wh = sp.getInt(Game_Play_Activity.this, "total_wh");

        if (sp.getString(getApplicationContext(), "wh_75").equals("")) {
            if (total_wh >= 75) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_75));
                    sp.putString(getApplicationContext(), "wh_75", "wh_75");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "wh_75").equals("")) {
            if (total_wh >= 75) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_75));
                    sp.putString(getApplicationContext(), "wh_75", "wh_75");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "wh_150").equals("")) {
            if (total_wh >= 150) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_150));
                    sp.putString(getApplicationContext(), "wh_150", "wh_150");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "wh_300").equals("")) {
            if (total_wh >= 300) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_300));
                    sp.putString(getApplicationContext(), "wh_300", "wh_300");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "wh_500").equals("")) {
            if (total_wh >= 500) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_500));
                    sp.putString(getApplicationContext(), "wh_500", "wh_500");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "wh_1000").equals("")) {
            if (total_wh >= 1000) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.wh_1000));
                    sp.putString(getApplicationContext(), "wh_1000", "wh_1000");

                }
            }
        }

        int Daily_finisher = sp.getInt(Game_Play_Activity.this, "Daily_finisher");

        if (sp.getString(getApplicationContext(), "DT_25").equals("")) {
            if (Daily_finisher >= 25) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.DT_25));
                    sp.putString(getApplicationContext(), "DT_25", "DT_25");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "DT_50").equals("")) {
            if (Daily_finisher >= 50) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.DT_50));
                    sp.putString(getApplicationContext(), "DT_50", "DT_50");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "DT_100").equals("")) {
            if (Daily_finisher >= 100) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.DT_100));
                    sp.putString(getApplicationContext(), "DT_100", "DT_100");

                }
            }
        }

        int continue_wh = sp.getInt(Game_Play_Activity.this, "continue_wh");

        if (sp.getString(getApplicationContext(), "CWH_50").equals("")) {
            if (continue_wh >= 50) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.CWH_50));
                    sp.putString(getApplicationContext(), "CWH_50", "CWH_50");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "CWH_75").equals("")) {
            if (continue_wh >= 75) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.CWH_75));
                    sp.putString(getApplicationContext(), "CWH_75", "CWH_75");

                }
            }
        }

        if (sp.getString(getApplicationContext(), "CWH_150").equals("")) {
            if (continue_wh >= 150) {
                if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.CWH_150));
                    sp.putString(getApplicationContext(), "CWH_150", "CWH_150");

                }
            }
        }


    }

    public void my_download() {
        if (isNetworkAvailable()) {
            downlode_game = "downlode_game";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    share_conetnt = "";
                    permission();

                } else {
                    new_data_insert();
                }
            } else {
                new_data_insert();
            }
        } else {
            no_internet_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            no_internet_dia.setContentView(R.layout.dia_no_internet);
            no_internet_dia.setCancelable(false);
            no_internet_dia.show();

            ImageView nointernet_close_img = (ImageView) no_internet_dia.findViewById(R.id.nointernet_close_img);

            nointernet_close_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            });
            TextView dia_done_nointernet = (TextView) no_internet_dia.findViewById(R.id.dia_done_nointernet);
            dia_done_nointernet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });

        }
    }

    public void permission() {
        need_permission = "";
        final Dialog premission_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        premission_dia.setContentView(R.layout.dia_permission);
        premission_dia.setCancelable(false);
        if (sp.getString(Game_Play_Activity.this, "PERMISSION_DENIED").equals("")) {
            premission_dia.show();
        } else {
            if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(Game_Play_Activity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 150);
            }
        }
        ImageView access_close_img = (ImageView) premission_dia.findViewById(R.id.access_close_img);
        TextView dia_done_access = (TextView) premission_dia.findViewById(R.id.dia_done_access);
        TextView access_content_txt1 = (TextView) premission_dia.findViewById(R.id.access_content_txt1);

        if (downlode_game.equals("downlode_game")) {
            access_content_txt1.setText("Access storage permission to download the pictures and play more games");
            access_close_img.setVisibility(View.INVISIBLE);
        }

        access_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premission_dia.dismiss();
            }
        });

        dia_done_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premission_dia.dismiss();
                if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(Game_Play_Activity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 150);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 150:
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (downlode_game.equals("downlode_game")) {
                        game_selection();
                    }

                    // game_selection();

                } else {
                  /*  if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!showRationale) {
                            final Intent i = new Intent();
                            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            i.addCategory(Intent.CATEGORY_DEFAULT);
                            i.setData(Uri.parse("package:nithra.pdfmaker"));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(i);
                        } else if (android.Manifest.permission.CAMERA.equals(permissions[0])) {

                        }
                    }
                    return;*/

                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        // user rejected the permission
                        boolean showRationale = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                        }

                        System.out.println("PERMISSION_DENIED : " + showRationale);
                        if (!showRationale) {

                            sp.putString(Game_Play_Activity.this, "PERMISSION_DENIED", "yes");

                            final Dialog premission_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                            premission_dia.setContentView(R.layout.dia_permission);
                            premission_dia.setCancelable(false);
                            premission_dia.show();

                            ImageView access_close_img = (ImageView) premission_dia.findViewById(R.id.access_close_img);
                            TextView access_content_txt1 = (TextView) premission_dia.findViewById(R.id.access_content_txt1);
                            TextView dia_done_access = (TextView) premission_dia.findViewById(R.id.dia_done_access);
                            if (downlode_game.equals("downlode_game")) {
                                access_content_txt1.setText("To download the pictures and play more games, Enable storage permission in settings menu.");
                            } else {
                                access_content_txt1.setText("To ask help from your friends, Enable storage permission in settings menu.");
                            }
                            access_close_img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    premission_dia.dismiss();
                                    if (!share_conetnt.equals("share_conetnt")) {
                                        Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            });

                            dia_done_access.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    need_permission = "need_permission";
                                    premission_dia.dismiss();
                                    Intent intent = new Intent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                    intent.setData(uri);
                                    getApplicationContext().startActivity(intent);
                                }
                            });


                           /* android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(Game_Play_Activity.this).create();
                            alertDialog.setMessage("Get access to the settings and allow yourself to start playing.");
                            alertDialog.setCancelable(false);
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "Setting",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            need_permission = "need_permission";
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                            intent.setData(uri);
                                            getApplicationContext().startActivity(intent);
                                        }
                                    });

                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "Exit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();


                                            if (!share_conetnt.equals("share_conetnt")) {
                                                Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                                                finish();
                                                startActivity(intent);
                                            }


                                        }
                                    });


                            alertDialog.show();*/

                        }

                        /*else if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(Game_Play_Activity.this).create();
                            alertDialog.setMessage("Grant permission to go ahead and experience the real Challenge.");
                            alertDialog.setCancelable(false);
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                                                ActivityCompat.requestPermissions(Game_Play_Activity.this,
                                                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 150);


                                            }
                                        }
                                    });

                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "Later",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            if (!share_conetnt.equals("share_conetnt")) {
                                                Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                                                finish();
                                                startActivity(intent);
                                            }


                                        }
                                    });


                            alertDialog.show();
                        }*/
                    }
                    return;
                }

                break;
        }
    }

    public void buttonanimation() {


        final Animation bounce1 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce2 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce3 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce4 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce5 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce6 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce7 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce8 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce9 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce10 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce11 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation bounce12 = AnimationUtils.loadAnimation(this, R.anim.bounce);


        // if (Utilities.mPreferences.getString("coinsound", "").equals("yes")) {
        if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
            if (coimove != null) {
                coimove.start();
            }
        }

        qus_img1.startAnimation(bounce1);
        qus_img1.setVisibility(View.VISIBLE);

        qus_img2.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img2.startAnimation(bounce2);
                qus_img2.setVisibility(View.VISIBLE);

            }
        }, 100);
        qus_img3.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img3.startAnimation(bounce3);
                qus_img3.setVisibility(View.VISIBLE);

            }
        }, 200);

        qus_img4.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img4.startAnimation(bounce4);
                qus_img4.setVisibility(View.VISIBLE);

            }
        }, 300);
        qus_img5.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img5.startAnimation(bounce5);
                qus_img5.setVisibility(View.VISIBLE);

            }
        }, 400);


        btnhint.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnhint.startAnimation(bounce7);
                btnhint.setVisibility(View.VISIBLE);

            }
        }, 500);


        btndeleteletter.postDelayed(new Runnable() {
            @Override
            public void run() {
                btndeleteletter.startAnimation(bounce8);
                btndeleteletter.setVisibility(View.VISIBLE);

            }
        }, 600);

        qus_img8.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img8.startAnimation(bounce9);
                qus_img8.setVisibility(View.VISIBLE);

            }
        }, 900);

        qus_img9.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img9.startAnimation(bounce10);
                qus_img9.setVisibility(View.VISIBLE);

            }
        }, 800);

        qus_img10.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img10.startAnimation(bounce11);
                qus_img10.setVisibility(View.VISIBLE);

            }
        }, 700);

        qus_img7.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img7.startAnimation(bounce12);
                qus_img7.setVisibility(View.VISIBLE);

            }
        }, 1000);
        qus_img6.postDelayed(new Runnable() {
            @Override
            public void run() {
                qus_img6.startAnimation(bounce6);
                qus_img6.setVisibility(View.VISIBLE);
            }
        }, 1100);

    }


    String word_of_image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__play_1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mydb_copy = this.openOrCreateDatabase("findtheword", MODE_PRIVATE, null);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            Dailytest_ok = getIntent().getStringExtra("Dailytest_ok");
            System.out.println("Dailytest_ok===: " + Dailytest_ok);
        }


        find_id();
        settext();

        my_theme_list.add("sundar1");
        my_theme_list.add("sundar2");
        my_theme_list.add("sundar3");
        my_theme_list.add("sundar4");
        my_theme_list.add("sundar5");
        my_theme_list.add("sundar6");
        my_theme_list.add("sundar7");
        my_theme_list.add("sundar8");
        my_theme_list.add("sundar9");
        my_theme_list.add("sundar10");

        int theme_position = sp.getInt(Game_Play_Activity.this, "theme_position");
        int img_resource_value = getResources().getIdentifier(my_theme_list.get(theme_position).toLowerCase(), "drawable", getPackageName());
        main_lay.setBackgroundResource(img_resource_value);

        my_theme_tool_list1.clear();

        my_theme_tool_list1.add("#494a4c");
        my_theme_tool_list1.add("#756baf");
        my_theme_tool_list1.add("#524836");
        my_theme_tool_list1.add("#53362d");
        my_theme_tool_list1.add("#51d22e");
        my_theme_tool_list1.add("#3e74b9");
        my_theme_tool_list1.add("#4c342d");
        my_theme_tool_list1.add("#2e4145");
        my_theme_tool_list1.add("#4c4a35");
        my_theme_tool_list1.add("#5672b6");
        my_theme_tool_list1.add("#3c3632");
        my_theme_tool_list1.add("#76767a");
        my_theme_tool_list1.add("#5e566f");
        my_theme_tool_list1.add("#8b7d5e");
        my_theme_tool_list1.add("#8c6148");
        my_theme_tool_list1.add("#874f49");
        my_theme_tool_list1.add("#4c5463");
        my_theme_tool_list1.add("#805948");
        my_theme_tool_list1.add("#546d77");
        my_theme_tool_list1.add("#7f7b59");
        my_theme_tool_list1.add("#52546a");
        my_theme_tool_list1.add("#675c54");
        my_theme_tool_list1.add("#5a4a30");
        my_theme_tool_list1.add("#573a2e");
        my_theme_tool_list1.add("#572c2c");
        my_theme_tool_list1.add("#552a3e");
        my_theme_tool_list1.add("#1b454b");
        my_theme_tool_list1.add("#1f4433");
        my_theme_tool_list1.add("#3f4831");
        my_theme_tool_list1.add("#472d38");
        my_theme_tool_list1.add("#1f4435");
        my_theme_tool_list1.add("#3f2d39");
        my_theme_tool_list1.add("#00afef");
        my_theme_tool_list1.add("#8e784d");
        my_theme_tool_list1.add("#8d654a");
        my_theme_tool_list1.add("#8d4f44");
        my_theme_tool_list1.add("#8d4f68");
        my_theme_tool_list1.add("#367382");
        my_theme_tool_list1.add("#427254");
        my_theme_tool_list1.add("#737b53");
        my_theme_tool_list1.add("#80576d");
        my_theme_tool_list1.add("#596f66");
        my_theme_tool_list1.add("#754d69");
        my_theme_tool_list1.add("#4e4869");


        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(Game_Play_Activity.this)
                .addConnectionCallbacks(Game_Play_Activity.this)
                .addOnConnectionFailedListener(Game_Play_Activity.this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        // ...

        /*mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);*/

        //*********************reward videos process 2***********************
        MobileAds.initialize(Game_Play_Activity.this, APP_ID);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(Game_Play_Activity.this);
        mRewardedVideoAd.setRewardedVideoAdListener(Game_Play_Activity.this);

//reward videos process 2***********************


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String today_date = mdformat.format(calendar.getTime());

        System.out.println("====--==--==today_date : " + today_date);

       /* Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");*/


        if (sp.getString(Game_Play_Activity.this, "first_time_LB").equals("")) {
            sp.putString(Game_Play_Activity.this, "first_time_LB", "first_time_LB");
            leader_board_connect("first_time");
        } else {
            if (!sp.getString(Game_Play_Activity.this, "dd_coind").equals(today_date)
                    & !sp.getString(Game_Play_Activity.this, "install_date").equals(today_date)) {
                sp.putString(Game_Play_Activity.this, "dd_coind", today_date);
                lastearn("dily", 10);
            } else {
                game_selection();
            }

        }
    }

    public boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    public void helpshare(final String pack) {


        btnfacebook.setVisibility(View.INVISIBLE);
        btnwhatsapp.setVisibility(View.INVISIBLE);
        addview.setVisibility(View.INVISIBLE);

    /*    btntwiter.setVisibility(View.INVISIBLE);
        btngplus.setVisibility(View.INVISIBLE);*/


        top.setVisibility(View.INVISIBLE);

        final TextView s1 = (TextView) findViewById(R.id.appname);
        final TextView s2 = (TextView) findViewById(R.id.solve1);
        s1.setVisibility(View.VISIBLE);

        s2.setVisibility(View.VISIBLE);
        final RelativeLayout view = (RelativeLayout) findViewById(R.id.main);

        int theme_position = sp.getInt(Game_Play_Activity.this, "theme_position");
        int img_resource_value = getResources().getIdentifier(my_theme_list.get(theme_position).toLowerCase(), "drawable", getPackageName());
        view.setBackgroundResource(img_resource_value);
        // view.setBackgroundColor(Color.parseColor("#ffffff"));
        final boolean appinstalled = appInstalledOrNot(pack);

        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (appinstalled) {

                    Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    view.draw(canvas);


                    String root = Environment.getExternalStorageDirectory().toString();
                    File mydir = new File(root + "/Nithra/1pic1word");
                    mydir.mkdirs();
                    String fname = "Image-1pic1word.jpg";
                    final File file = new File(mydir, fname);

                    if (file.exists()) {
                        file.delete();
                    }

                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();

                        if (file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            Intent share = new Intent();
                            share.setAction(Intent.ACTION_SEND);
                            share.setPackage(pack);
                            share.setType("image/*");
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            share.putExtra(Intent.EXTRA_TEXT, "Hey, I'm stuck...can you help me guess this word? https://goo.gl/8fxkWo");
                            share.putExtra(Intent.EXTRA_SUBJECT,
                                    "1Pic1Word");
                            startActivity(Intent.createChooser(share, "Share Card Using"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        }, 300);


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                btnfacebook.setVisibility(View.VISIBLE);
                btnwhatsapp.setVisibility(View.VISIBLE);
         /*       btntwiter.setVisibility(View.VISIBLE);
                btngplus.setVisibility(View.VISIBLE);*/
                top.setVisibility(View.VISIBLE);
                s1.setVisibility(View.GONE);
                s2.setVisibility(View.GONE);
                view.setBackgroundColor(Color.TRANSPARENT);

            }
        }, 1000);


    }

    public InterstitialAd interstitialAd_cate;

    public void loadInterstialAdd_cate_exit() {
        interstitialAd_cate = new InterstitialAd(Game_Play_Activity.this);
        interstitialAd_cate.setAdUnitId("ca-app-pub-4267540560263635/1526324920");
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd_cate.loadAd(adRequest);

        interstitialAd_cate.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO Auto-generated method stub
                super.onAdFailedToLoad(errorCode);
                loadInterstialAdd_cate_exit();
            }

            @Override
            public void onAdClosed() {
                // TODO Auto-generated method stub
                super.onAdClosed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        ads_handler.removeCallbacksAndMessages(null);
        reward_video = "";
        //******reward video pocess :4
        pauseGame();
        mRewardedVideoAd.pause(Game_Play_Activity.this);
        //reward video pocess :4 ************//

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!sp.getString(Game_Play_Activity.this, "Log_in").equals("")) {
            if (isNetworkAvailable() && !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    }


    public void ads_call() {
        ads_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.load_addFromMain1(addview);
                ads_call();

            }
        }, 10000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //******reward video pocess :4
        loadRewardedVideoAd();

        if (!mGameOver && mGamePaused) {
            resumeGame();
        }
        mRewardedVideoAd.resume(Game_Play_Activity.this);
        //reward video pocess :4 ************//

        if (sp.getString(Game_Play_Activity.this, "PointsPurchase").equals("")) {

            load_add_score(Game_Play_Activity.this);
            System.out.println("load add rect adView3 " + adView3);
            if (adView3 != null) {
                MainActivity.load_addFromMain1(addview);
                ads_call();
            } else {
                MainActivity.load_add1(addview, Game_Play_Activity.this);
            }
            loadInterstialAdd_cate_exit();

        } else {
            addview.setVisibility(View.GONE);
            int puc_coin = sp.getInt(Game_Play_Activity.this, "puc_coin");
            if (puc_coin > 0) {
                sp.putInt(Game_Play_Activity.this, "puc_coin", 0);
                lastearn("Coin_purchase", puc_coin);
            }

        }

        if (no_internet_dia != null) {
            if (isNetworkAvailable()) {
                no_internet_dia.dismiss();
                game_selection();
                no_internet_dia = null;
            }
        }

        if (need_permission.equals("need_permission")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    permission();

                } else {
                    if (!share_conetnt.equals("share_conetnt")) {
                        game_selection();
                    }
                    share_conetnt = "";

                }
            }
        }

    }

    public static AdView adView_score;
    public static AdRequest adRequestrec_score;
    static int addloded_score = 0;


    public static void load_add_score(final Context context) {
        adView_score = new AdView(context);
        adView_score.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView_score.setAdUnitId("ca-app-pub-4267540560263635/1683275009");
        adRequestrec_score = new AdRequest.Builder().build();
        System.out.println("load add rect");

        addloded_score = 0;
        adView_score.setAdListener(new AdListener() {
            public void onAdLoaded() {
                System.out.println("===----------======ads_score onAdLoaded() : ");
                addloded_score = 1;

                // load_addFrom_score(score_ad_lay);
                super.onAdLoaded();
            }

            public void onAdFailedToLoad(int errorcode) {
                System.out.println("===----------======ads_score errorcode : " + errorcode);
                load_add_score(context);
            }
        });
        adView_score.loadAd(adRequestrec_score);
        if (adView_score != null) {
            ViewGroup parentViewGroup = (ViewGroup) adView_score.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

    public static void load_addFrom_score(LinearLayout add_banner) {

        System.out.println("===----------======ads_score Load main rect addloded_score : " + addloded_score);
        try {
            if (adView_score != null) {
                ViewGroup parentViewGroup = (ViewGroup) adView_score.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
            if (addloded_score == 1) {
                add_banner.setVisibility(View.VISIBLE);
                add_banner.removeAllViews();
                add_banner.addView(adView_score);
            }
        } catch (Exception e) {

        }

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exit_fun();

    }

    public void exit_fun() {

        final Dialog exit_game_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
        exit_game_dia.setContentView(R.layout.dia_game_exit);
        exit_game_dia.setCancelable(false);
        exit_game_dia.show();

        TextView EXIT_done_letter = (TextView) exit_game_dia.findViewById(R.id.EXIT_done_letter);
        TextView EXIT_cancel_letter = (TextView) exit_game_dia.findViewById(R.id.EXIT_cancel_letter);

        EXIT_done_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dailytest_ok = "";
                if (sp.getString(Game_Play_Activity.this, "PointsPurchase").equals("")) {
                    if (interstitialAd_cate != null) {
                        if (interstitialAd_cate.isLoaded()) {
                            interstitialAd_cate.show();
                            interstitialAd_cate.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {

                                    Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                                    finish();
                                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                                    startActivity(intent);
                                }

                            });

                        } else {
                            Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                            finish();
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                        finish();
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    startActivity(intent);
                }
            }
        });
        EXIT_cancel_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continue_game == 10) {
                    continue_game = 0;
                    game_selection();
                }
                exit_game_dia.dismiss();
            }
        });

    }

    public void snackbar(String str) {
        // RelativeLayout view1 = (RelativeLayout) findViewById(R.id.main);
    /*CardView view1 =(CardView) findViewById(R.id.card_view);
    Snackbar snackbar = Snackbar.make(view1, ""+str, Snackbar.LENGTH_SHORT);
    snackbar.setActionTextColor(Color.RED);
    View sbView = snackbar.getView();
    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)sbView.getLayoutParams();
    params.gravity = Gravity.TOP;
    sbView.setLayoutParams(params);
    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(Color.YELLOW);
    textView.setTypeface(ttf_1);
    textView.setTextSize(17);
    textView.setGravity(Gravity.CENTER);
    snackbar.show();*/

        Toast.makeText(Game_Play_Activity.this, "" + str, Toast.LENGTH_SHORT).show();
    }


    public void fullview_img() {
        final Dialog fullview_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        fullview_dia.setContentView(R.layout.dia_fullview);
        fullview_dia.show();

        ImageView fullview_close_img = (ImageView) fullview_dia.findViewById(R.id.fullview_close_img);
        ImageView fullview_img = (ImageView) fullview_dia.findViewById(R.id.fullview_img);

        int img_resource_value = getResources().getIdentifier(word_of_image.toLowerCase(), "drawable", getPackageName());

        System.out.println("-----====jjj word_of_image img_resource_value " + img_resource_value);

        if (img_resource_value != 0) {
            fullview_img.setImageResource(img_resource_value);
        } else {
            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.onepiconeword/photos/" + word_of_image.toLowerCase() + ".webp";
            File file = new File(fullPath);
            if (file.exists()) {
                Bitmap bitimg1 = BitmapFactory.decodeFile(fullPath);
                Resources res = getResources();
                BitmapDrawable bd = new BitmapDrawable(res, bitimg1);
                fullview_img.setImageDrawable(bd);
            }
        }

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        fullview_img.startAnimation(anim1);


        fullview_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullview_dia.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnfacebook: {

                share_conetnt = "share_conetnt";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        permission();

                    } else {

                        if (isNetworkAvailable()) {
                            final boolean appinstalled = appInstalledOrNot("com.twitter.android");
                            if (appinstalled) {
                                helpshare("com.twitter.android");
                            } else {
                                snackbar("Twitter app not installed");
                            }
                        } else {
                            snackbar("Hey buddy, connect to the network");
                        }
                    }
                } else {
                    if (isNetworkAvailable()) {
                        final boolean appinstalled = appInstalledOrNot("com.twitter.android");
                        if (appinstalled) {
                            helpshare("com.twitter.android");
                        } else {
                            snackbar("Twitter app not installed");
                        }
                    } else {
                        snackbar("Hey buddy, connect to the network");
                    }
                }

            }
            break;
            case R.id.btnwhatsapp: {

                share_conetnt = "share_conetnt";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if ((ContextCompat.checkSelfPermission(Game_Play_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        permission();

                    } else {

                        if (isNetworkAvailable()) {
                            final boolean appinstalled = appInstalledOrNot("com.whatsapp");
                            if (appinstalled) {
                                helpshare("com.whatsapp");
                            } else {
                                // Toast.makeText(getApplicationContext(), "This app not install", Toast.LENGTH_SHORT).show();
                                snackbar("Whatsapp app not installed");
                            }
                        } else {
                            //Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                            snackbar("Hey buddy, connect to the network");
                        }
                    }
                } else {
                    if (isNetworkAvailable()) {
                        final boolean appinstalled = appInstalledOrNot("com.whatsapp");
                        if (appinstalled) {
                            helpshare("com.whatsapp");
                        } else {
                            // Toast.makeText(getApplicationContext(), "This app not install", Toast.LENGTH_SHORT).show();
                            snackbar("Whatsapp app not installed");
                        }
                    } else {
                        //Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                        snackbar("Hey buddy, connect to the network");
                    }
                }
            }
            break;
            case R.id.game_img: {
                fullview_img();
            }
            break;
            case R.id.ans_txt1: {
                ans_douch_pos = 0;
                touchedt(ans_txt1);
            }
            break;
            case R.id.ans_txt2: {
                ans_douch_pos = 1;
                touchedt(ans_txt2);
            }
            break;
            case R.id.ans_txt3: {
                ans_douch_pos = 2;
                touchedt(ans_txt3);
            }
            break;
            case R.id.ans_txt4: {
                ans_douch_pos = 3;
                touchedt(ans_txt4);
            }
            break;
            case R.id.ans_txt5: {
                ans_douch_pos = 4;
                touchedt(ans_txt5);
            }
            break;
            case R.id.ans_txt6: {
                ans_douch_pos = 5;
                touchedt(ans_txt6);
            }
            break;
            case R.id.ans_txt7: {
                ans_douch_pos = 6;
                touchedt(ans_txt7);
            }
            break;
            case R.id.qus_img1: {
                go_tag_value = 1;
                String imageName = (String) qus_img1.getTag();
                clickbtn(qus_img1);

            }
            break;
            case R.id.qus_img2: {
                go_tag_value = 2;
                clickbtn(qus_img2);
            }
            break;
            case R.id.qus_img3: {
                go_tag_value = 3;
                clickbtn(qus_img3);
            }
            break;
            case R.id.qus_img4: {
                go_tag_value = 4;
                clickbtn(qus_img4);
            }
            break;
            case R.id.qus_img5: {
                go_tag_value = 5;
                clickbtn(qus_img5);
            }
            break;
            case R.id.qus_img6: {
                go_tag_value = 6;
                clickbtn(qus_img6);
            }
            break;
            case R.id.qus_img7: {
                go_tag_value = 7;
                clickbtn(qus_img7);
            }
            break;
            case R.id.qus_img8: {
                go_tag_value = 8;
                clickbtn(qus_img8);
            }
            break;
            case R.id.qus_img9: {
                go_tag_value = 9;
                clickbtn(qus_img9);
            }
            break;
            case R.id.qus_img10: {
                go_tag_value = 10;
                clickbtn(qus_img10);
            }
            break;
            case R.id.btnhint: {
                hint_dialog();
            }
            break;
            case R.id.settings_img: {
                setting_dia();
            }
            break;
            case R.id.leader_board_img: {
                leader_board_connect("noti_first_time");
            }
            break;
            case R.id.theme_img: {

                theme_dia();

              /* if (bg < 3) {
                    bg += 1;
                } else {
                    bg = 1;
                }

                if (bg == 1) {
                    main_lay.setBackgroundResource(R.drawable.m_bg_one);
                } else if (bg == 2) {
                    main_lay.setBackgroundResource(R.drawable.m_bg_two);
                } else if (bg == 3) {
                    main_lay.setBackgroundResource(R.drawable.m_bg_three);
                }*/


            /*    if (bg < 2) {
                    bg += 1;
                } else {
                    bg = 1;
                }

                if (bg == 1) {
                    card_view.setBackgroundResource(R.drawable.rectangle_yellow);
                    dimen_back.setVisibility(View.GONE);

                } else if (bg == 2) {
                    dimen_back.setBackgroundResource(R.drawable.diamond_bg);
                    game_img.setPadding(20, 20, 20, 20);
                    card_view.setBackgroundResource(0);
                    dimen_back.setVisibility(View.VISIBLE);
                    }*/


            }


            break;
            case R.id.btndeleteletter: {
                if (sp.getString(Game_Play_Activity.this, "delete").equals("")) {
                    delete_letter();
                } else {
                    Toast.makeText(this, "Delete the wrong letter already used", Toast.LENGTH_SHORT).show();
                }

            }
            break;
            case R.id.play_coin_img: {
                earncoin();
            }
            break;
            case R.id.coin_lay: {
                earncoin();
            }
            break;

        }

    }

    int bg = 1;
    int diament = 1;

    public void deleteburron() {

        ans_txt1.setTag("");
        ans_txt2.setTag("");
        ans_txt3.setTag("");
        ans_txt4.setTag("");
        ans_txt5.setTag("");
        ans_txt6.setTag("");
        ans_txt7.setTag("");

        /*ans_txt1.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_answer_bg); */

        ans_txt1.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_qus_bg);

        ans_txt1.setImageResource(0);
        ans_txt2.setImageResource(0);
        ans_txt3.setImageResource(0);
        ans_txt4.setImageResource(0);
        ans_txt5.setImageResource(0);
        ans_txt6.setImageResource(0);
        ans_txt7.setImageResource(0);

        ans_tag_id.set(0, 0);
        ans_tag_id.set(1, 0);
        ans_tag_id.set(2, 0);
        ans_tag_id.set(3, 0);
        ans_tag_id.set(4, 0);
        ans_tag_id.set(5, 0);
        ans_tag_id.set(6, 0);

     /*   ans_txt1.setText("");
        ans_txt2.setText("");
        ans_txt3.setText("");
        ans_txt4.setText("");
        ans_txt5.setText("");
        ans_txt6.setText("");
        ans_txt7.setText("");

        ans_txt1.setTextColor(Color.BLACK);
        ans_txt2.setTextColor(Color.BLACK);
        ans_txt3.setTextColor(Color.BLACK);
        ans_txt4.setTextColor(Color.BLACK);
        ans_txt5.setTextColor(Color.BLACK);
        ans_txt6.setTextColor(Color.BLACK);
        ans_txt7.setTextColor(Color.BLACK);*/


        if (!word_of_image.toUpperCase().contains(qus_img1.getTag().toString().toUpperCase())) {
            qus_img1.setVisibility(View.INVISIBLE);
        } else {
            qus_img1.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img2.getTag().toString().toUpperCase())) {
            qus_img2.setVisibility(View.INVISIBLE);
        } else {
            qus_img2.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img3.getTag().toString().toUpperCase())) {
            qus_img3.setVisibility(View.INVISIBLE);
        } else {
            qus_img3.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img4.getTag().toString().toUpperCase())) {
            qus_img4.setVisibility(View.INVISIBLE);
        } else {
            qus_img4.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img5.getTag().toString().toUpperCase())) {
            qus_img5.setVisibility(View.INVISIBLE);
        } else {
            qus_img5.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img6.getTag().toString().toUpperCase())) {
            qus_img6.setVisibility(View.INVISIBLE);
        } else {
            qus_img6.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img7.getTag().toString().toUpperCase())) {
            qus_img7.setVisibility(View.INVISIBLE);
        } else {
            qus_img7.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img8.getTag().toString().toUpperCase())) {
            qus_img8.setVisibility(View.INVISIBLE);
        } else {
            qus_img8.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img9.getTag().toString().toUpperCase())) {
            qus_img9.setVisibility(View.INVISIBLE);
        } else {
            qus_img9.setVisibility(View.VISIBLE);
        }
        if (!word_of_image.toUpperCase().contains(qus_img10.getTag().toString().toUpperCase())) {
            qus_img10.setVisibility(View.INVISIBLE);
        } else {
            qus_img10.setVisibility(View.VISIBLE);
        }


    }

    public void hint_dialog() {
        final Dialog hint_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        hint_dia.setContentView(R.layout.dia_hint);
        hint_dia.show();

        ImageView hint_close_img = (ImageView) hint_dia.findViewById(R.id.hint_close_img);
        RelativeLayout word_sollution_lay = (RelativeLayout) hint_dia.findViewById(R.id.word_sollution_lay);
        RelativeLayout word_hint_lay = (RelativeLayout) hint_dia.findViewById(R.id.word_hint_lay);
        RelativeLayout letter_hint_lay = (RelativeLayout) hint_dia.findViewById(R.id.letter_hint_lay);
        RelativeLayout random_hint_lay = (RelativeLayout) hint_dia.findViewById(R.id.random_hint_lay);

        hint_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_dia.dismiss();
            }
        });

        word_sollution_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor bb = mydb_copy.rawQuery("select * from scoretable  ", null);
                bb.moveToFirst();
                int coin_total = bb.getInt(bb.getColumnIndex("coins"));
                if (coin_total >= 150) {
                    if (sp.getString(Game_Play_Activity.this, "SOLUTION_never").equals("")) {
                        hint_dia.dismiss();
                        hint_never_dialog("SOLUTION");
                    } else {
                        sp.putString(Game_Play_Activity.this, "SOLUTION_never", "yes");
                        hint_dia.dismiss();
                        minuscoin(150);
                        sollution();
                        sp.putInt(Game_Play_Activity.this, "nohint", 0);
                        sp.putInt(Game_Play_Activity.this, "view_hint", 1);
                    }

                } else {
                    hint_dia.dismiss();
                    earncoin();
                }


            }
        });

        word_hint_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString(Game_Play_Activity.this, "WORD_PRONOUNCE_click").equals("")) {

                    System.out.println("--------jjj WORD_PRONOUNCE " + sp.getString(Game_Play_Activity.this, "WORD_PRONOUNCE"));
                    System.out.println("--------jjj id1 " + id1);

                    if (!sp.getString(Game_Play_Activity.this, "WORD_PRONOUNCE").equals("" + id1)) {
                        Cursor bb = mydb_copy.rawQuery("select * from scoretable  ", null);
                        bb.moveToFirst();
                        int coin_total = bb.getInt(bb.getColumnIndex("coins"));

                        if (coin_total >= 25) {
                            if (sp.getString(Game_Play_Activity.this, "WORD PRONOUNCE_never").equals("")) {
                                hint_never_dialog("WORD PRONOUNCE");
                            } else {
                                sp.putString(Game_Play_Activity.this, "WORD_PRONOUNCE", "" + id1);
                                minuscoin(25);
                                // tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                                wordspeach();
                                sp.putInt(Game_Play_Activity.this, "nohint", 0);
                                sp.putInt(Game_Play_Activity.this, "view_hint", 1);
                            }

                        } else {
                            hint_dia.dismiss();
                            earncoin();
                        }
                    } else {
                        wordspeach();
                        // tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });
        letter_hint_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click").equals("")) {
                    if (!sp.getString(Game_Play_Activity.this, "LETTER_PRONOUNCE").equals("" + id1)) {
                        Cursor bb = mydb_copy.rawQuery("select * from scoretable  ", null);
                        bb.moveToFirst();
                        int coin_total = bb.getInt(bb.getColumnIndex("coins"));

                        if (coin_total >= 75) {
                            if (sp.getString(Game_Play_Activity.this, "LETTER PRONOUNCE_never").equals("")) {
                                hint_never_dialog("LETTER PRONOUNCE");
                            } else {
                                sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE", "" + id1);
                                sp.putInt(Game_Play_Activity.this, "nohint", 0);
                                sp.putInt(Game_Play_Activity.this, "view_hint", 1);
                                letterspeach();
                                minuscoin(75);
                            }

                        } else {
                            hint_dia.dismiss();
                            earncoin();
                        }
                    } else {
                        letterspeach();
                    }
                }
            }
        });
        random_hint_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor bb = mydb_copy.rawQuery("select * from scoretable  ", null);
                bb.moveToFirst();
                int coin_total = bb.getInt(bb.getColumnIndex("coins"));

                if (coin_total >= 50) {
                    if (sp.getString(Game_Play_Activity.this, "RANDOM LETTER_never").equals("")) {
                        hint_never_dialog("RANDOM LETTER");
                    } else {
                        random_hint();
                    }

                } else {
                    earncoin();
                }
                hint_dia.dismiss();


            }
        });
    }

    public void leader_board_connect(final String first_time) {
        final Dialog leaderboard_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        leaderboard_dia.setContentView(R.layout.dia_leaderboard);
        leaderboard_dia.show();

        LinearLayout yes_no_lay = (LinearLayout) leaderboard_dia.findViewById(R.id.yes_no_lay);
        LinearLayout leader_achive_lay = (LinearLayout) leaderboard_dia.findViewById(R.id.leader_achive_lay);
        RelativeLayout dia_leader_board_lay = (RelativeLayout) leaderboard_dia.findViewById(R.id.dia_leader_board_lay);
        RelativeLayout dia_achivement_lay = (RelativeLayout) leaderboard_dia.findViewById(R.id.dia_achivement_lay);

        ImageView leader_close_img = (ImageView) leaderboard_dia.findViewById(R.id.leader_close_img);
        ImageView demo_icon = (ImageView) leaderboard_dia.findViewById(R.id.demo_icon);
        TextView leaderboard_content_txt = (TextView) leaderboard_dia.findViewById(R.id.leaderboard_content_txt);
        TextView dia_done_leaderboard = (TextView) leaderboard_dia.findViewById(R.id.dia_done_leaderboard);
        TextView dia_cancel_leaderboard = (TextView) leaderboard_dia.findViewById(R.id.dia_cancel_leaderboard);

        if (mGoogleApiClient.isConnected()) {
            //leaderboard_content_txt.setText("You are connected to LeaderBoard\n\nDo you want to open Points or Achivements ?");
            leader_achive_lay.setVisibility(View.VISIBLE);
            leader_close_img.setVisibility(View.VISIBLE);
            leaderboard_content_txt.setVisibility(View.GONE);
            demo_icon.setVisibility(View.GONE);
            yes_no_lay.setVisibility(View.GONE);
        }

        leader_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderboard_dia.dismiss();
            }
        });

        dia_leader_board_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leader_board_show();
            }
        });
        dia_achivement_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                achivement_show();
            }
        });


        dia_done_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGoogleApiClient.isConnected()) {
                    if (isNetworkAvailable()) {
                        mGoogleApiClient.connect();
                        sp.putString(Game_Play_Activity.this, "Log_in", "Log_in");
                        leaderboard_dia.dismiss();
                    } else {
                        Toast.makeText(Game_Play_Activity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        leaderboard_dia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (first_time.equals("first_time"))
                    game_selection();
            }
        });


        dia_cancel_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderboard_dia.dismiss();
            }
        });


    }

    public void achivement_show() {
        if (isNetworkAvailable()) {
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
            Toast.makeText(Game_Play_Activity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
        }
    }


    public void leader_board_show() {
        final int my_score = sp.getInt(Game_Play_Activity.this, "My_leader_point");
        if (isNetworkAvailable()) {
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
            Toast.makeText(Game_Play_Activity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
        }
    }

    public void setting_dia() {
        final Dialog setting_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        setting_dia.setContentView(R.layout.dia_setting);
        setting_dia.show();

        RelativeLayout pronounce_lay = (RelativeLayout) setting_dia.findViewById(R.id.pronounce_lay);
        RelativeLayout sound_lay = (RelativeLayout) setting_dia.findViewById(R.id.sound_lay);
        final ImageView pronun_toogle = (ImageView) setting_dia.findViewById(R.id.pronun_toogle);
        final ImageView sound_toogle = (ImageView) setting_dia.findViewById(R.id.sound_toogle);
        final ImageView setting_close_img = (ImageView) setting_dia.findViewById(R.id.setting_close_img);
        if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
            pronun_toogle.setBackgroundResource(R.drawable.setting_on);
        }

        if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
            sound_toogle.setBackgroundResource(R.drawable.setting_on);
        }

        setting_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_dia.dismiss();
            }
        });


        pronounce_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                    sp.putString(Game_Play_Activity.this, "wordsound", "no");
                    pronun_toogle.setBackgroundResource(R.drawable.setting_off);
                } else {
                    sp.putString(Game_Play_Activity.this, "wordsound", "yes");
                    pronun_toogle.setBackgroundResource(R.drawable.setting_on);
                }

            }
        });
        sound_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                    sp.putString(Game_Play_Activity.this, "coinsound", "no");
                    sound_toogle.setBackgroundResource(R.drawable.setting_off);
                } else {
                    sp.putString(Game_Play_Activity.this, "coinsound", "yes");
                    sound_toogle.setBackgroundResource(R.drawable.setting_on);
                }
            }
        });

    }

    public void hint_never_dialog(final String never_title) {
        final Dialog hint_never_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        hint_never_dia.setContentView(R.layout.dia_never_hint);
        hint_never_dia.show();

        TextView never_title_txt = (TextView) hint_never_dia.findViewById(R.id.never_title_txt);
        final TextView never_content_txt = (TextView) hint_never_dia.findViewById(R.id.never_content_txt);
        TextView dia_never_done_letter = (TextView) hint_never_dia.findViewById(R.id.dia_never_done_letter);
        TextView dia_never_cancel_letter = (TextView) hint_never_dia.findViewById(R.id.dia_never_cancel_letter);
        ImageView never_close_img = (ImageView) hint_never_dia.findViewById(R.id.never_close_img);
        final CheckBox never_check = (CheckBox) hint_never_dia.findViewById(R.id.never_check);

        never_close_img.setVisibility(View.GONE);

        never_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_never_dia.dismiss();
            }
        });

       /* never_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sp.putString(Game_Play_Activity.this, never_title + "_never", "yes");
                    // Utilities.sharedPrefAdd("n2", "yes", Utilities.mPreferences);
                } else {
                    sp.putString(Game_Play_Activity.this, never_title, "");
                    // Utilities.sharedPrefAdd("n2", "", Utilities.mPreferences);
                }
            }
        });*/

        never_title_txt.setText(never_title);

        if (never_title.equals("SOLUTION")) {
            never_content_txt.setText("By using this hint 150 coins will be deducted");
        } else if (never_title.equals("WORD PRONOUNCE")) {
            never_content_txt.setText("By using this hint 25 coins will be deducted");
        } else if (never_title.equals("LETTER PRONOUNCE")) {
            never_content_txt.setText("By using this hint 75 coins will be deducted");
        } else if (never_title.equals("RANDOM LETTER")) {
            never_content_txt.setText("By using this hint 50 coins will be deducted");
        }

        dia_never_cancel_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_never_dia.dismiss();
            }
        });

        dia_never_done_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (never_check.isChecked()) {
                    sp.putString(Game_Play_Activity.this, never_title + "_never", "yes");
                }

                if (never_title.equals("SOLUTION")) {
                    minuscoin(150);
                    hint_never_dia.dismiss();
                    sollution();
                    sp.putInt(Game_Play_Activity.this, "nohint", 0);
                    sp.putInt(Game_Play_Activity.this, "view_hint", 1);


                } else if (never_title.equals("WORD PRONOUNCE")) {
                    minuscoin(25);
                    wordspeach();
                    sp.putString(Game_Play_Activity.this, "WORD_PRONOUNCE", "" + id1);
                    sp.putInt(Game_Play_Activity.this, "nohint", 0);
                    sp.putInt(Game_Play_Activity.this, "view_hint", 1);
                } else if (never_title.equals("LETTER PRONOUNCE")) {
                    sp.putInt(Game_Play_Activity.this, "nohint", 0);
                    sp.putInt(Game_Play_Activity.this, "view_hint", 1);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE", "" + id1);
                    letterspeach();
                    minuscoin(75);
                       /* if (isNetworkAvailable()) {
                            updatehints("LetterSpeach");
                        }*/
                } else if (never_title.equals("RANDOM LETTER")) {
                    random_hint();
                }

                hint_never_dia.dismiss();
            }
        });

    }

    public void sollution() {

        sp.putString(Game_Play_Activity.this, "solution_done", "solution_done");


        ans_txt1.setTag("" + word_of_image.toLowerCase().charAt(0));
        ans_txt1.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt1.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(0), "drawable", getPackageName()));

        ans_txt2.setTag("" + word_of_image.toLowerCase().charAt(1));
        ans_txt2.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt2.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(1), "drawable", getPackageName()));

        ans_txt3.setTag("" + word_of_image.toLowerCase().charAt(2));
        ans_txt3.setBackgroundResource(R.drawable.txt_qus_bg);
        ans_txt3.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(2), "drawable", getPackageName()));

        if (word_of_image.length() == 4) {
            ans_txt4.setTag("" + word_of_image.toLowerCase().charAt(3));
            ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt4.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(3), "drawable", getPackageName()));
        }
        if (word_of_image.length() == 5) {
            ans_txt4.setTag("" + word_of_image.toLowerCase().charAt(3));
            ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt4.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(3), "drawable", getPackageName()));
            ans_txt5.setTag("" + word_of_image.toLowerCase().charAt(4));
            ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt5.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(4), "drawable", getPackageName()));
        }
        if (word_of_image.length() == 6) {
            ans_txt4.setTag("" + word_of_image.toLowerCase().charAt(3));
            ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt4.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(3), "drawable", getPackageName()));
            ans_txt5.setTag("" + word_of_image.toLowerCase().charAt(4));
            ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt5.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(4), "drawable", getPackageName()));
            ans_txt6.setTag("" + word_of_image.toLowerCase().charAt(5));
            ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt6.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(5), "drawable", getPackageName()));
        }
        if (word_of_image.length() == 7) {

            ans_txt4.setTag("" + word_of_image.toLowerCase().charAt(3));
            ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt4.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(3), "drawable", getPackageName()));
            ans_txt5.setTag("" + word_of_image.toLowerCase().charAt(4));
            ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt5.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(4), "drawable", getPackageName()));
            ans_txt6.setTag("" + word_of_image.toLowerCase().charAt(5));
            ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt6.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(5), "drawable", getPackageName()));
            ans_txt7.setTag("" + word_of_image.toLowerCase().charAt(6));
            ans_txt7.setBackgroundResource(R.drawable.txt_qus_bg);
            ans_txt7.setImageResource(getResources().getIdentifier("" + word_of_image.toLowerCase().charAt(6), "drawable", getPackageName()));
        }
        validate();
    }

    public void wordspeach() {
        sp.putString(Game_Play_Activity.this, "WORD_PRONOUNCE_click", "not_click");
        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sp.putString(Game_Play_Activity.this, "WORD_PRONOUNCE_click", "");
            }
        }, 1500);
    }

    public void letterspeach() {
        sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "not_click");

        if (word_of_image.length() == 3) {
            Handler h0 = new Handler();
            h0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(0), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 500);
            //  t1.speak(a1[0], TextToSpeech.QUEUE_FLUSH, null);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1000);
            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(2), TextToSpeech.QUEUE_FLUSH, null);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "");
                }
            }, 1500);
        }
        if (word_of_image.length() == 4) {
            Handler h0 = new Handler();
            h0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(0), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 500);
            //  t1.speak(a1[0], TextToSpeech.QUEUE_FLUSH, null);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1000);
            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(2), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1500);
            Handler h3 = new Handler();
            h3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(3), TextToSpeech.QUEUE_FLUSH, null);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "");
                }
            }, 2000);
        }
        if (word_of_image.length() == 5) {
            Handler h0 = new Handler();
            h0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(0), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 500);
            //  t1.speak(a1[0], TextToSpeech.QUEUE_FLUSH, null);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1000);
            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(2), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1500);
            Handler h3 = new Handler();
            h3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(3), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 2000);
            Handler h4 = new Handler();
            h4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(4), TextToSpeech.QUEUE_FLUSH, null);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "");
                }
            }, 2500);

        }
        if (word_of_image.length() == 6) {
            Handler h0 = new Handler();
            h0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(0), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 500);
            //  t1.speak(a1[0], TextToSpeech.QUEUE_FLUSH, null);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1000);
            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(2), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1500);
            Handler h3 = new Handler();
            h3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(3), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 2000);
            Handler h4 = new Handler();
            h4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(4), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 2500);
            Handler h5 = new Handler();
            h5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(5), TextToSpeech.QUEUE_FLUSH, null);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "");
                }
            }, 3000);

        }
        if (word_of_image.length() == 7) {
            Handler h0 = new Handler();
            h0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(0), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 500);
            //  t1.speak(a1[0], TextToSpeech.QUEUE_FLUSH, null);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1000);
            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(2), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1500);
            Handler h3 = new Handler();
            h3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(3), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 2000);
            Handler h4 = new Handler();
            h4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(4), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 2500);
            Handler h5 = new Handler();
            h5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(5), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 3000);
            Handler h6 = new Handler();
            h6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tts.speak("" + word_of_image.charAt(6), TextToSpeech.QUEUE_FLUSH, null);
                    sp.putString(Game_Play_Activity.this, "LETTER_PRONOUNCE_click", "");
                }
            }, 3500);
        }
    }

    public void random_hint() {

        String word = word_of_image.toUpperCase();


        System.out.println("======random_hint ans_txt1.getTag().toString()) : " + ans_txt1.getTag().toString());
        System.out.println("======random_hint ans_txt2.getTag().toString()) : " + ans_txt2.getTag().toString());
        System.out.println("======random_hint ans_txt2.getTag().toString()) : " + ans_txt2.getTag().toString());


        if (word.length() == 3) {
            if ((ans_tag_id.get(0) != 0) && (ans_tag_id.get(1) != 0) && (ans_tag_id.get(2) != 0)) {
                // if ((ans_txt1.getText().toString().length() != 0) && (ans_txt2.getText().toString().length() != 0) && (ans_txt3.getText().toString().length() != 0)) {
                if ((word.contains(ans_txt1.getTag().toString())) && (word.contains(ans_txt2.getTag().toString())) && (word.contains(ans_txt3.getTag().toString()))) {
                    Toast.makeText(Game_Play_Activity.this, "Rearrange the letter correctly", Toast.LENGTH_SHORT).show();
                } else {

                    three();
                }
            } else {
                three();
            }
        }
        if (word.length() == 4) {
            if ((ans_tag_id.get(0) != 0) && (ans_tag_id.get(1) != 0) && (ans_tag_id.get(2) != 0) && (ans_tag_id.get(3) != 0)) {
                if ((word.contains(ans_txt1.getTag().toString())) && (word.contains(ans_txt2.getTag().toString())) && (word.contains(ans_txt3.getTag().toString())) &&
                        (word.contains(ans_txt4.getTag().toString()))) {
                    Toast.makeText(Game_Play_Activity.this, "Rearrange the letter correctly", Toast.LENGTH_SHORT).show();
                } else {
                    four();
                }

            } else {
                four();
            }
        }
        if (word.length() == 5) {
            if ((ans_tag_id.get(0) != 0) && (ans_tag_id.get(1) != 0) && (ans_tag_id.get(2) != 0) && (ans_tag_id.get(3) != 0)
                    && (ans_tag_id.get(4) != 0)) {
                if ((word.contains(ans_txt1.getTag().toString())) && (word.contains(ans_txt2.getTag().toString())) && (word.contains(ans_txt3.getTag().toString())) && (word.contains(ans_txt4.getTag().toString())) && (word.contains(ans_txt5.getTag().toString()))) {
                    Toast.makeText(Game_Play_Activity.this, "Rearrange the letter correctly", Toast.LENGTH_SHORT).show();
                } else {
                    five();
                }
            } else {
                five();
            }
        }

        if (word.length() == 6) {
            if ((ans_tag_id.get(0) != 0) && (ans_tag_id.get(1) != 0) && (ans_tag_id.get(2) != 0) && (ans_tag_id.get(3) != 0)
                    && (ans_tag_id.get(4) != 0) && (ans_tag_id.get(5) != 0)) {
                if ((word.contains(ans_txt1.getTag().toString())) && (word.contains(ans_txt2.getTag().toString())) && (word.contains(ans_txt3.getTag().toString())) &&
                        (word.contains(ans_txt4.getTag().toString())) && (word.contains(ans_txt5.getTag().toString())) && (word.contains(ans_txt6.getTag().toString()))) {
                    Toast.makeText(Game_Play_Activity.this, "Rearrange the letter correctly", Toast.LENGTH_SHORT).show();
                } else {
                    six();
                }
            } else {
                six();
            }
        }

        if (word.length() == 7) {
            if ((ans_tag_id.get(0) != 0) && (ans_tag_id.get(1) != 0) && (ans_tag_id.get(2) != 0) && (ans_tag_id.get(3) != 0)
                    && (ans_tag_id.get(4) != 0) && (ans_tag_id.get(5) != 0) && (ans_tag_id.get(6) != 0)) {
                if ((word.contains(ans_txt1.getTag().toString())) && (word.contains(ans_txt2.getTag().toString())) && (word.contains(ans_txt3.getTag().toString())) &&
                        (word.contains(ans_txt4.getTag().toString())) && (word.contains(ans_txt5.getTag().toString())) && (word.contains(ans_txt6.getTag().toString())) && (word.contains(ans_txt7.getTag().toString()))) {
                    Toast.makeText(Game_Play_Activity.this, "Rearrange the letter correctly", Toast.LENGTH_SHORT).show();
                } else {
                    seven();

                }
            } else {
                seven();
            }
        }

    }


    public String shuffle(String cards) {
        // TODO Auto-generated method stub

        if (cards.length() <= 1)
            return cards;

        int split = cards.length() / 2;

        String temp1 = shuffle(cards.substring(0, split));
        String temp2 = shuffle(cards.substring(split));

        if (Math.random() > 0.5)
            return temp1 + temp2;
        else
            return temp2 + temp1;
    }


    private void three() {
        String i = "";

        String s = String.valueOf(word_of_image.charAt(0)).toUpperCase();
        if (!ans_txt1.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "1";
        }
        if (!ans_txt2.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "2";
        }
        if (!ans_txt3.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "3";
        }
        System.out.println("iiii " + i);
        i = shuffle(i);
        if (String.valueOf(i.charAt(0)).equals("1")) {
            ranletter(ans_txt1, 1);
        }
        if (String.valueOf(i.charAt(0)).equals("2")) {
            ranletter(ans_txt2, 2);
        }
        if (String.valueOf(i.charAt(0)).equals("3")) {
            ranletter(ans_txt3, 3);
        }
    }

    private void four() {
        String i = "";
        String s = String.valueOf(word_of_image.charAt(0)).toUpperCase();
        if (!ans_txt1.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "1";
        }
        if (!ans_txt2.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "2";
        }
        if (!ans_txt3.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "3";
        }
        if (!ans_txt4.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "4";
        }

        System.out.println("iiii " + i);
        i = shuffle(i);
        if (String.valueOf(i.charAt(0)).equals("1")) {
            ranletter(ans_txt1, 1);
        }
        if (String.valueOf(i.charAt(0)).equals("2")) {
            ranletter(ans_txt2, 2);
        }
        if (String.valueOf(i.charAt(0)).equals("3")) {
            ranletter(ans_txt3, 3);
        }
        if (String.valueOf(i.charAt(0)).equals("4")) {
            ranletter(ans_txt4, 4);
        }
    }

    private void five() {
        String i = "";
        String s = String.valueOf(word_of_image.charAt(0)).toUpperCase();
        if (!ans_txt1.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "1";
        }
        if (!ans_txt2.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "2";
        }
        if (!ans_txt3.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "3";
        }
        if (!ans_txt4.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "4";
        }
        if (!ans_txt5.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "5";
        }

        System.out.println("iiii " + i);
        i = shuffle(i);
        if (String.valueOf(i.charAt(0)).equals("1")) {
            ranletter(ans_txt1, 1);
        }
        if (String.valueOf(i.charAt(0)).equals("2")) {
            ranletter(ans_txt2, 2);
        }
        if (String.valueOf(i.charAt(0)).equals("3")) {
            ranletter(ans_txt3, 3);
        }
        if (String.valueOf(i.charAt(0)).equals("4")) {
            ranletter(ans_txt4, 4);
        }
        if (String.valueOf(i.charAt(0)).equals("5")) {
            ranletter(ans_txt5, 5);
        }
    }

    private void six() {
        String i = "";
        String s = String.valueOf(word_of_image.charAt(0)).toUpperCase();
        if (!ans_txt1.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "1";
        }
        if (!ans_txt2.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "2";
        }
        if (!ans_txt3.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "3";
        }
        if (!ans_txt4.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "4";
        }
        if (!ans_txt5.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "5";
        }
        if (!ans_txt6.getTag().toString().equalsIgnoreCase(s)) {

            i = i + "6";
        }
        System.out.println("iiii " + i);
        i = shuffle(i);

        System.out.println("iiii " + i);
        if (String.valueOf(i.charAt(0)).equals("1")) {
            ranletter(ans_txt1, 1);
        }
        if (String.valueOf(i.charAt(0)).equals("2")) {
            ranletter(ans_txt2, 2);
        }
        if (String.valueOf(i.charAt(0)).equals("3")) {
            ranletter(ans_txt3, 3);
        }
        if (String.valueOf(i.charAt(0)).equals("4")) {
            ranletter(ans_txt4, 4);
        }
        if (String.valueOf(i.charAt(0)).equals("5")) {
            ranletter(ans_txt5, 5);
        }
        if (String.valueOf(i.charAt(0)).equals("6")) {
            ranletter(ans_txt6, 6);
        }
    }

    private void seven() {


        String i = "";
        if (!ans_txt1.getTag().toString().equalsIgnoreCase(String.valueOf(word_of_image.charAt(0)))) {

            i = i + "1";
        }
        if (!ans_txt2.getTag().toString().equals(String.valueOf(word_of_image.charAt(1)))) {

            i = i + "2";
        }
        if (!ans_txt3.getTag().toString().equals(String.valueOf(word_of_image.charAt(2)))) {

            i = i + "3";
        }
        if (!ans_txt4.getTag().toString().equals(String.valueOf(word_of_image.charAt(3)))) {

            i = i + "4";
        }
        if (!ans_txt5.getTag().toString().equals(String.valueOf(word_of_image.charAt(4)))) {

            i = i + "5";
        }
        if (!ans_txt6.getTag().toString().equals(String.valueOf(word_of_image.charAt(5)))) {

            i = i + "6";
        }
        if (!ans_txt7.getTag().toString().equals(String.valueOf(word_of_image.charAt(6)))) {

            i = i + "7";
        }
        System.out.println("iiiaaaa " + word_of_image.charAt(1));
        System.out.println("iiii " + i);
        i = shuffle(i);

        if (String.valueOf(i.charAt(0)).equals("1")) {
            ranletter(ans_txt1, 1);
        }
        if (String.valueOf(i.charAt(0)).equals("2")) {
            ranletter(ans_txt2, 2);
        }
        if (String.valueOf(i.charAt(0)).equals("3")) {
            ranletter(ans_txt3, 3);
        }
        if (String.valueOf(i.charAt(0)).equals("4")) {
            ranletter(ans_txt4, 4);
        }
        if (String.valueOf(i.charAt(0)).equals("5")) {
            ranletter(ans_txt5, 5);
        }
        if (String.valueOf(i.charAt(0)).equals("6")) {
            ranletter(ans_txt6, 6);
        }
        if (String.valueOf(i.charAt(0)).equals("7")) {
            ranletter(ans_txt7, 7);
        }
    }

    public void ranletter(final ImageView edt, int num) {
        minuscoin(50);
        sp.putInt(Game_Play_Activity.this, "nohint", 0);
        sp.putInt(Game_Play_Activity.this, "view_hint", 1);

        num = num - 1;
        String word = word_of_image.toUpperCase();
        String s = "" + word.charAt(num);

       /* ans_txt1.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt2.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt3.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt4.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt5.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt6.setBackgroundResource(R.drawable.txt_answer_bg);
        ans_txt7.setBackgroundResource(R.drawable.txt_answer_bg);*/

        /*ans_txt1.setTextColor(Color.BLACK);
        ans_txt2.setTextColor(Color.BLACK);
        ans_txt3.setTextColor(Color.BLACK);
        ans_txt4.setTextColor(Color.BLACK);
        ans_txt5.setTextColor(Color.BLACK);
        ans_txt6.setTextColor(Color.BLACK);
        ans_txt7.setTextColor(Color.BLACK);*/


        if (!edt.getTag().equals("")) {

            if ((s.equals(qus_img1.getTag().toString()) && qus_img1.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img2.getTag().toString()) && qus_img2.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img3.getTag().toString()) && qus_img3.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img4.getTag().toString()) && qus_img4.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img5.getTag().toString()) && qus_img5.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img6.getTag().toString()) && qus_img6.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img7.getTag().toString()) && qus_img7.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img8.getTag().toString()) && qus_img8.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img9.getTag().toString()) && qus_img9.getVisibility() == View.VISIBLE) ||
                    (s.equals(qus_img10.getTag().toString()) && qus_img10.getVisibility() == View.VISIBLE)) {

                if (edt.getTag().toString().equals(qus_img1.getTag().toString()) && qus_img1.getVisibility() == View.INVISIBLE) {
                    qus_img1.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img2.getTag().toString()) && qus_img2.getVisibility() == View.INVISIBLE) {
                    qus_img2.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img3.getTag().toString()) && qus_img3.getVisibility() == View.INVISIBLE) {
                    qus_img3.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img4.getTag().toString()) && qus_img4.getVisibility() == View.INVISIBLE) {
                    qus_img4.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img5.getTag().toString()) && qus_img5.getVisibility() == View.INVISIBLE) {
                    qus_img5.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img6.getTag().toString()) && qus_img6.getVisibility() == View.INVISIBLE) {
                    qus_img6.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img7.getTag().toString()) && qus_img7.getVisibility() == View.INVISIBLE) {
                    qus_img7.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img8.getTag().toString()) && qus_img8.getVisibility() == View.INVISIBLE) {
                    qus_img8.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img9.getTag().toString()) && qus_img9.getVisibility() == View.INVISIBLE) {
                    qus_img9.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img10.getTag().toString()) && qus_img10.getVisibility() == View.INVISIBLE) {
                    qus_img10.setVisibility(View.VISIBLE);
                }

            } else {
                System.out.println("iiii else");

                if (edt.getTag().toString().toString().equals(qus_img1.getTag().toString()) && qus_img1.getVisibility() == View.INVISIBLE) {
                    qus_img1.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img2.getTag().toString()) && qus_img2.getVisibility() == View.INVISIBLE) {
                    qus_img2.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img3.getTag().toString()) && qus_img3.getVisibility() == View.INVISIBLE) {
                    qus_img3.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img4.getTag().toString()) && qus_img4.getVisibility() == View.INVISIBLE) {
                    qus_img4.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img5.getTag().toString()) && qus_img5.getVisibility() == View.INVISIBLE) {
                    qus_img5.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img6.getTag().toString()) && qus_img6.getVisibility() == View.INVISIBLE) {
                    qus_img6.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img7.getTag().toString()) && qus_img7.getVisibility() == View.INVISIBLE) {
                    qus_img7.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img8.getTag().toString()) && qus_img8.getVisibility() == View.INVISIBLE) {
                    qus_img8.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img9.getTag().toString()) && qus_img9.getVisibility() == View.INVISIBLE) {
                    qus_img9.setVisibility(View.VISIBLE);
                } else if (edt.getTag().toString().toString().equals(qus_img10.getTag().toString()) && qus_img10.getVisibility() == View.INVISIBLE) {
                    qus_img10.setVisibility(View.VISIBLE);
                }
            }
        }

        System.out.println("======validate ranletter ans_txt1.getTag().toString().length(): " + ans_txt1.getTag().toString().length());
        System.out.println("======validate ranletter ans_txt2.getTag().toString().length(): " + ans_txt2.getTag().toString().length());
        System.out.println("======validate ranletter ans_txt3.getTag().toString().length(): " + ans_txt3.getTag().toString().length());


        /*if ((ans_txt1.getTag().toString().equals(s)) && (ans_tag_id.get(0) != 123) && (!ans_txt1.getTag().equals(""))) {
            // ans_txt1.setText("");
            ans_txt1.setTag("");
            ans_txt1.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt2.getTag().toString().equals(s)) && (ans_tag_id.get(1) != 123) && (!ans_txt2.getTag().equals(""))) {
            //ans_txt2.setText("");
            ans_txt2.setTag("");
            ans_txt2.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt3.getTag().toString().equals(s)) && (ans_tag_id.get(2) != 123) && (!ans_txt3.getTag().equals(""))) {
            // ans_txt3.setText("");
            ans_txt3.setTag("");
            ans_txt3.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt4.getTag().toString().equals(s)) && (ans_tag_id.get(3) != 123) && (!ans_txt4.getTag().equals(""))) {
            // ans_txt4.setText("");
            ans_txt4.setTag("");
            ans_txt4.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt5.getTag().toString().equals(s)) && (ans_tag_id.get(4) != 123) && (!ans_txt5.getTag().equals(""))) {
            // ans_txt5.setText("");
            ans_txt5.setTag("");
            ans_txt5.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt6.getTag().toString().equals(s)) && (ans_tag_id.get(5) != 123) && (!ans_txt6.getTag().equals(""))) {
            //ans_txt6.setText("");
            ans_txt6.setTag("");
            ans_txt6.setBackgroundResource(R.drawable.txt_answer_bg);
        } else if ((ans_txt7.getTag().toString().equals(s)) && (ans_tag_id.get(6) != 123) && (!ans_txt7.getTag().equals(""))) {
            //ans_txt7.setText("");
            ans_txt7.setTag("");
            ans_txt7.setBackgroundResource(R.drawable.txt_answer_bg);
        }*/

        System.out.println("======validate ranletterafter ans_txt1.getTag().toString().length(): " + ans_txt1.getTag().toString().length());
        System.out.println("======validate ranletterafter ans_txt2.getTag().toString().length(): " + ans_txt2.getTag().toString().length());
        System.out.println("======validate ranletterafter ans_txt3.getTag().toString().length(): " + ans_txt3.getTag().toString().length());


        // edt.setText("");
        //edt.setTag("123");
       /* edt.setTextColor(Color.BLACK);
        edt.setText(s);*/

        edt.setBackgroundResource(R.drawable.txt_qus_bg);
        edt.setTag(s);
        edt.setImageResource(getResources().getIdentifier(s.toLowerCase(), "drawable", getPackageName()));
        ans_tag_id.set(num, 123);


        if (s.contains(qus_img1.getTag().toString()) && qus_img1.getVisibility() == View.VISIBLE) {
            qus_img1.setVisibility(View.INVISIBLE);
            System.out.println("kkk  1");
        } else if (s.contains(qus_img2.getTag().toString()) && qus_img2.getVisibility() == View.VISIBLE) {
            qus_img2.setVisibility(View.INVISIBLE);
            System.out.println("kkk  2");
        } else if (s.contains(qus_img3.getTag().toString()) && qus_img3.getVisibility() == View.VISIBLE) {
            qus_img3.setVisibility(View.INVISIBLE);
            System.out.println("kkk  3");
        } else if (s.contains(qus_img4.getTag().toString()) && qus_img4.getVisibility() == View.VISIBLE) {
            qus_img4.setVisibility(View.INVISIBLE);
            System.out.println("kkk  4");
        } else if (s.contains(qus_img5.getTag().toString()) && qus_img5.getVisibility() == View.VISIBLE) {
            qus_img5.setVisibility(View.INVISIBLE);
            System.out.println("kkk  5");
        } else if (s.contains(qus_img6.getTag().toString()) && qus_img6.getVisibility() == View.VISIBLE) {
            qus_img6.setVisibility(View.INVISIBLE);
            System.out.println("kkk  6");
        } else if (s.contains(qus_img7.getTag().toString()) && qus_img7.getVisibility() == View.VISIBLE) {
            qus_img7.setVisibility(View.INVISIBLE);
            System.out.println("kkk  7");
        } else if (s.contains(qus_img8.getTag().toString()) && qus_img8.getVisibility() == View.VISIBLE) {
            qus_img8.setVisibility(View.INVISIBLE);
            System.out.println("kkk  8");
        } else if (s.contains(qus_img9.getTag().toString()) && qus_img9.getVisibility() == View.VISIBLE) {
            qus_img9.setVisibility(View.INVISIBLE);
            System.out.println("kkk  9");
        } else if (s.contains(qus_img10.getTag().toString()) && qus_img10.getVisibility() == View.VISIBLE) {
            qus_img10.setVisibility(View.INVISIBLE);
            System.out.println("kkk  10");

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                validate();
            }
        }, 200);
    }


    public void delete_letter() {
        final Dialog delete_letter_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        delete_letter_dia.setContentView(R.layout.dia_delete_letter);
        delete_letter_dia.show();

        ImageView delete_close_img = (ImageView) delete_letter_dia.findViewById(R.id.delete_close_img);
        TextView dia_done_letter = (TextView) delete_letter_dia.findViewById(R.id.dia_done_letter);
        TextView dia_cancel_letter = (TextView) delete_letter_dia.findViewById(R.id.dia_cancel_letter);


        delete_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_letter_dia.dismiss();
            }
        });
        dia_done_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Utilities.sharedPrefAdd("de", "yes", Utilities.mPreferences);

                Cursor bb = mydb_copy.rawQuery("select * from scoretable  ", null);
                bb.moveToFirst();
                int coin_total = bb.getInt(bb.getColumnIndex("coins"));

                if (coin_total >= 100) {
                    sp.putString(Game_Play_Activity.this, "de", "yes");

                    deleteburron();


                    sp.putInt(Game_Play_Activity.this, "id", id1);
                    sp.putString(Game_Play_Activity.this, "delete", "yes");

                    minuscoin(100);

                    if (isNetworkAvailable()) {
                        // updatehints("DeleteLetter");
                    }
                    sp.putInt(Game_Play_Activity.this, "nohint", 0);
                    sp.putInt(Game_Play_Activity.this, "view_hint", 1);

                    delete_letter_dia.dismiss();
                } else {
                    earncoin();
                }


            }
        });
        dia_cancel_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_letter_dia.dismiss();
            }
        });
    }

    public void minuscoin(int co) {


        //Utilities.sharedPrefAdd("clickhint", "yes", Utilities.mPreferences);
        sp.putString(Game_Play_Activity.this, "clickhint", "yes");

        Cursor bb = mydb_copy.rawQuery("select * from scoretable ", null);
        bb.moveToFirst();
        int jj = 0;
        jj = bb.getInt(bb.getColumnIndex("coins"));
        ContentValues values1 = new ContentValues();
        values1.put("coins", jj - co);
        mydb_copy.update("scoretable", values1, "", null);
        settext();


        Cursor bb1 = mydb_copy.rawQuery("select * from scoretable ", null);
        bb1.moveToFirst();
        int k1 = bb1.getInt(bb1.getColumnIndex("coins"));
        //Toast.makeText(getApplicationContext(),"points  "+k1,Toast.LENGTH_SHORT).show();
        if (k1 == 0) {
            //SetAlarm();
        }

    }

    public void settext() {
        Cursor sc = mydb_copy.rawQuery("select * from scoretable ", null);
        sc.moveToFirst();
        if (sc.getCount() != 0) {
            int jj1 = sc.getInt(sc.getColumnIndex("coins"));
            coins_txt.setText("" + jj1);
        }
    }


    public void earncoin() {
        final Dialog earncoin_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        earncoin_dia.setContentView(R.layout.dia_earccoin);
        earncoin_dia.show();

        RelativeLayout fb_credit_lay = (RelativeLayout) earncoin_dia.findViewById(R.id.fb_credit_lay);
        RelativeLayout purchass_credit_lay = (RelativeLayout) earncoin_dia.findViewById(R.id.purchass_credit_lay);
        RelativeLayout reward_credit_lay = (RelativeLayout) earncoin_dia.findViewById(R.id.reward_credit_lay);
        RelativeLayout wa_credit_lay = (RelativeLayout) earncoin_dia.findViewById(R.id.wa_credit_lay);
        RelativeLayout gplus_credit_lay = (RelativeLayout) earncoin_dia.findViewById(R.id.gplus_credit_lay);
        TextView coins_txt_earn = (TextView) earncoin_dia.findViewById(R.id.coins_txt_earn);
        ImageView eran_close_img = (ImageView) earncoin_dia.findViewById(R.id.eran_close_img);

        Cursor bb = mydb_copy.rawQuery("select * from scoretable ", null);
        bb.moveToFirst();
        int coin_value = bb.getInt(bb.getColumnIndex("coins"));
        coins_txt_earn.setText("" + coin_value);


        eran_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earncoin_dia.dismiss();

            }
        });
        fb_credit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 /*  if (isNetworkAvailable()) {
                    btn_str = "invite";
                    if (isLoggedIn()) {
                        Bundle params = new Bundle();
                        params.putString("message", "1 PIC 1 WORD");
                        showDialogWithoutNotificationBarInvite("apprequests", params);
                        // toast("yes");
                    } else {
                        openFacebookSession();
                        // toast("no");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                    //   snackbar1("Hey buddy, connect to the network",view );
                }*/
            }
        });
        purchass_credit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earncoin_dia.dismiss();
                Intent in = new Intent(Game_Play_Activity.this, Activity_Purchase.class);
                startActivity(in);

            }
        });
        reward_credit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                earncoin_dia.dismiss();
                reward_fun();
            }
        });

        wa_credit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    final boolean appinstalled = appInstalledOrNot("com.whatsapp");
                    if (appinstalled) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.setPackage("com.whatsapp");
                        String msg = ("Hi! I have completed " + id1 + " picture in the 1 PIC 1 WORD game. Simple and interesting word game for all ages and also it improves our vocabulary. Click the link to download:\nhttps://goo.gl/8fxkWo");
                        i.putExtra(Intent.EXTRA_TEXT, msg);
                        startActivityForResult(Intent.createChooser(i, "Share via"), 12);

                        earncoin_dia.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "whatsapp app not install", Toast.LENGTH_SHORT).show();
                        //  snackbar1("Hey buddy, connect to the network", view);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                    //   snackbar1("Hey buddy, connect to the network",view );


                }

            }
        });
        gplus_credit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    final boolean appinstalled = appInstalledOrNot("com.google.android.gm");
                    if (appinstalled) {
                        Intent i = new Intent(
                                Intent.ACTION_SEND);
                        i.setType("text/plain");
                        // i.setPackage("com.google.android.apps.plus");
                        i.setPackage("com.google.android.gm");
                        i.putExtra(Intent.EXTRA_SUBJECT, "ONE PIC ONE WORD");
                        String msg = ("Hi! I have completed " + id1 + " picture in the 1 PIC 1 WORD game. Simple and interesting word game for all ages and also it improves our vocabulary. Click the link to download:\nhttps://goo.gl/8fxkWo");

                        i.putExtra(Intent.EXTRA_TEXT, msg);
                        startActivityForResult(Intent.createChooser(i, "Share via"), 15);
                        earncoin_dia.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "G mail app not install", Toast.LENGTH_SHORT).show();
                        //  snackbar1("Hey buddy, connect to the network", view);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
                    //   snackbar1("Hey buddy, connect to the network",view );


                }
            }
        });


    }


    public void disablebutton() {
        qus_img1.setClickable(false);
        qus_img2.setClickable(false);
        qus_img3.setClickable(false);
        qus_img4.setClickable(false);
        qus_img5.setClickable(false);
        qus_img6.setClickable(false);
        qus_img7.setClickable(false);
        qus_img8.setClickable(false);
        qus_img9.setClickable(false);
        qus_img10.setClickable(false);

    }

    public void enablebutton() {
        qus_img1.setClickable(true);
        qus_img2.setClickable(true);
        qus_img3.setClickable(true);
        qus_img4.setClickable(true);
        qus_img5.setClickable(true);
        qus_img6.setClickable(true);
        qus_img7.setClickable(true);
        qus_img8.setClickable(true);
        qus_img9.setClickable(true);
        qus_img10.setClickable(true);
    }

    public void edittextdisable() {
        ans_txt1.setEnabled(false);
        ans_txt2.setEnabled(false);
        ans_txt3.setEnabled(false);
        ans_txt4.setEnabled(false);
        ans_txt5.setEnabled(false);
        ans_txt6.setEnabled(false);
        ans_txt7.setEnabled(false);
    }

    public void editextenable() {
        ans_txt1.setEnabled(true);
        ans_txt2.setEnabled(true);
        ans_txt3.setEnabled(true);
        ans_txt4.setEnabled(true);
        ans_txt5.setEnabled(true);
        ans_txt6.setEnabled(true);
        ans_txt7.setEnabled(true);
    }

    public void WrongAnimaton() {

        //if (Utilities.mPreferences.getString("coinsound", "").equals("yes")) {
        if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
            /*MediaPlayer wro = MediaPlayer.create(Playscreen.this, R.raw.wro);
            wro.start();*/
            wro.start();

        }
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.shake);

        if (word_of_image.length() == 3) {
            ans_txt1.startAnimation(anim1);
            ans_txt2.startAnimation(anim1);
            ans_txt3.startAnimation(anim1);

        }
        if (word_of_image.length() == 4) {
            ans_txt1.startAnimation(anim1);
            ans_txt2.startAnimation(anim1);
            ans_txt3.startAnimation(anim1);
            ans_txt4.startAnimation(anim1);
        }
        if (word_of_image.length() == 5) {
            ans_txt1.startAnimation(anim1);
            ans_txt2.startAnimation(anim1);
            ans_txt3.startAnimation(anim1);
            ans_txt4.startAnimation(anim1);
            ans_txt5.startAnimation(anim1);
        }
        if (word_of_image.length() == 6) {
            ans_txt1.startAnimation(anim1);
            ans_txt2.startAnimation(anim1);
            ans_txt3.startAnimation(anim1);
            ans_txt4.startAnimation(anim1);
            ans_txt5.startAnimation(anim1);
            ans_txt6.startAnimation(anim1);
        }
        if (word_of_image.length() == 7) {
            ans_txt1.startAnimation(anim1);
            ans_txt2.startAnimation(anim1);
            ans_txt3.startAnimation(anim1);
            ans_txt4.startAnimation(anim1);
            ans_txt5.startAnimation(anim1);
            ans_txt6.startAnimation(anim1);
            ans_txt7.startAnimation(anim1);
        }

    }

    public void RightAnimation() {
        final Animation anim71 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim72 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim73 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim74 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim75 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim76 = AnimationUtils.loadAnimation(this, R.anim.edittxt);
        final Animation anim77 = AnimationUtils.loadAnimation(this, R.anim.edittxt);

        qus_img1.setVisibility(View.INVISIBLE);
        qus_img2.setVisibility(View.INVISIBLE);
        qus_img3.setVisibility(View.INVISIBLE);
        qus_img4.setVisibility(View.INVISIBLE);
        qus_img5.setVisibility(View.INVISIBLE);
        qus_img6.setVisibility(View.INVISIBLE);
        qus_img7.setVisibility(View.INVISIBLE);
        qus_img8.setVisibility(View.INVISIBLE);
        qus_img9.setVisibility(View.INVISIBLE);
        qus_img10.setVisibility(View.INVISIBLE);
        btnhint.setVisibility(View.INVISIBLE);
        btndeleteletter.setVisibility(View.INVISIBLE);


        if (ans_txt1.getVisibility() == View.VISIBLE) {
            ans_txt1.startAnimation(anim71);
        }
        if (ans_txt2.getVisibility() == View.VISIBLE) {
            ans_txt2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt2.startAnimation(anim72);
                }
            }, 70);

        }
        if (ans_txt3.getVisibility() == View.VISIBLE) {
            ans_txt3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt3.startAnimation(anim73);
                }
            }, 140);
        }
        if (ans_txt4.getVisibility() == View.VISIBLE) {
            ans_txt4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt4.startAnimation(anim74);
                }
            }, 210);
        }
        if (ans_txt5.getVisibility() == View.VISIBLE) {
            ans_txt5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt5.startAnimation(anim75);
                }
            }, 280);
        }
        if (ans_txt6.getVisibility() == View.VISIBLE) {
            ans_txt6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt6.startAnimation(anim76);
                }
            }, 350);
        }
        if (ans_txt7.getVisibility() == View.VISIBLE) {
            ans_txt7.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ans_txt7.startAnimation(anim77);
                }
            }, 420);
        }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (daily_ok.equals("yes")) {

                    if (interstitialAd_word.isLoaded()) {
                        interstitialAd_word.show();
                        interstitialAd_word.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {

                                completed();
                                //stage.loadInterstialAdd_word(Playscreen.this);
                            }

                        });
                    } else {
                        completed();
                    }
                } else {
                    completed();
                }

            }
        }, 1500);


    }

    int continue_game = 0;

    public void completed() {
        continue_game = 0;
        int my_score = sp.getInt(Game_Play_Activity.this, "My_leader_point");
        my_score += 100;
        sp.putInt(Game_Play_Activity.this, "My_leader_point", my_score);


        if (Dailytest_ok.equals("")) {
            if (sp.getInt(Game_Play_Activity.this, "view_hint") == 0) {
                int continue_wh = sp.getInt(Game_Play_Activity.this, "continue_wh");
                continue_wh += 1;
                sp.putInt(Game_Play_Activity.this, "continue_wh", continue_wh);

                int total_wh = sp.getInt(Game_Play_Activity.this, "total_wh");
                total_wh += 1;
                sp.putInt(Game_Play_Activity.this, "total_wh", total_wh);

            } else {
                sp.putInt(Game_Play_Activity.this, "continue_wh", 0);
            }
        }

        sp.putString(Game_Play_Activity.this, "WORD PRONOUNCE", "");
        sp.putString(Game_Play_Activity.this, "LETTER PRONOUNCE", "");
        sp.putString(Game_Play_Activity.this, "delete", "");

        final Dialog scoreboard_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        scoreboard_dia.setContentView(R.layout.dia_scoreboard);
        scoreboard_dia.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        scoreboard_dia.show();

        final LinearLayout score_ad_lay = (LinearLayout) scoreboard_dia.findViewById(R.id.score_ad_lay);
        final TextView comcoin = (TextView) scoreboard_dia.findViewById(R.id.comcoin);
        final TextView scoin1 = (TextView) scoreboard_dia.findViewById(R.id.coin1);
        final TextView scoin2 = (TextView) scoreboard_dia.findViewById(R.id.coin2);
        final TextView scoin3 = (TextView) scoreboard_dia.findViewById(R.id.coin3);
        final TextView scoin4 = (TextView) scoreboard_dia.findViewById(R.id.coin4);
        final TextView scoin5 = (TextView) scoreboard_dia.findViewById(R.id.coin5);
        final TextView scoin6 = (TextView) scoreboard_dia.findViewById(R.id.coin6);
        final TextView scoin7 = (TextView) scoreboard_dia.findViewById(R.id.coin7);
        final TextView scoin8 = (TextView) scoreboard_dia.findViewById(R.id.coin8);
        final TextView scoin9 = (TextView) scoreboard_dia.findViewById(R.id.coin9);
        final TextView scoin10 = (TextView) scoreboard_dia.findViewById(R.id.coin10);
        final ImageView score_fb_img = (ImageView) scoreboard_dia.findViewById(R.id.score_fb_img);
        final ImageView score_twitter_img = (ImageView) scoreboard_dia.findViewById(R.id.score_twitter_img);
        final ImageView score_wa_img = (ImageView) scoreboard_dia.findViewById(R.id.score_wa_img);
        final ImageView score_gplus_img = (ImageView) scoreboard_dia.findViewById(R.id.score_gplus_img);

        if (sp.getString(Game_Play_Activity.this, "PointsPurchase").equals("")) {
            load_addFrom_score(score_ad_lay);
        }


        score_fb_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    fb();
                } else {
                    snackbar("Hey buddy, connect to the network");
                }
            }
        });
        score_twitter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_view = comcoin;
                tw();
            }
        });
        score_wa_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_view = comcoin;
                wap();
            }
        });
        score_gplus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_view = comcoin;
                gp();
            }
        });


        Cursor sc = mydb_copy.rawQuery("select * from scoretable ", null);
        sc.moveToFirst();
        if (sc.getCount() != 0) {
            int jj1 = sc.getInt(sc.getColumnIndex("coins"));
            comcoin.setText("" + jj1);
        }

        final TextView continue_txt = (TextView) scoreboard_dia.findViewById(R.id.continue_txt);
        continue_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scoreboard_dia.dismiss();
                continue_game = 1;
            }
        });

        scoreboard_dia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sp.putString(Game_Play_Activity.this, "solution_done", "");

                if (continue_game == 0) {
                    continue_game = 10;
                    exit_fun();
                } else {
                    settext();
                    if (Dailytest_ok.equals("")) {
                        random_question_count = 0;

                        if (Dailytest_ok.equals("")) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (id1 == 100) {
                                        call_rating();
                                    } else if (id1 == 500) {
                                        call_rating();
                                    } else if (id1 == 1000) {
                                        call_rating();
                                    }
                                }
                            }, 1500);

                            achivement_open();
                            game_selection();


                        }
                    } else {
                        random_question_count += 1;
                        System.out.println("============999999999999 random_question_count : " + random_question_count);
                        if (random_question_count < 5) {
                            game_selection();
                        } else {
                            int Daily_finisher = sp.getInt(Game_Play_Activity.this, "Daily_finisher");
                            Daily_finisher += 1;
                            sp.putInt(Game_Play_Activity.this, "Daily_finisher", Daily_finisher);
                            dailygame_finish();
                        }
                    }

                }
            }
        });

        if (sp.getString(Game_Play_Activity.this, "solution_done").equals("")) {


            scoin1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scoin1.setVisibility(View.VISIBLE);
                    if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin_collect.start();
                    }

                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin1.start();
                    } */

                }
            }, 500);
            scoin2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scoin2.setVisibility(View.VISIBLE);
                   /* if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin2.start();
                    }*/
                }
            }, 600);
            scoin3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin3.start();
                    }*/
                    scoin3.setVisibility(View.VISIBLE);

                }
            }, 700);
            scoin4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin4.start();
                    }*/
                    scoin4.setVisibility(View.VISIBLE);

                }
            }, 800);
            scoin5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin5.start();
                    }*/
                    scoin5.setVisibility(View.VISIBLE);

                }
            }, 900);


            if (sp.getInt(Game_Play_Activity.this, "view_hint") == 0) {
                scoin6.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin6.start();
                        }*/
                        scoin6.setVisibility(View.VISIBLE);


                    }
                }, 1000);
                scoin7.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin7.start();
                        }*/
                        scoin7.setVisibility(View.VISIBLE);

                    }
                }, 1100);
                scoin8.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin8.start();
                        }*/
                        scoin8.setVisibility(View.VISIBLE);

                    }
                }, 1200);
                scoin9.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       /* if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin9.start();
                        }*/
                        scoin9.setVisibility(View.VISIBLE);

                    }
                }, 1300);
                scoin10.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin10.start();
                        }*/
                        scoin10.setVisibility(View.VISIBLE);

                    }
                }, 1400);
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin1.start();
                    }*/
                    movecoin(scoin1, comcoin);

                }
            }, 1500);
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin2.start();
                    }*/

                    movecoin(scoin2, comcoin);
                }
            }, 1600);
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin3.start();
                    }*/
                    movecoin(scoin3, comcoin);
                }
            }, 1700);
            Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                        coin4.start();
                    }*/
                    movecoin(scoin4, comcoin);
                }
            }, 1800);
            Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    movecoin(scoin5, comcoin);

                    if (sp.getInt(Game_Play_Activity.this, "view_hint") != 0) {
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                            ting.start();
                        }*/
                        continue_txt.setVisibility(View.VISIBLE);
                    } else {
                        if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin5.start();
                        }
                    }


                }
            }, 1900);


            if (sp.getInt(Game_Play_Activity.this, "view_hint") == 0) {
                Handler handler5 = new Handler();
                handler5.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movecoin(scoin6, comcoin);
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin6.start();
                        }*/

                    }
                }, 2000);
                Handler handler6 = new Handler();
                handler6.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movecoin(scoin7, comcoin);
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin7.start();
                        }*/
                    }
                }, 2100);
                Handler handler7 = new Handler();
                handler7.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movecoin(scoin8, comcoin);
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin8.start();
                        }*/
                    }
                }, 2200);
                Handler handler8 = new Handler();
                handler8.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movecoin(scoin9, comcoin);
                        /*if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {

                            coin9.start();
                        }*/
                    }
                }, 2300);
                Handler handler9 = new Handler();
                handler9.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movecoin(scoin10, comcoin);

                       /* if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                            ting.start();
                        }*/

                        continue_txt.setVisibility(View.VISIBLE);
                    }
                }, 2400);
            }

        } else {
            continue_txt.setVisibility(View.VISIBLE);
        }

    }

    public void movecoin(final TextView textView, final TextView comcoin) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game_selection();
                /* nextwordassaign();*/
            }
        });
        int[] locationInWindow = new int[2];
        textView.getLocationInWindow(locationInWindow);
        int[] locationOnScreen = new int[2];
        textView.getLocationOnScreen(locationOnScreen);
        float sourceX = locationOnScreen[0];
        float sourceY = locationOnScreen[1];
        int[] locationInWindowSecond = new int[2];
        comcoin.getLocationInWindow(locationInWindowSecond);
        int[] locationOnScreenSecond = new int[2];
        comcoin.getLocationOnScreen(locationOnScreenSecond);
        float destinationX = locationOnScreenSecond[0];
        float destinationY = locationOnScreenSecond[1];
        TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
        transAnimation.setDuration(200);
        textView.startAnimation(transAnimation);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                textView.setVisibility(View.INVISIBLE);
                comcoin.setText("" + count());
            }
        }, transAnimation.getDuration());
    }

    public int count() {
        Cursor bb = mydb_copy.rawQuery("select * from scoretable ", null);
        bb.moveToFirst();

        if (bb.getCount() != 0) {
            int jj = bb.getInt(bb.getColumnIndex("coins"));
            ContentValues values1 = new ContentValues();
            values1.put("coins", jj + 1);
            mydb_copy.update("scoretable", values1, "", null);
        } else {
            ContentValues values1 = new ContentValues();
            values1.put("coins", 2);
            mydb_copy.insert("scoretable", null, values1);
        }

        Cursor sc = mydb_copy.rawQuery("select * from scoretable ", null);
        sc.moveToFirst();
        int jj1 = 0;
        if (sc.getCount() != 0) {
            jj1 = sc.getInt(sc.getColumnIndex("coins"));

        } else {
        }

        return jj1;
    }


    public void dailygame_finish() {
        final Dialog dailytest_dia = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dailytest_dia.setContentView(R.layout.dia_dt_success);
        dailytest_dia.show();
        TextView dia_done_dt = (TextView) dailytest_dia.findViewById(R.id.dia_done_dt);

        achivement_open();

        dia_done_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailytest_dia.dismiss();
            }
        });
        dailytest_dia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Dailytest_ok = "";
                if (sp.getString(Game_Play_Activity.this, "PointsPurchase").equals("")) {
                    if (interstitialAd_cate.isLoaded()) {
                        interstitialAd_cate.show();
                        interstitialAd_cate.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {

                                Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                                finish();
                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                                startActivity(intent);
                            }

                        });

                    } else {
                        Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                        finish();
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Game_Play_Activity.this, MainActivity.class);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    startActivity(intent);
                }
            }
        });


    }

    public void validate() {

        String n11 = ans_txt1.getTag().toString();
        String n12 = ans_txt2.getTag().toString();
        String n13 = ans_txt3.getTag().toString();
        String n14 = ans_txt4.getTag().toString();
        String n15 = ans_txt5.getTag().toString();
        String n16 = ans_txt6.getTag().toString();
        String n17 = ans_txt7.getTag().toString();

        String ans_value = n11 + n12 + n13 + n14 + n15 + n16 + n17;

        System.out.println("======validate ans_value: " + ans_value);
        System.out.println("======validate word_of_image.length(): " + word_of_image.length());
        System.out.println("======validate ans_txt1.getTag().toString().length(): " + ans_txt1.getTag().toString().length());
        System.out.println("======validate ans_txt2.getTag().toString().length(): " + ans_txt2.getTag().toString().length());
        System.out.println("======validate ans_txt3.getTag().toString().length(): " + ans_txt3.getTag().toString().length());

        if (word_of_image.length() == 3) {
            if (ans_txt1.getTag().toString().length() > 0 && ans_txt2.getTag().toString().length() > 0 && ans_txt3.getTag().toString().length() > 0) {

                disablebutton();
                if (ans_value.equalsIgnoreCase(word_of_image)) {
                    //fireanimation();
                    RightBack();
                    edittextdisable();
                    RightAnimation();
                    if (Dailytest_ok.equals("Dailytest_ok")) {
                        // mydb_copy.execSQL("UPDATE dailytest SET isshow='1' where id='" + id1 + "';");

                    } else {
                        mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1' where id='" + id1 + "';");
                    }

                    if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else {
                    enablebutton();
                    WrongBack();
                    WrongAnimaton();
                    edittextdisable();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editextenable();
                        }
                    }, 700);

                }
            } else {
                enablebutton();
            }
        }

        if (word_of_image.length() == 4) {
            if (ans_txt1.getTag().toString().length() > 0 && ans_txt2.getTag().toString().length() > 0 && ans_txt3.getTag().toString().length() > 0 && ans_txt4.getTag().toString().length() > 0) {
                disablebutton();
                if (ans_value.equalsIgnoreCase(word_of_image)) {
                    //  fireanimation();
                    RightBack();
                    edittextdisable();
                    RightAnimation();
                    if (Dailytest_ok.equals("Dailytest_ok")) {
                        // mydb_copy.execSQL("UPDATE dailytest SET isshow='1' where id='" + id1 + "';");

                    } else {
                        mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1' where id='" + id1 + "';");
                    }
                    if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    enablebutton();
                    WrongBack();
                    WrongAnimaton();
                    edittextdisable();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editextenable();
                        }
                    }, 700);

                }
            } else {
                enablebutton();
            }
        }
        if (word_of_image.length() == 5) {
            if (ans_txt1.getTag().toString().length() > 0 && ans_txt2.getTag().toString().length() > 0 && ans_txt3.getTag().toString().length() > 0 && ans_txt4.getTag().toString().length() > 0 && ans_txt5.getTag().toString().length() > 0) {
                disablebutton();
                if (ans_value.equalsIgnoreCase(word_of_image)) {
                    //  fireanimation();
                    RightBack();
                    edittextdisable();
                    RightAnimation();
                    if (Dailytest_ok.equals("Dailytest_ok")) {
                        // mydb_copy.execSQL("UPDATE dailytest SET isshow='1' where id='" + id1 + "';");

                    } else {
                        mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1' where id='" + id1 + "';");
                    }
                    if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    enablebutton();
                    WrongBack();
                    WrongAnimaton();
                    edittextdisable();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editextenable();
                        }
                    }, 700);

                }
            } else {
                enablebutton();
            }
        }
        if (word_of_image.length() == 6) {
            if (ans_txt1.getTag().toString().length() > 0 && ans_txt2.getTag().toString().length() > 0 && ans_txt3.getTag().toString().length() > 0 && ans_txt4.getTag().toString().length() > 0 && ans_txt5.getTag().toString().length() > 0 && ans_txt6.getTag().toString().length() > 0) {
                disablebutton();
                if (ans_value.equalsIgnoreCase(word_of_image)) {
                    // fireanimation();
                    RightBack();
                    edittextdisable();
                    RightAnimation();
                    if (Dailytest_ok.equals("Dailytest_ok")) {
                        //mydb_copy.execSQL("UPDATE dailytest SET isshow='1' where id='" + id1 + "';");

                    } else {
                        mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1' where id='" + id1 + "';");
                    }
                    if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    enablebutton();
                    WrongBack();
                    WrongAnimaton();
                    edittextdisable();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editextenable();
                        }
                    }, 700);

                }
            } else {
                enablebutton();
            }
        }
        if (word_of_image.length() == 7) {
            if (ans_txt1.getTag().toString().length() > 0 && ans_txt2.getTag().toString().length() > 0 && ans_txt3.getTag().toString().length() > 0 && ans_txt4.getTag().toString().length() > 0 && ans_txt5.getTag().toString().length() > 0 && ans_txt6.getTag().toString().length() > 0 && ans_txt7.getTag().toString().length() > 0) {
                disablebutton();
                if (ans_value.equalsIgnoreCase(word_of_image)) {
                    RightBack();
                    edittextdisable();
                    RightAnimation();
                    if (Dailytest_ok.equals("Dailytest_ok")) {
                        // mydb_copy.execSQL("UPDATE dailytest SET isshow='1' where id='" + id1 + "';");

                    } else {
                        mydb_copy.execSQL("UPDATE onepiconewords SET isshow='1' where id='" + id1 + "';");
                    }
                    if (sp.getString(Game_Play_Activity.this, "wordsound").equals("yes")) {
                        tts.speak(word_of_image.toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }

                } else {
                    enablebutton();
                    WrongBack();
                    WrongAnimaton();
                    edittextdisable();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editextenable();
                        }
                    }, 700);

                }
            } else {
                enablebutton();
            }
        }


    }

    public void RightBack() {
        qus_full_lay.setVisibility(View.VISIBLE);

        ans_txt1.setBackgroundResource(R.drawable.val);
        ans_txt2.setBackgroundResource(R.drawable.val);
        ans_txt3.setBackgroundResource(R.drawable.val);
        ans_txt4.setBackgroundResource(R.drawable.val);
        ans_txt5.setBackgroundResource(R.drawable.val);
        ans_txt6.setBackgroundResource(R.drawable.val);
        ans_txt7.setBackgroundResource(R.drawable.val);


       /* ans_txt1.setTextColor(Color.WHITE);
        ans_txt2.setTextColor(Color.WHITE);
        ans_txt3.setTextColor(Color.WHITE);
        ans_txt4.setTextColor(Color.WHITE);
        ans_txt5.setTextColor(Color.WHITE);
        ans_txt6.setTextColor(Color.WHITE);
        ans_txt7.setTextColor(Color.WHITE);*/

    }

    public void WrongBack() {

        ans_txt1.setBackgroundResource(R.drawable.val_rong);
        ans_txt2.setBackgroundResource(R.drawable.val_rong);
        ans_txt3.setBackgroundResource(R.drawable.val_rong);
        ans_txt4.setBackgroundResource(R.drawable.val_rong);
        ans_txt5.setBackgroundResource(R.drawable.val_rong);
        ans_txt6.setBackgroundResource(R.drawable.val_rong);
        ans_txt7.setBackgroundResource(R.drawable.val_rong);

        //setedittext();

       /* ans_txt1.setTextColor(Color.WHITE);
        ans_txt2.setTextColor(Color.WHITE);
        ans_txt3.setTextColor(Color.WHITE);
        ans_txt4.setTextColor(Color.WHITE);
        ans_txt5.setTextColor(Color.WHITE);
        ans_txt6.setTextColor(Color.WHITE);
        ans_txt7.setTextColor(Color.WHITE);*/


    }

    public void clickbtn(final ImageView btn) {
        disablebutton();

        final String btn_getletter = (String) btn.getTag();


        if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
          /*  MediaPlayer butclick = MediaPlayer.create(this, R.raw.popup);
            butclick.start();*/
            but.start();
        }
        if (ans_txt1.getTag().toString().length() == 0 && ans_txt1.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt1.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt1.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);
            ans_txt1.setEnabled(true);

            // ans_txt1.setTag(go_tag_value);
            ans_tag_id.set(0, go_tag_value);

            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub


                    // ans_txt1.setText(btn_getletter);
                    ans_txt1.setTag(btn_getletter);
                    ans_txt1.setBackgroundResource(R.drawable.txt_qus_bg);
                    ans_txt1.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));

                    //edt1.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);


                    //  enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt2.getTag().toString().length() == 0 && ans_txt2.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt2.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt2.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(1, go_tag_value);

            ans_txt2.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ans_txt2.setTag(btn_getletter);
                    ans_txt2.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt2.setBackgroundResource(R.drawable.txt_qus_bg);
                    ///edt2.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    //  enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt3.getTag().toString().length() == 0 && ans_txt3.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt3.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt3.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(2, go_tag_value);

            ans_txt3.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ans_txt3.setTag(btn_getletter);
                    ans_txt3.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt3.setBackgroundResource(R.drawable.txt_qus_bg);
                    //edt3.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    //    enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt4.getTag().toString().length() == 0 && ans_txt4.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt4.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt4.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(3, go_tag_value);

            ans_txt4.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ans_txt4.setTag(btn_getletter);
                    ans_txt4.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
                    //edt4.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    //  enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt5.getTag().toString().length() == 0 && ans_txt5.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt5.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt5.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(4, go_tag_value);

            ans_txt5.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ans_txt5.setTag(btn_getletter);
                    ans_txt5.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
                    //edt5.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    //   enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt6.getTag().toString().length() == 0 && ans_txt6.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt6.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt6.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(5, go_tag_value);

            ans_txt6.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    ans_txt6.setTag(btn_getletter);
                    ans_txt6.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
                    // edt6.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    //enablebutton();
                }
            }, transAnimation.getDuration());
        } else if (ans_txt7.getTag().toString().length() == 0 && ans_txt7.getVisibility() == View.VISIBLE) {
            int[] locationInWindow = new int[2];
            btn.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            btn.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            ans_txt7.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            ans_txt7.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation(0f, (destinationX - sourceX), 0f, (destinationY - sourceY));
            transAnimation.setDuration(300);
            btn.startAnimation(transAnimation);

            ans_tag_id.set(6, go_tag_value);

            ans_txt7.setEnabled(true);
            btn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    //ans_txt7.setText(btn_getletter);
                    ans_txt7.setTag(btn_getletter);
                    ans_txt7.setImageResource(getResources().getIdentifier(btn_getletter.toLowerCase(), "drawable", getPackageName()));
                    ans_txt7.setBackgroundResource(R.drawable.txt_qus_bg);


                    //edt7.setBackgroundResource(R.drawable.aabbaabb);
                    btn.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validate();
                        }
                    }, 200);
                    // enablebutton();
                }
            }, transAnimation.getDuration());
        }

    }

    public void touchedt(final ImageView edt) {
        enablebutton();


       /* ans_txt1.setTextColor(Color.BLACK);
        ans_txt2.setTextColor(Color.BLACK);
        ans_txt3.setTextColor(Color.BLACK);
        ans_txt4.setTextColor(Color.BLACK);
        ans_txt5.setTextColor(Color.BLACK);
        ans_txt6.setTextColor(Color.BLACK);
        ans_txt7.setTextColor(Color.BLACK);*/

        System.out.println("====---===kkk edt.getTag() : " + edt.getTag());
        //System.out.println("====---===kkk edt.getText().length() : " + edt.getText().length());

        String getTAG = edt.getTag().toString();

        // if (getTAG.equals("1") && edt.getText().length() != 0) {
        if (ans_tag_id.get(ans_douch_pos) == 1) {
            qus_img1.setVisibility(View.VISIBLE);
            // edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);
            // edt.setImageResource(getResources().getIdentifier(null, "drawable", getPackageName()));

            edt.setEnabled(false);
            qus_img1.setClickable(true);

            int[] locationInWindow = new int[2];
            qus_img1.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img1.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img1.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }

        } else if (ans_tag_id.get(ans_douch_pos) == 2) {
            qus_img2.setVisibility(View.VISIBLE);
            //edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            // edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img2.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img2.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img2.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img2.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 3) {
            qus_img3.setVisibility(View.VISIBLE);
            // edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);
            // edt.setImageResource(getResources().getIdentifier(null, "drawable", getPackageName()));

            edt.setEnabled(false);
            qus_img3.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img3.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img3.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img3.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 4) {
            qus_img4.setVisibility(View.VISIBLE);
            //edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img4.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img4.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img4.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img4.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 5) {
            qus_img5.setVisibility(View.VISIBLE);
            // edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img5.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img5.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img5.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img5.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 6) {
            qus_img6.setVisibility(View.VISIBLE);
            //edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img6.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img6.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img6.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img6.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 7) {
            qus_img7.setVisibility(View.VISIBLE);
            // edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img7.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img7.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img7.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img7.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 8) {
            qus_img8.setVisibility(View.VISIBLE);
            // edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img8.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img8.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img8.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img8.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 9) {
            qus_img9.setVisibility(View.VISIBLE);
            //edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            // edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img9.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img9.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img9.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img9.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        } else if (ans_tag_id.get(ans_douch_pos) == 10) {
            qus_img10.setVisibility(View.VISIBLE);
            //edt.setText("");
            edt.setTag("");
            edt.setImageResource(0);
            //edt.setBackgroundResource(R.drawable.txt_answer_bg);
            edt.setBackgroundResource(R.drawable.txt_qus_bg);

            edt.setEnabled(false);
            qus_img10.setClickable(true);
            int[] locationInWindow = new int[2];
            qus_img10.getLocationInWindow(locationInWindow);
            int[] locationOnScreen = new int[2];
            qus_img10.getLocationOnScreen(locationOnScreen);
            float sourceX = locationOnScreen[0];
            float sourceY = locationOnScreen[1];
            int[] locationInWindowSecond = new int[2];
            edt.getLocationInWindow(locationInWindowSecond);
            int[] locationOnScreenSecond = new int[2];
            edt.getLocationOnScreen(locationOnScreenSecond);
            float destinationX = locationOnScreenSecond[0];
            float destinationY = locationOnScreenSecond[1];
            TranslateAnimation transAnimation = new TranslateAnimation((destinationX - sourceX), 0f, (destinationY - sourceY), 0f);
            transAnimation.setDuration(300);
            qus_img10.startAnimation(transAnimation);
            if (sp.getString(Game_Play_Activity.this, "coinsound").equals("yes")) {
                edtt.start();
            }
        }

        if (ans_txt1.getTag().toString().length() == 1) {
            ans_txt1.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt2.getTag().toString().length() == 1) {
            ans_txt2.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt3.getTag().toString().length() == 1) {
            ans_txt3.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt4.getTag().toString().length() == 1) {
            ans_txt4.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt5.getTag().toString().length() == 1) {
            ans_txt5.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt6.getTag().toString().length() == 1) {
            ans_txt6.setBackgroundResource(R.drawable.txt_qus_bg);
        }
        if (ans_txt7.getTag().toString().length() == 1) {
            ans_txt7.setBackgroundResource(R.drawable.txt_qus_bg);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }


    public void fb() {
        if (isNetworkAvailable()) {
            btn_str = "share";
           /* if (isLoggedIn()) {
                publishFeedDialog();
            } else {
                openFacebookSession();
            }*/
        } else {
            //  Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
        }
    }

    public void wap() {
        if (isNetworkAvailable()) {
            final boolean appinstalled = appInstalledOrNot("com.whatsapp");
            if (appinstalled) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage("com.whatsapp");
                String msg = ("Hi! I have completed " + id1 + " picture in the 1 PIC 1 WORD game. Simple and interesting word game for all ages and also it improves our vocabulary. Click the link to download:\nhttps://goo.gl/8fxkWo");
                i.putExtra(Intent.EXTRA_TEXT, msg);
                startActivityForResult(Intent.createChooser(i, "Share via"), 21);

            } else {
                snackbar("whatsapp app not installed");
            }
        } else {
            snackbar("Hey buddy, connect to the network");
        }

    }

    public void gp() {
        if (isNetworkAvailable()) {
            final boolean appinstalled = appInstalledOrNot("com.google.android.gm");
            if (appinstalled) {
                Intent i = new Intent(
                        Intent.ACTION_SEND);
                i.setType("text/plain");
                //i.setPackage("com.google.android.apps.plus");
                i.setPackage("com.google.android.gm");
                String msg = ("Hi! I have completed Level " + id1 + "  in the 1 PIC 1 WORD game. Simple and interesting word game for all ages. To try https://goo.gl/8fxkWo");
                i.putExtra(Intent.EXTRA_SUBJECT, "ONE PIC ONE WORD");
                i.putExtra(Intent.EXTRA_TEXT, msg);
                startActivityForResult(Intent.createChooser(i, "Share via"), 13);


            } else {
                // Toast.makeText(getApplicationContext(), "This app not install", Toast.LENGTH_SHORT).show();
                snackbar("G mail app not installed");

            }
        } else {
            //Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
            snackbar("Hey buddy, connect to the network");
        }
    }

    public void tw() {
        if (isNetworkAvailable()) {
            final boolean appinstalled = appInstalledOrNot("com.twitter.android");
            if (appinstalled) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage("com.twitter.android");
                String msg = ("Hi! I have completed Level " + id1 + "  in the 1 PIC 1 WORD game. Simple and interesting word game for all ages. To try https://goo.gl/8fxkWo");
                i.putExtra(Intent.EXTRA_TEXT, msg);
                startActivityForResult(Intent.createChooser(i, "Share via"), 14);


            } else {
                // Toast.makeText(getApplicationContext(), "This app not install", Toast.LENGTH_SHORT).show();
                snackbar("Twitter app not installed");

            }
        } else {
            // Toast.makeText(getApplicationContext(), "Hey buddy, connect to the network", Toast.LENGTH_SHORT).show();
            snackbar("Hey buddy, connect to the network");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
        if (requestCode == 0) {
            if (isNetworkAvailable()) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if ((ContextCompat.checkSelfPermission(Playscreen.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        permission();

                    } else {
                        download_dailytest(daily_date, "no");
                    }
                } else {
                    download_dailytest(daily_date, "no");
                }
                //download_dailytest(daily_date, "no");
                //Toast.makeText(context, "ok net", Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(Playscreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert);
                //dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationPopup;
                dialog.setCancelable(false);
                TextView yes = (TextView) dialog.findViewById(R.id.yes);
                TextView no = (TextView) dialog.findViewById(R.id.no);
                TextView ww = (TextView) dialog.findViewById(R.id.txt_exit);
                ImageButton ss = (ImageButton) dialog.findViewById(R.id.ssss);
                TextView yy = (TextView) dialog.findViewById(R.id.wpro);
                ss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                yes.setTypeface(ttf_1);
                no.setTypeface(ttf_1);
                ww.setTypeface(ttf_1);
                yy.setTypeface(ttf_1);
                yy.setText("Daily Test");
                ww.setText("Please enable your internet conection so that new data will be available");
                ss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                ss.setVisibility(View.INVISIBLE);
                yes.setText("Now");
                no.setText("Later");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        daily_back = "no";
                        back();
                    }
                });
                dialog.show();
            }
        }*/

        System.out.println("===---====----RR requestCode " + requestCode);
        System.out.println("===---====----RR resultCode " + resultCode);

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

        if (requestCode == 12) {
            if (resultCode == -1) {
                if (sp.getString(Game_Play_Activity.this, "ec_wapp" + id1).equals("")) {
                    sp.putString(Game_Play_Activity.this, "ec_wapp" + id1, "yes");
                    lastearn("com1", 1);
                } else {
                    Toast.makeText(Game_Play_Activity.this, "Whatsapp coins already earned", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == 21) {
            if (resultCode == -1) {
                if (sp.getString(Game_Play_Activity.this, "score_wapp" + id1).equals("")) {
                    sp.putString(Game_Play_Activity.this, "score_wapp" + id1, "yes");
                    lastearn("com", 1);
                } else {
                    Toast.makeText(Game_Play_Activity.this, "Whatsapp coins already earned", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == 13) {
            if (sp.getString(Game_Play_Activity.this, "score_gm" + id1).equals("")) {
                sp.putString(Game_Play_Activity.this, "score_gm" + id1, "yes");
                lastearn("com", 1);
            } else {
                Toast.makeText(Game_Play_Activity.this, "G mail coins already earned", Toast.LENGTH_SHORT).show();
            }


            /*if (resultCode == -1) {
                lastearn("com", 1);
            }*/
        }
        if (requestCode == 14) {

            if (sp.getString(Game_Play_Activity.this, "ec_twitter" + id1).equals("")) {
                sp.putString(Game_Play_Activity.this, "ec_twitter" + id1, "yes");
                if (resultCode == -1) {
                    lastearn("com", 1);
                }
            } else {
                Toast.makeText(Game_Play_Activity.this, "Twitter coins already earned", Toast.LENGTH_SHORT).show();
            }


        }
        if (requestCode == 15) {
            if (sp.getString(Game_Play_Activity.this, "ec_gm" + id1).equals("")) {
                sp.putString(Game_Play_Activity.this, "ec_gm" + id1, "yes");
                lastearn("com", 1);
            } else {
                Toast.makeText(Game_Play_Activity.this, "G mail coins already earned", Toast.LENGTH_SHORT).show();
            }

            // lastearn("Share twiter", 1);
            /*if (resultCode == -1) {
                lastearn("Share twiter", 1);
            }*/
        }
    }

    public void lastearn(final String ee, final int per) {
        final Dialog dialog = new Dialog(Game_Play_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addcoins);
        dialog.setCancelable(false);
        final TextView tot = (TextView) dialog.findViewById(R.id.tot);
        final TextView no = (TextView) dialog.findViewById(R.id.no);

        TextView ss = (TextView) dialog.findViewById(R.id.txt_exit1);

        TextView yy = (TextView) dialog.findViewById(R.id.wpro);
        final TextView ww = (TextView) dialog.findViewById(R.id.txt_exit);

        yy.setText("Coins earned");
        no.setText("OK");
        no.setVisibility(View.INVISIBLE);
        if (ee.equals("extra")) {
            ww.setText("You have earned bonus");
        } else if (ee.equals("dily")) {
            ww.setText("You have earned daily bonus");
        } else if (ee.equals("reward_video")) {
            ww.setText("You have earned reward bonus");
        } else if (ee.equals("Coin_purchase")) {
            ww.setText("You have purchased");
        } else {
            ww.setText("You have earned");
        }
        ss.setText("Coins");

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (ee.equals("dily")) {
                    game_selection();
                }

                System.out.println("====---==-==-=temp_view :" + temp_view);

                if (temp_view != null) {
                    Cursor bb = mydb_copy.rawQuery("select * from scoretable ", null);
                    bb.moveToFirst();
                    int coins = bb.getInt(bb.getColumnIndex("coins"));
                    temp_view.setText("" + coins);
                    temp_view = null;
                }
            }
        });
        int ff = 10;
        if (ee.equals("com")) {
            ff = 25;

        } else if (ee.equals("reward_video")) {
            ff = 1;
        } else if (ee.equals("Coin_purchase")) {
            ff = 1;
        }
        final ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, (Math.abs(per) * ff));
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tot.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                no.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }, 500);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
                setcoin(Math.abs(per), ee);

            }
        });
        dialog.show();
    }

    public void setcoin(int co, String ee) {

        Cursor bb = mydb_copy.rawQuery("select * from scoretable ", null);
        bb.moveToFirst();
        int jj = 0;
        jj = bb.getInt(bb.getColumnIndex("coins"));
        int ff = 10;
        if (ee.equals("com")) {
            ff = 25;
        } else if (ee.equals("reward_video")) {
            ff = 1;
        } else if (ee.equals("Coin_purchase")) {
            ff = 1;
        }

        ContentValues values1 = new ContentValues();
        values1.put("coins", jj + (co * ff));
        mydb_copy.update("scoretable", values1, "", null);

        settext();
    }

    //*********************reward videos process 3***********************

    public void reward_fun() {

        if (Utils.isNetworkAvailable(Game_Play_Activity.this)) {
            final ProgressDialog reward_progressBar = ProgressDialog.show(Game_Play_Activity.this, "" + "Reward video", "Loading...");

            if (mRewardedVideoAd.isLoaded()) {
                reward_progressBar.dismiss();
                showRewardedVideo();
                // hind_dialog.dismiss();

            } else {
                startGame();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reward_progressBar.dismiss();
                        if (mRewardedVideoAd.isLoaded()) {
                            showRewardedVideo();
                            //hind_dialog.dismiss();
                        } else {
                            startGame();
                            Toast.makeText(Game_Play_Activity.this, "Video not available try later...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 2000);
            }
        } else {

            Toast.makeText(Game_Play_Activity.this, "Please check your internet..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        // Preload the next video ad.

        reward_video = "reward_video";

        if (mCoinCount != 0) {

            lastearn("reward_video", mCoinCount);

            // coin_collection(1, mCoinCount);

        } else {
            Toast.makeText(Game_Play_Activity.this, "Please watch the full video to earn 25 Coins", Toast.LENGTH_SHORT).show();
        }
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
       /* Toast.makeText(this,
                String.format(" onRewarded! currency: %s amount: %d", rewardItem.getType(),
                        rewardItem.getAmount()),
                Toast.LENGTH_SHORT).show();*/
        addCoins(rewardItem.getAmount());
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(Game_Play_Activity.this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }


    private void pauseGame() {
        // mCountDownTimer.cancel();
        mGamePaused = true;
    }

    private void resumeGame() {
        // createTimer(mTimeRemaining);
        mGamePaused = false;
    }

    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    private void addCoins(int coins) {
        mCoinCount = coins;
        //mCoinCountText.setText("Coins: " + mCoinCount);
    }

    private void startGame() {

        loadRewardedVideoAd();
        mGamePaused = false;
        mGameOver = false;
    }

    private void showRewardedVideo() {
        //mShowVideoButton.setVisibility(View.INVISIBLE);
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }
    //reward videos***********************//
    
    
    

   /* private void showDialogWithoutNotificationBarInvite(String action, Bundle params) {
        final WebDialog dialog = new WebDialog.Builder(context, Session.getActiveSession(), action, params)
                .setOnCompleteListener(new WebDialog.OnCompleteListener() {
                    @Override
                    public void onComplete(Bundle values,
                                           FacebookException error) {
                        if (error != null && !(error instanceof FacebookOperationCanceledException)) {

                        }

                        try {
                            System.out.println("Invitation was sent to " + values.toString());

                            for (int i = 0; values.containsKey("to[" + i + "]"); i++) {
                                String curId = values.getString("to[" + i + "]");
                            }


                            lastearn("invaite friends", (values.size() - 1));


                            if ((values.size() - 1) >= 1) {
                                if (isNetworkAvailable()) {
                                    updatesharedetails((values.size() - 1), "EFBI");
                                }

                            }

                        } catch (Exception e) {

                        }
                    }
                }).build();

        Window dialog_window = dialog.getWindow();
        dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // dialogAction = action;
        // dialogParams = params;

        dialog.show();
    }

    private void openFacebookSession() {
        Session.openActiveSession(this, true, Arrays.asList("email",
                "user_birthday", "user_hometown", "user_location"),
                new Session.StatusCallback() {
                    @Override
                    public void call(Session session, SessionState state,
                                     Exception exception) {

                        if (session != null && session.isOpened()) {
                            // toast("open");
                            if (btn_str.equals("share")) {

                                publishFeedDialog();
                            } else if (btn_str.equals("invite")) {

                                Bundle params = new Bundle();
                                params.putString("message", "1 PIC 1 WORD Game");
                                showDialogWithoutNotificationBarInvite("apprequests", params);
                            }
                        }

                    }
                });
    }

    public boolean isLoggedIn() {
        Session session = Session.getActiveSession();
        return (session != null && session.isOpened());
    }*/


}
