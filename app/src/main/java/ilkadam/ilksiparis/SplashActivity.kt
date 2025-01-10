package ilkadam.ilksiparis

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ilkadam.ilksiparis.ui.theme.IlkSiparisTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the splash screen
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                // Keeps the splash screen visible while loading (optional logic can be applied here)
                true
            }
        }

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set the content of the activity
        setContent {
            // Your composable functions can be added here
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

