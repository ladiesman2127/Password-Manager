package jp.aqua.passwordmanager.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.aqua.passwordmanager.R
import jp.aqua.passwordmanager.website.model.Website

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    websites: List<Website>,
    onWebsiteOpen: () -> Unit,
    setCurrentWebsite: (Website) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Password") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onWebsiteOpen) {
                Icon(Icons.Outlined.Add, stringResource(R.string.add_website))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            itemsIndexed(websites) { _, website ->
                ListItem(
                    leadingContent = {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f),
                            model = website.favicon,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "${website.url}'s favicon"
                        )
                    },
                    headlineContent = { Text(website.url) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable {
                            setCurrentWebsite(website)
                            onWebsiteOpen()
                        }
                )

            }
        }

    }
}