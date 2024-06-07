package com.settasit.nonnoi;

import static com.settasit.nonnoi.MainActivity.serviceIntent;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ForegroundService extends Service implements LocationListener {
    boolean isPermissionGranted = MainActivity.isPermissionGranted;
    double x0 = 0;
    double y0 = 0;
    LocationManager locationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new Notification.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.samoyed_round_)
                .setContentTitle("Woof!")
                .setContentText("Hello!")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        checkPermission();
        if (isPermissionGranted) {
            LocationUpdate();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                LocationUpdate();
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {}
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {}
        }).check();
    }
    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_desc);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    @SuppressLint("MissingPermission")
    private void LocationUpdate() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, (float) 0, this);
    }
    @SuppressLint("MissingPermission")
    public void onLocationChanged(@NonNull Location location) {
        double radius_ = MainActivity.radius_;
        double x1 = MainActivity.x1;
        double y1 = MainActivity.y1;
        int i = MainActivity.i;
        int j = MainActivity.j;
        int k = MainActivity.k;
        x0 = location.getLatitude();
        y0 = location.getLongitude();
        if (radius_ != 0 && (x1 != 0 || y1 != 0) && 6371.01 * Math.acos((Math.sin((x1 * 3.14159) / 180) * Math.sin((x0 * 3.14159) / 180)) + (Math.cos((x1 * 3.14159) / 180) * Math.cos((x0 * 3.14159) / 180) * Math.cos(((y1 * 3.14159) / 180) - ((y0 * 3.14159) / 180)))) <= radius_) {
            if (j == 1) {
                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
                Notification notification_ = new Notification.Builder(this, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.samoyed_round_)
                        .setContentTitle("Woof!")
                        .setContentText("Arrived!")
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification_);
            }
            if (i == 1) {
                if (k == 0) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    long[] pattern = {0, 200, 80, 200, 600, 200, 80, 200, 600, 700, 400, 200, 80, 200};
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1));
                } else if (k == 1) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.firstdate);
                    mp.start();
                } else if (k == 2) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.thegirlihaveacrushon);
                    mp.start();
                } else if (k == 3) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.florence);
                    mp.start();
                } else if (k == 4) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.blossoms);
                    mp.start();
                } else if (k == 5) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.bingsoo);
                    mp.start();
                } else if (k == 6) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.luvletters);
                    mp.start();
                } else if (k == 7) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.youmakemesmile);
                    mp.start();
                } else if (k == 8) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.chyeah);
                    mp.start();
                } else if (k == 9) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.shoreline);
                    mp.start();
                } else if (k == 10) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.withyou);
                    mp.start();
                }
            }
        }
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopService(serviceIntent);
        super.onTaskRemoved(rootIntent);
    }
    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
    }
}