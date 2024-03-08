package jp.aqua.passwordmanager.website.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import jp.aqua.passwordmanager.Application
import jp.aqua.passwordmanager.crypto.MasterPassword
import jp.aqua.passwordmanager.website.data.WebsiteRepository
import jp.aqua.passwordmanager.website.model.Website
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WebsiteViewModel(private val repository: WebsiteRepository) : ViewModel() {
    private val _currentWebsite = MutableStateFlow<Website?>(null)
    val currentWebsite: StateFlow<Website?> = _currentWebsite
    private val _masterPassword = MutableStateFlow<String?>(null)
    val masterPassword: StateFlow<String?> = _masterPassword

    init {
        viewModelScope.launch {
            _masterPassword.value = repository.getMasterPassword()
        }
    }

    suspend fun setMasterPassword(password: String) {
        repository.setMasterPassword(password)
        _masterPassword.value = repository.getMasterPassword()
    }

    fun setCurrentWebsite(website: Website) {
        _currentWebsite.value = website
    }

    fun resetCurrentWebsite() {
        _currentWebsite.value = null
    }


    fun getWebsites() = repository.getAllWebsites()

    suspend fun updateWebsite(website: Website, url: String, password: String) {
        repository.deleteWebsite(website)
        repository.addWebsite(url, password)
    }

    suspend fun addWebsite(context: Context, url: String, password: String) {
        repository.addWebsite(url, password)
            .onSuccess { Toast.makeText(context, "Successfully added", Toast.LENGTH_LONG).show() }
            .onFailure { Toast.makeText(context, "Check the url", Toast.LENGTH_LONG).show() }
    }
}

