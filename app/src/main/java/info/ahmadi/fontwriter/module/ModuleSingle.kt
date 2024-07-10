package info.ahmadi.fontwriter.module

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.ahmadi.fontwriter.api.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ModuleSingle {
    @Singleton
    @Provides
    fun getApi(): ApiInterface {
        return Retrofit.Builder().baseUrl("https://ahmadi-font.ahmadi-test-app.ir")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
    }

}