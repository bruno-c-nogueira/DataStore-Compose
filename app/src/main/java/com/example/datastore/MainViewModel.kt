package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.data.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class MainViewModel : ViewModel() {
    companion object {
        private val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
        private val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD")
    }

    fun incrementCounter(context: Context, userDTO: UserDTO) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[USER_FIRST_NAME] = userDTO.userName.orEmpty()
                preferences[USER_PASSWORD] = userDTO.password.orEmpty()
            }
        }
    }

    fun getUserFromPreferencesStore(context: Context): Flow<UserDTO> =
        context.dataStore.data.map { preferences ->
            UserDTO(userName = preferences[USER_FIRST_NAME], password = preferences[USER_PASSWORD])
        }
}