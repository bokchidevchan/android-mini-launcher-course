package com.example.minilauncher.practice

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.minilauncher.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 3일차 오후 과제 "홈 화면 위젯". 위젯 화면(widget_mini.xml), 설명서(mini_widget_info.xml), Manifest 등록은 만들어져 있습니다.

class MiniWidget : AppWidgetProvider() {

    // 위젯을 놓을 때, 그리고 "새로고침"을 누를 때 시스템이 부름
    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        val count = countApps(context)
        val time = nowText()
        Log.d("Widget", "위젯 갱신: 앱 ${count}개, $time")

        val views = RemoteViews(context.packageName, R.layout.widget_mini)   // 위젯 화면(widget_mini.xml)을 어떻게 바꿀지 적는 주문서
        // [1] 글자 넣기: 앱 개수와 새로고침 시각
        views.setTextViewText(R.id.widget_count, "설치 앱 ${count}개")
        views.setTextViewText(R.id.widget_refresh, "새로고침 ($time)")
        // [2] 누르면 할 일: 새로고침은 다시 그리기, 제목은 안내 화면 열기
        views.setOnClickPendingIntent(R.id.widget_refresh, refreshIntent(context))
        views.setOnClickPendingIntent(R.id.widget_title, openAppIntent(context))

        // 다 쓴 주문서를 시스템에 보내면 홈 앱이 위젯을 다시 그림. 이 줄이 있어야 위젯이 바뀜
        manager.updateAppWidget(ids, views)
    }
}

// 아이콘이 있는 앱이 몇 개인지 (1교시와 같은 질문)
private fun countApps(context: Context): Int {
    // [3] 1교시 1단계와 같은 질문으로 개수 세기
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
    // UPDATE_CURRENT: 같은 편지가 있으면 내용(ids)만 새것으로.  IMMUTABLE: 다른 앱이 내용을 못 바꾸게 잠금
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(context, 0, intent, flags)    // 0, 아래의 1: 편지 둘을 구분하는 번호
}

// 누르면 이 앱(위젯 연습)의 안내 화면을 엶
private fun openAppIntent(context: Context): PendingIntent {
    val intent = Intent(context, WidgetGuideActivity::class.java)
    return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
}
