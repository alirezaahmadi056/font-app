package info.ahmadi.fontwriter.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.model.AssetsApiResponse
import info.ahmadi.fontwriter.model.FontApiResponse
import info.ahmadi.fontwriter.model.FontApiResponseData
import info.ahmadi.fontwriter.model.ModelAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun getApi(): ApiInterface {
        return Retrofit.Builder().baseUrl("https://ahmadi-font.ahmadi-test-app.ir")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
    }



}