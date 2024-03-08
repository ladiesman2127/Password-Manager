package jp.aqua.passwordmanager.website

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import jp.aqua.passwordmanager.Application
import jp.aqua.passwordmanager.website.presentation.WebsiteViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WebsiteViewModel(
                application().websiteRepository
            )
        }
    }

}


fun CreationExtras.application(): Application =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)