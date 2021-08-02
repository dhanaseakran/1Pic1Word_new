package nithra.word.game.onepiconeword;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import nithra.word.game.onepiconeword.extra.DataBaseHelper;
import nithra.word.game.onepiconeword.extra.SharedPreference;

public class Loading_Activity extends AppCompatActivity {

    SharedPreference sp = new SharedPreference();

    String versionName = "";
    int versionCode = 0;

    DataBaseHelper myDbHelper;

    SQLiteDatabase mydb_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (sp.getString(Loading_Activity.this,"ask_rate_us").equals(""))
        {

            Date currentDate = new Date();
            // convert date to calendar
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            // manipulate date
           /* c.add(Calendar.YEAR, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, 15); //same with c.add(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.HOUR, 1);
            c.add(Calendar.MINUTE, 1);
            c.add(Calendar.SECOND, 1);*/

            c.add(Calendar.DAY_OF_MONTH, 15);

            String day1 =""+ (c.get(Calendar.DAY_OF_MONTH));
            String month1 =""+ (c.get(Calendar.MONTH) + 1);
            String year1 =""+ c.get(Calendar.YEAR);
            if (day1.length() == 1)
                day1 = "0" + day1;
            if (month1.length() == 1)
                month1 = "0" + month1;

            sp.putString(Loading_Activity.this, "ask_rate_us", day1 + "/" + month1 + "/" + year1);

            System.out.println("ask_rate_us =====---- "+sp.getString(Loading_Activity.this,"ask_rate_us"));

        }



        myDbHelper = new DataBaseHelper(Loading_Activity.this);
        mydb_copy = this.openOrCreateDatabase("findtheword_copy.db", MODE_PRIVATE, null);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        if (sp.getString(Loading_Activity.this, "install_date").equals("")) {
            sp.putString(Loading_Activity.this, "install_date", mdformat.format(calendar.getTime()));
        }

        sp.putInt(Loading_Activity.this, "versionCode", versionCode);
        sp.putString(Loading_Activity.this, "versionName", versionName);


        if (sp.getInt(Loading_Activity.this, "DB_MOVE")==0) {
            sp.putInt(Loading_Activity.this, "DB_MOVE", 1);
            try {
                myDbHelper.createDataBase();
                System.out.println("===DB_MOVE createDataBase");
            } catch (IOException ioe) {
                System.out.println("===DB_MOVE ioe:" + ioe + "Unable to create database");
                throw new Error("Unable to create database");
            }
            try {
                myDbHelper.openDataBase();
                System.out.println("===DB_MOVE openDataBase");

            } catch (SQLException sqle) {
                throw sqle;
            }

            Copy_DB_first();
        }

        if (sp.getString(Loading_Activity.this,"first_setting").equals(""))
        {
            sp.putString(Loading_Activity.this, "first_setting", "yes");
            sp.putString(Loading_Activity.this, "wordsound", "yes");
            sp.putString(Loading_Activity.this, "coinsound", "yes");
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading_Activity.this, MainActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(intent);
            }
        }, 3000);
    }

    private void Copy_DB_first() {
        System.out.println("Database Coppy");
        try {
            File data = Environment.getDataDirectory();
            String currentDBPath = "/data/" + getApplicationContext().getPackageName() + "/databases/findtheword_copy.db";
            String backupDBPath = "/data/" + getApplicationContext().getPackageName() + "/databases/findtheword";
            File currentDB = new File("" + data, "" + currentDBPath);
            File backupDB = new File("" + data, "" + backupDBPath);
            System.out.println("===DB_MOVE currentDB.exists():"+currentDB.exists());
            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                System.out.println("===DB_MOVE Database Restored successfully");
            } else {
                System.out.println("===DB_MOVE DatabaseRestored error");
            }
        } catch (Exception e) {
            System.out.println("===DB_MOVE Database Coppy error : "+e);
        }
    }
}
