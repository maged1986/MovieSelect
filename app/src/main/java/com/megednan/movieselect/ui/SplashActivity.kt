package com.megednan.movieselect.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.megednan.movieselect.R
import com.megednan.movieselect.databinding.ActivityMainBinding
import com.megednan.movieselect.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.flowable.FlowableReplay.observeOn
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
lateinit var binding:ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= DataBindingUtil.setContentView(
            this, R.layout.activity_splash)



        val intent=Intent(this,MainActivity::class.java)
        Observable.timer(4, TimeUnit.SECONDS).subscribe({startActivity(intent)})

    }


}