package com.example.minilauncher.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 비교 코드 1: MVVM 의 ViewModel. 화면(XML)은 MvvmActivity.kt 와 res/layout/mvvm_like.xml 입니다.
// 같은 "좋아요" 화면을 MVI 로 만든 것은 옆 폴더 mvi 입니다. 두 폴더를 나란히 놓고 비교합니다.
//
// MVVM 은 Model, View, ViewModel 의 앞 글자입니다.
//   View        화면. 그리기만 합니다 (mvvm_like.xml 과 MvvmActivity)
//   ViewModel   화면이 보여 줄 값과, 값을 바꾸는 함수를 들고 있습니다 (이 파일)
//   Model       데이터와 규칙. 이 예제는 작아서 따로 없습니다
// 화면이 ViewModel 에 말하는 방법: 할 일마다 함수가 따로 있습니다 (plus(), reset())
// 비유: 리모컨입니다. 버튼마다 하는 일이 따로 있습니다. C++ 로 치면 멤버 함수를 여러 개 부르는 방식
//
// 왜 이렇게 나누나
//   화면에서 계산을 빼기 위해서입니다. 화면은 그리기만 하고, 값을 바꾸는 규칙은 ViewModel 에 둡니다.
//   그래야 화면 없이 ViewModel 만 테스트할 수 있고, 화면을 돌려도 값이 남습니다
class MvvmViewModel : ViewModel() {

    // LiveData 는 "지켜볼 수 있는 값"입니다. 값이 바뀌면 지켜보던 쪽(Activity)에 알려 줍니다.
    // 밖에서는 읽기만 하게(LiveData), 안에서만 바꾸게(MutableLiveData) 둘로 나눠 둡니다
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    private val _message = MutableLiveData("눌러 보세요")
    val message: LiveData<String> = _message

    // 할 일마다 함수가 따로 있습니다
    fun plus() {
        Log.d("Arch", "MVVM: plus() 호출")
        val next = (_count.value ?: 0) + 1
        _count.value = next
        _message.value = "좋아요 ${next}개"      // 이 줄을 빼먹어도 빨간 줄이 안 생깁니다. 값 두 개를 내가 맞춰야 합니다
    }

    fun reset() {
        Log.d("Arch", "MVVM: reset() 호출")
        _count.value = 0
        _message.value = "처음으로 돌아왔습니다"
    }
}
