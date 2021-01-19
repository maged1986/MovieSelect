package com.megednan.movieselect.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.widget.Toast.*
import androidx.navigation.fragment.navArgs
import com.github.satoshun.reactivex.webkit.data.OnPageFinished
import com.github.satoshun.reactivex.webkit.data.OnPageStarted
import com.github.satoshun.reactivex.webkit.events
import com.megednan.movieselect.R
import com.megednan.movieselect.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class VideoFragment : Fragment(R.layout.fragment_video) {
    private val disposables = CompositeDisposable()
    private lateinit var binding: FragmentVideoBinding
    private lateinit var  videoLink:String
    val args: VideoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentVideoBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
         videoLink = args.videoLink
        /* val webview = context?.let { WebView(it) }
         webview?.events()
             ?.ofType(OnPageStarted::class.java)
             ?.subscribeOn(Schedulers.io())
             ?.observeOn(AndroidSchedulers.mainThread())
             ?.subscribe()
         binding.videoFragWebView.apply {
             webViewClient = WebViewClient()
             loadUrl(videoLink)}*/
        detectStartAndFinishEvent()
    }

    private fun detectStartAndFinishEvent() {
        binding.videoFragWebView.getSettings().setJavaScriptEnabled(true);
        binding.videoFragWebView.setWebViewClient( WebViewClient())
        binding.videoFragWebView.loadUrl(videoLink)

        disposables.add(
                binding.videoFragWebView.events(delegate = WebViewClient())
                       // .subscribeOn(Schedulers.io())
                        .publish { shared ->
                            Observable.merge(
                                    shared.ofType(OnPageStarted::class.java),
                                   shared.ofType(OnPageFinished::class.java)
                            )
                        }//.observeOn(AndroidSchedulers.mainThread())
                 .subscribe()
        )
        binding.videoFragWebView.getSettings().setJavaScriptEnabled(true);
        binding.videoFragWebView.setWebViewClient( WebViewClient())
        binding.videoFragWebView.loadUrl(videoLink)
    }
}