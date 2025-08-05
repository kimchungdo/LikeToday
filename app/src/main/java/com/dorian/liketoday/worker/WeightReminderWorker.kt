package com.dorian.liketoday.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dorian.liketoday.R
import com.dorian.liketoday.data.AppDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeightReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val weightDao = AppDatabase.getDatabase(context).weightDao()

    override suspend fun doWork(): Result {
        val type = inputData.getString("type") ?: return Result.failure()
        val date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        val entries = weightDao.getEntriesByDateDirect(date)
        val exists = entries.any { it.type == type }

        if (!exists) {
            sendNotification(type)
        }

        return Result.success()
    }

    private fun sendNotification(type: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "weight_reminder_channel"
        val channel = NotificationChannel(
            channelId,
            "체중 입력 리마인더",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)

        val title = if (type == "morning") "아침 체중을 입력해주세요" else "저녁 체중을 입력해주세요"

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText("likeToday와 함께 건강 루틴을 이어가요 💪")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        manager.notify(if (type == "morning") 1001 else 1002, notification)
    }
}
