package com.example.hci.injector

import com.example.hci.domain.GetDataUseCase
import com.example.hci.interactor.service.ApiService
import com.example.hci.view.viewmodel.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//
//@Module
//class ApplicationModule(private val application: Application) {
//    @Singleton
//    @Provides
//    fun provideContext(): Context {
//        return application
//    }
//}

@Component(modules = [ApiCallerModule::class,UseCaseProvider::class,ViewModelProvider::class])
interface ApplicationComponent {
    fun getDataUseCase(): GetDataUseCase
    fun getMainViewModel(): MainViewModel
}


@Module
class ApiCallerModule {
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        val builder = OkHttpClient.Builder()
            .readTimeout(60000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://private-a8e48-hcidtest.apiary-mock.com")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}

@Module
class UseCaseProvider{
    @Provides
    fun provideGetDataUseCase(apiService: ApiService):GetDataUseCase{
        return GetDataUseCase(apiService)
    }
}

@Module
class ViewModelProvider{
    @Provides
        fun provideMainViewModel(getDataUseCase: GetDataUseCase):MainViewModel{
        return MainViewModel(getDataUseCase)
    }
}