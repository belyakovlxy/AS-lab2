package com.example.inventory.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetails
import com.example.inventory.ui.item.ItemEntryBody
import com.example.inventory.ui.item.ItemUiState
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme


object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    onNavigateUp: (Int) -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val settingsUiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.initUiState()
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                scrollBehavior = scrollBehavior,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    )
    { innerPadding ->
        SettingsBody(
            settingsUiState = settingsUiState,
            viewModel = viewModel,
            navigateBack = navigateBack,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun SettingsBody(
    settingsUiState: SettingsUiState,
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        SettingsDefaultForm(
            settingsUiState = settingsUiState,
            viewModel = viewModel,
            navigateBack = navigateBack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
private fun SettingsDefaultForm(
    settingsUiState: SettingsUiState,
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier,
    enabled: Boolean = true
)
{
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        OutlinedTextField(
            value = settingsUiState.supplierName,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text(stringResource(R.string.supplier_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = settingsUiState.phoneNumber,
            onValueChange = { viewModel.onPhoneChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.phone_number_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = settingsUiState.supplierEmail,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text(stringResource(R.string.supplier_email_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(
                checked = settingsUiState.enableDefaultSettings,
                onCheckedChange = { viewModel.onEnableDefaultSettingsChage(it) },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = stringResource(R.string.enable_default_settings))
        }

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Checkbox(
                checked = settingsUiState.hideSensitiveData,
                onCheckedChange = { viewModel.onHideSensitiveDataChange(it) },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = stringResource(R.string.hide_sensitive_data))
        }

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(
                checked = settingsUiState.disableSharing,
                onCheckedChange = { viewModel.onDisableSharingChange(it) },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = stringResource(R.string.disable_direct_sharing))
        }

        Button(
            onClick = {
                viewModel.save()
                navigateBack()
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save_action))
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun ItemEntryScreenPreview() {
//    InventoryTheme {
//        SettingsBody(defaultSettings = DefaultSettings(supplierEmail = "ek", supplierName = "FDS", phoneNumber = "123")
//        )
//    }
//}
