package com.example.hci.injector.TestInjector

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
import dagger.BindsInstance
import javax.inject.Inject
import javax.inject.Named


//
//@Module
//class ApplicationModule(private val application: Application) {
//    @Singleton
//    @Provides
//    fun provideContext(): Context {
//        return application
//    }
//}

@Component(modules = [TestApiCallerModule::class,TestUseCaseProvider::class,TestViewModelProvider::class])
interface TestApplicationComponent {
    fun getDataUseCase(): GetDataUseCase
    fun getMainViewModel(): MainViewModel
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun mockConfigurator(mockConfigurator: MockConfigurator): Builder
        fun build(): TestApplicationComponent
    }
}


@Module
class TestApiCallerModule() {

    @Provides
    fun provideOkHttpClient():OkHttpClient{
        val builder = OkHttpClient.Builder()
            .readTimeout(60000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, mockConfigurator: MockConfigurator):Retrofit{
        return Retrofit.Builder()
            .baseUrl(mockConfigurator.url)
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
class TestUseCaseProvider{
    @Provides
    fun provideGetDataUseCase(apiService: ApiService):GetDataUseCase{
        return GetDataUseCase(apiService)
    }
}

@Module
class TestViewModelProvider{
    @Provides
        fun provideMainViewModel(getDataUseCase: GetDataUseCase):MainViewModel{
        return MainViewModel(getDataUseCase)
    }
}