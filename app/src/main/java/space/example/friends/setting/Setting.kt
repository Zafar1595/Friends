package space.example.friends.setting

import android.content.Context
import android.content.SharedPreferences

class Setting(context: Context) {

    companion object {
        const val IS_APP_FIRST_LAUNCHED = "isAppFirstLaunched"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences("WareHouseSharedPreferences", Context.MODE_PRIVATE)

    fun setFirstLaunched() {
        preferences.edit().putBoolean(IS_APP_FIRST_LAUNCHED, false).apply()
    }

    fun isAppFirstLaunched(): Boolean =
        preferences.getBoolean(IS_APP_FIRST_LAUNCHED, true)

}