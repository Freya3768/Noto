package com.noto.app.settings.whatsnew

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.noto.app.NotoTheme
import com.noto.app.R
import com.noto.app.components.Screen
import com.noto.app.settings.SettingsItem
import com.noto.app.settings.SettingsItemType
import com.noto.app.settings.SettingsSection
import com.noto.app.util.navController
import com.noto.app.util.setupMixedTransitions

class ReleaseFragment : Fragment() {

    private val args by navArgs<ReleaseFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = context?.let { context ->
        activity?.onBackPressedDispatcher?.addCallback { navController?.navigateUp() }
        setupMixedTransitions()
        ComposeView(context).apply {
            isTransitionGroup = true
            setContent {
                val version = remember(args.release) { args.release.versionFormatted }
                val date = remember(args.release) { args.release.dateFormatted }
                val changelog = remember(args.release) { args.release.changelog.formatAsList() }

                Screen(
                    title = version,
                    subtitle = date,
                    verticalArrangement = Arrangement.Top,
                ) {
                    SettingsSection {
                        changelog.forEach { item ->
                            SettingsItem(
                                title = item,
                                type = SettingsItemType.None,
                                painter = painterResource(id = R.drawable.ic_round_check_24),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(NotoTheme.dimensions.medium))

                    SettingsItem(
                        title = stringResource(id = R.string.learn_more),
                        type = SettingsItemType.None,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.release.githubUrl))
                            startActivity(intent)
                        },
                        painter = painterResource(id = R.drawable.ic_round_open_externally_24)
                    )
                }
            }
        }
    }
}