package jp.aqua.passwordmanager.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.aqua.passwordmanager.auth.AuthDialog
import jp.aqua.passwordmanager.home.presentation.HomeScreen
import jp.aqua.passwordmanager.website.AppViewModelProvider
import jp.aqua.passwordmanager.website.presentation.WebsiteScreen
import jp.aqua.passwordmanager.website.presentation.WebsiteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class Screens {
    HomeScreen,
    WebsiteScreen
}

@Composable
fun RootNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: WebsiteViewModel = viewModel(factory = AppViewModelProvider.Factory),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val context = LocalContext.current
    val websites = viewModel.getWebsites().collectAsState(emptyList()).value
    val currentWebsite = viewModel.currentWebsite.collectAsState(null).value
    val masterPassword = viewModel.masterPassword.collectAsState().value


    AuthDialog(
        masterPassword = masterPassword,
        coroutineScope = coroutineScope,
        setMasterPassword = viewModel::setMasterPassword,
    )


    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name
    ) {
        composable(route = Screens.HomeScreen.name) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                websites = websites,
                setCurrentWebsite = viewModel::setCurrentWebsite,
                onWebsiteOpen = { navController.navigate(Screens.WebsiteScreen.name) },
            )
        }
        composable(route = Screens.WebsiteScreen.name) {
            WebsiteScreen(
                modifier = Modifier.fillMaxSize(),
                website = currentWebsite,
                onCreateWebsite = { url, password ->
                    coroutineScope.launch {
                        viewModel.addWebsite(context, url, password)
                    }
                    viewModel.resetCurrentWebsite()
                    navController.navigateUp()
                },
                onUpdateWebsite = { website, url, password ->
                    coroutineScope.launch {
                        viewModel.updateWebsite(website, url, password)
                    }
                    viewModel.resetCurrentWebsite()
                    navController.navigateUp()
                },
                onCancel = {
                    viewModel.resetCurrentWebsite()
                    navController.navigateUp()
                }
            )
        }
    }
}

