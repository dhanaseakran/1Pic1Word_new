package nithra.word.game.onepiconeword.daily_test;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import nithra.word.game.onepiconeword.extra.SharedPreference;


public class Dailytest extends BroadcastReceiver  {

    final public static String ONE_TIME = "onetime";
    static SharedPreference sp=new SharedPreference();

    Dailytest_noti_helper dailytest_noti_helper=null;

    @Override
    public void onReceive(Context context, Intent intent) {

        dailytest_noti_helper= new Dailytest_noti_helper(context);

        int min = 1;
        int max = 7;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        System.out.println("-----gg i1 : "+i1);
        System.out.println("-----gg notification_setting : "+sp.getInt(context, "notification_setting"));

        Boolean isBooted = false;

        final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(BOOT_ACTION)) {
            isBooted = true;

        } else {
            isBooted = false;
        }

       // if (sp.getInt(context, "notification_setting") == 1) {

            if (!isBooted) {

                if (i1 == 1) {
                    dailytest_noti_helper.createNotification_double(context);

                   // dailytest_noti_helper.expandNoti(context);

                   // expandNoti(context);
                } else if (i1 == 2) {

                    dailytest_noti_helper.bigPic(context);
                } else if (i1 == 3) {

                    dailytest_noti_helper.normalNoti(context);
                } else if (i1 == 4) {
                    dailytest_noti_helper.bigPic(context);

                } else if (i1 == 5) {

                    dailytest_noti_helper.createNotification_double(context);
                } else if (i1 == 6) {

                    dailytest_noti_helper.bigPic(context);
                } else if (i1 == 7) {
                    dailytest_noti_helper.normalNoti(context);

                  //  dailytest_noti_helper.expandNoti(context);

                    //expandNoti(context);
                }
            }
       // }



        CancelAlarm(context);

        try

        {

            SetAlarm1(context, Dailytest.armTodayOrTomo1("8", "0",context));
            System.out.println("Aleram-----2");

        } catch (
                ParseException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void SetAlarm1(Context context, String armTodayOrTomo1) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Dailytest.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);

        int min = 6;
        int max = 23;

        Random r = new Random();
        int random_time = r.nextInt(max - min + 1) + min;

        sp.putInt(context,"SetAlarm1",random_time);


        System.out.println("=====----=====random_time SetAlarm1 : "+random_time);

        @SuppressLint("WrongConstant")
        PendingIntent pi = PendingIntent.getBroadcast(context, 4, intent, 4);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, random_time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (armTodayOrTomo1.equals("tomo")) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        }
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
    }

    public static String armTodayOrTomo1(String selectedHour, String selectedMinute,Context context) throws ParseException {

       /* String defti = selectedHour + ":" + selectedMinute;
        Time time = new Time();
        time.setToNow();

        String armTodayOrTomo1 = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date time1 = sdf.parse(time.hour + ":" + time.minute);
        Date time2 = sdf.parse(selectedHour + ":" + selectedMinute);

        System.out.println("curTime 2 : " + time.hour + ":" + time.minute);
        System.out.println("newTime 2 : " + selectedHour + ":" + selectedMinute);
        if (time1.compareTo(time2) >= 0) {
            armTodayOrTomo1 = "tomo";
        } else {
            armTodayOrTomo1 = "today";
        }
        System.out.println("A2----------" + armTodayOrTomo1);*/


      /*  int min = 6;
        int max = 23;

        Random r = new Random();
        int random_time = r.nextInt(max - min + 1) + min;*/


        int random_time =sp.getInt(context,"SetAlarm1");

        System.out.println("=====----=====random_time armTodayOrTomo1 : "+random_time);

        selectedHour=""+random_time;


        Time time = new Time();
        time.setToNow();

        String armTodayOrTomo1 = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date time1 = sdf.parse(time.hour + ":" + time.minute);
        Date time2 = sdf.parse(selectedHour + ":" + selectedMinute);

        System.out.println("curTime 2 : " + time.hour + ":" + time.minute);
        System.out.println("newTime 2 : " + selectedHour + ":" + selectedMinute);
        if (time1.compareTo(time2) >= 0) {
            armTodayOrTomo1 = "tomo";
        } else {
            armTodayOrTomo1 = "today";
        }
        System.out.println("A2----------" + armTodayOrTomo1);
        return armTodayOrTomo1;
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, Dailytest.class);
        @SuppressLint("WrongConstant")
        PendingIntent sender = PendingIntent.getBroadcast(context, 4, intent, 4);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        sender.cancel();
    }

}
