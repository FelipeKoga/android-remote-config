package com.koga.pocremoteconfig

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _homeConfigConfig = MutableStateFlow<HomeConfig?>(null)
    val homeConfig = _homeConfigConfig.filterNotNull()


    fun retrieveConfigData(context: Context) {
        FirebaseApp.initializeApp(context)

        val config = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }

        config.setConfigSettingsAsync(configSettings)

        config.fetchAndActivate().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                // TODO: implement callback

                return@addOnCompleteListener
            }

            val homeConfig = Gson().fromJson(
                config.getString(RemoteConfigConstants.HOME_CONFIG), HomeConfig::class.java
            )

            _homeConfigConfig.update { homeConfig }
        }
    }

}