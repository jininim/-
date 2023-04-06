package com.example.teamproject_hometrainingassistant_app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityLoginBinding
import com.example.teamproject_hometrainingassistant_app.ui.home.HomeFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KakaoSdk.init(this, "ef4cb117e81f2446c42e26f897b523c9")

        //카카오 로그인버튼 클릭시 메인화면 전환
        binding.kakaologin.setOnClickListener {
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
                    finish()
                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
//
//                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)

                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            }
                            else if (user != null) {
                                Log.i(TAG, "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                                val userInfo : ArrayList<String> = ArrayList()
                                userInfo.add(user.kakaoAccount?.profile?.nickname.toString())
                                userInfo.add(user.kakaoAccount?.profile?.thumbnailImageUrl.toString())// 카카오로그인을 통해 받아온 사용자 정보
                                val bundle = Bundle().apply {
                                    putStringArrayList("USER_INFO", userInfo)
                                }
                                val fragment = HomeFragment()
                                fragment.arguments = bundle

                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
                                finish()
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진11: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        val username = user.kakaoAccount?.profile?.nickname
                        val userImage = user.kakaoAccount?.profile?.thumbnailImageUrl
                        val intent = Intent(applicationContext, MainActivity::class.java).apply {
                            putExtra("USER_NAME",username)
                            putExtra("USER_IMAGE",userImage)
                        }
                        startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
                    }

                }
            }
        }



        //페이스북 로그인 버튼 클릭시
        binding.facebooklogin.setOnClickListener {
            val intent = Intent(applicationContext, DecibelActivity::class.java)
            val keyHash = Utility.getKeyHash(this)
            Log.d("giiiiiiiiii",keyHash)
            startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
            finish()
        }
        //구글 로그인화면 클릭시
        binding.googlelogin.setOnClickListener {
//        val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
//            finish()
        }
        //로그아웃
        binding.logout.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e(TAG, "연결 끊기 실패", error)
                }
                else {
                    Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }
    }
}
