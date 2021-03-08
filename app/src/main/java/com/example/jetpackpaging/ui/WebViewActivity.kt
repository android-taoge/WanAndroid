package com.example.jetpackpaging.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.navigation.navArgs
import com.example.jetpackpaging.R
import com.example.jetpackpaging.databinding.ActivityWebViewBinding
import com.example.jetpackpaging.model.SkipWeb

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding


    private var skipWeb: SkipWeb? = null


    companion object {
        val SKIP = "skip"
        fun start2Web(context: Context, skipWeb: SkipWeb) {
            val intent = Intent(context, WebViewActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(SKIP, skipWeb)
            intent.putExtras(bundle)
            context.startActivity(intent)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        skipWeb = intent.extras?.get(SKIP) as SkipWeb
        setContentView(binding.root)
        initWebView()
        initTitle()


    }

    private fun initTitle() {

        skipWeb?.let {
            binding.titleBar.toolbar.apply {
                title = it.title
                setTitleTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                setNavigationOnClickListener {
                    this@WebViewActivity.finish()
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        val webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressbar.apply {
                    progress = newProgress
                    isGone = (newProgress == 100)
                }
            }
        }

        binding.webView.apply {

            setWebChromeClient(webChromeClient)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return if (url == null || url!!.startsWith("http://") || url!!.startsWith("https://")) false else try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        view!!.context.startActivity(intent)
                        true
                    } catch (e: Exception) {
                        Log.i(
                            WebViewActivity::class.java.simpleName,
                            "shouldOverrideUrlLoading Exception:$e"
                        )
                        true
                    }

                }
            }
            settings.javaScriptEnabled = true
            skipWeb?.let {
                loadUrl(it.link)
            }
        }

    }
}