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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import ilkadam.ilksiparis.ui.theme.IlkSiparisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        InterstitialAd.load(this,"ca-app-pub-5764318432941968/4519905955", AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAd.show(this@MainActivity)
            }
        })

        setContent {
            IlkSiparisTheme {
                Scaffold(
                    bottomBar = {
                        AdMobBanner()
                    }) { innerPadding ->
                    ilkSiparisView(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

@Composable
fun ilkSiparisView(modifier: Modifier = Modifier) {
    val activity = LocalView.current.context as Activity
    val isFullScreen = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    this.settings.javaScriptEnabled = true
                    this.webViewClient = WebViewClient()
                }
            },
            update = { webView ->
                webView.loadUrl("http://77.245.150.206:11408/")
            }
        )

    }
    activity.requestedOrientation =
        if (isFullScreen.value) ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}