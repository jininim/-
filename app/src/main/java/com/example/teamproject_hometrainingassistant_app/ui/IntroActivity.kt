package com.example.teamproject_hometrainingassistant_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.LoginActivity
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.R
import com.kakao.sdk.common.util.Utility


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
//       액션바 숨기기
        val actionBar : ActionBar? = supportActionBar

        actionBar?.hide()
//      intro 화면
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
            finish()
        }, 500) //0.5초 후 인트로 실행

    }


}