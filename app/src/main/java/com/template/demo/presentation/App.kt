package com.template.demo.presentation

import android.app.Application
import com.template.appsettings.di.AppSettingsDI
import com.template.demo.cache.database.DatabaseRepository
import com.template.demo.cache.database.DatabaseRepositoryImpl
import com.template.demo.cache.preference.PreferenceRepository
import com.template.demo.cache.preference.PreferenceRepositoryImpl
import com.template.basecomponents.converters.FbConverter
import com.template.demo.data.converter.FbUserConvert
import com.template.demo.data.repository.firebase.UserFirebaseRepositoryImpl
import com.template.demo.data.repository.firebase.data.FbUserData
import com.template.demo.domain.data.UserModel
import com.template.demo.domain.interactor.user.UserInteractor
import com.template.demo.domain.interactor.user.UserInteractorImpl
import com.template.demo.domain.repository.UserFirebaseRepository
import com.template.demo.presentation.fragment.home.MainViewModel
import com.template.demo.presentation.fragment.login.LoginViewModel
import com.template.demo.presentation.fragment.settings.SettingsViewModel
import com.template.demo.presentation.fragment.signup.SignupViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            // Log Koin into Android logger
            androidLogger()

            // Reference Android context
            androidContext(this@App)

            // Load modules
            modules(listOf(applyRules(), AppSettingsDI.applyRules()))
        }
    }

    private fun applyRules() = module {
        singleOf(::PreferenceRepositoryImpl) {
            named("PreferenceRepositoryImpl")
            bind<PreferenceRepository>()
            createdAtStart()
        }
        singleOf(::DatabaseRepositoryImpl) {
            named("DatabaseRepositoryImpl")
            bind<DatabaseRepository>()
            createdAtStart()
        }

        factoryOf(::UserInteractorImpl) {
            named("UserInteractorImpl")
            bind<UserInteractor>()
            createdAtStart()
        }
        factoryOf(::FbUserConvert) {
            named("FbUserConvert")
            bind<FbConverter<FbUserData, UserModel>>()
            createdAtStart()
        }
        factoryOf(::UserFirebaseRepositoryImpl) {
            named("UserFirebaseRepository")
            bind<UserFirebaseRepository>()
            createdAtStart()
        }


        viewModelOf(::SignupViewModel)
        viewModelOf(::SettingsViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::MainViewModel)
    }
}