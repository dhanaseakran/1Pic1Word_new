package nithra.word.game.onepiconeword;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Calendar;

import nithra.word.game.onepiconeword.extra.SharedPreference;


public class ST_Activity extends AppCompatActivity {

    SharedPreference sharedPreference = new SharedPreference();
    // DBHandler_Class db;

    String str_title = "";

    WebView content_view;

    ImageView backdrop;
    String ismark = "";
    SQLiteDatabase myDB;
    String tablenew = "noti_cal";
    String title, message, msgType, date, time;
    int isvalided = 0;
    Bitmap bitmap;
    String str_msg;
    String summary1;
    InterstitialAd interstitialAd_noti;
    LinearLayout addview;

    int show_id, show_ads, bookmark_flag;

    private java.util.List<ResolveInfo> listApp;

    NestedScrollView scrool;
    int share_val = 0;

    FloatingActionButton share_but;

    ImageView go_back;
    String my_user_name = "";
    MediaPlayer mclick;
    Typeface myfont1, myfont2;
    TextView txtaddtopic;
    SharedPreference sp = new SharedPreference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.st_lay);

        myDB = openOrCreateDatabase("myDB", 0, null);
        my_user_name = sharedPreference.getString(getApplicationContext(), "usname1");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                + tablenew
                + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR," +
                "bm VARCHAR,ntype VARCHAR,url VARCHAR);");

        myDB.execSQL("CREATE TABLE IF NOT EXISTS share_noti (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,sahre_msg VARCHAR,date VARCHAR,time VARCHAR);");


        backdrop = (ImageView) findViewById(R.id.backdrop);


        interstitialAd_noti = new InterstitialAd(this);
        interstitialAd_noti.setAdUnitId("ca-app-pub-4267540560263635/9179093905");
        AdRequest notadRequest1 = new AdRequest.Builder().build();
        interstitialAd_noti.loadAd(notadRequest1);


        content_view = (WebView) findViewById(R.id.noti_web);
        share_but = (FloatingActionButton) findViewById(R.id.share_but);


        //addview = (LinearLayout) findViewById(R.id.ad_lay);

        go_back = (ImageView) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_back();

            }
        });

        txtaddtopic = (TextView) findViewById(R.id.txtaddtopic);
        Bundle extras;
        extras = getIntent().getExtras();
        if (extras != null) {

            int idd = extras.getInt("idd");
            int adss = extras.getInt("Noti_add");

            if (idd != 0) {
                show_id = idd;
            } else {
                show_id = sharedPreference.getInt(getApplicationContext(), "Noti_id");
            }

            if (adss != 0) {
                show_ads = adss;
                sharedPreference.putInt(getApplicationContext(), "Noti_add", show_ads);
            } else {
                show_ads = sharedPreference.getInt(getApplicationContext(), "Noti_add");
            }

        }
        myDB.execSQL("update " + tablenew + " set isclose='1' where id='" + show_id + "'");
        Cursor c = myDB.rawQuery("select * from " + tablenew + " where id =" + show_id + " ", null);
        c.moveToFirst();

        title = c.getString(c.getColumnIndex("bm"));
        message = c.getString(c.getColumnIndex("message"));
        msgType = c.getString(c.getColumnIndex("type"));
        date = c.getString(c.getColumnIndex("date"));
        time = c.getString(c.getColumnIndex("time"));
        String url = c.getString(c.getColumnIndex("url"));
        str_title = title;

        /*Cursor c1 = myDB.rawQuery("select * from notify_mark where id =" +show_id + " ", null);
        if(c1.getCount()!=0)
        {
            bookmark.setImageResource(R.drawable.bookmark);
            bookmark_flag=1;
        }
        else
        {
            bookmark.setImageResource(R.drawable.bookmark_1);
            bookmark_flag=0;
        }

        bookmark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (bookmark_flag == 0) {
                    myDB.execSQL("INSERT INTO notify_mark(id) values" + " ('" + show_id + "');");

                    Utils.toast_center(ST_Activity.this, "Message saved");
                    bookmark.setImageResource(R.drawable.bookmark);
                    bookmark_flag = 1;
                } else {
                    myDB.execSQL("delete from notify_mark where id = '" + show_id + "'");
                    Utils.toast_center(ST_Activity.this, "Message deleted");
                    bookmark.setImageResource(R.drawable.bookmark_1);
                    bookmark_flag = 0;
                }
            }
        });*/


        content_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        TextView tit_txt = (TextView) findViewById(R.id.sticky);
        tit_txt.setText(str_title);
        txtaddtopic.setText(str_title);

        WebSettings ws = content_view.getSettings();
        ws.setJavaScriptEnabled(true);


        final String summary = "<!DOCTYPE html> <html><head> </head> <body >"
                + "<br><br><br>" + message + "</body></html>";


        summary1 = "<!DOCTYPE html> <html><head> </head> <body >"
                + "<br><br><br>" + message + "</body></html>";


        String str = message.substring(0, 4);

        if (str.equals("http")) {
            content_view.loadUrl(message);
        } else {
            content_view.loadDataWithBaseURL("", summary, "text/html", "utf-8", null);
        }

        share_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog shareDialog = new Dialog(ST_Activity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                shareDialog.setContentView(R.layout.share_dialog);

                ListView share_list = (ListView) shareDialog.findViewById(R.id.share_list);

                listApp = showAllShareApp();
                if (listApp != null) {
                    share_list.setAdapter(new MyAdapter());
                    share_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            share(listApp.get(position));
                            shareDialog.dismiss();
                        }
                    });
                }
                shareDialog.show();
            }
        });

        content_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;
            }
        });

        content_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;
            }
        });


        content_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);

                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                try {
                    Utils.mProgress(ST_Activity.this, "Loading please wait...", true).show();
                } catch (Exception e) {

                }

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    Utils.mProgress.dismiss();
                } catch (Exception e) {

                }
                super.onPageFinished(view, url);
            }
        });


        Glide
                .with(ST_Activity.this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.app_logo)
                .into(backdrop);
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            Log.i("Long", "Long press!");
            /*btn_close.setVisibility(View.VISIBLE);*/
            //slideInFab(btn_close);
        }
    };

    public void go_back() {
        if (sharedPreference.getInt(ST_Activity.this, "Noti_add") == 1) {
            if (sp.getString(ST_Activity.this, "PointsPurchase").equals("")) {
                if (interstitialAd_noti.isLoaded()) {
                    interstitialAd_noti.show();

                    interstitialAd_noti.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent i = new Intent(ST_Activity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        }
                    });
                } else {
                    sharedPreference.putInt(getApplicationContext(), "Noti_add", 0);
                    Intent i = new Intent(ST_Activity.this, MainActivity.class);
                    finish();
                    startActivity(i);
                }
            } else {
                sharedPreference.putInt(getApplicationContext(), "Noti_add", 0);
                Intent i = new Intent(ST_Activity.this, MainActivity.class);
                finish();
                startActivity(i);
            }

        } else {
            if (sp.getString(ST_Activity.this, "PointsPurchase").equals("")) {
                if (interstitialAd_noti.isLoaded()) {
                    interstitialAd_noti.show();

                    interstitialAd_noti.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            finish();
                        }
                    });
                } else {
                    sharedPreference.putInt(getApplicationContext(), "Noti_add", 0);
                    finish();
                }
            } else {
                sharedPreference.putInt(getApplicationContext(), "Noti_add", 0);
                finish();
            }


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        go_back();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreference.getInt(ST_Activity.this, "open" + get_curday()) == 0) {
            sharedPreference.putInt(ST_Activity.this, "open" + get_curday(), 1);
        }
        addview = (LinearLayout) findViewById(R.id.ad_lay);

        if (sp.getString(ST_Activity.this, "PointsPurchase").equals("")) {
            adds(addview);
        } else {
            addview.setVisibility(View.GONE);
        }

    }

    public void adds(final LinearLayout layout) {
        AdView adView = new AdView(ST_Activity.this);
        adView.setAdUnitId("ca-app-pub-4267540560263635/3708077900");
        //AdView.setAdUnitId("ca-app-pub-4267540560263635/7816025901");
        adView.setAdSize(AdSize.SMART_BANNER);
        try {
            layout.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        layout.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                layout.setVisibility(View.VISIBLE);
            }
        });
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
    }


   /* public static void load_addFromMain(Context context, LinearLayout add_banner) {
        adds = add_banner;
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

    public String get_curday() {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }


    public void slideInFab(final AppCompatButton mFab) {


        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mFab.getLayoutParams();
        float dy = mFab.getWidth() + lp.rightMargin;
       /* if (mFab.getTranslationX() != dy) {
            return;
        }*/


        mFab.setVisibility(View.VISIBLE);
        mFab.animate()
                .setStartDelay(0)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .translationX(-lp.rightMargin)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        super.onAnimationEnd(animation);

                    }
                })
                .start();

    }

    public void slideOutFab(final AppCompatButton mFab) {

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mFab.getLayoutParams();
       /* if (mFab.getTranslationX() != 0f) {
            return;
        }*/


        mFab.animate()
                .setStartDelay(0)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .translationX(mFab.getWidth() - lp.rightMargin)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        super.onAnimationEnd(animation);
                        mFab.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }


    private void share(ResolveInfo appInfo) {

        String sharefinal;
        String str = summary1;
        //String str=cat_name.get(pos) + "\n\n" + Html.fromHtml(content.get(pos));
        //String value = Templehistorydistrict.arrayList.get(pos).getContent();;
        str = Html.fromHtml(str).toString();
        sharefinal = str;
        String value = ("Hi! Simple and interesting word game for all ages and also it improves our vocabulary. Click the link to download : \n https://goo.gl/8fxkWo");


        if (appInfo.activityInfo.packageName.equals("com.whatsapp")) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/*");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "1 PIC 1 WORD");
            Uri uriUrl = Uri.parse("whatsapp://send?text=" + value + "\n" + sharefinal+ "\n\n\n" +value);
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(uriUrl);
            sendIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName, appInfo.activityInfo.name));
            startActivity(sendIntent);

        } else {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "1 PIC 1 WORD");
            sendIntent.putExtra(Intent.EXTRA_TEXT, value + "\n" + sharefinal+ "\n" +value);
            sendIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName, appInfo.activityInfo.name));
            sendIntent.setType("text/*");
            startActivity(sendIntent);
        }
    }

    @SuppressLint("WrongConstant")
    private java.util.List<ResolveInfo> showAllShareApp() {

        java.util.List<ResolveInfo> mApps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        intent.setType("text/plain");
        PackageManager pManager = getPackageManager();
        mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }

    class MyAdapter extends BaseAdapter {

        PackageManager pm;

        public MyAdapter() {
            pm = getPackageManager();
        }


        @Override
        public int getCount() {
            return listApp.size();
        }

        @Override
        public Object getItem(int position) {
            return listApp.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_share_app, parent, false);
                holder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_logo);
                holder.tvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
                holder.tvPackageName = (TextView) convertView.findViewById(R.id.tv_app_package_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ResolveInfo appInfo = listApp.get(position);
            holder.ivLogo.setImageDrawable(appInfo.loadIcon(pm));
            holder.tvAppName.setText(appInfo.loadLabel(pm));
            holder.tvPackageName.setText(appInfo.activityInfo.packageName);

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView ivLogo;
        TextView tvAppName;
        TextView tvPackageName;
    }
}