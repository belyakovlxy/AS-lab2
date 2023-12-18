package com.example.inventory.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel() : ViewModel()
{
    fun initUiState() {
        settingsUiState.value = SettingsUiState(
            supplierName = Settings.defaultSupplierName,
            phoneNumber = Settings.defaultPhoneNumber,
            supplierEmail = Settings.defaultSupplierEmail,
            enableDefaultSettings = Settings.enableDefaultSettings,
            hideSensitiveData = Settings.hideSensitiveData,
            disableSharing = Settings.disableSharing,
        )
    }

    private val settingsUiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = settingsUiState.asStateFlow()

    fun onNameChange(value: String) {
        settingsUiState.value = settingsUiState.value.copy(supplierName = value)
    }

    fun onPhoneChange(value: String) {
        settingsUiState.value = settingsUiState.value.copy(phoneNumber = value)
    }

    fun onEmailChange(value: String) {
        settingsUiState.value = settingsUiState.value.copy(supplierEmail = value)
    }

    fun onEnableDefaultSettingsChage(value: Boolean) {
        settingsUiState.value = settingsUiState.value.copy(enableDefaultSettings = value)
    }

    fun onHideSensitiveDataChange(value: Boolean) {
        settingsUiState.value = settingsUiState.value.copy(hideSensitiveData = value)
    }

    fun onDisableSharingChange(value: Boolean) {
        settingsUiState.value = settingsUiState.value.copy(disableSharing = value)
    }

    fun save() {
        Settings.defaultSupplierName = uiState.value.supplierName
        Settings.defaultPhoneNumber = uiState.value.phoneNumber
        Settings.defaultSupplierEmail = uiState.value.supplierEmail
        Settings.enableDefaultSettings = uiState.value.enableDefaultSettings
        Settings.hideSensitiveData = uiState.value.hideSensitiveData
        Settings.disableSharing = uiState.value.disableSharing
    }
}

data class SettingsUiState(
    val supplierName: String = "",
    val phoneNumber: String = "",
    val supplierEmail: String = "",
    val enableDefaultSettings: Boolean = false,
    val hideSensitiveData: Boolean = false,
    val disableSharing: Boolean = false,
)