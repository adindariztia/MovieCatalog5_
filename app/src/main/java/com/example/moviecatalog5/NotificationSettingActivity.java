package com.example.moviecatalog5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationSettingActivity extends AppCompatActivity {
    Button btnDailyReminderStart, btnDailyReminderStop;
    Button btnReleaseTodayStart, btnReleaseTodayEnd;
    AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        btnDailyReminderStart = findViewById(R.id.btn_daily_reminder_start);
        btnDailyReminderStop = findViewById(R.id.btn_daily_reminder_end);
        btnReleaseTodayStart = findViewById(R.id.btn_release_today_start);
        btnReleaseTodayEnd = findViewById(R.id.btn_release_today_end);
        alarmReceiver = new AlarmReceiver();

        btnDailyReminderStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.setDailyReminder(v.getContext(), AlarmReceiver.TYPE_DAILY_REMINDER, AlarmReceiver.EXTRA_MESSAGE);
            }
        });

        btnDailyReminderStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.cancelAlarm(v.getContext(), AlarmReceiver.TYPE_DAILY_REMINDER);

            }
        });

        btnReleaseTodayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.setReleaseReminder(v.getContext(), AlarmReceiver.TYPE_RELEASE_REMINDER, AlarmReceiver.EXTRA_MESSAGE);
            }
        });

        btnReleaseTodayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.cancelAlarm(v.getContext(), AlarmReceiver.TYPE_RELEASE_REMINDER);
            }
        });
    }
}

