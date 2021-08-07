package com.noto.app

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.noto.app.databinding.MainActivityBinding
import com.noto.app.domain.model.Theme
import com.noto.app.notelist.SelectLibraryDialogFragment
import com.noto.app.util.createNotificationChannel
import com.noto.app.util.withBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        notificationManager.createNotificationChannel()
        MainActivityBinding.inflate(layoutInflater).withBinding {
            setContentView(root)
            setupState()
            handleIntentContent()
        }
    }

    private fun MainActivityBinding.handleIntentContent() {
        if (intent?.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT)
                ?.let { content ->
                    val selectLibraryItemClickListener = SelectLibraryDialogFragment.SelectLibraryItemClickListener {
                        val args = bundleOf("library_id" to it, "body" to content)
                        findNavController(R.id.nav_host_fragment).navigate(R.id.noteFragment, args)
                    }

                    val args = bundleOf("library_id" to 0L, "select_library_item_click_listener" to selectLibraryItemClickListener)
                    findNavController(R.id.nav_host_fragment).navigate(R.id.selectLibraryDialogFragment, args)
                }
        }
    }

    private fun MainActivityBinding.setupState() {
        when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                window.decorView.systemUiVisibility = 0
            }
        }

        viewModel.theme
            .onEach { theme -> setupTheme(theme) }
            .launchIn(lifecycleScope)
    }

    private fun MainActivityBinding.setupTheme(theme: Theme) {
        when (theme) {
            Theme.System -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            Theme.Light -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.Dark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}