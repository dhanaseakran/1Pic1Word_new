package nithra.word.game.onepiconeword;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import nithra.word.game.onepiconeword.extra.SharedPreference;


/**
 * Created by NITHRA-G5 on 12/2/2015.
 */
public class Utils {


    public static String ban_id = "ca-app-pub-2830969541475769/8027864734";



    public static String share_body = "அரசு வேலை தேடுபவர்களுக்கு அவசியமான ஒரு இலவச ஆன்ட்ராய்டு அப்ளிகேஷன். உங்கள் கைப்பேசி வழியாக நீங்கள் எங்கிருந்து வேண்டுமானாலும் சுலபமாக படிக்க "
            + "மற்றும் மாதிரி தேர்வுகளை செய்து பார்க்கும் வசதி இந்த இலவச ஆன்ட்ராய்டு அப்ளிகேஷனில் உள்ளது. நீங்கள் தேர்வினை தெளிவுடன் எதிர்கொள்ள இந்த அப்ளிகேஷனை பதிவிறக்கம் செய்யவும்.... "
            + "http://play.google.com/store/apps/details?id="
            + "nithra.tamil.tnpsc.tet.govt.exams"
            + "&referrer=utm_source%3DTNPSCnew_AppShare";

   public static ProgressDialog  mProgress;

    public static int MY_EMAIL_REQUEST_WRITE = 128;
    public static int MY_EMAIL_REQUEST_WRITE2 = 130;


    public static ProgressDialog mProgress(Context context,String txt,Boolean aBoolean){
             mProgress = new ProgressDialog(context);
        mProgress.setMessage(txt);
        mProgress.setCancelable(aBoolean);
        return mProgress;
    }

    public static void  settypeface(Context context,TextView  textView){
        Typeface   tf1 = Typeface.createFromAsset(context.getAssets(), "baamini.ttf");
        textView.setTypeface(tf1);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String android_id(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static int versioncode_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }

    public static String versionname_get(Context context) {
        PackageInfo pInfo = null;

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pInfo.versionName;
    }


    public static void toast_normal(Context context,String str){
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();
    }
    public static void toast_center(Context context,String str){
        Toast toast = Toast.makeText(context,""+str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static String pad(String str){
        if(str.length()==1){
            str="0"+str;
        }
        return str;
    }






    public static void date_put(Context context,String str, int val){
        Calendar calendar=Calendar.getInstance();
        long next_hour= calendar.getTimeInMillis() + val * DateUtils.DAY_IN_MILLIS;

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date results = new Date(next_hour);
        String formatted = sdf1.format(results);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month=rep_month-1;

        String strdate = rep_day + "/" + rep_month + "/" + rep_year;

        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.putString(context, str, strdate);
    }

    public static void ClearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            System.out.println("clr_chace : error ClearCache");
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static Boolean clr_chace(Context context){
        Boolean aBoolean = false;
        SharedPreference sharedPreference = new SharedPreference();
        Calendar calendar = Calendar.getInstance();
        long next_hour = calendar.getTimeInMillis();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/M/yyyy");
        Date result = new Date(next_hour);
        String formatted = sdf1.format(result);

        StringTokenizer st2 = new StringTokenizer(formatted, "/");
        int rep_day = Integer.parseInt(st2.nextToken());
        int rep_month = Integer.parseInt(st2.nextToken());
        int rep_year = Integer.parseInt(st2.nextToken());

        rep_month = rep_month - 1;

        String today_date = rep_day + "/" + rep_month + "/" + rep_year;

        Date date_today = null, date_app_update = null;

        try {
            date_today = sdf1.parse(today_date);
            if (!sharedPreference.getString(context, "clr_chace").equals("")) {
                date_app_update = sdf1.parse(sharedPreference.getString(context, "clr_chace"));
            }
            else{
                date_app_update = sdf1.parse(today_date);;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("clr_chace : error");
        }


        if (sharedPreference.getString(context, "clr_chace").equals("")) {

            aBoolean = true;
            System.out.println("clr_chace : "+aBoolean);
        } else {
            if (date_today.compareTo(date_app_update) >= 0) {
                aBoolean = true;
                System.out.println("clr_chace : "+aBoolean);
            }
        }



        return aBoolean;
    }
    public static String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String Brand = Build.BRAND;
        String Product = Build.PRODUCT;

        return manufacturer + "-" + model + "-" + Brand + "-" + Product;
    }

    public  static String am_pm1(int hur,int min){

        String AM_PM="AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad(""+hur)+" : "+Utils.pad(""+min)+" "+AM_PM;
    }







    public  static String am_pm(int hur,int min){

        String AM_PM="AM";

        if (hur >= 12) {
            hur = hur - 12;
            AM_PM = "PM";
        } else {
            AM_PM = "AM";

        }
        if (hur == 0) {
            hur = 12;
        }

        return Utils.pad(""+hur)+" : "+Utils.pad(""+min)+" "+AM_PM;
    }
}

