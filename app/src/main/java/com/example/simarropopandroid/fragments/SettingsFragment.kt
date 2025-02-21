package com.example.simarropopandroid.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.example.simarropopandroid.R
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    // 💥 🔄 Ajuste de la firma del método (p1: String?)
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when (key) {
            // 🌐 Cambio de idioma
            "language" -> {
                val lang = sharedPreferences.getString(key, "es") ?: "es"
                val locale = Locale(lang)
                Locale.setDefault(locale)
                val config = requireContext().resources.configuration
                config.setLocale(locale)
                requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
                activity?.recreate() // 🔄 Reinicia para aplicar el idioma
            }

            // 🌙 Cambio de modo oscuro
            "dark_mode" -> {
                val nightMode = sharedPreferences.getBoolean(key, false)
                val mode = if (nightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}
