package com.example.minilauncher.practice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// 2일차 오후 알림 실습 5단계.
// MainActivity.kt 의 notificationPractice(this, step = 1) 에서 숫자를 1부터 5까지 바꿔 가며 Run 합니다.
// Logcat 검색창에 tag:Noti 를 넣고, 에뮬레이터 화면 위쪽을 끌어내려 알림을 봅니다.
// 알림이 안 보이면 앱의 동기화 시작을 한 번 눌러 알림 권한을 허락합니다.
// 직접 써 보고 싶으면 같은 폴더의 NotificationPractice_My.kt 에 옮겨 적습니다.

private const val TAG = "Noti"
const val PRACTICE_CHANNEL = "practice"      // 알림 채널의 id
const val PRACTICE_NOTIFICATION = 100        // 알림의 번호. 같은 번호로 다시 띄우면 그 알림이 바뀝니다

private fun show(message: String) {
    Log.d(TAG, message)
}

fun notificationPractice(context: Context, step: Int) {
    show("===== ${step}단계 =====")
    if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
        show("알림 권한이 없습니다. 앱의 동기화 시작을 한 번 눌러 권한을 허락한 뒤 다시 Run 하세요")
    }
    when (step) {
        1 -> step01Channel(context)
        2 -> step02Notify(context)
        3 -> step03Update(context)
        4 -> step04Progress(context)
        5 -> step05Cancel(context)
        else -> show("1부터 5까지의 숫자를 넣어 주세요")
    }
}

// 알림 채널을 만듭니다. 이미 있으면 아무 일도 일어나지 않아서 여러 번 불러도 됩니다
fun createPracticeChannel(context: Context): NotificationManager {
    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(
        NotificationChannel(PRACTICE_CHANNEL, "실습 알림", NotificationManager.IMPORTANCE_DEFAULT)
    )
    return manager
}

// 알림 하나를 만듭니다. 작은 아이콘, 제목, 내용 세 가지가 기본입니다
fun practiceNotification(context: Context, text: String): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, PRACTICE_CHANNEL)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("알림 실습")
        .setContentText(text)
}

// 1단계. 알림 채널 만들기
// 알림은 채널에 속해야 뜹니다 (Android 8 부터). 사용자는 설정에서 채널마다 알림을 끌 수 있습니다
private fun step01Channel(context: Context) {
    val manager = createPracticeChannel(context)
    show("채널 개수: ${manager.notificationChannels.size}")
    manager.notificationChannels.forEach { show("채널: ${it.id} (${it.name})") }
    // 바꿔 보기: 에뮬레이터의 설정 > 앱 > Mini Launcher > 알림에서 "실습 알림" 채널이 생겼는지 봅니다
}

// 2단계. 알림 하나 띄우기
private fun step02Notify(context: Context) {
    val manager = createPracticeChannel(context)
    manager.notify(PRACTICE_NOTIFICATION, practiceNotification(context, "처음 띄운 알림").build())
    show("알림을 띄웠습니다. 화면 위쪽을 끌어내려 보세요")
    // 바꿔 보기: 제목과 내용을 바꿔 봅니다. setContentTitle, setContentText
}

// 3단계. 같은 번호로 다시 띄우면 그 알림이 바뀝니다
private fun step03Update(context: Context) {
    val manager = createPracticeChannel(context)
    manager.notify(PRACTICE_NOTIFICATION, practiceNotification(context, "2초 뒤에 글자가 바뀝니다").build())
    show("알림을 띄웠습니다")
    Handler(Looper.getMainLooper()).postDelayed({
        manager.notify(PRACTICE_NOTIFICATION, practiceNotification(context, "바뀌었습니다").build())
        show("같은 번호로 다시 띄워 글자를 바꿨습니다")
    }, 2000)
    // 바꿔 보기: 두 번째 notify 의 번호를 101 로 바꾸면 알림이 몇 개가 될까요?
}

// 4단계. 진행 막대가 있는 알림
// 동기화 Service 의 알림이 이 모양입니다. 0.5초마다 20씩 올리고 끝나면 막대를 없앱니다
private fun step04Progress(context: Context) {
    val manager = createPracticeChannel(context)
    val handler = Handler(Looper.getMainLooper())
    for (i in 0..5) {
        handler.postDelayed({
            val percent = i * 20
            val builder = if (percent < 100) {
                practiceNotification(context, "진행 중 $percent%").setProgress(100, percent, false).setOnlyAlertOnce(true)
            } else {
                practiceNotification(context, "끝났습니다")
            }
            manager.notify(PRACTICE_NOTIFICATION, builder.build())
            show("진행 $percent%")
        }, i * 500L)
    }
    // 바꿔 보기: setProgress(100, percent, false) 의 false 를 true 로 바꾸면 막대가 어떻게 보일까요?
}

// 5단계. 알림 지우기
private fun step05Cancel(context: Context) {
    val manager = createPracticeChannel(context)
    manager.notify(PRACTICE_NOTIFICATION, practiceNotification(context, "3초 뒤에 사라집니다").build())
    show("알림을 띄웠습니다")
    Handler(Looper.getMainLooper()).postDelayed({
        manager.cancel(PRACTICE_NOTIFICATION)
        show("알림을 지웠습니다")
    }, 3000)
    // 바꿔 보기: setOngoing(true) 를 붙이면 사용자가 밀어서 지울 수 없게 됩니다. Foreground Service 의 알림이 그렇습니다
}
