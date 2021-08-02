package nithra.word.game.onepiconeword;

import android.annotation.SuppressLint;
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
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String PRIMARY_CHANNEL = "default";
    NotificationChannel chan1 = null;
    Context context;

    @SuppressLint("WrongConstant")
    public NotificationHelper(Context ctx) {
        super(ctx);
        context = ctx;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan1 = new NotificationChannel(PRIMARY_CHANNEL, "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setShowBadge(true);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan1);
        }
    }

    public void Notification_normal(int id, String title, String body, String imgg, String style, String bm, int sund_chk1, Class activity) {
        Uri mUri;
        mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder nb;
            if (style.equals("bt")) {
                nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                        .setContentTitle(title)
                        .setSound(mUri)
                        .setContentText(body)
                        .setContentIntent(resultPendingIntent(title, body, id, activity))
                        .setSmallIcon(getSmallIcon())
                        .setColor(Color.parseColor("#6460AA"))
                        .setLargeIcon(LargeIcon(imgg))
                        .setGroup("" + title)
                        .setStyle(bigtext1(title, "1 PIC 1 WORD", body))
                        .setAutoCancel(true);
            } else {
                nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                        .setContentTitle(title)
                        .setSound(mUri)
                        .setContentText(body)
                        .setContentIntent(resultPendingIntent(title, body, id, activity))
                        .setSmallIcon(getSmallIcon())
                        .setColor(Color.parseColor("#6460AA"))
                        .setLargeIcon(LargeIcon(imgg))
                        .setGroup("" + title)
                        .setStyle(bigimg1(title, "1 PIC 1 WORD", imgg))
                        .setAutoCancel(true);
            }
            notify(id, nb);
        } else {
            Notification myNotification;
            if (style.equals("bt")) {
                System.out.println("bt___________");
                myNotification = new NotificationCompat.Builder(context)
                        .setSound(mUri)
                        .setSmallIcon(getSmallIcon())
                        .setColor(Color.parseColor("#6460AA"))
                        .setLargeIcon(getlogo1())
                        .setAutoCancel(true)
                        .setPriority(2)
                        .setContentIntent(resultPendingIntent(title, body, id, activity))
                        .setContentTitle(title)
                        .setContentText(body)
                        .setGroup("" + title)
                        .setStyle(bigtext(title, "1 PIC 1 WORD", body))
                        .build();
                notify(id, myNotification);
            } else {
                myNotification = new NotificationCompat.Builder(context)
                        .setSound(mUri)
                        .setSmallIcon(getSmallIcon())
                        .setColor(Color.parseColor("#6460AA"))
                        .setLargeIcon(getlogo1())
                        .setAutoCancel(true)
                        .setPriority(2)
                        .setContentIntent(resultPendingIntent(title, body, id, activity))
                        .setContentTitle(title)
                        .setContentText(body)
                        .setGroup("" + title)
                        .setStyle(bigimg(title, "1 PIC 1 WORD", imgg))
                        .build();
                notify(id, myNotification);
            }
        }
    }

    public void Notification_bm(int id, String title, String body, String imgg, String style, String bm, int sund_chk1, Class activity) {
        try {
            Uri mUri;
            mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            System.out.println("mUri : " + mUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder nb;
                if (style.equals("bt")) {
                    nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setContentTitle("1 PIC 1 WORD")
                            .setSound(mUri)
                            .setContentText("")
                            .setContentIntent(resultPendingIntent(bm, body, id, activity))
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(LargeIcon(imgg))
                            .setGroup("" + title)
                            .setStyle(bigtext1("1 PIC 1 WORD", "1 PIC 1 WORD", bm))
                            .setAutoCancel(true);
                } else {
                    nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setContentTitle(bm)
                            .setSound(mUri)
                            .setContentText("")
                            .setContentIntent(resultPendingIntent(bm, body, id, activity))
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(LargeIcon(imgg))
                            .setGroup("" + title)
                            .setStyle(bigimg1("1 PIC 1 WORD", bm, imgg))
                            .setAutoCancel(true);
                }
                notify(id, nb);
            } else {
                Notification myNotification;
                if (style.equals("bt")) {
                    myNotification = new NotificationCompat.Builder(context)
                            .setSound(mUri)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(getlogo1())
                            .setAutoCancel(true)
                            .setPriority(2)
                            .setContentIntent(resultPendingIntent(bm, body, id, activity))
                            .setContentTitle("1 PIC 1 WORD")
                            .setContentText("")
                            .setGroup("" + title)
                            .setStyle(bigtext("1 PIC 1 WORD", "1 PIC 1 WORD", bm))
                            .build();
                    notify(id, myNotification);
                } else {
                    myNotification = new NotificationCompat.Builder(context)
                            .setSound(mUri)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(getlogo1())
                            .setAutoCancel(true)
                            .setPriority(2)
                            .setContentIntent(resultPendingIntent(bm, body, id, activity))
                            .setContentTitle(bm)
                            .setContentText("")
                            .setGroup("" + title)
                            .setStyle(bigimg("1 PIC 1 WORD", bm, imgg))
                            .build();
                    notify(id, myNotification);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("---notifi e : "+e);
        }
    }

    public void Notification1(int id, String title, String body1, String imgg, String style, String bm, int sund_chk1, Class activity) {
        try {
            Uri mUri;
            mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder nb;
                if (style.equals("bt")) {
                    nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setContentTitle(title)
                            .setSound(mUri)
                            .setContentText("")
                            .setContentIntent(resultPendingIntent(bm, body1, id, activity))
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(LargeIcon(imgg))
                            .setGroup("" + title)
                            .setStyle(bigtext1(title, "1 PIC 1 WORD", ""))
                            .setAutoCancel(true);
                } else {
                    nb = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setContentTitle(title)
                            .setSound(mUri)
                            .setContentText("")
                            .setContentIntent(resultPendingIntent1(bm))
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(LargeIcon(imgg))
                            .setGroup("" + title)
                            .setStyle(bigimg1(title, "1 PIC 1 WORD", imgg))
                            .setAutoCancel(true);
                }
                notify(id, nb);
            } else {
                Notification myNotification;
                if (style.equals("bt")) {
                    myNotification = new NotificationCompat.Builder(context)
                            .setSound(mUri)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(getlogo1())
                            .setAutoCancel(true)
                            .setPriority(2)
                            .setContentIntent(resultPendingIntent(bm, body1, id, activity))
                            .setContentTitle(title)
                            .setContentText("")
                            .setGroup("" + title)
                            .setStyle(bigtext(title, "1 PIC 1 WORD", ""))
                            .build();
                    notify(id, myNotification);
                } else {
                    myNotification = new NotificationCompat.Builder(context)
                            .setSound(mUri)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setLargeIcon(getlogo1())
                            .setAutoCancel(true)
                            .setPriority(2)
                            .setContentIntent(resultPendingIntent1(bm))
                            .setContentTitle(title)
                            .setGroup("" + title)
                            .setContentText("")
                            .setStyle(bigimg(title, "1 PIC 1 WORD", imgg))
                            .build();
                    notify(id, myNotification);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Notification_custom(int id, String titlee, String body, String imgg, String style, String bm, int sund_chk1, Class activity) {
        try {
            Uri mUri;
            mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_shown_st);
                contentView.setImageViewResource(R.id.image, getlogo());
                contentView.setTextViewText(R.id.title, bm);
                Notification.Builder mBuilder = null;
                if (style.equals("bt")) {
                    mBuilder = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setGroup("" + titlee)
                            .setCustomContentView(contentView);
                } else {
                    RemoteViews expandView = new RemoteViews(getPackageName(), R.layout.notification_shown_bi);
                    expandView.setImageViewResource(R.id.image, getlogo());
                    expandView.setTextViewText(R.id.title, bm);
                    expandView.setImageViewBitmap(R.id.imgg, LargeIcon(imgg));
                    mBuilder = new Notification.Builder(context, PRIMARY_CHANNEL)
                            .setSmallIcon(getSmallIcon())
                            .setGroup("" + titlee)
                            .setColor(Color.parseColor("#6460AA"))
                            .setCustomContentView(contentView)
                            .setCustomBigContentView(expandView);
                }
                Notification notification = mBuilder.build();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    notification.priority |= Notification.PRIORITY_MAX;
                }
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.flags |= Notification.FLAG_SHOW_LIGHTS;
                notification.contentIntent = resultPendingIntent(bm, body, id, activity);
                getManager().notify(id, notification);
            } else {
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_shown_st);
                contentView.setImageViewResource(R.id.image, getlogo());
                contentView.setTextViewText(R.id.title, bm);
                NotificationCompat.Builder mBuilder = null;
                if (style.equals("bt")) {
                    mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setGroup("" + titlee)
                            .setContent(contentView);
                } else {
                    RemoteViews expandView = new RemoteViews(getPackageName(), R.layout.notification_shown_bi);
                    expandView.setImageViewResource(R.id.image, getlogo());
                    expandView.setTextViewText(R.id.title, bm);
                    expandView.setImageViewBitmap(R.id.imgg, LargeIcon(imgg));
                    mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(getSmallIcon())
                            .setColor(Color.parseColor("#6460AA"))
                            .setGroup("" + titlee)
                            .setContent(contentView)
                            .setCustomBigContentView(expandView);
                }
                Notification notification = mBuilder.build();
                if (sund_chk1 == 0) {
                    notification.defaults |= Notification.DEFAULT_SOUND;
                } else {
                    notification.sound = mUri;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    notification.priority |= Notification.PRIORITY_MAX;
                }
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.flags |= Notification.FLAG_SHOW_LIGHTS;
                notification.contentIntent = resultPendingIntent(bm, body, id, activity);
                getManager().notify(id, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    public void notify(int id, Notification myNotification) {
        getManager().notify(id, myNotification);
    }

    private int getSmallIcon() {
        return R.drawable.notifi_icon;//
    }

    private int getlogo() {
        return R.drawable.app_logo;
    }

    private Bitmap getlogo1() {
        return BitmapFactory.decodeResource(getResources(), getlogo());
    }

    private Bitmap LargeIcon(String url) {
        Bitmap remote_picture = BitmapFactory.decodeResource(getResources(), getlogo());
        if (url.length() > 5) {
            try {
                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            } catch (IOException e) {
                e.printStackTrace();
                remote_picture = BitmapFactory.decodeResource(getResources(), getlogo());
            }
        } else {
            remote_picture = BitmapFactory.decodeResource(getResources(), getlogo());
        }
        return remote_picture;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
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

    public NotificationCompat.BigPictureStyle bigimg(String Title, String Summary, String imgg) {
        return new NotificationCompat.BigPictureStyle()
                .setBigContentTitle(Title)
                // .setSummaryText(Summary)
                .bigPicture(LargeIcon(imgg));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification.BigPictureStyle bigimg1(String Title, String Summary, String imgg) {
        return new Notification.BigPictureStyle()
                .setBigContentTitle(Title)
                .setSummaryText(Summary)
                .bigPicture(LargeIcon(imgg));
    }

    public PendingIntent resultPendingIntent(String titt, String msgg, int idd, Class activity) {
        Intent intent = set_intent(context, idd, titt, msgg, activity);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(activity);
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent resultPendingIntent1(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        return stackBuilder.getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public Intent set_intent(Context context, int iddd, String titt, String msgg, Class activity) {
        Intent intent;
        intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", msgg);
        intent.putExtra("title", titt);
        intent.putExtra("idd", iddd);
        intent.putExtra("Noti_add", 1);
        return intent;
    }
}