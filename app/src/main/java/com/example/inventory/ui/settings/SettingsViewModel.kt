package com.example.inventory.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class DefaultSettings(
    val supplierName: String = "",
    val phoneNumber: String = "",
    val supplierEmail: String = ""
)

class SettingsViewModel() : ViewModel()
{
    var defaultSettings by mutableStateOf(DefaultSettings())
        private set

    fun updateDefaultSettings(defaultSettings: DefaultSettings) {
        this.defaultSettings = defaultSettings
    }
}