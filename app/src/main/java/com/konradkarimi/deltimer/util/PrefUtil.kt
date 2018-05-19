package com.konradkarimi.deltimer.util

import android.content.Context
import android.preference.PreferenceManager
import com.konradkarimi.deltimer.TimerActivity

class PrefUtil {
    companion object {

        private const val TIMER_LENGHT_ID = "com.konradkarimi.deltimer.timer_lenght"

        fun getTimerLenght(context: Context): Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getInt(TIMER_LENGHT_ID, 10)
        }

        private const val PREVIOUS_LENGHT_SECONDS_ID = "com.konradkarimi.timier.previous_timer_lenght_seconds_id"

        fun getPreviousTimerLenghtSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_LENGHT_SECONDS_ID, 0)
        }

        fun setPreviousTimerLenghtSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_LENGHT_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "com.konradkarimi.timer.timer_state"

        fun getTimerState(context: Context): TimerActivity.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return TimerActivity.TimerState.values()[ordinal]
        }

        fun setTimerState(state: TimerActivity.TimerState, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING = "com.konradkarimi.timier.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING, seconds)
            editor.apply()
        }

        private const val ALARM_SET_ID = "com.konradkarimi.deltimer.backgrounded_time"

        fun getAlarmSetTime(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_ID, 0)
        }
        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_ID, time)
            editor.apply()
        }
    }
}