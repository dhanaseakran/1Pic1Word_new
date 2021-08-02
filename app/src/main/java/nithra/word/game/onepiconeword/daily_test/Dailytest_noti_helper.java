package nithra.word.game.onepiconeword.daily_test;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;
import java.util.Calendar;
import nithra.word.game.onepiconeword.Game_Play_Activity;
import nithra.word.game.onepiconeword.R;

class Dailytest_noti_helper extends ContextWrapper {

    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "default";
    NotificationChannel chan1 = null;

    Context context;

    public Dailytest_noti_helper(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan1 = new NotificationChannel(PRIMARY_CHANNEL, "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setShowBadge(true);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan1);
        }
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public void expandNoti(Context context) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            int icon = R.drawable.ic_find;
            long when = System.currentTimeMillis();
            Notification notification = new Notification(icon, "Guess the picture! Join the Fun Now!", when);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.daily_normal);
            contentView.setImageViewResource(R.id.noti, R.drawable.unexp);

            notification.contentView = contentView;
            if (Build.VERSION.SDK_INT >= 16) {
                RemoteViews expandView = new RemoteViews(context.getPackageName(), R.layout.daily_normal_expand);
                expandView.setImageViewResource(R.id.noti_img, R.drawable.exp2);
                notification.bigContentView = expandView;
            }

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 4, intent, 0);
            notification.contentIntent = contentIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                notification.priority |= Notification.PRIORITY_MAX;
            }
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
            mNotificationManager.notify(4, notification);


        } else {
            int icon = R.drawable.ic_find;
            long when = System.currentTimeMillis();
            Notification notification = new Notification(icon, "Guess the picture! Join the Fun Now!", when);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.daily_normal);
            contentView.setImageViewResource(R.id.noti, R.drawable.unexp);

            notification.contentView = contentView;
            if (Build.VERSION.SDK_INT >= 16) {
                RemoteViews expandView = new RemoteViews(context.getPackageName(), R.layout.daily_normal_expand);
                expandView.setImageViewResource(R.id.noti_img, R.drawable.exp2);
                notification.bigContentView = expandView;
            }

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 4, intent, 0);
            notification.contentIntent = contentIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                notification.priority |= Notification.PRIORITY_MAX;
            }
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
            mNotificationManager.notify(4, notification);
        }


    }

    public void bigPic(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the style object with BigPictureStyle subclass.
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.setBigContentTitle("Guess the picture! Join the Fun Now!");
            notiStyle.setSummaryText("");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigpic);

            notiStyle.bigPicture(icon);

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent, 0);
            Bitmap tempBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo);
            Notification myNotification = new NotificationCompat.Builder(context, PRIMARY_CHANNEL)
                    .setSmallIcon(R.drawable.ic_find)
                    .setAutoCancel(true)
                    .setLargeIcon(tempBMP)
                    .setPriority(2)
                    .setContentIntent(pendingIntent1)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle("Guess the picture! Join the Fun Now!")
                    .setContentText("")
                    .setStyle(notiStyle).build();

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(4, myNotification);

           /* // Create the style object with BigPictureStyle subclass.
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.setBigContentTitle("எண்ணிலடங்காத செந்தமிழ் வார்த்தைகளை செம்மொழி வேட்டையில் காண்போம் !!");
            notiStyle.setSummaryText("");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigpic);

            notiStyle.bigPicture(icon);

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent, 0);
            Bitmap tempBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_find);
            Notification.Builder mBuilder = new Notification.Builder(context, PRIMARY_CHANNEL)
                    .setContentTitle("எண்ணிலடங்காத செந்தமிழ் வார்த்தைகளை செம்மொழி வேட்டையில் காண்போம் !!")
                    .setContentText("எண்ணிலடங்காத செந்தமிழ் வார்த்தைகளை செம்மொழி வேட்டையில் காண்போம் !!")
                    .setContentIntent(pendingIntent1)
                    .setSmallIcon(getSmallIcon())
                    .setColor(Color.parseColor("#6460AA"))
                    .setLargeIcon(icon)
                    .setGroup("எண்ணிலடங்காத செந்தமிழ் வார்த்தைகளை செம்மொழி வேட்டையில் காண்போம் !!")
                    .setStyle(bigtext1("எண்ணிலடங்காத செந்தமிழ் வார்த்தைகளை செம்மொழி வேட்டையில் காண்போம் !!","செம்மொழி  வேட்டை","வேட்டையில் காண்போம் !!"))
                    .setAutoCancel(true);
            notify(0,mBuilder);*/

        } else {
            // Create the style object with BigPictureStyle subclass.
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.setBigContentTitle("Guess the picture! Join the Fun Now!");
            notiStyle.setSummaryText("");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bigpic);

            notiStyle.bigPicture(icon);

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent, 0);
            Bitmap tempBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_logo);
            Notification myNotification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_find)
                    .setAutoCancel(true)
                    .setLargeIcon(tempBMP)
                    .setPriority(2)
                    .setContentIntent(pendingIntent1)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle("Guess the picture! Join the Fun Now!")
                    .setContentText("")
                    .setStyle(notiStyle).build();

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(4, myNotification);
        }

    }

    public void normalNoti(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL)
                    .setSmallIcon(R.drawable.ic_find)
                    .setAutoCancel(true)
                    .setContentTitle("1 PIC 1 WORD !!")
                    .setContentText("Try this Interesting Pictoword Game to keep yourself relaxed..");

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            Calendar calendar2 = Calendar.getInstance();
            int cur_year = calendar2.get(Calendar.YEAR);
            int cur_month = calendar2.get(Calendar.MONTH);
            int cur_day = calendar2.get(Calendar.DAY_OF_MONTH);

            String str_month = "" + (cur_month + 1);
            if (str_month.length() == 1) {
                str_month = "0" + str_month;
            }

            String str_day = "" + cur_day;
            if (str_day.length() == 1) {
                str_day = "0" + str_day;
            }
            String str_date = cur_year + "-" + str_month + "-" + str_day;

            intent.putExtra("datee", str_date);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(4, builder.build());


        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_find)
                    .setAutoCancel(true)
                    .setContentTitle("1 PIC 1 WORD !!")
                    .setContentText("Try this Interesting Pictoword Game to keep yourself relaxed..");

            Intent intent = new Intent(context, Game_Play_Activity.class);
            intent.putExtra("Dailytest_ok", "Dailytest_ok");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            Calendar calendar2 = Calendar.getInstance();
            int cur_year = calendar2.get(Calendar.YEAR);
            int cur_month = calendar2.get(Calendar.MONTH);
            int cur_day = calendar2.get(Calendar.DAY_OF_MONTH);

            String str_month = "" + (cur_month + 1);
            if (str_month.length() == 1) {
                str_month = "0" + str_month;
            }

            String str_day = "" + cur_day;
            if (str_day.length() == 1) {
                str_day = "0" + str_day;
            }
            String str_date = cur_year + "-" + str_month + "-" + str_day;

            intent.putExtra("datee", str_date);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(4, builder.build());
        }


    }

    public void createNotification_double(final Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL);
            Intent i = new Intent(context, Game_Play_Activity.class);
            i.putExtra("Dailytest_ok", "Dailytest_ok");
            //sps.putString(context, "Daily", "on");
            //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            Calendar calendar2 = Calendar.getInstance();
            int cur_year = calendar2.get(Calendar.YEAR);
            int cur_month = calendar2.get(Calendar.MONTH);
            int cur_day = calendar2.get(Calendar.DAY_OF_MONTH);

            String str_month = "" + (cur_month + 1);
            if (str_month.length() == 1) {
                str_month = "0" + str_month;
            }

            String str_day = "" + cur_day;
            if (str_day.length() == 1) {
                str_day = "0" + str_day;
            }
            String str_date = cur_year + "-" + str_month + "-" + str_day;

            i.putExtra("datee", str_date);

            PendingIntent intent = PendingIntent.getActivity(context, 4, i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);
            builder.setTicker(context.getResources().getString(R.string.app_name));
            builder.setSmallIcon(R.drawable.ic_find);
            builder.setAutoCancel(true);
            Notification notification = builder.build();
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.daily_normal);

            notification.contentView = contentView;
            if (Build.VERSION.SDK_INT >= 16) {
                RemoteViews expandView = new RemoteViews(context.getPackageName(), R.layout.daily_normal_expand);
                expandView.setImageViewResource(R.id.noti_img, R.drawable.exp1);
                notification.bigContentView = expandView;
            }

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(4, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Intent i = new Intent(context, Game_Play_Activity.class);
            i.putExtra("Dailytest_ok", "Dailytest_ok");
            //sps.putString(context, "Daily", "on");
            //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            Calendar calendar2 = Calendar.getInstance();
            int cur_year = calendar2.get(Calendar.YEAR);
            int cur_month = calendar2.get(Calendar.MONTH);
            int cur_day = calendar2.get(Calendar.DAY_OF_MONTH);

            String str_month = "" + (cur_month + 1);
            if (str_month.length() == 1) {
                str_month = "0" + str_month;
            }

            String str_day = "" + cur_day;
            if (str_day.length() == 1) {
                str_day = "0" + str_day;
            }
            String str_date = cur_year + "-" + str_month + "-" + str_day;

            i.putExtra("datee", str_date);

            PendingIntent intent = PendingIntent.getActivity(context, 4, i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);
            builder.setTicker(context.getResources().getString(R.string.app_name));
            builder.setSmallIcon(R.drawable.ic_find);
            builder.setAutoCancel(true);
            Notification notification = builder.build();
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.daily_normal);

            notification.contentView = contentView;
            if (Build.VERSION.SDK_INT >= 16) {
                RemoteViews expandView = new RemoteViews(context.getPackageName(), R.layout.daily_normal_expand);
                expandView.setImageViewResource(R.id.noti_img, R.drawable.exp1);
                notification.bigContentView = expandView;
            }

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(4, notification);
        }


    }

    public NotificationCompat.BigTextStyle bigtext(String Title, String Summary, String bigText) {
        return new NotificationCompat.BigTextStyle()
                .setBigContentTitle(Title)
                .setSummaryText(Summary)
                .bigText(bigText);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification.BigTextStyle bigtext1(String Title, String Summary, String bigText) {
        return new Notification.BigTextStyle()
                .setBigContentTitle(Title)
                .setSummaryText(Summary)
                .bigText(bigText);
    }

    private int getSmallIcon() {
        return R.drawable.ic_find;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }
}
