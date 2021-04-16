package com.example.submissiontwo.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.edit
import com.example.submissiontwo.R
import com.example.submissiontwo.helper.AlarmHelper.cancelAlarm
import com.example.submissiontwo.helper.AlarmHelper.setRepeatingAlarm
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "ReminderPref"
    }

    private lateinit var mPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(tb_setting)

        tb_setting.setNavigationOnClickListener {
            val goHome = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(goHome)
            finish()
        }

        btn_change.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        supportActionBar?.elevation = 0f
        mPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        switch_reminder.apply {
            isChecked = mPreferences.getBoolean(PREFS_NAME, false)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    setRepeatingAlarm(context, resources.getString(R.string.daily_reminder))
                    mPreferences.edit { putBoolean(PREFS_NAME, true) }
                    Toast.makeText(context,
                            resources.getString(R.string.reminderOn),
                            Toast.LENGTH_LONG).show()
                } else {
                    cancelAlarm(context)
                    mPreferences.edit { putBoolean(PREFS_NAME, false) }
                    Toast.makeText(context,
                            resources.getString(R.string.reminderOff),
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
