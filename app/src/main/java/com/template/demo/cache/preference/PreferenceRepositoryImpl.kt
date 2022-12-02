package com.template.demo.cache.preference

import com.template.appsettings.data.ThemeType

class PreferenceRepositoryImpl: PreferenceRepository {

    override fun getTheme(): com.template.appsettings.data.ThemeType {
        return com.template.appsettings.data.ThemeType.LIGHT
    }

    override fun isAuthorized(): Boolean {
        return false
    }

    override fun saveUserData(email: String, password: String) {

    }

    override fun getUserData(): Pair<String, String> {
        return "" to ""
    }

    override fun clearUserData() {

    }
}