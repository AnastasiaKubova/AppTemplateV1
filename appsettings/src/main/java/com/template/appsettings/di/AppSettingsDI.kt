package com.template.appsettings.di

import com.template.appsettings.AppSettingsHandler
import com.template.appsettings.AppSettingsHandlerImpl
import com.template.appsettings.preference.SettingsPreferenceRepository
import com.template.appsettings.preference.SettingsPreferenceRepositoryImpl
import com.template.appsettings.repository.SettingsRepository
import com.template.appsettings.repository.SettingsRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.bind
import org.koin.core.qualifier.named
import org.koin.core.module.dsl.createdAtStart
import org.koin.dsl.module

object AppSettingsDI {

    fun applyRules() = module {
        factoryOf(::SettingsPreferenceRepositoryImpl) {
            named("SettingsPreferenceRepositoryImpl")
            bind<SettingsPreferenceRepository>()
            createdAtStart()
        }
        factoryOf(::AppSettingsHandlerImpl) {
            named("AppSettingsHandlerImpl")
            bind<AppSettingsHandler>()
            createdAtStart()
        }
        factoryOf(::SettingsRepositoryImpl) {
            named("SettingsRepositoryImpl")
            bind<SettingsRepository>()
            createdAtStart()
        }
    }
}