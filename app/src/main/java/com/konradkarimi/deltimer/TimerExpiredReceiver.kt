package com.konradkarimi.deltimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.konradkarimi.deltimer.util.NotificationUtil
import com.konradkarimi.deltimer.util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        NotificationUtil.showTimerExpiried(context)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)

    }
}
