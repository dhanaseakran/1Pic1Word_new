package nithra.word.game.onepiconeword;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nithra.word.game.onepiconeword.extra.SharedPreference;


public class Noti_Mark extends Activity {


    RelativeLayout NoNotification_lay;
    TextView txtNoNotification, remove_noti_txt, txtaddtopic;
    ImageView go_back;
    ListView listView;
    LinearLayout ads_lay;

    SQLiteDatabase myDB;
    SharedPreference sharedPreference = new SharedPreference();

    SharedPreference sp = new SharedPreference();

    String my_user_name = "";

    Typeface ttf_1;

    private CustomListAdapter adapter;
    public static List<Item_noti> movieList = new ArrayList<>();

    ImageView deleteall;

    String tablenew = "noti_cal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_noti_mark);

        myDB = this.openOrCreateDatabase("myDB", MODE_PRIVATE, null);

        myDB.execSQL("CREATE TABLE IF NOT EXISTS '" + tablenew + "' (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR,bm VARCHAR,ntype VARCHAR,url VARCHAR)");

        ttf_1 = Typeface.createFromAsset(this.getAssets(), "bubblegum.otf");

        txtaddtopic = (TextView) findViewById(R.id.txtaddtopic);
        txtNoNotification = (TextView) findViewById(R.id.txtNoNotification);
        remove_noti_txt = (TextView) findViewById(R.id.remove_noti_txt);


        go_back = (ImageView) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                startActivity(intent);

            }
        });

        deleteall = (ImageView) findViewById(R.id.deleteall);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog no_datefun = new Dialog(Noti_Mark.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                no_datefun.setContentView(R.layout.nodate_dia);
                no_datefun.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);


                Button btnSet = (Button) no_datefun.findViewById(R.id.btnSet);
                Button btnok = (Button) no_datefun.findViewById(R.id.btnok);
                TextView head_txt = (TextView) no_datefun.findViewById(R.id.head_txt);
                TextView editText1 = (TextView) no_datefun.findViewById(R.id.editText1);
                btnSet.setText("Yes");
                btnok.setText("No");


                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        myDB.execSQL("delete from noti_cal");

                        set_ada();

                        no_datefun.dismiss();

                    }
                });

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        no_datefun.dismiss();
                    }
                });

                no_datefun.show();

            }
        });


        ads_lay = (LinearLayout) findViewById(R.id.ads_lay);
        NoNotification_lay = (RelativeLayout) findViewById(R.id.NoNotification_lay);

        listView = (ListView) findViewById(R.id.listView1);
        adapter = new CustomListAdapter(Noti_Mark.this, movieList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sp.getString(Noti_Mark.this, "PointsPurchase").equals("")) {
            MainActivity.load_addFromMain1(ads_lay);
        } else {
            ads_lay.setVisibility(View.GONE);
        }

        set_ada();
    }


    public void set_ada() {
        Cursor c = myDB.rawQuery("select * from noti_cal order by id desc", null);
        if (c.getCount() != 0) {
            movieList.clear();
            listView.setVisibility(View.VISIBLE);
            NoNotification_lay.setVisibility(View.GONE);
            deleteall.setVisibility(View.VISIBLE);

            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);

                Item_noti movie = new Item_noti();

                movie.setId(c.getInt(c.getColumnIndex("id")));
                movie.setTitle(c.getString(c.getColumnIndex("title")));
                movie.setmessage(c.getString(c.getColumnIndex("message")));
                movie.setmsgType(c.getString(c.getColumnIndex("type")));
                movie.setisclose(c.getInt(c.getColumnIndex("isclose")));
                movie.setbm(c.getString(c.getColumnIndex("bm")));
                movie.seturll(c.getString(c.getColumnIndex("url")));
                movie.setntype(c.getString(c.getColumnIndex("ntype")));
                Cursor c1 = myDB.rawQuery("select * from noti_cal where id =" + c.getInt(c.getColumnIndex("id")) + " ", null);
                if (c1.getCount() == 0) {
                    movie.setismark(0);
                } else {
                    movie.setismark(1);
                }
                c1.close();
                movieList.add(movie);
            }

            adapter.notifyDataSetChanged();
        } else {
            listView.setVisibility(View.GONE);
            deleteall.setVisibility(View.GONE);
            NoNotification_lay.setVisibility(View.VISIBLE);
        }
        c.close();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void gcmfun(final String message, final String title, String type, final int id, String ntype) {


        /* dialog for show message */
        final Dialog dialog_s = new Dialog(Noti_Mark.this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_s.setContentView(R.layout.cross_app1);

        Button ok = (Button) dialog_s.findViewById(R.id.button1);
        ok.setText("Ok");
        TextView textView1 = (TextView) dialog_s.findViewById(R.id.version2);
        TextView textView2 = (TextView) dialog_s.findViewById(R.id.textView2);
        textView1.setText("" + title);
        textView2.setText("" + message);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog_s.dismiss();
            }
        });
        /* dialog for show for web link message */

        final Dialog dialog_w = new Dialog(Noti_Mark.this,
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog_w.setContentView(R.layout.notification_url);
        TextView txtHeading = (TextView) dialog_w.findViewById(R.id.txtHeading);
        Button btnClkHere = (Button) dialog_w.findViewById(R.id.btnClkHere);

        txtHeading.setText(title);
        btnClkHere.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Utils.isNetworkAvailable(Noti_Mark.this)) {

                    otfun(title, message);

                } else {
                    Utils.toast_normal(Noti_Mark.this, "Hey buddy, connect to the network");
                }
                dialog_w.dismiss();
            }
        });


        if (type.equals("s")) {
            if (ntype.equals("bt") || ntype.equals("bi")) {
                sharedPreference.putInt(Noti_Mark.this, "Noti_add", 0);
                Intent i = new Intent(Noti_Mark.this, ST_Activity.class);
                sharedPreference.putInt(Noti_Mark.this, "Noti_id", id);
                i.putExtra("idd", id);
                i.putExtra("Noti_add", 0);
                startActivity(i);
            } else {
                dialog_s.show();
            }
        } else if (type.equals("st")) {
           /* dia_dismiss(dialog_st);
            dialog_st.show();*/
            sharedPreference.putInt(Noti_Mark.this, "Noti_add", 0);
            Intent i = new Intent(Noti_Mark.this, ST_Activity.class);
            sharedPreference.putInt(Noti_Mark.this, "Noti_id", id);
            i.putExtra("idd", id);
            i.putExtra("Noti_add", 0);
            startActivity(i);
        } else if (type.equals("w")) {
            if (ntype.equals("bt") || ntype.equals("bi")) {
                sharedPreference.putInt(Noti_Mark.this, "Noti_add", 0);
                Intent i = new Intent(Noti_Mark.this, ST_Activity.class);
                sharedPreference.putInt(Noti_Mark.this, "Noti_id", id);
                i.putExtra("idd", id);
                i.putExtra("Noti_add", 0);
                startActivity(i);
            } else {
                dialog_w.show();
            }
        }

    }


    public void otfun(String title, String message) {


        final Dialog dialog_ot = new Dialog(Noti_Mark.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog_ot.setContentView(R.layout.otdia);
        TextView titletxtot = (TextView) dialog_ot.findViewById(R.id.title);
        titletxtot.setText(title);
        final LinearLayout addview = (LinearLayout) dialog_ot.findViewById(R.id.addview);
        MainActivity.load_addFromMain1(addview);


        WebView notesWebView = (WebView) dialog_ot
                .findViewById(R.id.webView2);
        WebSettings ws = notesWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        notesWebView.loadUrl(message);

        notesWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        notesWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //	view.loadUrl(url);
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Utils.mProgress(Noti_Mark.this, "Loading. Please wait", true).show();
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
        dialog_ot.show();
    }


    public class CustomListAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private List<Item_noti> movieItems;


        public CustomListAdapter(Activity activity, List<Item_noti> movieItems) {

            this.activity = activity;
            this.movieItems = movieItems;
        }

        @Override
        public int getCount() {
            return movieItems.size();
        }

        @Override
        public Object getItem(int location) {
            return movieItems.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.notify_item1, null);


            TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
            TextView count_txt = (TextView) convertView.findViewById(R.id.count_txt);
           // ImageView cunt = (ImageView) convertView.findViewById(R.id.cunt);
            final Button markk = (Button) convertView.findViewById(R.id.markk);
            markk.setVisibility(View.GONE);
            Item_noti m = movieItems.get(position);

            count_txt.setText(""+(position+1));

            if (m.getismark() == 0) {
                markk.setBackgroundResource(R.drawable.bookmark_1);
            } else {
                markk.setBackgroundResource(R.drawable.bookmark);
            }

            markk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item_noti m = movieItems.get(position);
                    Cursor c1 = myDB.rawQuery("select * from noti_cal where id =" + m.getId() + " ", null);
                    if (c1.getCount() == 0) {
                        myDB.execSQL("INSERT INTO noti_cal(id) values" + " ('" + m.getId() + "');");
                        Utils.toast_center(Noti_Mark.this, "Notice saved");
                        markk.setBackgroundResource(R.drawable.bookmark);
                    } else {
                        myDB.execSQL("delete from noti_cal where id = '" + m.getId() + "'");
                        movieItems.remove(position);
                        adapter.notifyDataSetChanged();
                        Utils.toast_center(Noti_Mark.this, "The announcement has been deleted");
                        if (movieItems.isEmpty()) {
                            listView.setVisibility(View.GONE);
                            NoNotification_lay.setVisibility(View.VISIBLE);
                        }
                    }
                    c1.close();
                }
            });

           /* Glide
                    .with(activity)
                    .load(m.geturll())
                    .centerCrop()
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .crossFade()
                    .into(cunt);*/

            textView1.setText("" + m.getbm());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Item_noti m = movieItems.get(position);
                    myDB.execSQL("update noti_cal set isclose='1' where id='" + m.getId() + "'");
                    gcmfun(m.getmessage(), m.getTitle(), m.getmsgType(), m.getId(), m.getntype());

                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Item_noti m = movieItems.get(position);
                    delet_fun("" + m.getId(), 0);
                    return false;
                }
            });
            if (m.getisclose() == 1) {
                convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#B2DFDB"));
            }

            return convertView;
        }


    }


    public void delet_fun(final String id, final int type) {

        final Dialog no_datefun = new Dialog(Noti_Mark.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        no_datefun.setContentView(R.layout.nodate_dia);
        no_datefun.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        Button btnSet = (Button) no_datefun.findViewById(R.id.btnSet);
        Button btnok = (Button) no_datefun.findViewById(R.id.btnok);
        TextView head_txt = (TextView) no_datefun.findViewById(R.id.head_txt);
        TextView editText1 = (TextView) no_datefun.findViewById(R.id.editText1);
        btnSet.setText("Yes");
        btnok.setText("No");

        head_txt.setVisibility(View.GONE);


        if (type == 0) {
            editText1.setText("Do you want to delete this notification?");
        } else {
            editText1.setText("Do you want to delete all notifications?");
        }


        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    myDB.execSQL("delete from noti_cal where id = '" + id + "'");
                    myDB.execSQL("delete from noti_cal where id = '" + id + "'");
                } else {
                    myDB.execSQL("delete from noti_cal ");
                }
                set_ada();
                no_datefun.dismiss();

            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_datefun.dismiss();
            }
        });


        no_datefun.show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        startActivity(intent);

    }
}
