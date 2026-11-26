package com.example.perte11.repositori

import android.app.Application
import com.example.perte11.apiservice.ServiceApiSiswa
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ContainerApp {
    val repositoryDataSiswa: RepositoryDataSiswa
}

class DefaultContainerApp : ContainerApp {
    // 10.0.2.2 = localhost komputer jika pakai emulator.
    // Folder di htdocs: tiumy
    private val baseUrl = "http://10.0.2.2/tiumy/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        JaringanRepositoryDataSiswa(retrofitService)
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainerApp()
    }
}