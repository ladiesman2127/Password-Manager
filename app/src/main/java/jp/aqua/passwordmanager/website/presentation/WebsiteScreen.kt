package jp.aqua.passwordmanager.website.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jp.aqua.passwordmanager.R
import jp.aqua.passwordmanager.crypto.Сryptographer
import jp.aqua.passwordmanager.website.model.Website


@Composable
fun WebsiteScreen(
    modifier: Modifier = Modifier,
    website: Website?,
    onCreateWebsite: (url: String, password: String) -> Unit,
    onUpdateWebsite: (website: Website, url: String, password: String) -> Unit,
    onCancel: () -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            var url by rememberSaveable { mutableStateOf(website?.url ?: "https://") }
            var password by rememberSaveable { mutableStateOf(Сryptographer.decrypt(website?.password)) }
            if (website == null)
                Text("Create")
            else
                Text("Update")
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("URL")
                TextField(
                    value = url,
                    onValueChange = { url = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Password")
                TextField(value = password, onValueChange = { password = it })
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onCancel) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        if (website == null)
                            onCreateWebsite(url, password)
                        else
                            onUpdateWebsite(website, url, password)
                    }
                ) {
                    if (website == null)
                        Text("Create")
                    else
                        Text("Update")
                }
            }
        }
    }
}
