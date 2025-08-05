package com.dorian.liketoday.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dorian.liketoday.worker.WeightReminderWorker
import java.util.concurrent.TimeUnit

object WorkScheduler {
    fun schedulePeriodicReminders(context: Context) {
        schedulePeriodicWorker(context, "morning", 7)
        schedulePeriodicWorker(context, "evening", 23)
    }

    private fun schedulePeriodicWorker(context: Context, type: String, hour: Int) {
        val request = PeriodicWorkRequestBuilder<WeightReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(getDelayUntil(hour), TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("type" to type))
            .addTag("weight_reminder_$type")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "weight_reminder_$type",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    // 현재 시각으로부터 지정 시간까지의 지연 시간 계산
    private fun getDelayUntil(targetHour: Int): Long {
        val now = java.time.LocalDateTime.now()
        var target = now.withHour(targetHour).withMinute(0).withSecond(0).withNano(0)

        if (target.isBefore(now)) {
            target = target.plusDays(1) // 오늘 시각 지났으면 내일로 미룸
        }

        return java.time.Duration.between(now, target).toMillis()
    }
}