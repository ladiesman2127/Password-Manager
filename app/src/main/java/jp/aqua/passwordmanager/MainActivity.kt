package jp.aqua.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import jp.aqua.passwordmanager.navigation.RootNavHost
import jp.aqua.passwordmanager.ui.theme.PasswordManagerTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                RootNavHost()
            }
        }
    }
}
