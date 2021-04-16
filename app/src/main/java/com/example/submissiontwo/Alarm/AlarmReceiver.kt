package com.example.submissiontwo.Alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.submissiontwo.Activity.MainActivity
import com.example.submissiontwo.R
import com.example.submissiontwo.helper.AlarmHelper.showAlarmNotification
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "megamusfiva"
        const val CHANNEL_NAME = "Alarm Manager"
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = context.resources.getString(R.string.app_name)
        val message = context.resources.getString(R.string.daily_message)
        showAlarmNotification(context, title, message)
    }

}