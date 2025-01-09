package ilkadam.ilksiparis

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ilkadam.ilksiparis.ui.theme.IlkSiparisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IlkSiparisTheme {
                Scaffold {innerPadding->
                    ilkSiparisView(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

@Composable
fun ilkSiparisView(modifier: Modifier=Modifier) {
    val activity = LocalView.current.context as Activity
    val isFullScreen = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    webChromeClient = object : WebChromeClient() {
                        var customView: View? = null
                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                            super.onShowCustomView(view, callback)
                            isFullScreen.value = true
                            if (this.customView != null) {
                                onHideCustomView()
                                return
                            }
                            this.customView = view
                            (activity.window.decorView as FrameLayout).addView(this.customView, FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                        }

                        override fun onHideCustomView() {
                            super.onHideCustomView()
                            isFullScreen.value = false
                            (activity.window.decorView as FrameLayout).removeView(this.customView)
                            this.customView = null
                        }
                    }
                    loadUrl("http://77.245.150.206:11408/")
                }
            }
        )

    }
    activity.requestedOrientation = if (isFullScreen.value) ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}