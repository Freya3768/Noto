package com.noto.app.settings.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.noto.app.R
import com.noto.app.components.Screen
import com.noto.app.settings.SettingsItem
import com.noto.app.settings.SettingsItemType
import com.noto.app.settings.SettingsSection
import com.noto.app.util.navController
import com.noto.app.util.setupMixedTransitions

private const val TurkishTranslatorWebsite = "https://linkedin.com/in/nuraysabri"
private const val TurkishProofreaderWebsite = "https://sakci.me"
private const val SpanishTranslatorWebsite = "https://github.com/faus32"
private const val ItalianTranslatorWebsite = "https://github.com/matteolomba"
private const val FrenchTranslatorWebsite = "https://github.com/kernoeb"
private const val FrenchTranslator2Website = "https://geoffreycrofte.com"
private const val ArabicTranslatorWebsite = "https://twitter.com/trjman_en"
private const val ArabicProofreaderWebsite = "https://www.alialbaali.com"

class TranslationsSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = context?.let { context ->
        setupMixedTransitions()

        activity?.onBackPressedDispatcher?.addCallback {
            navController?.navigateUp()
        }

        ComposeView(context).apply {
            isTransitionGroup = true
            setContent {
                Screen(title = stringResource(id = R.string.translations)) {
                    SettingsSection(title = stringResource(id = R.string.arabic), painter = painterResource(id = R.drawable.ic_uae)) {
                        SettingsItem(
                            title = stringResource(id = R.string.arabic_translator),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ArabicTranslatorWebsite))
                                startActivity(intent)
                            }
                        )

                        SettingsItem(
                            title = stringResource(id = R.string.arabic_proofreader),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ArabicProofreaderWebsite))
                                startActivity(intent)
                            }
                        )
                    }

                    SettingsSection(title = stringResource(id = R.string.turkish), painter = painterResource(id = R.drawable.ic_turkey)) {
                        SettingsItem(
                            title = stringResource(id = R.string.turkish_translator),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TurkishTranslatorWebsite))
                                startActivity(intent)
                            }
                        )

                        SettingsItem(
                            title = stringResource(id = R.string.turkish_proofreader),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TurkishProofreaderWebsite))
                                startActivity(intent)
                            }
                        )
                    }

                    SettingsSection(title = stringResource(id = R.string.german), painter = painterResource(id = R.drawable.ic_germany)) {
                        SettingsItem(
                            title = stringResource(id = R.string.german_translator),
                            type = SettingsItemType.None,
                            onClick = {}
                        )
                    }

                    SettingsSection(title = stringResource(id = R.string.spanish), painter = painterResource(id = R.drawable.ic_spain)) {
                        SettingsItem(
                            title = stringResource(id = R.string.spanish_translator),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SpanishTranslatorWebsite))
                                startActivity(intent)
                            }
                        )
                    }

                    SettingsSection(title = stringResource(id = R.string.french), painter = painterResource(id = R.drawable.ic_france)) {
                        SettingsItem(
                            title = stringResource(id = R.string.french_translator),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FrenchTranslatorWebsite))
                                startActivity(intent)
                            }
                        )

                        SettingsItem(
                            title = stringResource(id = R.string.french_translator2),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FrenchTranslator2Website))
                                startActivity(intent)
                            }
                        )
                    }

                    SettingsSection(title = stringResource(id = R.string.italian), painter = painterResource(id = R.drawable.ic_italy)) {
                        SettingsItem(
                            title = stringResource(id = R.string.italian_translator),
                            type = SettingsItemType.None,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ItalianTranslatorWebsite))
                                startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}