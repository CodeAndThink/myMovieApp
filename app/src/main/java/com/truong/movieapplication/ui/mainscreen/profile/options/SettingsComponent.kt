package com.truong.movieapplication.ui.mainscreen.profile.options

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.truong.movieapplication.R

class SettingsComponent : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}