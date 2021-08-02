package nithra.word.game.onepiconeword;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import nithra.word.game.onepiconeword.extra.SharedPreference;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    SQLiteDatabase myDB;
    SharedPreference sharedPreference;
    int iddd;
    String msgg, titt, bmmm;
    private NotificationHelper noti;
    int isclose = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());


        myDB = openOrCreateDatabase("myDB", 0, null);
        noti = new NotificationHelper(this);

        String tablenew = "noti_cal";


        myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                + tablenew + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,title VARCHAR,message VARCHAR,date VARCHAR,time VARCHAR,isclose INT(4),isshow INT(4) default 0,type VARCHAR," +
                "bm VARCHAR,ntype VARCHAR,url VARCHAR);");

        sharedPreference = new SharedPreference();
        if (remoteMessage.getData().size() > 0) {
            try {
                Log.e("Data Payload: ", remoteMessage.getData().toString());
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);

                Log.e("JSON_OBJECT", object.toString());
                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(object);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception: ", e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject data) {
        /*Log.e(TAG, "push json: " + json.toString());*/
        /*JSONObject data = null;*/

            /*data = json.getJSONObject("data");*/
        try {
            String message = data.getString("message");
            String title = data.getString("title");
            String date = data.getString("date");
            String time = data.getString("time");
            String type = data.getString("type");
            String bm = data.getString("bm");
            String ntype = data.getString("ntype");
            String url = data.getString("url");
            String pac = data.getString("pac");
            int intent_id = (int) System.currentTimeMillis();
            if (!sharedPreference.getString(getApplicationContext(), "old_msg").equals(message) || !sharedPreference.getString(getApplicationContext(), "old_tit").equals(title)) {
                sharedPreference.putString(getApplicationContext(), "old_msg", message);
                sharedPreference.putString(getApplicationContext(), "old_tit", title);
                try {
                    if (type.equals("s")) {

                    }
                } catch (Exception e) {
                    type = "s";
                }
                try {
                    if (pac.equals("s")) {

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    pac = "no";
                }
                try {
                    if (bm.equals("s")) {

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    bm = "no";
                }
                try {
                    if (ntype.equals("s")) {

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    ntype = "no";
                }

                try {
                    title = URLDecoder.decode(title, "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (type.equals("s")) {
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    myDB.execSQL("INSERT INTO noti_cal(title,message,date,time,isclose,type,bm,ntype,url) values " +
                            "('" + title + "','" + message + "','" + date + "','" + time + "','" + isclose + "','s','" + bm + "','" + ntype + "','" + url + "');");
                    sharedPreference.putInt(this, "typee", 0);
                    Cursor c = myDB.rawQuery("select id from noti_cal", null);
                    c.moveToLast();
                    iddd = c.getInt(0);
                    myDB.close();

                        if (ntype.equals("bt")) {
                            noti.Notification_custom(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("bi")) {
                            noti.Notification_custom(iddd, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else {
                            noti.Notification_bm(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        }

                } else if (type.equals("h")) {
                    msgg = message;
                    titt = title;
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bmmm = bm;
                    if (ntype.equals("bt")) {
                        noti.Notification_custom(0, title, message, url, "bt", bm, sharedPreference
                                .getInt(this, "sund_chk1"), MainActivity.class);
                    } else if (ntype.equals("bi")) {
                        noti.Notification_custom(0, title, message, url, "bi", bm, sharedPreference
                                .getInt(this, "sund_chk1"), MainActivity.class);
                    }
                } else if (type.equals("st")) {
                    msgg = message;
                    titt = title;
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bmmm = bm;
                    myDB.execSQL("INSERT INTO noti_cal(title,message,date,time,isclose,type,bm,ntype,url) values " +
                            "('" + title + "','" + message + "','" + date + "','" + time + "','" + isclose + "','s','" + bm + "','" + ntype + "','" + url + "');");
                    sharedPreference.putInt(this, "typee", 0);
                    Cursor c = myDB.rawQuery("select id from noti_cal", null);
                    c.moveToLast();
                    iddd = c.getInt(0);
                    Cursor c1 = myDB.rawQuery("select id from noti_cal where isclose = '0'", null);
                    if (c1.getCount() != 0) {

                    } else {

                    }
                    myDB.close();
                        if (ntype.equals("bt")) {
                            noti.Notification_bm(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("bi")) {
                            noti.Notification_bm(iddd, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else {
                            noti.Notification_bm(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        }

                } else if (type.equals("w")) {
                    msgg = message;
                    titt = title;
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bmmm = bm;
                    myDB.execSQL("INSERT INTO noti_cal(title,message,date,time,isclose,type,bm,ntype,url) values " +
                            "('" + title + "','" + message + "','" + date + "','" + time + "','" + isclose + "','w','" + bm + "','" + ntype + "','" + url + "');");
                    sharedPreference.putInt(this, "typee", 0);
                    Cursor c = myDB.rawQuery("select id from noti_cal", null);
                    c.moveToLast();
                    iddd = c.getInt(0);
                    Cursor c1 = myDB.rawQuery("select id from noti_cal where isclose = '0'", null);
                    if (c1.getCount() != 0) {

                    } else {

                    }
                    myDB.close();

                    if (ntype.equals("bt")) {
                            noti.Notification_custom(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("bi")) {
                            noti.Notification_custom(iddd, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else {
                            noti.Notification_bm(iddd, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        }

                } else if (type.equals("ns")) {
                    myDB.execSQL("INSERT INTO noti_cal(title,message,date,time,isclose,type,bm,ntype,url) values " +
                            "('" + title + "','" + message + "','" + date + "','" + time + "','" + isclose + "','ns','" + bm + "','" + ntype + "','" + url + "');");
                    Cursor c = myDB.rawQuery("select id from noti_cal", null);
                    c.moveToLast();
                    iddd = c.getInt(0);
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bmmm = bm;
                    Cursor c1 = myDB.rawQuery("select id from noti_cal where isclose = '0'", null);
                    if (c1.getCount() != 0) {

                    } else {

                    }
                    myDB.close();
                } else if (type.equals("ins")) {
                    try {
                        bm = URLDecoder.decode(bm, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                        noti.Notification1(intent_id, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);

                } else if (type.equals("ap")) {
                    if (appInstalledOrNot(pac)) {

                    } else {
                        msgg = message;
                        titt = title;
                        if (ntype.equals("n")) {
                            try {
                                bm = URLDecoder.decode(bm, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            noti.Notification1(intent_id, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("bt")) {
                            try {
                                bm = URLDecoder.decode(bm, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            noti.Notification1(intent_id, title, message, url, "bt", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("bi")) {
                            try {
                                bm = URLDecoder.decode(bm, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            noti.Notification1(intent_id, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        } else if (ntype.equals("w")) {
                            try {
                                bm = URLDecoder.decode(bm, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            noti.Notification1(intent_id, title, message, url, "bi", bm, sharedPreference.getInt(this, "sund_chk1"), ST_Activity.class);
                        }
                    }
                } else if (type.equals("u")) {
                    sharedPreference.putInt(this, "gcmvcode", Integer.parseInt(message));
                    sharedPreference.putInt(this, "isvupdate", 1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean appInstalledOrNot(String uri) {
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
}