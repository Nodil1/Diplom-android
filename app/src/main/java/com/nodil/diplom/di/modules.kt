package com.nodil.diplom.di

import com.nodil.diplom.data.repositories.*
import com.nodil.diplom.domain.repositories.*
import com.nodil.diplom.domain.usecase.action.GetMyWorkerStatusUseCase
import com.nodil.diplom.domain.usecase.action.SaveWorkerActionUseCase
import com.nodil.diplom.domain.usecase.attachment.SaveImageAttachmentUseCase
import com.nodil.diplom.domain.usecase.attachment.SaveSignUseCase
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
import com.nodil.diplom.ui.task.TaskAttachmentViewModel
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
    factory { SaveImageAttachmentUseCase(get()) }
    factory { SaveSignUseCase(get()) }

}

// Data layer
val dataModule = module {
    single<AuthRepository> { AuthRepositoryApi() }
    single<GeoRepository> { GeoRepositoryApi(get()) }
    single<TaskRepository> { TaskRepositoryApi() }
    single<TaskAttachmentRepository> { TaskAttachmentRepositoryApi() }
    single<WorkerActionRepository> {WorkerActionRepositoryApi()}
    single { SharedPreferencesStorage(androidContext()) }
    single { LocalGeoRepository(get()) }

}

// Presentation layer
val presentationModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { StatusViewModel() }
    viewModel { TaskViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { TaskAttachmentViewModel(get(), get()) }

}