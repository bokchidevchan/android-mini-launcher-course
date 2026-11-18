package com.example.minilauncher.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.minilauncher.MainActivity
import com.example.minilauncher.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MiniWidget : AppWidgetProvider() {

    // 위젯을 놓을 때, 그리고 "새로고침"을 누를 때 시스템이 부름
    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        val count = countApps(context)
        val time = nowText()
        Log.d("Widget", "위젯 갱신: 앱 ${count}개, $time")

        val views = RemoteViews(context.packageName, R.layout.widget_mini)
        views.setTextViewText(R.id.widget_count, "설치 앱 ${count}개")
        views.setTextViewText(R.id.widget_refresh, "새로고침 ($time)")
        views.setOnClickPendingIntent(R.id.widget_refresh, refreshIntent(context))
        views.setOnClickPendingIntent(R.id.widget_title, openAppIntent(context))

        manager.updateAppWidget(ids, views)
    }
}

// 아이콘이 있는 앱이 몇 개인지 (1교시와 같은 질문)
private fun countApps(context: Context): Int {
    val query = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return context.packageManager.queryIntentActivities(query, 0).size
}

// 지금 시각 "14:05:30"
private fun nowText(): String = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(Date())

// 누르면 내 위젯에 "다시 그려 줘"(APPWIDGET_UPDATE) 방송을 보냄. 부모 클래스가 받아서 onUpdate 를 부름
private fun refreshIntent(context: Context): PendingIntent {
    val me = ComponentName(context, MiniWidget::class.java)
    val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(me)     // 놓인 위젯 전부
    val intent = Intent(context, MiniWidget::class.java)
        .setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(context, 0, intent, flags)
}

// 누르면 Mini Launcher 화면을 엶
private fun openAppIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
}
