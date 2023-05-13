package com.nodil.diplom.di

import com.nodil.diplom.data.repositories.*
import com.nodil.diplom.domain.repositories.AuthRepository
import com.nodil.diplom.domain.repositories.GeoRepository
import com.nodil.diplom.domain.repositories.TaskRepository
import com.nodil.diplom.domain.repositories.WorkerActionRepository
import com.nodil.diplom.domain.usecase.action.GetMyWorkerStatusUseCase
import com.nodil.diplom.domain.usecase.action.SaveWorkerActionUseCase
import com.nodil.diplom.domain.usecase.auth.GetMyIdUseCase
import com.nodil.diplom.domain.usecase.auth.IsUserAuthUseCase
import com.nodil.diplom.domain.usecase.auth.LoginUseCase
import com.nodil.diplom.domain.usecase.geo.SaveGeoUseCase
import com.nodil.diplom.domain.usecase.task.GetMyTasksUseCase
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.home.status.StatusViewModel
import com.nodil.diplom.ui.home.task.TaskViewModel
import com.nodil.diplom.ui.login.LoginViewModel
import com.nodil.diplom.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get(), get()) }
    factory { IsUserAuthUseCase(get()) }
    factory { SaveGeoUseCase(get()) }
    factory { GetMyTasksUseCase(get()) }
    factory { SaveWorkerActionUseCase(get()) }
    factory { GetMyIdUseCase(get()) }
    factory { GetMyWorkerStatusUseCase(get()) }

}

// Data layer
val dataModule = module {
    single<AuthRepository> { AuthRepositoryApi() }
    single<GeoRepository> { GeoRepositoryApi() }
    single<TaskRepository> { TaskRepositoryApi() }
    single<WorkerActionRepository> {WorkerActionRepositoryApi()}
    single { SharedPreferencesStorage(androidContext()) }

}

// Presentation layer
val presentationModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { StatusViewModel() }
    viewModel { TaskViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }

}